package feicq.controller;

import feicq.domain.AggregateResponse;
import feicq.domain.ApiRequest;
import feicq.domain.ApiResponse;
import feicq.domain.Task;
import feicq.service.AggregatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;

/**
 * @author: fcq
 * @create: 2021-01-13
 */
@RestController
public class AggregatorController {

    @Autowired
    private AggregatorService service;

    @PostMapping("/aggregator")
    public DeferredResult<ResponseEntity<AggregateResponse>> aggregator(@RequestBody ApiRequest apiRequest){
        DeferredResult<ResponseEntity<AggregateResponse>> deferredResult = new DeferredResult();
        Task task = new Task(deferredResult, apiRequest);
        service.execute(task);
        return deferredResult;
    }
}
