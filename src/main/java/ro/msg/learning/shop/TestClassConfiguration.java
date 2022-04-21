package ro.msg.learning.shop;

import javax.persistence.DiscriminatorColumn;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@ConfigurationProperties(prefix = "shop.page.number")
public class TestClassConfiguration {
	
}
