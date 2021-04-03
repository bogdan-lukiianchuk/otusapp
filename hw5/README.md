Все команды выполняются из папки charts

Команды установки баз данных (namespace postgres)
*  `helm install postgres bitnami/postgresql -f helm-postgres/db_values.yml --namespace postgres --create-namespace`
*  `helm install postgres-auth bitnami/postgresql -f helm-postgres/db_auth_values.yml --namespace postgres --create-namespace`

Команда запуска манифестов
user-service-auth в неймспейсе auth
user-service в неймспейсе auth users
* `helm install user-service-auth ./user-auth-chart --namespace auth --create-namespace` 
* `helm install user-service ./user-chart --namespace users --create-namespace`

 Команда установки nginx
* `kubectl apply -f ./nginx-k8s/`

Тесты postman - "hw5-user-auth.postman_collection.json"

В приложении три сервиса - сервис аутентификации, сервис информации о пользователе и nginx в роли API gateway.

Когда пользователь регистрируется отправляется запрос в nginx, откуда роутится на сервис аутентификации, сервис аутентификации
создает пользователя, дальше пользователь переходит на форму логина (в тестах есть проверка 
что информация о пользователе недоступна без логина). Логин также производится в сервисе аутентификации, который возвращает 
две куки с jwt токеном  и refresh токеном. Дальше при каждом запросе на редактирование или просмотр данных пользователя
необходимо проставлять токен из куки в header Authorization.
Редактирование или просмотр данных пользователя находятся в сервисе пользователя (user-service).
Время жизни токена 15 минут, в сервисе аутентификации есть метод /refresh для обновления токена. 
Данные о залогиненном пользователе берутся из jwt токена, поэтому нет возможности просматривать и редактировать другого пользователя.

![Alt text](./hw5schema.jpg?raw=true "Schema")
