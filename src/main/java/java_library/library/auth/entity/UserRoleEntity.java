package java_library.library.auth.entity;

import jakarta.persistence.*;
import java_library.library.user.entity.UserEntity;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_role", uniqueConstraints = {@UniqueConstraint(name = "uk_user_role", columnNames = {"user_id", "role_id"})})
public class UserRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "role_id", nullable = false)
    private AuthRoleEntity role;
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }

    public void setRole(AuthRoleEntity role) {
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public UserEntity getUser() {
        return user;
    }

    public AuthRoleEntity getRole() {
        return role;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
}