package by.zemich.kufar.dao.repository;

import by.zemich.kufar.dao.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, UUID> {

    boolean existsByAdId(long adId);

    @Query("SELECT a FROM Advertisement a where a.parameters.size = 15")
    List<Advertisement> findAllByModel(String model);

}
