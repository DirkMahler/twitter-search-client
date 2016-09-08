package net.softwareminds.config;

import org.togglz.core.Feature;
import org.togglz.core.annotation.EnabledByDefault;
import org.togglz.core.annotation.Label;
import org.togglz.core.context.FeatureContext;

public enum TwitterFeatureToggles implements Feature {

    @EnabledByDefault
    @Label("First Feature")
    FEATURE_ONE,

    @Label("Twitter Trends")
    TWITTER_TRENDS;

    public boolean isActive() {
        return FeatureContext.getFeatureManager().isActive(this);
    }

}
