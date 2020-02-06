package com.instahipsta.harCRUD.property;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "rabbitmq")
@Component
@Data
public class RabbitmqProperties {

    private String harQueue;
    private String deadletterQueue;
    private String harExchange;
    private String deadletterExchange;
    private String harRoutingKey;
    private String deadletterRoutingKey;

}
