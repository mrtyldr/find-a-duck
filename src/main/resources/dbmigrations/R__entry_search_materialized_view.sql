drop materialized view if exists entry_search;
create materialized view entry_search
as
select    e.id,
          e.company_id,
          c.company_name,
          e.title,
          e.content,
          e.category,
          e.hourly_pay,
          e.created_on,
          e.job_start_date,
          e.valid_til,
          e.expected_professions,
       (
        setweight(to_tsvector(e.title), 'A') ||
        setweight(to_tsvector(e.category), 'B') ||
        setweight(to_tsvector(e.content), 'C') ||
        setweight(to_tsvector(coalesce(array_to_string(e.expected_professions, ' '),' ')::text),'D')
           ) as document
from entry e
         inner join company c on e.company_id = c.company_id
where e.status = 'ACTIVE';
create unique index on entry_search (document);
create or replace procedure refresh_entry_search()
    language plpgsql
as
$$
BEGIN
    refresh materialized view concurrently entry_search;
end;
$$