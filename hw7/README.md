Для удобного запуска скрипт `"deploy.sh"`  (переходит в папку charts и вызывает все команды)    

Все команды выполняются из папки charts

Команды установки баз данных (namespace postgres)
*  `helm install postgres bitnami/postgresql -f helm-postgres/db_values.yml --namespace postgres --create-namespace`
*  `helm install postgres-auth bitnami/postgresql -f helm-postgres/db_auth_values.yml --namespace postgres --create-namespace`
*  `helm install postgres-order bitnami/postgresql -f helm-postgres/db_order_values.yml --namespace postgres --create-namespace`
*  `helm install postgres-notification bitnami/postgresql -f helm-postgres/db_notification_values.yml --namespace postgres --create-namespace`
*  `helm install postgres-billing bitnami/postgresql -f helm-postgres/db_billing_values.yml --namespace postgres --create-namespace`

kafka
*  `helm install kafka -f kafka/values.yml bitnami/kafka --namespace infrastructure --create-namespace`  
Команда запуска манифестов  
user-service-auth в неймспейсе auth  
user-service, notification-service, billing-service и order-service в неймспейсе applications  
* `helm install user-service-auth ./user-auth-chart --namespace auth --create-namespace` 
* `helm install user-service ./user-chart --namespace applications --create-namespace`
* `helm install order-service ./order-chart --namespace applications --create-namespace`
* `helm install notification-service ./notification-chart --namespace applications --create-namespace`
* `helm install billing-service ./billing-chart --namespace applications --create-namespace`

 Команда установки nginx
* `kubectl apply -f ./nginx-k8s/`

Тесты postman - "hw7-billing.postman_collection.json"  

В приложении шесть сервисов - сервис аутентификации, сервис информации о пользователе, сервис заказов,
сервис биллинга, сервис нотификации и nginx в роли API gateway.

Схема работы:    
user-service-auth при регистации пользователя посылает post запрос в billing-service для создания аккаунта.
Billing-service проводит все операции со счетом (создание, пополнение, снятие).
Order-service  проводит все операции с заказом (создание, изменение статуса, добавление позиций).
При переводе заказа в статус "WAITING_PAY" order-service post запрос в billing-service для попытки оплаты. 
После ответа от billing-service order-service отправляет в топик в кафке информацию об
успешной/неуспешной оплате заказа. Notification-service читает сообщения из кафки и делает рассылку.

Выбрана архитектура с http взаимодействием для обеспечения последовательности операций и гарантии что запрос был обработан
(Например создание аккаунта после регистрации пользователя).
Для сервиса нотификации выбрано взаимодействие через кафку так как нет необходимости дожидаться окончания операции.

![Alt text](./billing-service-hw7.jpg?raw=true "Schema")
