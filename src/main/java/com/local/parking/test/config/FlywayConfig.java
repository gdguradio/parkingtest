package com.local.parking.test.config;

import org.flywaydb.core.Flyway;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class FlywayConfig {

    private final DataSource dataSource;

    @Autowired
    public FlywayConfig(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        Flyway flyway = Flyway.configure()
                .dataSource(dataSource)
                .locations("classpath:db/migration")
                .baselineOnMigrate(true) // Add this line for baseline on migrate
                .load();
        return flyway;
    }
}


//@Configuration
//@EnableConfigurationProperties(FlywayProperties.class)
//public class FlywayConfig {
//
//    private final FlywayProperties flywayProperties;
//
//    @Autowired
//    public FlywayConfig(FlywayProperties flywayProperties) {
//        this.flywayProperties = flywayProperties;
//    }
//
//    @Bean
//    public Flyway flyway() {
//        Flyway flyway = Flyway.configure()
//                .dataSource(flywayProperties.getUrl(), flywayProperties.getUser(), flywayProperties.getPassword())
//                //.dataSource("jdbc:mysql://localhost:3306/parking", "root", "password")
//                .load();
//        // Additional Flyway configuration if needed
//        return flyway;
//    }
//}
