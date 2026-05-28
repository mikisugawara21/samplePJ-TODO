# レビュー結果（2026-05-28）: 11-css-and-layout.md

ステータス: **Partial**

## 差分の確認
- `src/main/resources/static/css/app.css` が新規追加され、`.btn` / `.btn-primary` / `.page-link` / `.form-group` / `.form-control` の共通クラスが定義されています。
- `list.html` / `form-new.html` / `form-edit.html` / `login.html` / `register.html` が `app.css` を読み込み、ボタン類はクラス指定に置き換わっています。
- ページャの「現在ページ」判定は `th:classappend` でクラス切替に統一されています。
- `<label for="...">` と `<input id="...">` の紐付けも全フォームで対応されました。

## 残課題（Minor）: インライン style がまだ多い
共通 CSS の切り出しは進んだものの、各 HTML には以下のようなインライン style が残っています。

`list.html`: `style="padding: 20px;"` `style="text-align: center;"` など
`login.html` / `register.html`: `style="text-align:center; margin-top: 30px;"` など

これらを `app.css` に移すと「インライン style 多用」の指摘がほぼクローズします。

### 追加修正案: app.css に追記
```css
.page-container { padding: 20px; }
.text-center { text-align: center; }
.text-right  { text-align: right; }
.mb-10 { margin-bottom: 10px; }
.alert-info { background-color: #e0f0ff; padding: 12px; border-radius: 4px; }
.card { display: inline-block; border: 1px solid #ccc; padding: 15px 25px; border-radius: 5px; }
.field-error { color: red; font-size: 13px; }
```

### 追加修正案: 共通レイアウトの導入
`templates/_layout/base.html` を作り `th:fragment` を使うと、全画面共通のヘッダー／フッターを1か所で管理できます。

## 検証
- 全 HTML から `style="..."` がほぼ消えていること。
- `app.css` のブランドカラーを1か所変えると、すべての画面のボタン色が一斉に変わること。

## 関連
- [10-thymeleaf-form-binding.md](../issues/10-thymeleaf-form-binding.md)
