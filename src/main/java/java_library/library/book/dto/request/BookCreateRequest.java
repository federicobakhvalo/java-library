
package java_library.library.book.dto.request;

import jakarta.validation.constraints.*;
import java_library.library.common.enums.BookStatus;

public class BookCreateRequest {

    @NotNull(message = "Author ID cannot be null")
    @Positive(message = "Author ID must be a positive number")
    private Long authorId;

    @NotBlank(message = "Book name cannot be blank")
    @Size(max = 255, message = "Book name cannot exceed 255 characters")
    private String bookname;

    @PositiveOrZero(message = "Quantity must be a positive number or zero")
    private Integer quantity;

    @Size(max = 1000, message = "Image URL cannot exceed 1000 characters")
    @Pattern(regexp = "^(https?://.*\\.(?:png|jpg|jpeg|gif|svg))?$", message = "Image URL must be a valid image URL")
    private String imageUrl;

    @Size(max = 255, message = "Description cannot exceed 255 characters")
    private String description;

    private BookStatus bookStatus;

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getBookname() {
        return bookname;
    }

    public void setBookname(String bookname) {
        this.bookname = bookname;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }
}

