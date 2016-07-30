package net.softwareminds.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;

@EnableAutoConfiguration
@Configuration
public class TwitterConfig extends SocialConfigurerAdapter {
}
