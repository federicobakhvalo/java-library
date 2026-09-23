package java_library.library.book.mapper;

import org.springframework.stereotype.Component;
import java_library.library.book.dto.request.BookLoanCreateRequest;
import java_library.library.book.dto.response.BookLoanResponse;
import java_library.library.book.entity.BookEntity;
import java_library.library.book.entity.BookLoanEntity;
import java_library.library.user.entity.ReaderTicketEntity;

@Component
public class BookLoanMapper {
    public BookLoanEntity toEntity(BookLoanCreateRequest request, ReaderTicketEntity ticket, BookEntity book) {
        BookLoanEntity loan = new BookLoanEntity();
        loan.setTicket(ticket);
        loan.setBook(book);
        loan.setDueDate(request.getDueDate());
        return loan;
    }

    public BookLoanResponse toResponse(BookLoanEntity loan) {
        BookLoanResponse response = new BookLoanResponse();
        response.setId(loan.getId());
        response.setTicketId(loan.getTicket().getId());
        response.setBookId(loan.getBook().getId());
        response.setStatus(loan.getStatus());
        response.setReturnedAt(loan.getReturnedAt());
        response.setDueDate(loan.getDueDate());
        response.setCreatedAt(loan.getCreatedAt());
        return response;
    }
}