package com.recMall.mall.domain;


import java.time.LocalDateTime;
import java.util.List;

/**
 * @author zedthm
 * @version 1.0
 * @date 2025/5/4 17:49
 * @description:
 */

public class Recommendation {
    private String userId;
    private String bookId;
    private Double prediction;
    private LocalDateTime time;

    public Recommendation() {
    }

    public Recommendation(String userId, String bookId, Double prediction, LocalDateTime time) {
        this.userId = userId;
        this.bookId = bookId;
        this.prediction = prediction;
        this.time = time;
    }


    @Override
    public String toString() {
        return "Recommendation{" +
                "userId='" + userId + '\'' +
                ", bookId='" + bookId + '\'' +
                ", prediction=" + prediction +
                ", time=" + time +
                '}';
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public Double getPrediction() {
        return prediction;
    }

    public void setPrediction(Double prediction) {
        this.prediction = prediction;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
