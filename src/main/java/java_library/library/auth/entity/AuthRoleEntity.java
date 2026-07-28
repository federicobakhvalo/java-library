package java_library.library.auth.entity;


import jakarta.persistence.*;
import java_library.library.common.enums.AuthRoleGroup;

@Entity
@Table(name = "auth_role")
public class AuthRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    @Column(nullable = false, unique = true)
    private AuthRoleGroup role;


    public long getId() {
        return id;
    }

    public AuthRoleGroup getRole() {
        return role;
    }


    public void setRole(AuthRoleGroup role) {
        this.role = role;
    }

}
