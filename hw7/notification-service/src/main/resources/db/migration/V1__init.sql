create table if not exists email_notification
(
    email_notification_id uuid primary key,
    user_id               bigint    not null,
    message               text      not null,
    send_time             timestamp not null
);
