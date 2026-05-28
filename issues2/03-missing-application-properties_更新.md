# レビュー結果（2026-05-28）: 03-missing-application-properties.md

ステータス: **Partial（重大な追加問題あり）**

## 差分の確認
- `.gitignore` から `src/main/resources/application.properties` の除外が外れ、ファイル本体がコミットされています（コミット `610dba4`）。
- MyBatis のマッパー位置も書かれており、クローンしてきたメンバーが起動できない状態は解消されました。
- README.md にもセットアップ手順が追記され、`./gradlew bootRun` で起動できる旨が書かれています。

ここまでは良い対応です。

## 重大な残課題: DB パスワードを平文でコミットしている
`application.properties` 9 行目に **`spring.datasource.password=${DB_PASSWORD:11miki21}`** と書かれており、環境変数が無い場合の既定値として実 DB パスワードが記録されています。git の履歴に永久に残り、リポジトリを公開した時点で漏洩したのと同じ扱いになります。

詳細・修正案 → [13-db-password-committed.md](./13-db-password-committed.md)

## 追加で対応するとさらに良くなる点
- `application-local.properties.example` の雛形コミット / `application-local.properties` の gitignore
- README に「環境変数 `DB_PASSWORD` を必ず設定する」旨を明記
- `.gitignore` への `.DS_Store` 追記

## 関連
- [13-db-password-committed.md](./13-db-password-committed.md)
