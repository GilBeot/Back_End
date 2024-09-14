package gilBeot.Course.service;

import gilBeot.Course.Repository.CourseRepository;
import gilBeot.Course.dto.response.courseInfoDto;
import gilBeot.Course.dto.response.searchCourseDto;
import gilBeot.Course.model.course;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class CourseService {
    private final CourseRepository courseRepository;

    public CourseService(CourseRepository courseRepository) {
        this.courseRepository = courseRepository;
    }

    // 나이별 검색
    public List<String> getAngeRankCourse(String age){
        int Age = Integer.parseInt(age);
        String level;
        if(Age < 30){ // 10대 ~ 20대
            level = "3";
        }else if(Age < 50){ // 30대 ~ 40대
            level = "2";
        }else{ // 50대 이상
            level = "1";
        }
        List<course> ageRank = this.courseRepository.findByAgeRank(level);
        List<String> res = new ArrayList<>();
        for(int i = 0; i < ageRank.size(); i++){
            if(i == 10) break;
            res.add(ageRank.get(i).getName());
        }

        return res;
    }

    // 현재 랭킹
    public List<String> getRankCourse(){
        List<course> rank = this.courseRepository.findTop3Course();
        List<String> res = new ArrayList<>();
        for(int i = 0; i < 3; i++) res.add(rank.get(i).getName());

        return res;
    }

    // 지역 검색
    public List<searchCourseDto> getSearchCourse(String location){
        List<course> courseList = this.courseRepository.findAll();
        List<searchCourseDto> res = new ArrayList<>();
        Point searchPoint = this.addressToPoint(location.replaceAll("_", " "));

        double minDistance = 1000.0;
        double spX = Double.parseDouble(searchPoint.x);
        double spY = Double.parseDouble(searchPoint.y);
        for(course c : courseList){
            double nowX = Double.parseDouble(c.getX());
            double nowY = Double.parseDouble(c.getY());

            double nowDist = Math.sqrt(Math.pow((spX - nowX), 2.0) + Math.pow((spY - nowY), 2.0));

            minDistance = Math.min(minDistance, nowDist);
        }

        for(course c : courseList) {
            double nowX = Double.parseDouble(c.getX());
            double nowY = Double.parseDouble(c.getY());

            double nowDist = Math.sqrt(Math.pow((spX - nowX), 2.0) + Math.pow((spY - nowY), 2.0));

            if(nowDist == minDistance) {
                res.add(searchCourseDto.builder()
                        .dist(Double.toString(minDistance))
                        .name(c.getName())
                        .build());
            }
        }

        return res;
    }

    // 코스별 상세정보
    public courseInfoDto getCourseInfo(String courseName){
        course findcourse = this.courseRepository.findByName(courseName);

        return courseInfoDto.builder()
                .name(findcourse.getName())
                .level(findcourse.getLevel())
                .content(findcourse.getContent())
                .summary(findcourse.getSummary())
                .tourInfo(findcourse.getTourInfo())
                .address(findcourse.getAddress())
                .travelInfo(findcourse.getTravelInfo())
                .cnt(findcourse.getCnt())
                .build();
    }

    public Point addressToPoint(String address){
        String apikey = "CEA2FEFE-AEC6-3DC5-9B56-2867B353228A";
        String searchType = "parcel";
        String epsg = "epsg:4326";

        StringBuilder sb = new StringBuilder("https://api.vworld.kr/req/address");
        sb.append("?service=address");
        sb.append("&request=getCoord");
        sb.append("&format=json");
        sb.append("&crs=").append(epsg);
        sb.append("&key=").append(apikey);
        sb.append("&type=").append(searchType);
        sb.append("&address=").append(URLEncoder.encode(address, StandardCharsets.UTF_8));

        try{
            URL url = new URL(sb.toString());
            BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
            JSONParser jspa = new JSONParser();
            JSONObject jsob = (JSONObject) jspa.parse(reader);
            JSONObject jsrs = (JSONObject) jsob.get("response");
            JSONObject jsResult = (JSONObject) jsrs.get("result");
            JSONObject jspoitn = (JSONObject) jsResult.get("point");

            return new Point(jspoitn.get("x").toString(), jspoitn.get("y").toString());

        } catch (IOException | ParseException e) {
            throw new RuntimeException(e);
        }
    }

    static class Point {
        String x, y;
        Point(String x, String y) {
            this.x = x;
            this.y = y;
        }
    }
}
