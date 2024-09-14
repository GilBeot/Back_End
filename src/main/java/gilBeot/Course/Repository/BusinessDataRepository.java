package gilBeot.Course.Repository;

import gilBeot.Course.model.routeList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BusinessDataRepository extends JpaRepository<routeList, Long> {

}
