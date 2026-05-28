# [P1] GlobalExceptionHandler が ResponseStatusException も 500 として処理してしまう（退行）

## 概要
`GlobalExceptionHandler` を追加した点は良い改善なのですが、`@ExceptionHandler(Exception.class)` がより具体的な `ResponseStatusException` も拾ってしまうため、本来 404 ページに飛ばしたいケースで `error/500.html` が表示されます。

## 該当箇所
`src/main/java/sample/logic/GlobalExceptionHandler.java`

## 何が問題か
1. `ResponseStatusException` は `NotFoundException` の親子関係には無く、`Exception` の方が広いのでそっちが先にマッチします。結果、404 を投げても 500 ページが返ります。
2. Issue 02 の検証ステップ（他人の id を編集→404）が壊れている状態です。

## 修正方針

### 方針A（推奨）: 業務例外 `NotFoundException` に統一する
```java
// TaskController.java
if (task == null) {
    throw new NotFoundException("task not found or not owned");
}

int updated = taskService.updateTask(task);
if (updated == 0) {
    throw new NotFoundException("task not found or not owned");
}

int deleted = taskService.deleteTask(id, username);
if (deleted == 0) {
    throw new NotFoundException("task not found or not owned");
}
```

```java
// NotFoundException
@ResponseStatus(HttpStatus.NOT_FOUND)
public class NotFoundException extends RuntimeException {
    public NotFoundException(String message) { super(message); }
}
```

### 方針B: `GlobalExceptionHandler` 側で `ResponseStatusException` を別扱いする
```java
@ExceptionHandler(ResponseStatusException.class)
public Object handleResponseStatus(ResponseStatusException e) {
    if (e.getStatusCode() == HttpStatus.NOT_FOUND) return "error/404";
    return "error/500";
}
```

## 検証
- ユーザー A のタスクにユーザー B でアクセス → `error/404.html` が表示されること。
- 故意に NPE を投げると `error/500.html` が出ること。

## 関連
- 初回 Issue 02 (`02-idor-task-ownership.md`)
- 初回 Issue 05 (`05-transaction-and-exception-design.md`)
