package com.local.parking.test.config;

import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@ComponentScan("com.local.parking.test") // Replace with your base package
public class DataSourceConfig {

    @Value("jdbc:mysql://localhost:3306/parking")
    private String databaseUrl;

    @Value("root")
    private String username;

    @Value("password")
    private String password;

    @Bean
    public DataSource dataSource() {
        System.out.println("Database URL: " + databaseUrl);
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);

        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(databaseUrl);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        // Configure other connection pool settings if needed
        return dataSource;
    }
}
