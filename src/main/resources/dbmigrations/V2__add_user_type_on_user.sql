alter table users
add column user_type varchar(10);

update users
set user_type = 'EMPLOYEE'
where user_type is null;