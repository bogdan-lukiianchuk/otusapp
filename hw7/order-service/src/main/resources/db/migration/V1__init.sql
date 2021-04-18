create table if not exists order_event
(
    order_event_id uuid primary key,
    user_id        bigint    not null,
    order_id       bigint    not null,
    create_time    timestamp not null,
    type           text      not null,
    json_value     jsonb     not null
);

create table if not exists orders
(
    order_id    bigint primary key,
    user_id     bigint    not null,
    last_update timestamp not null,
    data        jsonb     not null
);

create table if not exists product
(
    product_id bigint primary key,
    name       text not null unique,
    price      int4 not null
);

create sequence if not exists order_sequence;
create sequence if not exists product_id_sequence owned by product.product_id;
alter table product
    alter column product_id set default nextval('product_id_sequence');