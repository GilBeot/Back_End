package gilBeot.Course.Repository;

import gilBeot.Course.model.toilet;
import gilBeot.Course.model.toiletInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ToiletRepository extends JpaRepository<toilet, Long> {
    @Query("SELECT ti FROM toilet t JOIN t.toiletInfoList ti WHERE ti.city = :city")
    List<toiletInfo> findByCityToilet(@Param("city") String city);
}
