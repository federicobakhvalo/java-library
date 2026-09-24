
package java_library.library.book.service;

import jakarta.transaction.Transactional;
import java_library.library.book.entity.BookEntity;
import java_library.library.book.entity.BookLoanEntity;
import java_library.library.book.repository.BookLoanRepository;
import java_library.library.common.enums.BookLoanStatus;
import java_library.library.user.entity.ReaderTicketEntity;
import java_library.library.user.service.ReaderTicketService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class BookLoanService {
    private final BookLoanRepository bookLoanRepository;
    private final ReaderTicketService readerTicketService;
    private final BookService bookService;

    public BookLoanService(BookLoanRepository bookLoanRepository, ReaderTicketService readerTicketService, BookService bookService) {
        this.bookLoanRepository = bookLoanRepository;
        this.readerTicketService = readerTicketService;
        this.bookService = bookService;
    }

    public List<BookLoanEntity> findAll() {
        return bookLoanRepository.findAll();
    }

    public BookLoanEntity findById(Long id) {
        return bookLoanRepository.findById(id).orElseThrow(() -> new RuntimeException("Book loan not found: " + id));
    }

    public List<BookLoanEntity> findByTicketId(Long ticketId) {
        return bookLoanRepository.findByTicketId(ticketId);
    }

    public List<BookLoanEntity> findByBookId(Long bookId) {
        return bookLoanRepository.findByBookId(bookId);
    }

    public List<BookLoanEntity> findActiveByTicketId(Long ticketId) {
        return bookLoanRepository.findByTicketIdAndStatus(ticketId, BookLoanStatus.ACTIVE);
    }

    public List<BookLoanEntity> findActiveByBookId(Long bookId) {
        return bookLoanRepository.findByBookIdAndStatus(bookId, BookLoanStatus.ACTIVE);
    }

    public List<BookLoanEntity> findByUserId(Long userId) {
        return bookLoanRepository.findByTicketUserId(userId);
    }

    public List<BookLoanEntity> findActiveByUserId(Long userId) {
        return bookLoanRepository.findByTicketUserIdAndStatus(userId, BookLoanStatus.ACTIVE);
    }

    @Transactional
    public BookLoanEntity createLoanForUser(Long userId, Long bookId, LocalDate dueDate) {
        ReaderTicketEntity ticket = readerTicketService.findByUserId(userId);
        if (!Boolean.TRUE.equals(ticket.getIsActive())) {
            throw new RuntimeException("Reader ticket is not active");
        }
        return createLoanInternal(ticket, bookId, dueDate);
    }

    @Transactional
    public BookLoanEntity createLoan(Long ticketId, Long bookId, LocalDate dueDate) {
        ReaderTicketEntity ticket = readerTicketService.findById(ticketId);
        if (!Boolean.TRUE.equals(ticket.getIsActive())) {
            throw new RuntimeException("Reader ticket is not active");
        }
        return createLoanInternal(ticket, bookId, dueDate);
    }

    private BookLoanEntity createLoanInternal(ReaderTicketEntity ticket, Long bookId, LocalDate dueDate) {
        BookEntity book = bookService.findById(bookId);
        validateDueDate(dueDate);
        checkUserAlreadyHasBook(ticket, book);
        checkBookAvailability(book);
        return saveLoan(ticket, book, dueDate);
    }


    @Transactional
    public BookLoanEntity returnBook(Long loanId) {
        BookLoanEntity loan = findById(loanId);
        validateReturnableLoan(loan);
        loan.setReturnedAt(LocalDate.now());
        loan.setStatus(BookLoanStatus.RETURNED);
        return bookLoanRepository.save(loan);
    }

    @Transactional
    public BookLoanEntity returnBook(Long loanId, Long userId) {
        BookLoanEntity loan = findById(loanId);
        validateReturnableLoan(loan);
        Long loanUserId = loan.getTicket().getUser().getId();
        if (!loanUserId.equals(userId)) {
            throw new RuntimeException("You cannot return another user's book");
        }
        loan.setReturnedAt(LocalDate.now());
        loan.setStatus(BookLoanStatus.RETURNED);
        return bookLoanRepository.save(loan);
    }


    private void validateDueDate(LocalDate dueDate) {
        if (dueDate == null) {
            throw new RuntimeException("Due date cannot be null");
        }
        if (dueDate.isBefore(LocalDate.now())) {
            throw new RuntimeException("Due date cannot be in the past");
        }
    }

    private void checkUserAlreadyHasBook(ReaderTicketEntity ticket, BookEntity book) {
        boolean hasActiveLoan = bookLoanRepository.existsByTicketIdAndBookIdAndStatus(ticket.getId(), book.getId(), BookLoanStatus.ACTIVE);
        if (hasActiveLoan) {
            throw new RuntimeException("User already has this book");
        }
        boolean hasOverdueLoan = bookLoanRepository.existsByTicketIdAndBookIdAndStatus(ticket.getId(), book.getId(), BookLoanStatus.OVERDUE);
        if (hasOverdueLoan) {
            throw new RuntimeException("User already has this book and it is overdue");
        }
    }

    private void checkBookAvailability(BookEntity book) {
        long activeLoans = bookLoanRepository.findByBookIdAndStatus(book.getId(), BookLoanStatus.ACTIVE).size();
        long overdueLoans = bookLoanRepository.findByBookIdAndStatus(book.getId(), BookLoanStatus.OVERDUE).size();
        long unavailableCopies = activeLoans + overdueLoans;
        if (unavailableCopies >= book.getQuantity()) {
            throw new RuntimeException("No available copies of the book");
        }
    }

    private BookLoanEntity saveLoan(ReaderTicketEntity ticket, BookEntity book, LocalDate dueDate) {
        BookLoanEntity loan = new BookLoanEntity();
        loan.setTicket(ticket);
        loan.setBook(book);
        loan.setStatus(BookLoanStatus.ACTIVE);
        loan.setDueDate(dueDate);
        return bookLoanRepository.save(loan);
    }

    private void validateReturnableLoan(BookLoanEntity loan) {
        if (loan.getStatus() != BookLoanStatus.ACTIVE && loan.getStatus() != BookLoanStatus.OVERDUE) {
            throw new RuntimeException("Book loan is already returned: " + loan.getId());
        }
    }

}


