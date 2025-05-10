package com.recMall.mall.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

/**
 * @author zedthm
 * @version 1.0
 * @date 2025/5/4 17:28
 * @description:
 */
public class MallRecBooksUserDto {
    private List<UserDto> userList;

    public List<UserDto> getUserList() {
        return userList;
    }

    public void setUserList(List<UserDto> userList) {
        this.userList = userList;
    }

    static public class UserDto extends MallUserProfiles {
        private String bookId;
        private Integer isPurchased;
        private Integer isCollect;
        private Integer isCart;
        private Integer isComments;
        // 新增排序专用字段
        @JsonIgnore  // 不参与序列化
        private transient Long sortUserId;

        @JsonIgnore  // 不参与序列化
        private transient Long sortBookId;

        // Getter/Setter
        public Long getSortUserId() {
            return Long.parseLong(this.getUserId());
        }

        public Long getSortBookId() {
            return Long.parseLong(this.bookId);
        }

        public void setSortUserId(Long sortUserId) {
            this.sortUserId = sortUserId;
        }

        public void setSortBookId(Long sortBookId) {
            this.sortBookId = sortBookId;
        }

        public Integer getIsPurchased() {
            return isPurchased;
        }

        public void setIsPurchased(Integer isPurchased) {
            this.isPurchased = isPurchased;
        }

        public Integer getIsCollect() {
            return isCollect;
        }

        public void setIsCollect(Integer isCollect) {
            this.isCollect = isCollect;
        }

        public Integer getIsCart() {
            return isCart;
        }

        public void setIsCart(Integer isCart) {
            this.isCart = isCart;
        }

        public Integer getIsComments() {
            return isComments;
        }

        public void setIsComments(Integer isComments) {
            this.isComments = isComments;
        }

        public String getBookId() {
            return bookId;
        }

        public void setBookId(String bookId) {
            this.bookId = bookId;
        }
    }
}
