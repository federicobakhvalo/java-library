
package java_library.library.book.controller;

import jakarta.validation.Valid;
import java_library.library.book.entity.BookAuthorEntity;
import java_library.library.book.service.BookAuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java_library.library.book.dto.request.BookCreateRequest;
import java_library.library.book.dto.response.BookResponse;
import java_library.library.book.entity.BookEntity;
import java_library.library.book.mapper.BookMapper;
import java_library.library.book.service.BookService;

@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService bookService;
    private final BookMapper bookMapper;
    private final BookAuthorService bookAuthorService;

    public BookController(BookService bookService, BookMapper bookMapper, BookAuthorService bookAuthorService) {
        this.bookService = bookService;
        this.bookMapper = bookMapper;
        this.bookAuthorService = bookAuthorService;
    }

    @GetMapping
    public List<BookResponse> getAllBooks() {
        return bookService.findAll().stream().map(bookMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public BookResponse getBookById(@PathVariable Long id) {
        BookEntity book = bookService.findById(id);
        return bookMapper.toResponse(book);
    }

    @GetMapping("/search")
    public List<BookResponse> searchBooks(@RequestParam String name) {
        return bookService.searchByName(name).stream().map(bookMapper::toResponse).toList();
    }

    @GetMapping("/author/{authorId}")
    public List<BookResponse> getBooksByAuthor(@PathVariable Long authorId) {
        return bookService.findByAuthorId(authorId).stream().map(bookMapper::toResponse).toList();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public BookResponse createBook(@Valid @RequestBody BookCreateRequest request) {
        BookAuthorEntity author = bookAuthorService.findById(request.getAuthorId());
        BookEntity book = bookMapper.toEntity(request, author);
        BookEntity savedBook = bookService.create(book);

        return bookMapper.toResponse(savedBook);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public BookResponse updateBook(@PathVariable Long id, @Valid @RequestBody BookCreateRequest request) {
        BookEntity book = bookService.update(id, request);
        return bookMapper.toResponse(book);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void deleteBook(@PathVariable Long id) {
        bookService.delete(id);
    }
}
