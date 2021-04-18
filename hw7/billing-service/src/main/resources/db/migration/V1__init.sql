create table if not exists account
(
    account_id bigint primary key,
    user_id    bigint not null,
    money      bigint not null
);

create table if not exists operation_log
(
    operation_uuid uuid primary key,
    account_id     bigint    not null,
    execution_time timestamp not null,
    data           jsonb     not null
);

create table if not exists operation_code
(
    operation_code_id int primary key,
    operation         text not null
);

insert into operation_code
values (1, 'TOP_UP'),
       (2, 'WITHDRAW');

create sequence if not exists account_sequence owned by account.account_id;
alter table account
    alter column account_id set default nextval('account_sequence');

create sequence if not exists operation_code_id_sequence owned by operation_code.operation_code_id;
alter table operation_code
    alter column operation_code_id set default nextval('operation_code_id_sequence');