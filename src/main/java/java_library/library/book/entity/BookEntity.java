package java_library.library.book.entity;

import jakarta.persistence.*;
import java_library.library.common.enums.BookStatus;

@Entity
@Table(name = "book", uniqueConstraints = {@UniqueConstraint(name = "uk_book_author_name", columnNames = {"author_id", "bookname"})})
public class BookEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "author_id", nullable = false)
    private BookAuthorEntity author;
    @Column(nullable = false)
    private String bookname;
    private String imageUrl;
    private String description;
    @Column(nullable = false)
    private Integer quantity = 0;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private BookStatus bookStatus = BookStatus.AVAILABLE;

    public Long getId() {
        return id;
    }

    public BookAuthorEntity getAuthor() {
        return author;
    }

    public String getBookname() {
        return bookname;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public Integer getQuantity() {
        return quantity;
    }


}
