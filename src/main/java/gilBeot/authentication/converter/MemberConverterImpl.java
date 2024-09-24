package gilBeot.authentication.converter;

import gilBeot.authentication.domain.MemberDomain;
import gilBeot.authentication.entity.MemberEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MemberConverterImpl implements MemberConverter {

    @Override
    public MemberEntity toEntity(MemberDomain memberDomain) {
        return MemberEntity.builder()
                .id(memberDomain.getId())
                .username(memberDomain.getUsername())
                .name(memberDomain.getName())
                .email(memberDomain.getEmail())
                .password(memberDomain.getPassword())
                .role(memberDomain.getRole())
                .build();
    }

    @Override
    public MemberDomain toDomain(MemberEntity memberEntity) {
        return MemberDomain.builder()
                .id(memberEntity.getId())
                .username(memberEntity.getUsername())
                .name(memberEntity.getName())
                .email(memberEntity.getEmail())
                .password(memberEntity.getPassword())
                .role(memberEntity.getRole())
                .build();
    }
}
