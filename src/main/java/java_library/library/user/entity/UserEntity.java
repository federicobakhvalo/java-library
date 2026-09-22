package java_library.library.user.entity;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Entity
@Table(name = "users")
public class UserEntity {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Column(nullable = false, unique = true)
    private String username;


    @Column(nullable = false)
    private String password;


    @Column(nullable = false, unique = true)
    private String email;


    @Column(nullable = false)
    private Boolean isActive = true;


    private LocalDate birthDate;


    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;


    private String avatarUrl;


    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }


}