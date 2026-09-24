package java_library.library.book.service;

import java_library.library.book.dto.request.BookCreateRequest;
import java_library.library.book.entity.BookAuthorEntity;
import java_library.library.book.entity.BookEntity;
import java_library.library.book.repository.BookAuthorRepository;
import java_library.library.book.repository.BookRepository;
import java_library.library.common.enums.BookStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final BookAuthorRepository bookAuthorRepository;

    public BookService(BookRepository bookRepository, BookAuthorRepository bookAuthorRepository) {
        this.bookRepository = bookRepository;
        this.bookAuthorRepository = bookAuthorRepository;
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

    public BookEntity update(Long id, BookCreateRequest request) {
        BookEntity book = findById(id);
        BookAuthorEntity author = bookAuthorRepository.findById(request.getAuthorId()).orElseThrow(() -> new RuntimeException("Author not found: " + request.getAuthorId()));
        if (!book.getAuthor().getId().equals(request.getAuthorId()) || !book.getBookname().equals(request.getBookname())) {
            if (bookRepository.existsByAuthorIdAndBookname(request.getAuthorId(), request.getBookname())) {
                throw new RuntimeException("Book with this author and name already exists");
            }
        }
        book.setAuthor(author);
        book.setBookname(request.getBookname());
        book.setImageUrl(request.getImageUrl());
        book.setDescription(request.getDescription());
        book.setQuantity(request.getQuantity());
        book.setBookStatus(request.getBookStatus());
        return bookRepository.save(book);
    }

    public void delete(Long id) {
        BookEntity book = findById(id);
        bookRepository.delete(book);
    }

//    public BookEntity update(Long id, BookEntity book) {
//        BookEntity existingBook = findById(id);
//        if (!existingBook.getAuthor().getId().equals(book.getAuthor().getId()) || !existingBook.getBookname().equals(book.getBookname())) {
//            if (bookRepository.existsByAuthorIdAndBookname(book.getAuthor().getId(), book.getBookname())) {
//                throw new RuntimeException("Book already exists for this author: " + book.getBookname());
//            }
//        }
//        if (book.getQuantity() == null || book.getQuantity() < 0) {
//            throw new RuntimeException("Book quantity cannot be negative");
//        }
//        existingBook.setAuthor(book.getAuthor());
//        existingBook.setBookname(book.getBookname());
//        existingBook.setDescription(book.getDescription());
//        existingBook.setQuantity(book.getQuantity());
//        existingBook.setPrice(book.getPrice());
//        existingBook.setAvatarUrl(book.getAvatarUrl());
//        existingBook.setBookStatus(book.getBookStatus());
//        return bookRepository.save(existingBook);
//    }

//    public void delete(Long id) {
//        BookEntity book = findById(id);
//        bookRepository.delete(book);
//    }
}