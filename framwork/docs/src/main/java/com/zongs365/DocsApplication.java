package com.zongs365;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import io.github.yedaxia.apidocs.Docs;
import io.github.yedaxia.apidocs.DocsConfig;

@SpringBootApplication
public class DocsApplication {
    public static void main(String[] args) {
        SpringApplication.run(DocsApplication.class, args);


        DocsConfig config= new DocsConfig();
        config.setProjectPath("C:/Users/mckj/Documents/zhl/application/test/test-impl"); // 项目根目录
        config.setProjectName("ProjectName"); // 项目名称
        config.setApiVersion("V1.0");      // 声明该API的版本
        config.setDocsPath("C:/Users/mckj/Documents/zhl/"); // 生成API 文档所在目录
        config.setAutoGenerate(Boolean.TRUE);  // 配置自动生成
        Docs.buildHtmlDocs(config); // 执行生成文档
    }

}
