# [P1] /register POST に Bean Validation が掛かっていない（Issue 06 の片側未対応）

## 概要
タスク側は `TaskForm` + `@Validated` でしっかりバリデーションが入りましたが、ユーザー登録（`/register`）の方は `@RequestParam String username, @RequestParam String password` のままで、長さも形式もチェックされていません。

## 該当箇所
`src/main/java/sample/thymeleaf/web/LoginController.java` 57-62 行目

## 何が問題か
1. 空文字 / 半角スペースのみ / 1000 文字超など、明らかに不正な username でも `loginMapper.insert` まで届く。
2. `LoginServiceImpl#register` が `IllegalArgumentException` を投げているが、Controller 側で `try-catch` していないため `error/500.html` が返る。
3. パスワードに対する強度ポリシーが無い。

## 修正方針

### 1) `RegisterForm` を作る
```java
public class RegisterForm {
    @NotBlank(message = "ユーザー名は必須です")
    @Size(min = 3, max = 20, message = "ユーザー名は3〜20文字で入力してください")
    @Pattern(regexp = "[A-Za-z0-9_]+", message = "ユーザー名は半角英数字とアンダースコアのみ使用できます")
    private String username;

    @NotBlank(message = "パスワードは必須です")
    @Size(min = 8, max = 72, message = "パスワードは8〜72文字で入力してください")
    private String password;
}
```

### 2) `LoginController` の修正
```java
@PostMapping("/register")
public String registerPost(@Validated @ModelAttribute("registerForm") RegisterForm form,
                           BindingResult br) {
    if (br.hasErrors()) {
        return "register";
    }
    try {
        loginService.register(form.getUsername(), form.getPassword());
    } catch (DuplicateUsernameException e) {
        br.rejectValue("username", "duplicate", e.getMessage());
        return "register";
    }
    return "redirect:/login";
}
```

### 3) `register.html` を `th:object` / `th:field` に置き換え
```html
<form th:action="@{/register}" th:object="${registerForm}" method="post">
    <input id="username" type="text" th:field="*{username}" class="form-control">
    <p th:errors="*{username}" class="field-error"></p>
    <input id="password" type="password" th:field="*{password}" class="form-control">
    <p th:errors="*{password}" class="field-error"></p>
</form>
```

## 検証
- 空 username で submit → 同じ画面にエラー表示
- 既存ユーザー名で submit → 「ユーザー名は既に使われています」表示
- 7 文字のパスワード → エラー

## 関連
- 初回 Issue 06 (`06-input-validation.md`)
- 初回 Issue 05 (`05-transaction-and-exception-design.md`)
