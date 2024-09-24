package gilBeot.Course.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class surroundInfoDto {
    List<point> point_toilet = new ArrayList<>();
    List<point> point_course = new ArrayList<>();

    @Data
    @AllArgsConstructor
    static class point{
        String x, y;
    }

    public void addCoursePoint(String x, String y){
        this.point_course.add(new point(x, y));
    }
    public void addToiletPoint(String x, String y){
        this.point_toilet.add(new point(x, y));
    }
}
