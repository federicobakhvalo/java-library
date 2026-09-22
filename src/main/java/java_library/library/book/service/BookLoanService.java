
package java_library.library.book.service;

import java_library.library.book.entity.BookEntity;
import java_library.library.book.entity.BookLoanEntity;
import java_library.library.book.repository.BookLoanRepository;
import java_library.library.common.enums.BookLoanStatus;
import java_library.library.user.entity.ReaderTicketEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookLoanService {

    private final BookLoanRepository bookLoanRepository;

    public BookLoanService(BookLoanRepository bookLoanRepository) {
        this.bookLoanRepository = bookLoanRepository;
    }

    public List<BookLoanEntity> findAll() {
        return bookLoanRepository.findAll();
    }

    public BookLoanEntity findById(Long id) {
        return bookLoanRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException(
                                "Book loan not found: " + id
                        )
                );
    }

    public List<BookLoanEntity> findByTicketId(Long ticketId) {
        return bookLoanRepository.findByTicketId(ticketId);
    }

    public List<BookLoanEntity> findByBookId(Long bookId) {
        return bookLoanRepository.findByBookId(bookId);
    }

    public List<BookLoanEntity> findActiveByTicketId(Long ticketId) {
        return bookLoanRepository.findByTicketIdAndStatus(
                ticketId,
                BookLoanStatus.ACTIVE
        );
    }

    public List<BookLoanEntity> findActiveByBookId(Long bookId) {
        return bookLoanRepository.findByBookIdAndStatus(
                bookId,
                BookLoanStatus.ACTIVE
        );
    }

    public BookLoanEntity createLoan(
            ReaderTicketEntity ticket,
            BookEntity book,
            LocalDate dueDate
    ) {

        if (!Boolean.TRUE.equals(ticket.getIsActive())) {
            throw new RuntimeException(
                    "Reader ticket is not active"
            );
        }

        if (dueDate == null) {
            throw new RuntimeException(
                    "Due date cannot be null"
            );
        }

        if (dueDate.isBefore(LocalDate.now())) {
            throw new RuntimeException(
                    "Due date cannot be in the past"
            );
        }

        long activeLoans = bookLoanRepository
                .findByBookIdAndStatus(
                        book.getId(),
                        BookLoanStatus.ACTIVE
                )
                .size();

        if (activeLoans >= book.getQuantity()) {
            throw new RuntimeException(
                    "No available copies of the book"
            );
        }

        BookLoanEntity loan = new BookLoanEntity();

        loan.setTicket(ticket);
        loan.setBook(book);
        loan.setStatus(BookLoanStatus.ACTIVE);
        loan.setDueDate(dueDate);

        return bookLoanRepository.save(loan);
    }

    public BookLoanEntity returnBook(Long loanId) {

        BookLoanEntity loan = findById(loanId);

        if (loan.getStatus() != BookLoanStatus.ACTIVE
                && loan.getStatus() != BookLoanStatus.OVERDUE) {

            throw new RuntimeException(
                    "Book loan is already returned: " + loanId
            );
        }

        loan.setReturnedAt(LocalDate.now());
        loan.setStatus(BookLoanStatus.RETURNED);

        return bookLoanRepository.save(loan);
    }
}


