package com.bank.profile.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.actuate.info.Info;
import org.springframework.boot.actuate.info.InfoContributor;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

/**
 * Добавление информации о профиле приложения в метрики Spring Boot Actuator.
 * Поля:
 * - name: имя приложения, из конфигурации.
 * - artifactId: идентификатор артефакта сборки, из конфигурации.
 * - startupTime: время запуска приложения.
 * - version: версия приложения, из конфигурации.
 * - contextPath: контекстный путь сервера, из конфигурации.
 */
@Configuration
public class ProfileInfoContributor implements InfoContributor {

    @Value("${spring.application.name}")
    private String name;

    @Value("${info.build.artifactId}")
    private String artifactId;

    @Value("${info.build.version}")
    private String version;

    @Value("${server.servlet.context-path}")
    private String contextPath;

    private ZonedDateTime startupTime;

    /**
     * Инициализирует время запуска приложения после внедрения всех зависимостей.
     */
    @PostConstruct
    public void init() {
        startupTime = ZonedDateTime.ofInstant(Instant.now(), ZoneId.systemDefault()).truncatedTo(ChronoUnit.SECONDS);
    }

    /**
     * Добавляет информацию о профиле приложения в метрики Spring Boot Actuator.
     *
     * @param builder объект Info.Builder, используемый для добавления информации.
     */
    @Override
    public void contribute(Info.Builder builder) {
        builder.withDetail("name", name)
                .withDetail("artifactId", artifactId)
                .withDetail("startTime", formatTime(startupTime))
                .withDetail("version", version)
                .withDetail("contextPath", contextPath);
    }

    private String formatTime(ZonedDateTime time) {
        final DateTimeFormatter formatTime = DateTimeFormatter.ofPattern("yyyy.MM.dd HH:mm:ss 'UTC' XXX");
        return time.format(formatTime);
    }
}
