package java_library.library.book.service;

import java_library.library.book.dto.request.BookAuthorCreateRequest;
import java_library.library.book.entity.BookAuthorEntity;
import java_library.library.book.repository.BookAuthorRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookAuthorService {
    private final BookAuthorRepository bookAuthorRepository;

    public BookAuthorService(BookAuthorRepository bookAuthorRepository) {
        this.bookAuthorRepository = bookAuthorRepository;
    }

    public List<BookAuthorEntity> findAll() {
        return bookAuthorRepository.findAll();
    }

    public BookAuthorEntity findById(Long id) {
        return bookAuthorRepository.findById(id).orElseThrow(() -> new RuntimeException("Author not found: " + id));
    }

    public BookAuthorEntity findByName(String firstName, String lastName) {
        return bookAuthorRepository.findByFirstNameAndLastName(firstName, lastName).orElseThrow(() -> new RuntimeException("Author not found: " + firstName + " " + lastName));
    }

    public BookAuthorEntity create(BookAuthorEntity author) {
        return bookAuthorRepository.save(author);
    }

    public BookAuthorEntity update(Long id, BookAuthorCreateRequest request) {
        BookAuthorEntity author = findById(id);
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        author.setAvatarUrl(request.getAvatarUrl());
        author.setDescription(request.getDescription());
        return bookAuthorRepository.save(author);
    }

    public void delete(Long id) {
        BookAuthorEntity author = findById(id);
        bookAuthorRepository.delete(author);
    }

}