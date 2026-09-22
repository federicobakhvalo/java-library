package java_library.library.book.service;

import java_library.library.book.entity.BookEntity;
import java_library.library.book.repository.BookRepository;
import java_library.library.common.enums.BookStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public List<BookEntity> findAll() {
        return bookRepository.findAll();
    }

    public BookEntity findById(Long id) {
        return bookRepository.findById(id).orElseThrow(() -> new RuntimeException("Book not found: " + id));
    }

    public BookEntity findByAuthorAndName(Long authorId, String bookname) {
        return bookRepository.findByAuthorIdAndBookname(authorId, bookname).orElseThrow(() -> new RuntimeException("Book not found: " + bookname + ", author id: " + authorId));
    }

    public List<BookEntity> findByAuthorId(Long authorId) {
        return bookRepository.findByAuthorId(authorId);
    }

    public List<BookEntity> findByStatus(BookStatus status) {
        return bookRepository.findByBookStatus(status);
    }

    public List<BookEntity> searchByName(String bookname) {
        return bookRepository.findByBooknameContainingIgnoreCase(bookname);
    }

    public BookEntity create(BookEntity book) {
        if (bookRepository.existsByAuthorIdAndBookname(book.getAuthor().getId(), book.getBookname())) {
            throw new RuntimeException("Book already exists for this author: " + book.getBookname());
        }
        if (book.getQuantity() == null || book.getQuantity() < 0) {
            throw new RuntimeException("Book quantity cannot be negative");
        }
        if (book.getBookStatus() == null) {
            book.setBookStatus(BookStatus.AVAILABLE);
        }
        return bookRepository.save(book);
    }
}