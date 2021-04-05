Все команды выполняются из папки charts

Команды установки баз данных (namespace postgres)
*  `helm install postgres bitnami/postgresql -f helm-postgres/db_values.yml --namespace postgres --create-namespace`
*  `helm install postgres-auth bitnami/postgresql -f helm-postgres/db_auth_values.yml --namespace postgres --create-namespace`
*  `helm install postgres-order bitnami/postgresql -f helm-postgres/db_order_values.yml --namespace postgres --create-namespace`

Команда запуска манифестов  
user-service-auth в неймспейсе auth  
user-service и order-service в неймспейсе applications  
* `helm install user-service-auth ./user-auth-chart --namespace auth --create-namespace` 
* `helm install user-service ./user-chart --namespace applications --create-namespace`
* `helm install order-service ./order-chart --namespace applications --create-namespace`

 Команда установки nginx
* `kubectl apply -f ./nginx-k8s/`

Тесты postman - "hw6-order-service.postman_collection.json"  

`статусы заказов: (меняются методом post http://{{baseUrl}}/orders/{{orderId}})`  
`CREATED,`  
`WAITING_PAY,`   
`CANCELLED,`  
`DONE,`  
`DELETED`  
`тест approveOrder устанавливает статус WAITING_PAY`

`сортировка по "createTime" и "totalCost"`  
В приложении четыре сервиса - сервис аутентификации, сервис информации о пользователе, сервис заказов и nginx в роли API gateway.

Архитектура:  
В качестве event store используется таблица бд postgres   
Создание заказа, добавление позиций и изменение статуса заказа происходит путем добавления событий в таблицу order_event.
Для чтения берутся все ивенты по id заказа, выполняется проверка на наличие снапшота, если актуальный снапшот найден,
то объект строится из него, если нет, то по очереди накатываются ивенты, создается снапшот и возвращается объект.
Снапшоты хранятся в таблице orders (id заказа, id пользователя, время создания и json объекта)

![Alt text](./hw6-schema.jpg?raw=true "Schema")
