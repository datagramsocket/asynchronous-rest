package feicq.controller;

import feicq.domain.TimeResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;

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

    @GetMapping("/re")
    public ResponseEntity<TimeResponse>  timeResponseEntity(){
        log.info("responseEntity time request");
        return new ResponseEntity(now(), HttpStatus.OK);
    }

    @GetMapping("/callable")
    public Callable<TimeResponse> timeCallable(){
        log.info("callable time request");
        return () -> now();
    }

    @GetMapping("/deferred")
    public DeferredResult<TimeResponse> timeDeferredResult(){
        log.info("deferredResult time request");
        DeferredResult deferredResult = new DeferredResult();
        Executors.newFixedThreadPool(1).submit(() -> {
            deferredResult.setResult(now());
        });
        return deferredResult;

    }

    public TimeResponse now(){
        log.info("Creating TimeResponse");
        return new TimeResponse(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
    }


}
