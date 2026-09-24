package java_library.library.book.controller;

import jakarta.validation.Valid;
import java_library.library.auth.dto.response.JwtUserData;
import java_library.library.book.dto.request.BookLoanCreateRequest;
import java_library.library.book.dto.response.BookLoanResponse;
import java_library.library.book.entity.BookLoanEntity;
import java_library.library.book.mapper.BookLoanMapper;
import java_library.library.book.service.BookLoanService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/loans")
public class BookLoanController {

    private final BookLoanService bookLoanService;
    private final BookLoanMapper bookLoanMapper;

    public BookLoanController(BookLoanService bookLoanService, BookLoanMapper bookLoanMapper) {
        this.bookLoanService = bookLoanService;
        this.bookLoanMapper = bookLoanMapper;
    }

    @GetMapping("/me")
//    @PreAuthorize("hasRole('USER')")
    public List<BookLoanResponse> getMyLoans(Authentication authentication) {
        JwtUserData userData = (JwtUserData) authentication.getPrincipal();
        return bookLoanService.findByUserId(userData.getUserId()).stream().map(bookLoanMapper::toResponse).toList();
    }

    @GetMapping("/me/active")
//    @PreAuthorize("hasRole('USER')")
    public List<BookLoanResponse> getMyActiveLoans(Authentication authentication) {
        JwtUserData userData = (JwtUserData) authentication.getPrincipal();
        return bookLoanService.findActiveByUserId(userData.getUserId()).stream().map(bookLoanMapper::toResponse).toList();
    }

    @PostMapping("/me")
    @ResponseStatus(HttpStatus.CREATED)
//    @PreAuthorize("hasRole('USER')")
    public BookLoanResponse createLoanForUser(@Valid @RequestBody BookLoanCreateRequest request, Authentication authentication) {
        JwtUserData userData = (JwtUserData) authentication.getPrincipal();
        BookLoanEntity loan = bookLoanService.createLoanForUser(userData.getUserId(), request.getBookId(), request.getDueDate());
        return bookLoanMapper.toResponse(loan);
    }

    @PutMapping("/me/{id}/return")
//    @PreAuthorize("hasRole('USER')")
    public BookLoanResponse returnMyBook(@PathVariable Long id, Authentication authentication) {
        JwtUserData userData = (JwtUserData) authentication.getPrincipal();
        BookLoanEntity loan = bookLoanService.returnBook(id, userData.getUserId());
        return bookLoanMapper.toResponse(loan);
    }

    @GetMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<BookLoanResponse> getAllLoans() {
        return bookLoanService.findAll().stream().map(bookLoanMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public BookLoanResponse getLoanById(@PathVariable Long id) {
        BookLoanEntity loan = bookLoanService.findById(id);
        return bookLoanMapper.toResponse(loan);
    }

    @GetMapping("/ticket/{ticketId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<BookLoanResponse> getLoansByTicket(@PathVariable Long ticketId) {
        return bookLoanService.findByTicketId(ticketId).stream().map(bookLoanMapper::toResponse).toList();
    }

    @GetMapping("/ticket/{ticketId}/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<BookLoanResponse> getActiveLoansByTicket(@PathVariable Long ticketId) {
        return bookLoanService.findActiveByTicketId(ticketId).stream().map(bookLoanMapper::toResponse).toList();
    }

    @GetMapping("/book/{bookId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<BookLoanResponse> getLoansByBook(@PathVariable Long bookId) {
        return bookLoanService.findByBookId(bookId).stream().map(bookLoanMapper::toResponse).toList();
    }

    @GetMapping("/book/{bookId}/active")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public List<BookLoanResponse> getActiveLoansByBook(@PathVariable Long bookId) {
        return bookLoanService.findActiveByBookId(bookId).stream().map(bookLoanMapper::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public BookLoanResponse createLoan(@Valid @RequestBody BookLoanCreateRequest request) {
        BookLoanEntity loan = bookLoanService.createLoan(request.getTicketId(), request.getBookId(), request.getDueDate());
        return bookLoanMapper.toResponse(loan);
    }

    @PutMapping("/{id}/return")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public BookLoanResponse returnBook(@PathVariable Long id) {
        BookLoanEntity loan = bookLoanService.returnBook(id);
        return bookLoanMapper.toResponse(loan);
    }
}
