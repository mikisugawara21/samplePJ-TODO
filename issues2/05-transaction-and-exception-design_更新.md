# レビュー結果（2026-05-28）: 05-transaction-and-exception-design.md

ステータス: **Resolved（ただし退行 1 件と Minor 1 件あり）**

## 差分の確認
- `TaskServiceImpl` にクラスレベル `@Transactional(readOnly = true)`、更新系（`createTask` / `updateTask` / `deleteTask`）にメソッドレベル `@Transactional` が付与されています。
- `LoginServiceImpl#register` にも `@Transactional` が付与されています。
- `sample.logic.GlobalExceptionHandler` が `@ControllerAdvice` で追加され、`error/404.html` と `error/500.html` も用意されています。
- 業務例外クラス `NotFoundException` / `DuplicateUsernameException` が `sample/logic/exception/` 配下に追加されています。

ここまでは指摘どおりの構成です。

## 退行: 404 が 500 に化ける
別ファイル [14-exception-handler-swallows-404.md](./14-exception-handler-swallows-404.md) を起票しています。`@ExceptionHandler(Exception.class)` が `ResponseStatusException` も飲み込むため、`TaskController` が投げる NOT_FOUND がすべて 500 ページになっています。

## Minor: 業務例外クラスを定義したのに使っていない
`NotFoundException` と `DuplicateUsernameException` は宣言だけされていて、実際にはコード中で投げられていません。定義したが使っていないクラスはデッドコードと同じです。実際に使うか、削除するか、どちらかに寄せましょう。

## 関連
- [14-exception-handler-swallows-404.md](./14-exception-handler-swallows-404.md)
- [15-register-validation-missing.md](./15-register-validation-missing.md)
