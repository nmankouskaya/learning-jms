package org.learning.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

public class BookOrder implements Serializable {
    private String bookTitle;
    private LocalDateTime orderDate;
    private String status;

    public BookOrder() {
    }

    public BookOrder(String bookTitle, LocalDateTime orderDate, String status) {
        this.bookTitle = bookTitle;
        this.orderDate = orderDate;
        this.status = status;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public LocalDateTime getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(LocalDateTime orderDate) {
        this.orderDate = orderDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
