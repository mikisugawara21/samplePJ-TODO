# [P2] LoginInterceptor のパスパターン / CSRF 対策（追加発見）

## 概要
ログイン要求を強制する `LoginInterceptor` を `WebConfig` で追加した点は良い改善です。一方で、Spring の `AntPathMatcher` のクセに引っかかるパスパターンになっていることと、POST フォームに CSRF トークンが付いていないことを補足しておきます。

## 何が問題か
1. `/tasks/**` は `/tasks/xxx` にはマッチしますが `/tasks` 単体にはマッチしません。
2. `new LoginInterceptor()` で直接 new しているため DI が効きません。
3. POST フォームに CSRF トークンが無く、他サイトに仕込まれたフォームからの攻撃を防げません。

## 修正方針

### 1) パスパターンを明示・DI 化
```java
@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final LoginInterceptor loginInterceptor;

    public WebConfig(LoginInterceptor loginInterceptor) {
        this.loginInterceptor = loginInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/tasks", "/tasks/**")
                .excludePathPatterns("/login", "/register", "/", "/css/**", "/js/**", "/error/**");
    }
}
```

`LoginInterceptor` 側に `@Component` を付与する。

### 2) CSRF（中期的）
本格対応は Spring Security の導入を推奨します。`spring-boot-starter-security` を入れれば CSRF トークンの埋め込みも自動になります。今回のスコープでは「パスパターンの明示と DI 化」だけでも十分前進します。

## 検証
- 未ログイン状態で `/tasks`（trailing slash 無し）にアクセス → `/login` にリダイレクトされること。
- ログイン後に `/tasks`, `/tasks?page=2`, `/tasks/edit/1` がすべてアクセスできること。

## 関連
- 初回 Issue 08 (`08-session-fixation.md`)
