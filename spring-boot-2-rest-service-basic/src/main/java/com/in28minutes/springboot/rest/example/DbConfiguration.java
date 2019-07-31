package com.in28minutes.springboot.rest.example;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration  
public class DbConfiguration  {  
      
    @Autowired  
    private DataSourceProperties properties;  
      
    @Bean  
    public DataSource dataSource() {  
        DataSourceBuilder factory = DataSourceBuilder  
                .create(this.properties.getClassLoader())  
                .driverClassName(this.properties.getDriverClassName())  
                .url(this.properties.getUrl()).username(this.properties.getUsername())  
                .password("password");  
        if (this.properties.getType() != null) {  
            factory.type(this.properties.getType());  
        }  
        return factory.build();  
    }  
  
}  