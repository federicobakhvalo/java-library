
package java_library.library.user.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "reader_ticket",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_reader_ticket_user",
                        columnNames = "user_id"
                ),
                @UniqueConstraint(
                        name = "uk_reader_ticket_code",
                        columnNames = "code"
                )
        }
)
public class ReaderTicketEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Column(nullable = false, unique = true)
    private String code;

    @Column(nullable = false)
    private Boolean isActive = true;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}
