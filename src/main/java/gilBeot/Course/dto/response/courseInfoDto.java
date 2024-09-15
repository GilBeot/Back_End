package gilBeot.Course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class courseInfoDto {
    private String name;
    private String level;
    private String content;
    private String summary;
    private String tourInfo;
    private String address;
    private String travelInfo;
    private int cnt;
}
