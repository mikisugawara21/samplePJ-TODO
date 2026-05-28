# 第 2 回レビュー結果レポート（菅原さん samplePJ-TODO）

- 入力ディレクトリ: `issues/`（初回レビュー、2026-05-22）
- 出力ディレクトリ: `issues2/`（本レポート、2026-05-28）
- 対象リポジトリ: `cloned-repo/`（branch: `master`）
- 対象コミット範囲: `d195334` → `6f111df`（現在の HEAD）

## 1. 再提出サマリ

| 区分 | 件数 |
| ---- | ---: |
| 対応済 (Resolved) | 8 |
| 部分対応 (Partial) | 3 |
| 未対応 (NotAddressed) | 0 |
| 退行 (Regression) | 1 |
| 追加発見 (NewFinding) | 4 |

直近からの改善度: **○（良）**
12 件中 8 件が完全対応、3 件が部分対応、未対応 0 件。コア機能（パスワードハッシュ化・IDOR 対策・セッション固定・トランザクション境界・コンストラクタ注入・th:field 化・デッドコード削除）はしっかり修正できています。

## 2. Issue 別ステータス表

| # | 優先度 | タイトル | ステータス |
|---|--------|----------|------------|
| 01 | P0 | パスワードが平文で保存・比較されている | Resolved |
| 02 | P0 | 他人のタスクを編集・削除できる（IDOR） | Resolved |
| 03 | P0 | `application.properties` が無い | Partial |
| 04 | P1 | `@SpringBootApplication` が 2 つある | Resolved |
| 05 | P1 | `@Transactional` 未付与 / 例外設計が無い | Resolved |
| 06 | P1 | 入力バリデーション無し | Partial |
| 07 | P1 | ページネーションの page 未検証 | Resolved |
| 08 | P1 | セッション固定対策が無い | Resolved |
| 09 | P2 | フィールド注入 | Resolved |
| 10 | P2 | Thymeleaf `th:object` / `th:field` 未使用 | Resolved |
| 11 | P2 | インライン style 多用 / 共通レイアウト未使用 | Partial |
| 12 | P2 | デッドコード残存 | Resolved |
| 13 | P0(新) | DB パスワードを平文コミット | NewFinding |
| 14 | P1(新) | `@ExceptionHandler` が `ResponseStatusException` を飲み込む | NewFinding |
| 15 | P1(新) | `/register` に Bean Validation が未適用 | NewFinding |
| 16 | P2(新) | `LoginInterceptor` のパスパターン / CSRF 対策 | NewFinding |

## 3. 次に直すべき優先順位リスト

1. `13-db-password-committed.md` — 最優先。`application.properties` から DB パスワードを取り除く。
2. `14-exception-handler-swallows-404.md` — 404 を 404 として返す。
3. `15-register-validation-missing.md` — `/register` を `RegisterForm` + `@Validated` に置き換える。
4. `05-transaction-and-exception-design_更新.md` — 定義した業務例外クラスを実際に使う or 削除。
5. `02-idor-task-ownership_更新.md` — `findById` を削除。
6. `11-css-and-layout_更新.md` — `style="..."` を `app.css` に追い出し、fragment 化。
7. `16-interceptor-and-csrf.md` — `addPathPatterns` を `/tasks` も含める。

## 4. 良くなった点
- P0 が確実に潰されている（パスワード平文・IDOR・セッション固定）
- コンストラクタ注入への切替が網羅的
- `TaskForm` を Entity から分離できた
- `@Transactional(readOnly = true)` をクラスに、更新系メソッドにだけ `@Transactional` のベストプラクティスを実践
- PR 単位で 1 Issue ずつ着実に潰している
