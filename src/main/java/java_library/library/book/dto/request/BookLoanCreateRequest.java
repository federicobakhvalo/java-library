package java_library.library.book.dto.request;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.time.LocalDate;

public class BookLoanCreateRequest {

    @NotNull(message = "Ticket ID cannot be null")
    @Positive(message = "Ticket ID must be a positive number")
    private Long ticketId;
    @NotNull(message = "Book ID cannot be null")
    @Positive(message = "Book ID must be a positive number")
    private Long bookId;
    @NotNull(message = "Due date cannot be null")
    @FutureOrPresent(message = "Due date must be a future or present date")
    private LocalDate dueDate;

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

    public LocalDate getDueDate() {
        return dueDate;
    }

    public void setDueDate(LocalDate dueDate) {
        this.dueDate = dueDate;
    }
}