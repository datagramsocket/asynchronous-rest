package feicq.domain;

import lombok.Data;
import okhttp3.Response;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @author: fcq
 * @create: 2021-01-13
 */
@Data
public class Task {

    private DeferredResult<ResponseEntity<AggregateResponse>> deferredResult;
    private ApiRequest apiRequest;
    private List<ApiResponse> apiResponseList;
    private AtomicInteger atomicInteger = new AtomicInteger();

    public Task(DeferredResult deferredResult, ApiRequest apiRequest){
        this.deferredResult = deferredResult;
        this.apiRequest = apiRequest;
        apiResponseList = apiRequest.getUrls().stream().map((url) -> new ApiResponse()).collect(Collectors.toList());
        atomicInteger.set(apiRequest.getUrls().size());
    }

    public void success(int index, Response response) throws Exception{
        ApiResponse apiResponse = apiResponseList.get(index);
        apiResponse.setStatus(response.code());
        apiResponse.setBody(response.body().string());
        checkIsDone();
    }

    public void failure(int index, Exception e){
        ApiResponse apiResponse = apiResponseList.get(index);
        apiResponse.setStatus(502);
        apiResponse.setBody("failed:" + e.getMessage());
        checkIsDone();
    }

    public void checkIsDone(){
        if(atomicInteger.decrementAndGet() == 0){
            deferredResult.setResult(new ResponseEntity(new AggregateResponse(apiResponseList), HttpStatus.OK));
        }
    }
}
