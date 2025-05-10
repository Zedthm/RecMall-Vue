package com.recMall.framework.config;

import org.apache.http.client.config.RequestConfig;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * TensorFlow Serving 服务调用配置类
 *
 * <p>本配置类用于创建高性能的RestTemplate实例，专门优化与TensorFlow Serving模型的交互，
 * 包含连接池管理、超时控制等生产级配置</p>
 *
 * @author zedthm
 * @version 1.1
 * @date 2025/5/4 17:16
 * @since 2025.1.0
 */
@Configuration
public class TFConfig {

    /**
     * 创建定制化的RestTemplate Bean
     *
     * <p>该Bean专门用于TensorFlow Serving服务调用，包含以下特性配置：
     * <ol>
     *   <li>连接池管理：控制最大连接数，避免资源耗尽</li>
     *   <li>超时设置：防止长时间阻塞的请求影响系统稳定性</li>
     *   <li>HTTP客户端优化：使用高性能的Apache HttpClient实现</li>
     * </ol>
     *
     * @param timeout 单位毫秒，配置连接建立超时和socket读写超时时间，
     *                建议设置为服务P99响应时间的2倍
     * @param maxConnections 最大连接池数量，根据服务实例数量和硬件资源配置，
     *                       建议设置为 (核心数 * 2) / 服务实例数
     * @return 定制化的RestTemplate实例
     *
     * @see <a href="https://www.tensorflow.org/tfx/serving/api_rest">
     *      TensorFlow Serving REST API</a>
     */
    @Bean
    public RestTemplate tfRestTemplate(
            @Value("${tensorflow.serving.timeout}") int timeout,
            @Value("${tensorflow.serving.max-connections}") int maxConnections) {

        // 创建连接池管理器（建议保持单例）
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager();
        // 设置整个连接池最大连接数
        connectionManager.setMaxTotal(maxConnections);
        // 设置单个路由默认最大连接数（根据服务实例数量调整）
        connectionManager.setDefaultMaxPerRoute(maxConnections);

        // 配置请求参数（单位：毫秒）
        RequestConfig config = RequestConfig.custom()
                .setConnectTimeout(timeout)    // 建立TCP连接的超时时间
                .setSocketTimeout(timeout)     // 两个数据包之间的最大间隔时间
                .build();

        // 创建定制化HttpClient
        CloseableHttpClient httpClient = HttpClients.custom()
                .setConnectionManager(connectionManager)  // 注入连接池
                .setDefaultRequestConfig(config)          // 注入请求配置
                .build();

        // 使用HttpComponents客户端工厂（替代默认的SimpleClientHttpRequestFactory）
        HttpComponentsClientHttpRequestFactory factory =
                new HttpComponentsClientHttpRequestFactory(httpClient);

        return new RestTemplate(factory);
    }
}