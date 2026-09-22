package java_library.library.user.service;

import java_library.library.user.entity.ReaderTicketEntity;
import java_library.library.user.entity.UserEntity;
import java_library.library.user.repository.ReaderTicketRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ReaderTicketService {
    private final ReaderTicketRepository readerTicketRepository;

    public ReaderTicketService(ReaderTicketRepository readerTicketRepository) {
        this.readerTicketRepository = readerTicketRepository;
    }

    public List<ReaderTicketEntity> findAll() {
        return readerTicketRepository.findAll();
    }

    public ReaderTicketEntity findById(Long id) {
        return readerTicketRepository.findById(id).orElseThrow(() -> new RuntimeException("Reader ticket not found: " + id));
    }

    public ReaderTicketEntity findByCode(String code) {
        return readerTicketRepository.findByCode(code).orElseThrow(() -> new RuntimeException("Reader ticket not found: " + code));
    }

    public ReaderTicketEntity findByUserId(Long userId) {
        return readerTicketRepository.findByUserId(userId).orElseThrow(() -> new RuntimeException("Reader ticket not found for user: " + userId));
    }

    public ReaderTicketEntity create(UserEntity user) {
        if (readerTicketRepository.findByUserId(user.getId()).isPresent()) {
            throw new RuntimeException("User already has a reader ticket: " + user.getId());
        }
        ReaderTicketEntity ticket = new ReaderTicketEntity();
        ticket.setUser(user);
        ticket.setCode(generateUniqueCode());
        ticket.setIsActive(true);
        return readerTicketRepository.save(ticket);
    }

    private String generateUniqueCode() {
        String code;
        do {
            code = UUID.randomUUID().toString();
        } while (readerTicketRepository.existsByCode(code));
        return code;
    }
}