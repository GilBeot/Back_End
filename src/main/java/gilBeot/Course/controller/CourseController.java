package gilBeot.Course.controller;

import gilBeot.Course.dto.response.courseInfoDto;
import gilBeot.Course.dto.response.getRouteListDto;
import gilBeot.Course.dto.response.searchCourseDto;
import gilBeot.Course.service.BusinessDataService;
import gilBeot.Course.service.CourseService;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CourseController {
    private final BusinessDataService businessDataService;
    private final CourseService courseService;
    public CourseController(BusinessDataService businessDataService, CourseService courseService) {
        this.businessDataService = businessDataService;
        this.courseService = courseService;
    }

    // 공공데이터 -> 서버 길 get
    @GetMapping("/routeList")
    private void loadRouteList() throws MalformedURLException, URISyntaxException, ParseException {
        this.businessDataService.getRouteList();
    }

    // 서버 -> 클라이언트 길 get
    @GetMapping("/getRouteList")
    private ResponseEntity<List<getRouteListDto>> getRouteList() {
        return new ResponseEntity<>(this.businessDataService.getClientRouteList(), HttpStatus.OK);
    }
    @GetMapping("/courseList")
    private void loadCourseList() throws URISyntaxException, ParseException, InterruptedException {
        this.businessDataService.loadCourseList();
    }

    /**
     *
     * @param courseName :: 코스이름
     * @return :: 코스별 정보
     */
    @GetMapping("/course/{courseName}")
    private ResponseEntity<courseInfoDto> getCourseInfo(@PathVariable String courseName) {
        return ResponseEntity.ok().body(this.courseService.getCourseInfo(courseName));
    }

    /**
     *
     * @return :: Top 3 인기 코스
     */
    @GetMapping("/course/rank")
    private ResponseEntity<List<String>> getRankCourse(){
        return new ResponseEntity<List<String>>(this.courseService.getRankCourse(), HttpStatus.OK);
    }

    /**
     *
     * @param location :: 서울시 광진구 등
     * @return :: 가장 가까운 코스
     */
    @GetMapping("/course/search/{location}")
    private ResponseEntity<List<searchCourseDto>> getSearchCourse(@PathVariable String location){
        return new ResponseEntity<>(this.courseService.getSearchCourse(location), HttpStatus.OK);
    }

    @GetMapping("/course/age/{userAge}")
    private ResponseEntity<List<String>> getAgeRankCourse(@PathVariable String userAge){
        return new ResponseEntity<>(this.courseService.getAngeRankCourse(userAge), HttpStatus.OK);
    }
}
