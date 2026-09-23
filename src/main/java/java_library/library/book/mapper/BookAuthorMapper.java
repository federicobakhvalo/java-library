package java_library.library.book.mapper;

import org.springframework.stereotype.Component;
import java_library.library.book.dto.request.BookAuthorCreateRequest;
import java_library.library.book.dto.response.BookAuthorResponse;
import java_library.library.book.entity.BookAuthorEntity;

@Component
public class BookAuthorMapper {
    public BookAuthorEntity toEntity(BookAuthorCreateRequest request) {
        BookAuthorEntity author = new BookAuthorEntity();
        author.setFirstName(request.getFirstName());
        author.setLastName(request.getLastName());
        author.setAvatarUrl(request.getAvatarUrl());
        author.setDescription(request.getDescription());
        return author;
    }

    public BookAuthorResponse toResponse(BookAuthorEntity author) {
        BookAuthorResponse response = new BookAuthorResponse();
        response.setId(author.getId());
        response.setFirstName(author.getFirstName());
        response.setLastName(author.getLastName());
        response.setAvatarUrl(author.getAvatarUrl());
        response.setDescription(author.getDescription());
        return response;
    }
}