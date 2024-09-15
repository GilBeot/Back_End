package gilBeot.Course.service;
import gilBeot.Course.Repository.BusinessDataRepository;
import gilBeot.Course.Repository.CourseRepository;
import gilBeot.Course.Repository.ToiletRepository;
import gilBeot.Course.dto.response.courseInfoDto;
import gilBeot.Course.dto.response.getRouteListDto;
import gilBeot.Course.dto.response.searchCourseDto;
import gilBeot.Course.model.course;
import gilBeot.Course.model.routeList;
import gilBeot.Course.model.toilet;
import gilBeot.Course.model.toiletInfo;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Service
public class BusinessDataService {
    private final ToiletRepository toiletRepository;
    private final BusinessDataRepository businessDataRepository;
    private final CourseRepository courseRepository;
    private final RestClient restClient;
    @Value("${app.config.secret_key}")
    String key;

    public BusinessDataService(ToiletRepository toiletRepository, BusinessDataRepository businessDataRepository, CourseRepository courseRepository) {
        this.toiletRepository = toiletRepository;
        this.businessDataRepository = businessDataRepository;
        this.courseRepository = courseRepository;
        this.restClient = RestClient.create();
    }

    private URL makeUrl() throws MalformedURLException {
        String OS = "AND";
        String AppName = "GilBeot";
        String type = "json";

        String urlBuilder = "https://apis.data.go.kr/B551011/Durunubi/routeList" + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + this.key + /*Service Key*/
                "&" + URLEncoder.encode("MobileOS", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(OS, StandardCharsets.UTF_8) + /*페이지 번호*/
                "&" + URLEncoder.encode("MobileApp", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(AppName, StandardCharsets.UTF_8) + /*한 페이지 결과 수 (최소 10, 최대 9999)*/
                "&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(type, StandardCharsets.UTF_8);
        return new URL(urlBuilder);
    }

    public void getRouteList() throws MalformedURLException, URISyntaxException, ParseException {
        URL Url = this.makeUrl();

        ResponseEntity<String> result = this.restClient.get()
                .uri(Url.toURI())
                .retrieve()
                .toEntity(String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result.getBody());
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        System.out.println(body);

        JSONArray item = (JSONArray) items.get("item");

        for(Object value : item){
            JSONObject nowValue = (JSONObject) value;

            routeList newRouteList = routeList.builder()
                    .routeName((String) nowValue.get("themeNm"))
                    .routeIdx((String) nowValue.get("routeIdx"))
                    .content((String) nowValue.get("themedescs"))
                    .build();
            this.registerBusinessData(newRouteList);
        }
    }

    public void registerBusinessData(routeList routelist){
        this.businessDataRepository.save(routelist);
    }

    public List<getRouteListDto> getClientRouteList(){
        List<getRouteListDto> res = new ArrayList<>();
        List<routeList> routeLists = this.businessDataRepository.findAll();
        for(routeList routelist : routeLists){
            getRouteListDto getRouteListDto = new getRouteListDto(routelist.getRouteName(), routelist.getContent());
            res.add(getRouteListDto);
        }

        return res;
    }

    private URI makeCourseDataURI() throws URISyntaxException {

        String OS = "AND";
        String AppName = "GilBeot";
        String type = "json";
        String num = "1000";

        String urlBuilder = "https://apis.data.go.kr/B551011/Durunubi/courseList" + "?" + URLEncoder.encode("serviceKey", StandardCharsets.UTF_8) + "=" + this.key + /*Service Key*/
                "&" + URLEncoder.encode("MobileOS", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(OS, StandardCharsets.UTF_8) + /*페이지 번호*/
                "&" + URLEncoder.encode("MobileApp", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(AppName, StandardCharsets.UTF_8) + /*한 페이지 결과 수 (최소 10, 최대 9999)*/
                "&" + URLEncoder.encode("_type", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(type, StandardCharsets.UTF_8) +
                "&" + URLEncoder.encode("numOfRows", StandardCharsets.UTF_8) + "=" + URLEncoder.encode(num, StandardCharsets.UTF_8);
        return new URI(urlBuilder);
    }

    public void loadCourseList() throws URISyntaxException, ParseException, InterruptedException {
        URI uri = this.makeCourseDataURI();

        ResponseEntity<String> result = this.restClient.get()
                .uri(uri)
                .retrieve()
                .toEntity(String.class);

        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(result.getBody());
        JSONObject response = (JSONObject) jsonObject.get("response");
        JSONObject body = (JSONObject) response.get("body");
        JSONObject items = (JSONObject) body.get("items");
        JSONArray item = (JSONArray) items.get("item");

        for(Object value : item){
            JSONObject nowValue = (JSONObject) value;

            course Course = course.builder()
                    .name(((String) nowValue.get("crsKorNm")).replaceAll(" ", "_"))
                    .address(((String) nowValue.get("sigun")).replaceAll(" ", "_"))
                    .level((String) nowValue.get("crsLevel"))
                    .crsIdx((String) nowValue.get("crsIdx"))
                    .summary((String) nowValue.get("crsSummary"))
                    .content((String) nowValue.get("crsContents"))
                    .tourInfo((String) nowValue.get("crsTourInfo"))
                    .travelInfo((String) nowValue.get("travelerinfo"))
                    .gpxLink((String) nowValue.get("gpxpath"))
                    .cnt(0)
                    .build();
            this.courseRepository.save(Course);
        }
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

    @Transactional
    public String gpxToPoint(){
        List<course> courses = this.courseRepository.findAll();

        try {
            for(course c : courses){
                ResponseEntity<String> result = this.restClient.get()
                        .uri(c.getGpxLink())
                        .retrieve()
                        .toEntity(String.class);

                org.json.JSONObject jsonObject = XML.toJSONObject(result.getBody());
                org.json.JSONObject root = (org.json.JSONObject) jsonObject.get("gpx");
                org.json.JSONObject trk = (org.json.JSONObject) root.get("trk");
                org.json.JSONObject trkseg = (org.json.JSONObject) trk.get("trkseg");
                org.json.JSONArray trkpt = (org.json.JSONArray) trkseg.get("trkpt");

                int cnt = 0;
                double lat = 0.0;
                double lon = 0.0;

                for(Object o : trkpt){
                    cnt++;
                    org.json.JSONObject object = (org.json.JSONObject) o;

                    BigDecimal tmp_lat = (BigDecimal) object.get("lat");
                    BigDecimal tmp_lon = (BigDecimal) object.get("lon");

                    double double_lat = tmp_lat.doubleValue();
                    double double_lon = tmp_lon.doubleValue();

                    lat += double_lat;
                    lon += double_lon;
                }

                c.update( String.valueOf(lat / cnt) , String.valueOf(lon / cnt));
            }
        }catch (Exception e){
            System.out.println(e);
            return "좌표 업데이트 실패";
        }

        return "좌표 업데이트 성공";
    }

    public void createToilet(){
        this.toiletRepository.save(toilet.builder()
                .seoul(new ArrayList<>())
                .build());
    }
    @Transactional
    public String toiletPointData(){
        List<toilet> toilets = this.toiletRepository.findAll();

        toilet t = toilets.get(0);

        t.getSeoul().add(toiletInfo.builder().
                name("화장실이름")
                .build());
        return "dd";
    }

    public void readExel() throws IOException {
        String filePath = "/Users/chobeomhee/Desktop/SEOUL.xlsx";

        FileInputStream fileInputStream = new FileInputStream(new File(filePath));

        Workbook workbook = WorkbookFactory.create(fileInputStream);
        Sheet sheet = workbook.getSheetAt(0);

        //System.out.println(sheet.getRow(0).);

        for(Row row : sheet){
            for(Cell cell : row){
                System.out.print(cell + " ");
            }
            break;
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