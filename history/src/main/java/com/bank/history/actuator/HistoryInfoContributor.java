package com.bank.history.actuator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.boot.info.BuildProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

/**
 * Обогащение актуатора ифнормацией по микросервису
 */
@Configuration
public class HistoryInfoContributor implements InfoContributor {

    private final BuildProperties buildProperties;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Autowired
    public HistoryInfoContributor(BuildProperties buildProperties) {
        this.buildProperties = buildProperties;
    }

    public void contribute(Info.Builder builder) {
        final String formattedStartTime = getFormattedStartTime();
        builder.withDetail("applicationName", buildProperties.getName())
                .withDetail("artifactId", buildProperties.getArtifact())
                .withDetail("startTime", formattedStartTime + " UTC +0")
                .withDetail("version", buildProperties.getVersion())
                .withDetail("contextPath", contextPath);
    }

    private static String getFormattedStartTime() {
        final long startTimeMillis = ManagementFactory.getRuntimeMXBean().getStartTime();
        final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss");
        final LocalDateTime startTime = LocalDateTime.ofEpochSecond(
                startTimeMillis / 1000, 0, ZoneOffset.UTC
        );
        return startTime.format(dateTimeFormatter);
    }
}
