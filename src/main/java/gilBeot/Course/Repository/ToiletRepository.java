package gilBeot.Course.Repository;

import gilBeot.Course.model.toilet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ToiletRepository extends JpaRepository<toilet, Long> {
}
