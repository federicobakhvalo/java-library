
package java_library.library.book.repository;

import java_library.library.book.entity.BookAuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface BookAuthorRepository extends JpaRepository<BookAuthorEntity, Long> {

    Optional<BookAuthorEntity> findByFirstNameAndLastName(
            String firstName,
            String lastName
    );
}

