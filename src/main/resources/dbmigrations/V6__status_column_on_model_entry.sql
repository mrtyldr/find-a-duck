alter table entry
    add column status varchar(10);
alter table entry
    add column job_start_date timestamp with time zone;

alter table job
    add column start_date timestamp with time zone;
