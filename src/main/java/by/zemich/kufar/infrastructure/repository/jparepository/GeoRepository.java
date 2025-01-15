package by.zemich.kufar.infrastructure.repository.jparepository;

import by.zemich.kufar.domain.model.GeoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GeoRepository extends JpaRepository<GeoData, Integer> {
    @Query("""
            SELECT g FROM geo g
            WHERE g.region = ?1 and g.pid = 1
            """)
    List<GeoData> findAllByRegionNumber(int regionNumber);

    @Query("""
            SELECT g from geo g
            WHERE g.region = ?1
            AND g.type NOT IN ('country','area','region')
            """)
    List<GeoData> findAllSettlementsByRegionNumber(int regionNumber);

    @Query("""
            SELECT g FROM geo g
            WHERE g.pid = 1 AND g.type LIKE 'area'
            """)
    List<GeoData> findAllByRegions();
}
