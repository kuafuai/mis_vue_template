package com.kuafu.llm.vector;

import com.kuafu.MisAppBackendApplication;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.zeromq.ZContext;
import org.zeromq.ZMQ;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest(classes = {MisAppBackendApplication.class})
@RunWith(SpringRunner.class)
class RemoteVectorTest {

    @Resource
    private RemoteVector  vector;

    @Test
    void save() {
        final ArrayList<String> text = new ArrayList<>();
        text.add("你真漂亮");
        text.add("你真好看");
        text.add("你真丑");
        vector.save("test", text);
    }

    @Test
    void search() {
        final List<String> search = vector.search("test", "你丑");
        System.out.println(search);
    }

    @Test
    void startRemoteVector() {
        vector.startRemoteVector();
    }
}