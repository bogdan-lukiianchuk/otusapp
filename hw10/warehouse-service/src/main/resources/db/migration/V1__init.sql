create table orders
(
    order_id             bigint primary key,
    status               text      not null,
    list_reservation_ids bigint[],
    created_time         timestamp not null,
    reservation_end_time timestamp not null
);


create table replenishment
(
    replenishment_id   bigint primary key,
    replenishment_code text,
    supplies           jsonb
);

create sequence if not exists replenishment_sequence owned by replenishment.replenishment_id;

alter table replenishment
    alter column replenishment_id set default nextval('replenishment_sequence');

create index replenishment_code_idx on replenishment (replenishment_code);

create table supply
(
    supply_id bigint primary key,
    count     int4 not null
);

create table reservation
(
    reservation_id bigint primary key,
    supply_id      bigint references supply (supply_id) not null,
    count          int4                                 not null
);

create sequence if not exists reservation_sequence owned by reservation.reservation_id;

alter table reservation
    alter column reservation_id set default nextval('reservation_sequence');

