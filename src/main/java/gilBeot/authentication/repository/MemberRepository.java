package gilBeot.authentication.repository;

import gilBeot.authentication.domain.MemberDomain;
import gilBeot.authentication.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository {

    MemberDomain save(MemberDomain memberDomain);

    boolean existsByUsername(String username);

    Optional<MemberDomain> findByUsername(String username);

    Optional<MemberDomain> findByEmail(String email);
}
