
Все команды выполняются из папки charts

Команды установки баз данных (namespace postgres)
*  `helm install postgres-order bitnami/postgresql -f db_order_values.yml --namespace postgres --create-namespace`  

Команда запуска манифестов неймспейс applications  
* `helm install order-service ./order-chart --namespace applications --create-namespace`

Тесты postman - "hw8-idempotence.postman_collection.json"  

Реализация идемпотентности:  
1) Создание заказа: метод POST /api/orders/ принимает заголовок requestId. 
   Если запрос с таким значением заголовка уже обрабатывался, то возвращается id созданного заказа и новый не создается.
2) Добавление позиций в заказ: метод POST /api/orders/${orderId}/products 
   принимает json в теле запроса с названием позиции и количеством. 
   Идемпотентность достигается за счет того что количество товаров устанавливается в запросе, а не увеличивается или уменьшается.