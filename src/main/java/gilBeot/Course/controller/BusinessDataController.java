package gilBeot.Course.controller;

import gilBeot.Course.model.toiletInfo;
import gilBeot.Course.service.BusinessDataService;
import org.json.simple.parser.ParseException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
public class BusinessDataController {
    private final Map<String, String> KorToEngName;
    private final BusinessDataService businessDataService;

    public BusinessDataController(BusinessDataService businessDataService) {
        this.KorToEngName = new HashMap<>();
        this.KorToEngName.put("서울", "seoul");

        this.businessDataService = businessDataService;
    }

    @GetMapping("/gpxData")
    public String gpxToPoint(){
        return this.businessDataService.gpxToPoint();
    }

    @GetMapping("/toilet")
    public void toiletCreate(){
        this.businessDataService.createToilet();
    }
    @GetMapping("/toiletData/{fileName}")
    public ResponseEntity<String> toiletData (@PathVariable String fileName) throws IOException {
        return ResponseEntity.ok(this.businessDataService.readExel(fileName));
    }

    @GetMapping("/courseList")
    private void loadCourseList() throws URISyntaxException, ParseException, InterruptedException {
        this.businessDataService.loadCourseList();
        // 넌 데브야!!!
    }
    @GetMapping("/courseRoot")
    private void courseRoot() throws IOException, URISyntaxException, ParserConfigurationException, SAXException {
        for(int i = 1; i < 306; i++){
            this.businessDataService.courseRoot(i - 1, i);
        }
//        this.businessDataService.courseRoot(51, 100);
//        this.businessDataService.courseRoot(101, 150);
//        this.businessDataService.courseRoot(151, 200);
//        this.businessDataService.courseRoot(201, 250);
//        this.businessDataService.courseRoot(251, 300);
//        this.businessDataService.courseRoot(301, 306);
    }

    @GetMapping("/test")
    private List<toiletInfo> test(){
        return this.businessDataService.test("서울");
    }
}
