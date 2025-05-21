package com.recMall.web.controller.mall;

import java.util.List;
import java.util.concurrent.TimeUnit;
import javax.servlet.http.HttpServletResponse;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.recMall.common.annotation.Log;
import com.recMall.common.core.controller.BaseController;
import com.recMall.common.core.domain.AjaxResult;
import com.recMall.common.enums.BusinessType;
import com.recMall.mall.domain.MallBooks;
import com.recMall.mall.service.IMallBooksService;
import com.recMall.common.utils.poi.ExcelUtil;
import com.recMall.common.core.page.TableDataInfo;

/**
 * 商品信息-书籍核心数据Controller
 *
 * @author recMall
 * @date 2025-05-03
 */
@RestController
@RequestMapping("/mall/books")
public class MallBooksController extends BaseController {
    @Autowired
    private IMallBooksService mallBooksService;
    @Autowired
    public RedisTemplate redisTemplate;

    /**
     * 查询商品信息-书籍核心数据列表
     */
    @PreAuthorize("@ss.hasPermi('mall:books:list')")
    @GetMapping("/list")
    public TableDataInfo list(MallBooks mallBooks) {
        startPage();
        List<MallBooks> list = mallBooksService.selectMallBooksList(mallBooks);

        for (MallBooks book : list) {
            String bookKey = "book:info:" + book.getBookId();
            MallBooks mallBook = (MallBooks) redisTemplate.opsForValue().get(bookKey);
            if (mallBook == null) {
                // 缓存未命中，查询数据库
                mallBook = book;
                if (mallBook != null) {
                    // 存入Redis，1小时过期
                    redisTemplate.opsForValue().set(bookKey, mallBook, 48, TimeUnit.HOURS);
                } else {
                    // 处理空值，防止缓存穿透
                    redisTemplate.opsForValue().set(bookKey, new MallBooks(), 1, TimeUnit.MINUTES);
                }
            }
        }
        return getDataTable(list);
    }

    /**
     * 导出商品信息-书籍核心数据列表
     */
    @PreAuthorize("@ss.hasPermi('mall:books:export')")
    @Log(title = "商品信息-书籍核心数据", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, MallBooks mallBooks) {
        List<MallBooks> list = mallBooksService.selectMallBooksList(mallBooks);
        ExcelUtil<MallBooks> util = new ExcelUtil<MallBooks>(MallBooks.class);
        util.exportExcel(response, list, "商品信息-书籍核心数据数据");
    }

    /**
     * 获取商品信息-书籍核心数据详细信息
     */
    @PreAuthorize("@ss.hasPermi('mall:books:query')")
    @GetMapping(value = "/{bookId}")
    public AjaxResult getInfo(@PathVariable("bookId") String bookId) {
        return success(mallBooksService.selectMallBooksByBookId(bookId));
    }

    /**
     * 新增商品信息-书籍核心数据
     */
    @PreAuthorize("@ss.hasPermi('mall:books:add')")
    @Log(title = "商品信息-书籍核心数据", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody MallBooks mallBooks) {
        return toAjax(mallBooksService.insertMallBooks(mallBooks));
    }

    /**
     * 修改商品信息-书籍核心数据
     */
    @PreAuthorize("@ss.hasPermi('mall:books:edit')")
    @Log(title = "商品信息-书籍核心数据", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody MallBooks mallBooks) {
        return toAjax(mallBooksService.updateMallBooks(mallBooks));
    }

    /**
     * 删除商品信息-书籍核心数据
     */
    @PreAuthorize("@ss.hasPermi('mall:books:remove')")
    @Log(title = "商品信息-书籍核心数据", businessType = BusinessType.DELETE)
    @DeleteMapping("/{bookIds}")
    public AjaxResult remove(@PathVariable String[] bookIds) {
        return toAjax(mallBooksService.deleteMallBooksByBookIds(bookIds));
    }
}
