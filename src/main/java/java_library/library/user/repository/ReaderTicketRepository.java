
package java_library.library.user.repository;

import java_library.library.user.entity.ReaderTicketEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReaderTicketRepository extends JpaRepository<ReaderTicketEntity, Long> {

    Optional<ReaderTicketEntity> findByCode(String code);

    boolean existsByCode(String code);

    Optional<ReaderTicketEntity> findByUserId(Long userId);
}

