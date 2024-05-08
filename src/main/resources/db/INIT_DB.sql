# Create schema and tables
CREATE DATABASE emailserver;

CREATE USER 'msapi'@'%' IDENTIFIED BY 'msapi123';
GRANT ALL PRIVILEGES ON emailserver.* TO 'msapi'@'%' WITH GRANT OPTION;
FLUSH PRIVILEGES;

use emailserver;

# Create email table

create table if not exists email
(
    email_id           bigint auto_increment
        primary key,
    email_body         varchar(255) not null,
    email_cc           varchar(255) null,
    email_from         varchar(255) not null,
    email_to           varchar(255) not null,
    state              varchar(255) not null,
    created_by         varchar(255) null,
    created_date       datetime(6)  null,
    last_modified_by   varchar(255) null,
    last_modified_date datetime(6)  null
);
