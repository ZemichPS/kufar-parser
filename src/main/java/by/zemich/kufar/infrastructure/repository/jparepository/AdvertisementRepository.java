package by.zemich.kufar.infrastructure.repository.jparepository;

import by.zemich.kufar.domain.model.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {

    boolean existsByAdId(Long adId);

    boolean existsByPublishedAtAndAdIdAndCategory(LocalDateTime localDateTime, Long adId, String category);

    @Query(value = """
            SELECT a.*
            FROM app.advertisements a
            WHERE a.parameters @> CAST(:parameters AS jsonb)
            """, nativeQuery = true)
    List<Advertisement> findAllByParameters(@Param("parameters") String parameters);

}
