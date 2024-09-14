package gilBeot.authentication.controller;

import gilBeot.authentication.dto.request.MemberRequestDTO;
import gilBeot.authentication.service.JoinService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@ResponseBody
@RequiredArgsConstructor
public class JoinController {

    private final JoinService joinService;
    @PostMapping("/join")
    public String join(MemberRequestDTO memberRequestDTO) {
        joinService.join(memberRequestDTO);
        return "OK";
    }
}
