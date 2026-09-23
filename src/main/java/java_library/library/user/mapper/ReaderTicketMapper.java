
package java_library.library.user.mapper;

import org.springframework.stereotype.Component;

import java_library.library.user.dto.response.ReaderTicketResponse;
import java_library.library.user.entity.ReaderTicketEntity;

@Component
public class ReaderTicketMapper {

    public ReaderTicketResponse toResponse(ReaderTicketEntity ticket) {
        ReaderTicketResponse response = new ReaderTicketResponse();

        response.setId(ticket.getId());
        response.setUserId(ticket.getUser().getId());
        response.setCode(ticket.getCode());
        response.setIsActive(ticket.getIsActive());
        response.setCreatedAt(ticket.getCreatedAt());

        return response;
    }
}

