create table job
(
    job_id uuid primary key ,
    entry_id uuid not null,
    employee_id varchar(255) not null,
    employee_rating int,
    company_rating int
);