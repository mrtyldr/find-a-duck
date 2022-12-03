create table application
(
    application_id uuid primary key,
    entry_id uuid not null,
    employee_id varchar(255) not null,
    applied_on timestamp with time zone,
    status varchar(10) not null
);