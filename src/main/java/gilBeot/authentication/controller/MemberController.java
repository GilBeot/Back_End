package gilBeot.authentication.controller;

import gilBeot.authentication.domain.dto.request.SignupRequestDto;
import gilBeot.authentication.domain.dto.response.MemberResponseDto;
import gilBeot.authentication.domain.dto.response.SignupResponseDto;
import gilBeot.authentication.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/members")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/signup")
    public ResponseEntity<SignupResponseDto> signup(@RequestBody SignupRequestDto signupRequestDto) {
        SignupResponseDto signupResponseDto = memberService.signUp(signupRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(signupResponseDto);
    }

    @GetMapping("/myPage")
    public ResponseEntity<MemberResponseDto> myPage() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        MemberResponseDto memberResponseDto = memberService.findByUsername(username);
        return ResponseEntity.status(HttpStatus.OK).body(memberResponseDto);
    }
}
