package by.zemich.kufar.dao.repository;

import by.zemich.kufar.dao.entity.GeoData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GeoRepository extends JpaRepository<GeoData, Integer> {
    @Query("""
            SELECT g FROM geo g
            WHERE g.region = ?1 and g.pid = 1
            """)
    List<GeoData> findAllByRegionNumber(int regionNumber);

    @Query("""
            SELECT g from geo g
            WHERE g.region = ?1 
            AND g.type NOT IN ('country','area')
            """)
    List<GeoData> findAllSettlementsByRegionNumber(int regionNumber);

}
