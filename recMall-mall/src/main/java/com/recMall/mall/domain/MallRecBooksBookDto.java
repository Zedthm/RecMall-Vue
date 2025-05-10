package com.recMall.mall.domain;

import java.util.List;

/**
 * @author zedthm
 * @version 1.0
 * @date 2025/5/4 17:28
 * @description:
 */
public class MallRecBooksBookDto {
    private List<BookDto> bookList;

    public List<BookDto> getBookList() {
        return bookList;
    }

    public void setBookList(List<BookDto> bookList) {
        this.bookList = bookList;
    }

    public static  class BookDto extends MallBooks{
        private String bookTag1;
        private String bookTag2;
        private String bookTag3;
        private String bookTag4;
        private String bookTag5;

        public String getBookTag1() {
            return bookTag1;
        }

        public void setBookTag1(String bookTag1) {
            this.bookTag1 = bookTag1;
        }

        public String getBookTag2() {
            return bookTag2;
        }

        public void setBookTag2(String bookTag2) {
            this.bookTag2 = bookTag2;
        }

        public String getBookTag3() {
            return bookTag3;
        }

        public void setBookTag3(String bookTag3) {
            this.bookTag3 = bookTag3;
        }

        public String getBookTag4() {
            return bookTag4;
        }

        public void setBookTag4(String bookTag4) {
            this.bookTag4 = bookTag4;
        }

        public String getBookTag5() {
            return bookTag5;
        }

        public void setBookTag5(String bookTag5) {
            this.bookTag5 = bookTag5;
        }
    }
}
