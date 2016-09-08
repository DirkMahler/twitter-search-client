package net.softwareminds.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.user.UserProvider;
import org.togglz.spring.security.SpringSecurityUserProvider;

@EnableAutoConfiguration
@Configuration
public class TwitterConfig extends SocialConfigurerAdapter {

    @Bean
    public FeatureProvider featureProvider() {
        return new EnumBasedFeatureProvider(TwitterFeatureToggles.class);
    }

    @Bean
    public UserProvider getUserProvider() {
        return new SpringSecurityUserProvider("ROLE_ADMIN");
    }
}
