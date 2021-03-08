Все команды выполняются из папки hw3_helm

Команды установки базы данных


*  `helm repo add bitnami https://charts.bitnami.com/bitnami`
 
*  ` helm install user-app-postgres bitnami/postgresql -f helm-postgres/db_values.yml`


Команда запуска манифестов

* `helm install user-service ./user-chart`

Тесты postman - UserTest.postman_collection.json


2 helm repo add prometheus-community https://prometheus-community.github.io/helm-charts

3 helm repo update

4 helm install prom prometheus-community/kube-prometheus-stack -f prometheus.yaml

curl -H'

helm install nginx ingress-nginx/ingress-nginx -f nginx-ingress.yaml

**metrics** 

increase(http_server_requests_seconds_count{uri="/actuator/health"}[30m])

rate(http_server_requests_seconds_count{uri="/actuator/health"}[30m]) - rps

sum(rate(http_server_requests_seconds_count{uri="/actuator/health"}[30m])) - rps all instants

histogram_quantile(0.5, sum by(le) (rate(nginx_ingress_controller_response_duration_seconds_bucket[1m]))) * 1000
histogram_quantile(0.95, sum by(le) (rate(nginx_ingress_controller_response_duration_seconds_bucket[1m]))) * 1000
histogram_quantile(0.99, sum by(le) (rate(nginx_ingress_controller_response_duration_seconds_bucket[1m]))) * 1000