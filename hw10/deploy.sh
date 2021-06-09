cd ./charts
helm install postgres bitnami/postgresql -f helm-postgres/db_values.yml --namespace postgres --create-namespace
helm install postgres-auth bitnami/postgresql -f helm-postgres/db_auth_values.yml --namespace postgres --create-namespace
helm install postgres-order bitnami/postgresql -f helm-postgres/db_order_values.yml --namespace postgres --create-namespace
helm install postgres-notification bitnami/postgresql -f helm-postgres/db_notification_values.yml --namespace postgres --create-namespace
helm install postgres-billing bitnami/postgresql -f helm-postgres/db_billing_values.yml --namespace postgres --create-namespace
helm install postgres-warehouse bitnami/postgresql -f helm-postgres/db_warehouse_values.yml --namespace postgres --create-namespace
helm install postgres-product bitnami/postgresql -f helm-postgres/db_product_values.yml --namespace postgres --create-namespace
helm install kafka -f kafka/values.yml bitnami/kafka --namespace infrastructure --create-namespace

helm install user-service-auth ./user-auth-chart --namespace auth --create-namespace
helm install user-service ./user-chart --namespace applications --create-namespace
helm install delivery-service ./delivery-chart --namespace applications --create-namespace
helm install product-service ./product-chart --namespace applications --create-namespace
helm install warehouse-service ./warehouse-chart --namespace applications --create-namespace
helm install notification-service ./notification-chart --namespace applications --create-namespace
helm install billing-service ./billing-chart --namespace applications --create-namespace
helm install order-service ./order-chart --namespace applications --create-namespace
kubectl apply -f ./nginx-k8s/