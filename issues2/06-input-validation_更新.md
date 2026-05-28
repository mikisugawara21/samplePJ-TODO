# レビュー結果（2026-05-28）: 06-input-validation.md

ステータス: **Partial**

## 差分の確認
- `sample.thymeleaf.web.form.TaskForm` が追加され、`@NotBlank` / `@Size` / `@NotNull` / `@AssertTrue`（終了日チェック）が適切に当てられています。
- `TaskController#create` / `update` で `@Validated @ModelAttribute("task") TaskForm form, BindingResult br` を受け取り、`br.hasErrors()` のときに同じテンプレートに戻す実装になっています。
- `form-new.html` / `form-edit.html` で `th:errors="*{title}"` 等のエラー表示も整備されています。
- `build.gradle` に `spring-boot-starter-validation` が追加されています。

タスク領域はほぼ満点の対応です。

## 残課題: `/register` 側に Bean Validation が掛かっていない
ユーザー登録（`/register`）の POST は依然として `@RequestParam String username, @RequestParam String password` のまま。空文字や 1000 文字でも通ってしまいます。詳細と修正案は [15-register-validation-missing.md](./15-register-validation-missing.md) を参照してください。

## 細かな改善余地
- `TaskForm#isEndDateAfterStartDate` のエラーメッセージ表示は動作確認だけしておきましょう。
- 大量に同じ `style="color:red; font-size: 13px;"` が書かれているので、`.field-error` のような共通クラスに寄せると保守性が上がります。

## 関連
- [15-register-validation-missing.md](./15-register-validation-missing.md)
- [11-css-and-layout_更新.md](./11-css-and-layout_更新.md)
