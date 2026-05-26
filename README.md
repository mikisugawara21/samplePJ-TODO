# samplePJ-TODO

Spring Boot で作成したTODO管理アプリです。

## 使用技術

- Java 21
- Spring Boot 3.3.5
- Thymeleaf
- MyBatis
- PostgreSQL

## セットアップ手順

### 1. リポジトリをクローン
git clone https://github.com/mikisugawara21/samplePJ-TODO.git

### 2. データベースを作成
PostgreSQLで `todo_app` データベースを作成してください。

### 3. 設定ファイルを作成
`src/main/resources/application.properties` を作成して
DB接続情報を設定してください。

### 4. アプリを起動
./gradlew bootRun

## 機能一覧

- ユーザー登録・ログイン・ログアウト
- タスクの作成・編集・削除
- タスク一覧のページネーション