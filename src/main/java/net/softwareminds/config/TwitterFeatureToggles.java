package net.softwareminds.config;

import org.togglz.core.Feature;
import org.togglz.core.activation.UserRoleActivationStrategy;
import org.togglz.core.annotation.ActivationParameter;
import org.togglz.core.annotation.DefaultActivationStrategy;
import org.togglz.core.annotation.InfoLink;
import org.togglz.core.annotation.Label;
import org.togglz.core.annotation.Owner;
import org.togglz.core.context.FeatureContext;

public enum TwitterFeatureToggles implements Feature {

    @DefaultActivationStrategy(id=UserRoleActivationStrategy.ID,
            parameters = {@ActivationParameter(name = UserRoleActivationStrategy.PARAM_ROLES_NAME, value = "QA")})
    @Label("Twitter Recent Searches")
    @InfoLink("http://jira-user-story")
    @Owner("Kai")
    TWITTER_RECENT_SEARCHES,

    @Label("Twitter Trends")
    @InfoLink("http://jira-user-story")
    @Owner("Kai")
    TWITTER_TRENDS,

    @Label("Twitter Profiles")
    @InfoLink("http://jira-profile-story")
    @Owner("Kai")
    TWITTER_PROFILE;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}
