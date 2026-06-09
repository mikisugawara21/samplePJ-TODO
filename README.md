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

### 3. DB接続情報を環境変数で設定
本アプリは接続情報を環境変数から読み込みます。

例（Windows PowerShell）:

    $env:DB_URL="jdbc:postgresql://localhost:5432/todo_app"
    $env:DB_USER="postgres"
    $env:DB_PASSWORD="あなたのDBパスワード"

### 4. （任意）ローカル用設定ファイル
DEBUGログなどローカル専用設定が欲しい場合は、雛形をコピーして使ってください。
application-local.properties は .gitignore 済みなのでコミットされません。

    cp src/main/resources/application-local.properties.example src/main/resources/application-local.properties

### 5. アプリを起動
./gradlew bootRun

## 機能一覧

- ユーザー登録・ログイン・ログアウト
- タスクの作成・編集・削除
- タスク一覧のページネーション
