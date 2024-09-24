package gilBeot.authentication.service;

import gilBeot.authentication.domain.dto.request.SignupRequestDto;
import gilBeot.authentication.domain.dto.response.MemberResponseDto;
import gilBeot.authentication.domain.dto.response.SignupResponseDto;

public interface MemberService {

    SignupResponseDto signUp(SignupRequestDto signupRequestDto);

    MemberResponseDto findByUsername(String username);
}
