
package java_library.library.book.repository;

import java_library.library.book.entity.BookLoanEntity;
import java_library.library.common.enums.BookLoanStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookLoanRepository extends JpaRepository<BookLoanEntity, Long> {

    List<BookLoanEntity> findByStatus(BookLoanStatus status);

    List<BookLoanEntity> findByTicketId(Long ticketId);

    List<BookLoanEntity> findByBookId(Long bookId);

    List<BookLoanEntity> findByTicketIdAndStatus(
            Long ticketId,
            BookLoanStatus status
    );

    List<BookLoanEntity> findByBookIdAndStatus(
            Long bookId,
            BookLoanStatus status
    );
}


