package com.kuafu.llm.config;

import com.kuafu.llm.loader.FixedSizeTextSplit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
@Slf4j
@ConditionalOnProperty(prefix = "llm", name = "enable", havingValue = "true")
public class FileScanLoader implements ApplicationListener<ContextRefreshedEvent> {

    private static String PROJECT_ROOT_DIRECTORY = System.getProperty("user.dir").replaceAll("\\\\", "/");
    public static String SEPARATOR_SPOT = ".";

    @Autowired
    private LLMStartBusiness llmStartBusiness;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        log.info("init llm");
        llmStartBusiness.init();

        FixedSizeTextSplit split = new FixedSizeTextSplit(200);

        File tplRootFile = new File(PROJECT_ROOT_DIRECTORY + "/file");
        if (tplRootFile.exists() && tplRootFile.isDirectory()) {

            File[] tplFileArray = tplRootFile.listFiles();
            assert tplFileArray != null;
            for (File tplFileItem : tplFileArray) {
                if (tplFileItem.isFile()) {

                    String tplFileItemName = tplFileItem.getName();
                    String[] tplFileNameArray = tplFileItemName.split("\\" + SEPARATOR_SPOT);

                    List<String> documents = split.split(tplFileItem, tplFileNameArray[1]);

                    llmStartBusiness.createEmbedding(documents);
                }
            }
        }

        log.info("end llm");
    }
}
