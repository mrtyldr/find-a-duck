package com.kodizim.findaduck.domain.entry;


import com.vladmihalcea.hibernate.type.array.StringArrayType;
import lombok.RequiredArgsConstructor;
import org.hibernate.query.NativeQuery;
import org.hibernate.type.OffsetDateTimeType;
import org.hibernate.type.UUIDCharType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Tuple;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public interface EntryRepository extends JpaRepository<Entry, UUID>, EntryQueries {

    boolean existsByCompanyIdAndId(String userId, UUID entryId);


    boolean existsByIdAndCompanyId(UUID entryId, String userId);

    @Query(value = "select e.* from active_entries e order by e.created_on desc", nativeQuery = true)
    List<Entry> getActiveEntries();

    @Modifying
    @Query(value = "call refresh_entry_search()", nativeQuery = true)
    void refreshEntrySearch();

    @Query("select e.expectedProfessions from Entry e where e.id = :entryId")
    List<String> getProfessions(UUID entryId);

    @RequiredArgsConstructor
    class EntryQueriesImpl implements EntryQueries {
        private final EntityManager entityManager;

        @Override
        public List<EntryDto> getEntryDtoForCompany(String companyId) {
            var sql = """
                    select
                    e.id,
                    c.company_name,
                    e.title,
                    e.content,
                    e.category,
                    e.hourly_pay,
                    e.created_on,
                    e.job_start_date,
                    e.valid_til,
                    e.expected_professions
                                
                    from entry_search e
                    inner join company c on e.company_id = c.company_id
                    where e.company_id = :companyId
                    order by e.created_on desc
                    """;
            var query = entityManager.createNativeQuery(sql, Tuple.class)
                    .setParameter("companyId", companyId);
            return toEntryDto(query);
        }

        public List<EntryDto> getEntryDto(String employeeId, String professions) {
            var sql = """
                    select
                    e.id,
                    c.company_name,
                    e.title,
                    e.content,
                    e.category,
                    e.hourly_pay,
                    e.created_on,
                    e.job_start_date,
                    e.valid_til,
                    e.expected_professions
                                
                    from entry_search e
                    inner join company c on e.company_id = c.company_id           
                    order by ts_rank(document, to_tsquery(:professions)) desc
                    """;
            return toEntryDto(entityManager.createNativeQuery(sql,Tuple.class)
                    .setParameter("professions",professions));

        }

        @Override
        public List<EntryDto> entrySearchEmployee(String searchString) {
            return entrySearchResult(searchString,null);
        }
        private List<EntryDto> entrySearchResult(String searchString,String companyId) {
            var entryDtosql =
                    """
                    select
                    e.id,
                    c.company_name,
                    e.title,
                    e.content,
                    e.category,
                    e.hourly_pay,
                    e.created_on,
                    e.job_start_date,
                    e.valid_til,
                    e.expected_professions
                                
                    from entry_search e
                    inner join company c on e.company_id = c.company_id
                    where e.document @@ to_tsquery(:searchString)
                    """;
            javax.persistence.Query query;
            if(companyId != null) {
                entryDtosql = entryDtosql + " and e.company_id = :companyId order by ts_rank(document,to_tsquery(:searchString)) desc";
                 query = entityManager.createNativeQuery(entryDtosql, Tuple.class)
                        .setParameter("searchString",searchString)
                         .setParameter("companyId",companyId);
            } else {
                entryDtosql = entryDtosql +" order by ts_rank(document,to_tsquery(:searchString)) desc";
                query = entityManager.createNativeQuery(entryDtosql, Tuple.class)
                        .setParameter("searchString",searchString);
            }
            return toEntryDto(query);
        }

        @Override
        public List<EntryDto> entrySearchCompany(String searchString, String userId) {
            return entrySearchResult(searchString,userId);
        }

        private List<EntryDto> toEntryDto(javax.persistence.Query query) {
            var tuple1 = query.unwrap(NativeQuery.class)
                    .addScalar("id", UUIDCharType.INSTANCE)
                    .addScalar("company_name")
                    .addScalar("title")
                    .addScalar("content")
                    .addScalar("category")
                    .addScalar("hourly_pay")
                    .addScalar("created_on", OffsetDateTimeType.INSTANCE)
                    .addScalar("job_start_date", OffsetDateTimeType.INSTANCE)
                    .addScalar("valid_til", OffsetDateTimeType.INSTANCE)
                    .addScalar("expected_professions", StringArrayType.INSTANCE);

                 var tuple =  (List<Tuple>)tuple1.getResultList();

            return tuple.stream().map(t -> {
                var category = Category.valueOf(t.get("category",String.class));
                var professions = getList(t,"expected_professions");
               return new EntryDto(
                    t.get("id", UUID.class),
                    t.get("company_name", String.class),
                    t.get("title", String.class),
                    t.get("content", String.class),
                    category,
                    t.get("hourly_pay", BigDecimal.class),
                    t.get("created_on", OffsetDateTime.class),
                    t.get("job_start_date", OffsetDateTime.class),
                    t.get("valid_til", OffsetDateTime.class),
                    professions
               );
                    }
            ).toList();

        }

        private <T>List<T> getList(Tuple tuple, String column) {
            T[] array = (T[]) tuple.get(column);
            if (array == null)
                return List.of();
            return Stream.of(array).collect(Collectors.toList());
        }
    }


}
