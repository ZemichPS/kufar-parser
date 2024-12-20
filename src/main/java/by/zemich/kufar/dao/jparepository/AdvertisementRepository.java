package by.zemich.kufar.dao.jparepository;

import by.zemich.kufar.dao.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {

    boolean existsByAdId(Long adId);

    @Query(value = """
            SELECT a.*
            FROM app.advertisements a
            WHERE a.parameters @> CAST(:parameters AS jsonb)
            """, nativeQuery = true)
    List<Advertisement> findAllByParameters(@Param("parameters") String parameters);

}
