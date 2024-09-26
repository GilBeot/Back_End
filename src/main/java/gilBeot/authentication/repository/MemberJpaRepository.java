package gilBeot.authentication.repository;

import gilBeot.authentication.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {

    boolean existsByUsername(String username);

    Optional<MemberEntity> findByUsername(String username);

    Optional<MemberEntity> findByEmail(String email);
}

