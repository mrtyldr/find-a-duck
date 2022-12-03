create table entry
(
    id                   uuid primary key,
    category             varchar(255)             not null,
    title                varchar(255)             not null,
    company_id          varchar(255)                     not null,
    content              varchar(255)             not null,
    hourly_pay           numeric                  not null,
    valid_til            timestamp with time zone not null,
    created_on           timestamp with time zone not null,
    modified_on          timestamp with time zone not null,
    expected_professions uuid[]
);

create table profession
(
    profession_id   uuid primary key,
    profession_name varchar(255) not null
);


create table employee
(
    employee_id        varchar(255) primary key,
    name               varchar(255),
    surname            varchar(255),
    email              varchar(255) not null,
    phone_number       varchar(10),
    photo_location_key varchar(255),
    birth_date         DATE,
    about              text,
    professions        uuid[]
);

create table company
(
    company_id         varchar(255) primary key,
    company_name       varchar(255) ,
    phone_number       varchar(15)  ,
    email              varchar(255) not null,
    about              text,
    photo_location_key varchar(255)
);