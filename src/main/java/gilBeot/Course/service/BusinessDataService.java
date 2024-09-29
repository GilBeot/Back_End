package gilBeot.Course.service;
import gilBeot.Course.Repository.BusinessDataRepository;
import gilBeot.Course.Repository.CourseRepository;
import gilBeot.Course.Repository.ToiletRepository;
import gilBeot.Course.dto.response.courseInfoDto;
import gilBeot.Course.dto.response.getRouteListDto;
import gilBeot.Course.dto.response.searchCourseDto;
import gilBeot.Course.model.*;
import jakarta.transaction.Transactional;
import org.apache.poi.ss.usermodel.*;
import org.apache.xml.security.parser.XMLParser;
import org.json.XML;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.math.BigDecimal;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.Arrays.asList;

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
                .toiletInfoList(new ArrayList<>())
//                .busan(new ArrayList<>())
//                .daegu(new ArrayList<>())
//                .incheon(new ArrayList<>())
//                .gwangju(new ArrayList<>())
//                .daejeon(new ArrayList<>())
//                .ulsan(new ArrayList<>())
//                .sejong(new ArrayList<>())
//                .gyeonggi(new ArrayList<>())
//                .gangwon(new ArrayList<>())
//                .chungbuk(new ArrayList<>())
//                .chungnam(new ArrayList<>())
//                .jeonbuk(new ArrayList<>())
//                .jeonnam(new ArrayList<>())
//                .gyeongbuk(new ArrayList<>())
//                .gyeongnam(new ArrayList<>())
//                .jeju(new ArrayList<>())
                .build());
    }


    @Transactional
    public String readExel(String fileName) throws IOException {
        String filePath = "/Users/chobeomhee/Desktop/" + fileName + ".csv";

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();

        List<String> headerList = new ArrayList<String>();

        try{
            BufferedReader br = Files.newBufferedReader(Paths.get(filePath));
            String line = "";

            while((line = br.readLine()) != null){
                List<String> stringList = new ArrayList<>();
                String[] stringArray = line.split(",");

                stringList = asList(stringArray);

                // csv 1열 데이터를 header로 인식
                if(headerList.isEmpty()){
                    headerList = stringList;
                } else {
                    Map<String, Object> map = new HashMap<String, Object>();
                    // header 컬럼 개수를 기준으로 데이터 set
                    int idx = Math.min(headerList.size(), stringList.size());
                    if(headerList.size() != stringList.size()) continue;
                    for(int i = 0; i < idx; i++){
                        map.put(headerList.get(i).replaceAll("\"", ""), stringList.get(i).replaceAll("\"", ""));
                    }
                    mapList.add(map);
                }
            }
        }catch (Exception e){
            e.printStackTrace();

            return fileName + " 파일 읽던 중 문제 발생";
        }

        for (Map<String, Object> Info : mapList) {
            System.out.println(Info);
            System.out.print(Info.get("화장실명"));
        }

            try{
            toilet t = toiletRepository.findAll().get(0);

            for (Map<String, Object> Info : mapList) {
                toiletInfo newData = toiletInfo.builder()
                        .name((String) Info.get("화장실명"))
                        .address((String) Info.get("소재지도로명주소"))
                        .openTime((String) Info.get("개방시간상세"))
                        .office((String) Info.get("관리기관명"))
                        .X((String) Info.get("WGS84위도"))
                        .Y((String) Info.get("WGS84경도"))
                        .city(fileName)
                        .build();
                t.getToiletInfoList().add(newData);

//                switch (fileName) {
//                    case "서울" -> t.getSeoul().add(newData);
//                    case "부산" -> t.getBusan().add(newData);
//                    case "대구" -> t.getDaegu().add(newData);
//                    case "인천" -> t.getIncheon().add(newData);
//                    case "광주" -> t.getGwangju().add(newData);
//                    case "대전" -> t.getDaejeon().add(newData);
//                    case "울산" -> t.getUlsan().add(newData);
//                    case "세종" -> t.getSejong().add(newData);
//                    case "경기" -> t.getGyeonggi().add(newData);
//                    case "강원" -> t.getGangwon().add(newData);
//                    case "충북" -> t.getChungbuk().add(newData);
//                    case "충남" -> t.getChungnam().add(newData);
//                    case "전북" -> t.getJeonbuk().add(newData);
//                    case "전남" -> t.getJeonnam().add(newData);
//                    case "경북" -> t.getGyeongbuk().add(newData);
//                    case "경남" -> t.getGyeongnam().add(newData);
//                    case "제주" -> t.getJeju().add(newData);
//                }
            }
        }catch (Exception e){
            return fileName + " DB삽입 중 문제 발생";
        }

        return fileName + " 화장실 정보 삽입 성공";
    }

    @Transactional
    public void courseRoot(int start, int end) throws IOException, URISyntaxException, ParserConfigurationException, SAXException {
        List<course> AllCourse = this.courseRepository.findAll();

        for(int idx = start; idx < end; idx++){
            course c = AllCourse.get(idx);
            try{
            URL Url = new URL(c.getGpxLink());

            ResponseEntity<String> result = this.restClient.get()
                    .uri(Url.toURI())
                    .retrieve()
                    .toEntity(String.class);

            String[] splitString = result.toString().split("\n");
            StringBuilder xml = new StringBuilder();
            for(int i = 1; i < splitString.length - 1; i++){
                xml.append(splitString[i] + "\n");
            }


                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder builder = factory.newDocumentBuilder();
                Document document = builder.parse(new InputSource(new StringReader(xml.toString())));

                document.getDocumentElement().normalize();

                NodeList tagList = document.getElementsByTagName("trkpt");

                for (int i = 0; i < tagList.getLength(); i++) {
                    Element element = (Element) tagList.item(i);
                    String lat = element.getAttribute("lat");
                    String lon = element.getAttribute("lon");

                    c.getCourseRoot().add(coursePoint.builder()
                            .X(lat)
                            .Y(lon)
                            .build());
                }
            }catch (Exception e){
                System.out.println("오류가 났지만 넘어갑니다.");
            }
        }

        System.out.println("코스 좌표 삽입 완료");
    }

    public List<toiletInfo> test(String city){
        return this.toiletRepository.findByCityToilet(city);
    }

    static class Point {
        String x, y;
        Point(String x, String y) {
            this.x = x;
            this.y = y;
        }
    }
}