Все команды выполняются из папки hw2_helm

Команды установки базы данных


*  `helm repo add bitnami https://charts.bitnami.com/bitnami`
 
*  ` helm install user-app-postgres bitnami/postgresql -f helm-postgres/db_values.yml`


Команда запуска манифестов

* `helm install user-service ./user-chart`

Тесты postman - UserTest.postman_collection.json
