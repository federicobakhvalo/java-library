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
}