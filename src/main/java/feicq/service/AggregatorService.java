package feicq.service;

import feicq.domain.AggregateResponse;
import feicq.domain.ApiRequest;
import feicq.domain.Task;
import okhttp3.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.async.DeferredResult;

import java.io.IOException;
import java.util.List;

/**
 * @author: fcq
 * @create: 2021-01-13
 */
@Service
public class AggregatorService {

    private OkHttpClient okHttpClient = new OkHttpClient();
    public void execute(Task task){
        ApiRequest apiRequest = task.getApiRequest();
        List<String> urls = apiRequest.getUrls();
        for(int i=0; i<urls.size(); i++){
            int index = i;
            Request request = new Request.Builder().url(urls.get(i)).build();
            okHttpClient.newCall(request).enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {
                    task.failure(index, e);
                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {
                    try {
                        task.success(index, response);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        }


    }


}
