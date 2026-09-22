package java_library.library.book.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "book_author")
public class BookAuthorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstName;
    @Column(nullable = false)
    private String lastName;
    private String avatarUrl;
    private String description;
}
