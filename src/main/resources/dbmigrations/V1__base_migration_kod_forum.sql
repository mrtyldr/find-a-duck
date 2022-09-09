create table category
(
    id          uuid primary key,
    name        varchar(255)             not null,
    about       varchar(255),
    created_on  timestamp with time zone not null,
    modified_on timestamp with time zone not null
);

create table entry
(
    id          uuid primary key,
    category_id uuid                     not null,
    title       varchar(255)             not null,
    content     varchar(255)             not null,
    created_on  timestamp with time zone not null,
    modified_on timestamp with time zone not null
);

create table users
(
    user_id varchar(255) primary key not null,
    user_name varchar(255) not null,
    email varchar(255) not null
);