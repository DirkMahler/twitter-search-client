package net.softwareminds;

import net.softwareminds.config.TwitterFeatureToggles;
import net.softwareminds.repo.RecentSearchesRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Trend;
import org.springframework.social.twitter.api.Trends;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.social.twitter.api.TwitterProfile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/")
public class TwitterSearchController {

    @Autowired
    private Twitter twitter;

    @Autowired
    private RecentSearchesRepo recentSearchesRepo;

    @Autowired
    private WhereOnEarthIDMapper woeIDMap;

    @RequestMapping(method = RequestMethod.GET)
    public String search(@RequestParam(required = false) String searchQuery, Model model,
            Authentication authentication) {
        if (searchQuery != null) {
            recentSearchesRepo.addRecentSearch(authentication.getName(), searchQuery);
            SearchResults results = twitter.searchOperations().search(searchQuery);

            List<Tweet> searchResults = results.getTweets();
            model.addAttribute("searchResults", searchResults);
        }
        model.addAttribute("recentSearches", recentSearchesRepo.getRecentSearchesByUser(authentication.getName()));
        return "search";
    }

    @RequestMapping(path = "trend", method = RequestMethod.GET)
    public String trend(@RequestParam(required = false) String city, Model model, HttpServletResponse response) {
        if (TwitterFeatureToggles.TWITTER_TRENDS.isActive() == false) {
            throw new ToggleNotActiveForRequestedPath();
        }

        if (city != null) {
            Long woeID = woeIDMap.getWhereOnEarthID(city);

            if (woeID != null) {
                Trends trends = twitter.searchOperations().getLocalTrends(woeID);
                List<Trend> searchResults = trends.getTrends();
                model.addAttribute("searchResults", searchResults);
            }

        }
        return "trend";
    }

    @RequestMapping(path = "profile", method = RequestMethod.GET)
    public String profile(@RequestParam(required = false) String userScreenName, Model model) {
        if (TwitterFeatureToggles.TWITTER_PROFILE.isActive() == false) {
            throw new ToggleNotActiveForRequestedPath();
        }

        if (userScreenName != null) {
            TwitterProfile twitterProfile = twitter.userOperations().getUserProfile(userScreenName);
            model.addAttribute("twitterProfile", twitterProfile);
        }
        return "profile";
    }
}
