package gilBeot.authentication.converter;

import gilBeot.authentication.domain.MemberDomain;
import gilBeot.authentication.entity.MemberEntity;

public interface MemberConverter {

    MemberEntity toEntity(MemberDomain memberDomain);

    MemberDomain toDomain(MemberEntity memberEntity);
}
