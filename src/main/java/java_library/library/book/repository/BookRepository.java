package java_library.library.book.repository;

import java_library.library.book.entity.BookEntity;
import java_library.library.common.enums.BookStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookRepository extends JpaRepository<BookEntity, Long> {

    Optional<BookEntity> findByAuthorIdAndBookname(
            Long authorId,
            String bookname
    );

    boolean existsByAuthorIdAndBookname(
            Long authorId,
            String bookname
    );

    List<BookEntity> findByBookStatus(BookStatus bookStatus);

    List<BookEntity> findByAuthorId(Long authorId);

    List<BookEntity> findByBooknameContainingIgnoreCase(String bookname);
}