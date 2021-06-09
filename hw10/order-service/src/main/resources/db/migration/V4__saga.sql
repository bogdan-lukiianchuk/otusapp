create table if not exists order_stage
(
    order_id bigint,
    stage    text
);

create table if not exists order_pay_idempotence_operation
(
    order_id   bigint primary key,
    key        uuid,
    account_id bigint
);
