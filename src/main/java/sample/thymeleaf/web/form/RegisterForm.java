package sample.thymeleaf.web.form;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class RegisterForm {

    @NotBlank(message = "ユーザー名は必須です")
    @Size(min = 3, max = 20, message = "ユーザー名は3〜20文字で入力してください")
    @Pattern(regexp = "[A-Za-z0-9_]+", message = "ユーザー名は半角英数字とアンダースコアのみ使用できます")
    private String username;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 8, max = 72, message = "パスワードは8〜72文字で入力してください")
    private String password;

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
}