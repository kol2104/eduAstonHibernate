package org.example.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@Configuration
@ComponentScan("org.example")
@EnableWebMvc
@PropertySource("classpath:application.properties")
public class SpringConfig {
    @Autowired
    Environment env;

    @Bean
    public Connection getConnection() {
        Connection connection = null;
        try {
            Class.forName("org.postgresql.Driver");
            connection = DriverManager.getConnection("jdbc:postgresql://" + env.getProperty("POSTGRES_HOST") + ":" +
                    env.getProperty("POSTGRES_PORT") + "/" + env.getProperty("POSTGRES_DB"),
                    env.getProperty("POSTGRES_USERNAME"), env.getProperty("POSTGRES_PASSWORD"));
        } catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return connection;
    }
}
