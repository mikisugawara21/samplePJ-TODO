# レビュー結果（2026-05-28）: 02-idor-task-ownership.md

ステータス: **Resolved（軽微な残課題あり）**

## 差分の確認
- Controller (`TaskController.java#L48-L114`): `edit` / `update` / `delete` のすべてで `username` を Session から取得し、`taskService.getTaskByIdAndUsername` / `taskService.updateTask(task)` / `taskService.deleteTask(id, username)` を経由するよう変更されています。0 件なら `ResponseStatusException(HttpStatus.NOT_FOUND)` を投げており、コントローラの責務として適切です。
- Service (`TaskServiceImpl.java`): `getTaskByIdAndUsername` / `updateTask` / `deleteTask` のシグネチャが正しく `username` 込み・戻り値 `int` 化されています。
- Mapper XML (`TaskMapper.xml`): `update` / `delete` の WHERE 句に `username = #{username}` が入っており、SQL レベルでの多重防御も完成しています。

ここまでは申し分ない修正です。

## 残課題（Minor）: `findById` が残っている
`TaskMapper.java#L13` と `TaskServiceImpl.java#L28-L30` に、所有者を見ない `findById` が残ったままです。現状はどこからも呼ばれていないように見えますが、`Mapper` / `Service` の両方に「id だけで取れる API」が残っていると、将来別の開発者が間違って使ってしまう温床になります。

## 関連
- [14-exception-handler-swallows-404.md](./14-exception-handler-swallows-404.md)（404 ハンドリングの退行）
