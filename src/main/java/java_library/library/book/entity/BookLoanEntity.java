package java_library.library.book.entity;

import jakarta.persistence.*;
import java_library.library.common.enums.BookLoanStatus;
import java_library.library.user.entity.ReaderTicketEntity;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "book_loan")
public class BookLoanEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ticket_id", nullable = false)
    private ReaderTicketEntity ticket;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "book_id", nullable = false)
    private BookEntity book;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookLoanStatus status = BookLoanStatus.ACTIVE;
    private LocalDate returnedAt;
    @Column(nullable = false)
    private LocalDate dueDate;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

//    create getters and setters for all fields

    public Long getId() {
        return id;
    }

    public ReaderTicketEntity getTicket() {
        return ticket;
    }

    public void setTicket(ReaderTicketEntity ticket) {
        this.ticket = ticket;
    }

    public BookEntity getBook() {
        return book;
    }

    public void setBook(BookEntity book) {
        this.book = book;
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
}