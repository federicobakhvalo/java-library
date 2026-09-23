
package java_library.library.book.mapper;

import org.springframework.stereotype.Component;

import java_library.library.book.dto.request.BookCreateRequest;
import java_library.library.book.dto.response.BookResponse;
import java_library.library.book.entity.BookEntity;
import java_library.library.book.entity.BookAuthorEntity;

@Component
public class BookMapper {

    public BookEntity toEntity(
            BookCreateRequest request,
            BookAuthorEntity author
    ) {
        BookEntity book = new BookEntity();

        book.setAuthor(author);
        book.setBookname(request.getBookname());
        book.setQuantity(
                request.getQuantity() != null
                        ? request.getQuantity()
                        : 0
        );
        book.setImageUrl(request.getImageUrl());
        book.setDescription(request.getDescription());

        return book;
    }

    public BookResponse toResponse(BookEntity book) {
        BookResponse response = new BookResponse();

        response.setId(book.getId());
        response.setAuthorId(book.getAuthor().getId());
        response.setBookname(book.getBookname());
        response.setQuantity(book.getQuantity());
        response.setImageUrl(book.getImageUrl());
        response.setDescription(book.getDescription());
        response.setBookStatus(book.getBookStatus());

        return response;
    }
}

