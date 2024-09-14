package gilBeot.Course.controller;

import gilBeot.Course.service.BusinessDataService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;

@RestController
public class BusinessDataController {
    private final BusinessDataService businessDataService;

    public BusinessDataController(BusinessDataService businessDataService) {
        this.businessDataService = businessDataService;
    }

    @GetMapping("/gpxData")
    public String gpxToPoint(){
        return this.businessDataService.gpxToPoint();
    }

    @GetMapping("/toilet")
    public String toiletData(){
        this.businessDataService.createToilet();
        return this.businessDataService.toiletPointData();
    }
    @GetMapping("/testExel")
    public void exel() throws IOException {
        this.businessDataService.readExel();
    }
}
