create table if not exists order_idempotence_operation
(
    order_idempotence_operation_id uuid primary key,
    order_id                       bigint not null
);