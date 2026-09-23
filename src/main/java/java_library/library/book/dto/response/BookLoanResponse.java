package java_library.library.book.dto.response;


import java.time.LocalDate;
import java.time.LocalDateTime;

import java_library.library.common.enums.BookLoanStatus;

public class BookLoanResponse {

    private Long id;

    private Long ticketId;

    private Long bookId;

    private BookLoanStatus status;

    private LocalDate returnedAt;

    private LocalDate dueDate;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getBookId() {
        return bookId;
    }

    public void setBookId(Long bookId) {
        this.bookId = bookId;
    }

    public BookLoanStatus getStatus() {
        return status;
    }

    public void setStatus(BookLoanStatus status) {
        this.status = status;
    }

    public LocalDate getReturnedAt() {
        return returnedAt;
    }

    public void setReturnedAt(LocalDate returnedAt) {
        this.returnedAt = returnedAt;
    }

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}

