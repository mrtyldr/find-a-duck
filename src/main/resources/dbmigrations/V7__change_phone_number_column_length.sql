alter table employee
    alter column phone_number type varchar(15);

drop materialized view if exists active_entries;

alter table entry
    alter column content type text;