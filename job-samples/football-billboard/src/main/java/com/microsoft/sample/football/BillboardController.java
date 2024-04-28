package com.microsoft.sample.football;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BillboardController {
    
    @Autowired
    private BillboardService billboardService;
    
    @GetMapping(value = "/api/result")
    public ResultReport sayHello() {
        return billboardService.getResultReport();
    }

    
    @PostMapping(value = "/api/result/update")
    public void updateResultReport(@RequestBody ResultReport resultReport) {
        if(resultReport != null) {
            billboardService.setResultReport(resultReport);
        }
    }
}
