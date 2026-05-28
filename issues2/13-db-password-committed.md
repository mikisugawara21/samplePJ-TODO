# [P0] DB パスワードが application.properties に平文でコミットされている（新規）

## 概要
Issue 03 への対応で `src/main/resources/application.properties` をリポジトリに含めた点は方向としては正しいのですが、その中に実際の DB パスワードが `${DB_PASSWORD:11miki21}` の既定値として書かれてコミットされています。git の履歴に永久に残るため、リポジトリを公開した瞬間に漏洩したのと同じ扱いになります。

## 該当箇所
`src/main/resources/application.properties` 9 行目

## 何が問題か
1. `${VAR:default}` の `default` 部分は環境変数がセットされていなければこの値を使うという意味なので、実 DB パスワードを書くと事実上の平文コミットになる。
2. 一度コミットしてしまったパスワードは、`git rm` してコミットし直しても過去コミットから復元可能。本気で守るなら DB パスワード自体を変更するのが正攻法。
3. `logging.level.sample.*=DEBUG` も本番設定としては危険。プロファイルで切り替えるべき。

## 修正方針

### 1) `application.properties` の既定値を空にする
```properties

@'
# [P0] DB パスワードが application.properties に平文でコミットされている（新規）

## 概要
Issue 03 への対応で `src/main/resources/application.properties` をリポジトリに含めた点は方向としては正しいのですが、その中に実際の DB パスワードが `${DB_PASSWORD:11miki21}` の既定値として書かれてコミットされています。git の履歴に永久に残るため、リポジトリを公開した瞬間に漏洩したのと同じ扱いになります。

## 該当箇所
`src/main/resources/application.properties` 9 行目

## 何が問題か
1. `${VAR:default}` の `default` 部分は環境変数がセットされていなければこの値を使うという意味なので、実 DB パスワードを書くと事実上の平文コミットになる。
2. 一度コミットしてしまったパスワードは、`git rm` してコミットし直しても過去コミットから復元可能。本気で守るなら DB パスワード自体を変更するのが正攻法。
3. `logging.level.sample.*=DEBUG` も本番設定としては危険。プロファイルで切り替えるべき。

## 修正方針

### 1) `application.properties` の既定値を空にする
```properties
spring.datasource.password=${DB_PASSWORD:}
logging.level.sample=INFO
```

### 2) ローカル開発用は `application-local.properties` に分離
`.gitignore` で `application-local.properties` を無視し、雛形だけ `application-local.properties.example` としてコミットする。

### 3) 履歴に残ったパスワードの扱い
一番安全なのは DB パスワードそのものを変更すること。同じパスワードを他で使い回していないか確認してください。

## 検証
- `application.properties` を `cat` してパスワード文字列が無いこと。
- `DB_PASSWORD` 環境変数を設定して `./gradlew bootRun` で起動できること。

## 関連
- 初回 Issue 03 (`03-missing-application-properties.md`)
