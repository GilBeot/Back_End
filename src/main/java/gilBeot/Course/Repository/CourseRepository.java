package gilBeot.Course.Repository;

import gilBeot.Course.model.course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<course, Long> {
    @Query(value = "SELECT c FROM course c WHERE c.name = :name")
    course findByName(@Param("name") String name);

    @Query(value = "SELECT c FROM course c ORDER BY c.cnt DESC")
    List<course> findTop3Course();

    @Query(value = "SELECT c FROM course c WHERE c.address = :address")
    List<course> findSearchCourse(@Param("address") String address);

    @Query(value = "SELECT c FROM  course c WHERE c.level = :level ORDER BY c.cnt")
    List<course> findByAgeRank(@Param("level") String level);
}
