package com.bank.transfer.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Предоставление информации о микросервисе transfer посредством реализации интерефейса InfoContributor
 *Поля:
 * - appName: имя приложения, из конфигурации.
 * - artifactId: идентификатор артефакта сборки, из конфигурации.
 * - appVersion: версия приложения, из конфигурации.
 * - contextPath: контекстный путь сервера, из конфигурации.
 * - startupTime: время запуска приложения.
 */
@Configuration
public class TransferInfoContributor implements InfoContributor {

    @Value("${spring.application.name}")
    private String appName;

    @Value("${info.build.artifactId}")
    private String artifactId;

    @Value("${info.build.version}")
    private String appVersion;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private ZonedDateTime startupTime;

    /**
     *Инициализация поля 'startupTime'
     */
    @PostConstruct
    public void init() {
        startupTime = ZonedDateTime.now(ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS);
    }

    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("appName", appName)
                .withDetail("artifactId", artifactId)
                .withDetail("appVersion", appVersion)
                .withDetail("contextPath", contextPath)
                .withDetail("startupTime", startupTime
                        .format(DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss 'UTC' XXX")));
    }
}
