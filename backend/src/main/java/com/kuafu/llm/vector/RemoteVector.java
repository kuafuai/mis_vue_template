package com.kuafu.llm.vector;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.kuafu.common.shell.ShellExecutor;
import com.kuafu.common.util.StringUtils;
import com.kuafu.llm.model.RemoteVectorRequest;
import com.kuafu.llm.model.RemoteVectorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.stereotype.Component;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import javax.annotation.PreDestroy;
import java.io.File;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
@Slf4j
@ConditionalOnExpression("${llm.enable:false} and '${llm.vector.provider}'.equalsIgnoreCase('remote')")
public class RemoteVector implements Vector, InitializingBean {

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
//      等待远程的向量服务是否启动
        final RemoteVectorRequest remoteVectorRequest = new RemoteVectorRequest();
        remoteVectorRequest.setType("heartbeat");
        responder.send(gson.toJson(remoteVectorRequest));
        final String s = responder.recvStr();
        if (StringUtils.isNotEmpty(s)) {
            RemoteVectorResponse remoteVectorResponse = gson.fromJson(s, new TypeToken<RemoteVectorResponse>() {
            }.getType());
            if (remoteVectorResponse != null) {
                if (remoteVectorResponse.getCode() == 0) {
                    final String msg = remoteVectorResponse.getMsg();
                    return "success".equals(msg);
                }
            }
        }
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

    //  在bean加载之前，启动远程的数据库服务
    @Override
    public void afterPropertiesSet() throws Exception {

        //          如果启动成功会一直卡在这里
        new Thread(this::startRemoteVector).start();
    }


    public void startRemoteVector() {
        Map<String, String> env = System.getenv();
        log.info("remote vector init");
        final ClassLoader classLoader = RemoteVector.class.getClassLoader();
        final URL resource = classLoader.getResource("remote_db/chroma_db_utils.py");
        final URL relyResource = classLoader.getResource("remote_db/requirements.txt");
        File file = new File(resource.getFile());
        File relyFile = new File(relyResource.getFile());
//          创建虚拟环境
//        final ShellExecutor venv = ShellExecutor.execute("python3 -m venv chatbot", ".", env);
//        venv.start();
//        log.info("venv log {}",venv.getContent());
//        final ShellExecutor source = ShellExecutor.execute("source chatbot/bin/activate", ".", env);
//        source.start();
//        log.info("source log {}",source.getContent());
//        final ShellExecutor install = ShellExecutor.execute("pip3 install -r " + relyFile.getAbsolutePath(), ".", env);
//        install.start();
//        log.info("install log {}",install.getContent());
//        final String command = "python3 -W ignore " + file.getAbsolutePath();

        String command = String.join(" && ",
                "python3 -m venv chatbot",
                "echo 'create chatbot venv'",
                "source chatbot/bin/activate",
                "echo 'source chatbot venv'",
                "pip3 install -r " + relyFile.getAbsolutePath(),
                "echo 'install' ",
                "python3 -W ignore " + file.getAbsolutePath()

        );
        log.info("remote vector cmd is {}", command);
        final ShellExecutor execute = ShellExecutor.execute(command, ".", env);
        execute.start();
        log.error("remote vector end,{}", execute.getContent());
    }
}
