package gilBeot.Course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
public class getRouteListDto {
    private String routeName;
    private String content;
}
