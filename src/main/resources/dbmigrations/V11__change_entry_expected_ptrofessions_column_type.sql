drop materialized view if exists active_entries;
alter table entry alter column expected_professions type text[];
delete from entry where expected_professions is null;

drop materialized view if exists active_entries;
create materialized view active_entries
as
select e.*
from entry e
where e.status = 'ACTIVE';
create unique index on active_entries (id);
create index on active_entries (company_id);
create or replace procedure refresh_active_entries()
    language plpgsql
as
$$
begin
    refresh materialized view concurrently active_entries;
end;
$$;