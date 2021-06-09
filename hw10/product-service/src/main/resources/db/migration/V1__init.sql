create table product
(
    product_id        bigint primary key,
    name              text,
    price             int4,
    availability_date timestamp,
    category          text,
    brand             text
);

create sequence product_id_sequence owned by product.product_id;

alter table product
    alter column product_id set default nextval('product_id_sequence');