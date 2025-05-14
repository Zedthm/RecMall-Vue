package com.recMall.mall.domain;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author zedthm
 * @version 1.0
 * @date 2025/5/4 17:49
 * @description:
 */

public class Recommendation {
    private String userId;
    private List<BookRec> books;

    public Recommendation(String userId, List<BookRec> books) {
        this.userId = userId;
        this.books = books != null ? books : new ArrayList<>();
    }

    static public class BookRec {
        private String bookId;
        private Double score;
        private LocalDateTime time;

        public BookRec() {
        }

        public BookRec(String bookId, Double aDouble, LocalDateTime now) {
            this.bookId = bookId;
            this.score = aDouble;
            this.time = now;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }

        public Double getScore() {
            return score;
        }

        public void setScore(Double score) {
            this.score = score;
        }

        public LocalDateTime getTime() {
            return time;
        }

        public void setTime(LocalDateTime time) {
            this.time = time;
        }
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public List<BookRec> getBooks() {
        return books;
    }

    public void setBooks(List<BookRec> books) {
        this.books = books;
    }

    @Override
    public String toString() {
        return "Recommendation{" +
                "userId='" + userId + '\'' +
                ", books=" + books +
                '}';
    }
}
