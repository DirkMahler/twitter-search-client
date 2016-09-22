package net.softwareminds.config;

import net.softwareminds.repo.InMemoryRecentSearchesRepo;
import net.softwareminds.repo.NewRecentSearchesRepo;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.togglz.core.manager.EnumBasedFeatureProvider;
import org.togglz.core.repository.StateRepository;
import org.togglz.core.repository.composite.CompositeStateRepository;
import org.togglz.core.repository.mem.InMemoryStateRepository;
import org.togglz.core.repository.property.PropertyBasedStateRepository;
import org.togglz.core.spi.FeatureProvider;
import org.togglz.core.user.UserProvider;
import org.togglz.spring.boot.autoconfigure.PropertiesPropertySource;
import org.togglz.spring.proxy.FeatureProxyFactoryBean;
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

    @Bean
    public StateRepository getStateRepository() {
        StateRepository commandPropsStateRepo =
                new PropertyBasedStateRepository(new PropertiesPropertySource(System.getProperties()));

        return new CompositeStateRepository(commandPropsStateRepo, new InMemoryStateRepository());
    }

    @Bean
    public FeatureProxyFactoryBean recentSearchesRepo(InMemoryRecentSearchesRepo inMemoryRecentSearchesRepo,
            NewRecentSearchesRepo refactoredRecentSearchesRepo) {
        FeatureProxyFactoryBean featureProxy = new FeatureProxyFactoryBean();
        featureProxy.setFeature("TWITTER_RECENT_SEARCHES");
        featureProxy.setInactive(inMemoryRecentSearchesRepo);
        featureProxy.setActive(refactoredRecentSearchesRepo);

        return featureProxy;
    }
}
