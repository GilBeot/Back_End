package gilBeot.authentication.repository;

import gilBeot.authentication.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Boolean existsByUsername(String username);

    Member findByUsername(String username); //username을 받아 DB 테이블에서 회원을 조회
}
