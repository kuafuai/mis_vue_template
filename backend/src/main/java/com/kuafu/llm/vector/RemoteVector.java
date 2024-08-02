package com.kuafu.llm.vector;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kuafu.llm.model.RemoteVectorRequest;
import com.kuafu.llm.model.RemoteVectorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import javax.annotation.PreDestroy;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

@Component
@Slf4j
@ConditionalOnExpression("${llm.enable:false} and '${llm.vector.provider}'.equalsIgnoreCase('remote')")
public class RemoteVector implements Vector {

    private ZMQ.Socket responder;
    private ZContext context;
    private static Gson gson = new Gson();

    public RemoteVector() {
        init();
    }

    public void init() {
        context = new ZContext(1);
        responder = context.createSocket(ZMQ.REQ);
//        responder.bind("tcp://127.0.0.1:46758");
        responder.connect("tcp://127.0.0.1:46758");

    }

    @Override
    public boolean createCollection(String name, Integer dimension) {
        return false;
    }

    @Override
    public boolean deleteCollection(String name) {
        return false;
    }

    @Override
    public boolean save(String collectionName, String text, List<Double> vectors) {
        return false;

    }

    @Override
    public boolean save(String collectionName, List<String> text) {
        final RemoteVectorRequest remoteVectorRequest = new RemoteVectorRequest();
        remoteVectorRequest.setCollectionName(collectionName);
        remoteVectorRequest.setType("add");
        remoteVectorRequest.setText(text);
        responder.send(gson.toJson(remoteVectorRequest).getBytes(StandardCharsets.UTF_8));
//        接收响应
        String response = responder.recvStr();
        log.info("remote vector add response is {}", response);
        RemoteVectorResponse remoteVectorResponse = gson.fromJson(response, new TypeToken<RemoteVectorResponse>() {
        }.getType());

        if (remoteVectorResponse.getCode() == 0) {
            return true;
        } else {
            return false;
        }
    }

    @Override
    public List<String> search(String collectionName, List<Double> vectors) {
        return null;
    }

    @Override
    public List<String> search(String collectionName, String searchText) {
        final RemoteVectorRequest remoteVectorRequest = new RemoteVectorRequest();
        remoteVectorRequest.setCollectionName(collectionName);
        remoteVectorRequest.setType("query");
        remoteVectorRequest.setText(Collections.singletonList(searchText));
        responder.send(gson.toJson(remoteVectorRequest));
//        接收响应
        String response = responder.recvStr();
        log.info("remote vector search response is {}", response);
        RemoteVectorResponse remoteVectorResponse = gson.fromJson(response, new TypeToken<RemoteVectorResponse>() {
        }.getType());

        if (remoteVectorResponse.getCode() == 0) {
            return remoteVectorResponse.getResult().get(0);
        }
        return null;
    }

    @Override
    public Object getCollection(String name) {
        return null;
    }

    @PreDestroy
    public void close() {
        if (responder != null) {
            responder.close();
        }
        if (context != null) {
            context.close();
        }
    }
}
