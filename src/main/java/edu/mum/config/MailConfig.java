package edu.mum.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource("classpath:application.properties")
public class MailConfig {

    @Value("${mailGun.api.key}")
    String apiKey;

    @Value("${mailGun.api.messages.url}")
    String mailMessageUrl;

    @Value("${mail.no-reply}")
    String noReplyEmail;


    @Bean
    public String getApiKey() {
        return this.apiKey;
    }

    @Bean
    public String getNoReplyEmail(){
        return noReplyEmail;
    }

    @Bean
    public String getMailMessageUrl() {
        return this.mailMessageUrl;
    }
}
