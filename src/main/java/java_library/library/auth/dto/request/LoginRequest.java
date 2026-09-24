
package java_library.library.auth.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank(message = "Login cannot be blank")
    @Size(max = 255, message = "Login cannot exceed 255 characters")
    private String login;

    @NotBlank(message = "Password cannot be blank")
    @Size(max = 100, message = "Password cannot exceed 100 characters")
    private String password;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

