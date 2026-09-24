package java_library.library.book.controller;


import jakarta.validation.Valid;
import java_library.library.book.dto.request.BookAuthorCreateRequest;
import java_library.library.book.dto.response.BookAuthorResponse;
import java_library.library.book.entity.BookAuthorEntity;
import java_library.library.book.mapper.BookAuthorMapper;
import java_library.library.book.service.BookAuthorService;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/authors")
public class BookAuthorController {

    private final BookAuthorService bookAuthorService;
    private final BookAuthorMapper bookAuthorMapper;

    public BookAuthorController(BookAuthorService bookAuthorService, BookAuthorMapper bookAuthorMapper) {
        this.bookAuthorService = bookAuthorService;
        this.bookAuthorMapper = bookAuthorMapper;
    }

    @GetMapping
    public List<BookAuthorResponse> getAllAuthors() {
        return bookAuthorService.findAll().stream().map(bookAuthorMapper::toResponse).toList();
    }

    @GetMapping("/{id}")
    public BookAuthorResponse getAuthorById(@PathVariable Long id) {
        BookAuthorEntity author = bookAuthorService.findById(id);
        return bookAuthorMapper.toResponse(author);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public BookAuthorResponse createAuthor(@Valid @RequestBody BookAuthorCreateRequest request) {
        BookAuthorEntity author = bookAuthorMapper.toEntity(request);
        BookAuthorEntity savedAuthor = bookAuthorService.create(author);
        return bookAuthorMapper.toResponse(savedAuthor);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public BookAuthorResponse updateAuthor(@PathVariable Long id, @Valid @RequestBody BookAuthorCreateRequest request) {
        BookAuthorEntity author = bookAuthorService.update(id, request);
        return bookAuthorMapper.toResponse(author);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAnyRole('ADMIN', 'MODERATOR')")
    public void deleteAuthor(@PathVariable Long id) {
        bookAuthorService.delete(id);
    }

}
