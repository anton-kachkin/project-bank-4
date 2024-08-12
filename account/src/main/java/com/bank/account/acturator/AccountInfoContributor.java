package com.bank.account.acturator;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Configuration;

import java.lang.management.ManagementFactory;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

/**
 * Кастомизация для Endpoint /actuator/info Spring Boot Actuator
 * Используется утилитный класс, реализующий интерфейс InfoContributor
 */
@Configuration
public class AccountInfoContributor implements InfoContributor {

    @Value("${info.build.artifactId}")
    private String artifactId;

    @Value("${info.build.version}")
    private String version;

    @Value("${spring.application.name}")
    private String appName;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    @Override
    public void contribute(Info.Builder builder) {
        final String formattedStartTime = getFormattedStartTime();

        final Map<String, Object> appCustomDetails = new HashMap<>();
        appCustomDetails.put("artifactId", artifactId);
        appCustomDetails.put("version", version);
        appCustomDetails.put("name", appName);
        appCustomDetails.put("contextPath", contextPath);
        appCustomDetails.put("startTime", (formattedStartTime + " UTC +0"));

        builder.withDetail("application", appCustomDetails);
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
