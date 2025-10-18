package com.example.task.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "files")
public class AppProperties {
    private String poolPath;
    private String fileAPath;

    public String getPoolPath() { return poolPath; }
    public void setPoolPath(String poolPath) { this.poolPath = poolPath; }

    public String getFileAPath() { return fileAPath; }
    public void setFileAPath(String fileAPath) { this.fileAPath = fileAPath; }
}
