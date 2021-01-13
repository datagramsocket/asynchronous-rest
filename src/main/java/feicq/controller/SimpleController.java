package feicq.controller;

import feicq.domain.TimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author: fcq
 * @create: 2021-01-13
 */
@RestController
@Slf4j
@RequestMapping("/time")
public class SimpleController {

    @GetMapping("/base")
    public TimeResponse timeBase(){
        log.info("base time request");
        return now();
    }

    public TimeResponse now(){
        return new TimeResponse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }
}
