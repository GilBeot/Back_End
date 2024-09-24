package gilBeot.authentication.repository;

import gilBeot.authentication.domain.MemberDomain;
import gilBeot.authentication.converter.MemberConverter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository{

    private final MemberJpaRepository memberJpaRepository;
    private final MemberConverter memberConverter;

    @Override
    public MemberDomain save(MemberDomain memberDomain) {
        return memberConverter.toDomain(
                memberJpaRepository.save(memberConverter.toEntity(memberDomain)));
    }

    @Override
    public boolean existsByUsername(String username) {
        return memberJpaRepository.existsByUsername(username);
    }

    @Override
    public Optional<MemberDomain> findByUsername(String username) {
        return memberJpaRepository.findByUsername(username)  // Optional<MemberEntity>를 반환
                .map(memberConverter::toDomain);  // Optional 내부의 MemberEntity를 MemberDomain으로 변환
        //map()은 Optional의 메서드로, Optional 내부의 값이 있을 때만 변환을 적용하고, 값이 없으면 아무런 동작도 하지 않으며 Optional.empty()를 그대로 반환
    }

}
