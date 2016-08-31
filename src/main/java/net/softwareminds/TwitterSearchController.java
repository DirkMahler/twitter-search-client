package net.softwareminds;

import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Trend;
import org.springframework.social.twitter.api.Trends;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/")
public class TwitterSearchController {

    @Inject
    private Twitter twitter;

    private WhereOnEarthIDMapper woeIDMap;

    public TwitterSearchController() {
        woeIDMap = new WhereOnEarthIDMapper();
    }

    @RequestMapping(method = RequestMethod.GET)
    public String search(@RequestParam(required = false) String searchQuery, Model model) {
        if (searchQuery != null) {
            SearchResults results = twitter.searchOperations().search(searchQuery);

            List<Tweet> searchResults = results.getTweets();
            model.addAttribute("searchResults", searchResults);
        }
        return "search";
    }

    @RequestMapping(path = "trend", method = RequestMethod.GET)
    public String trend(@RequestParam(required = false) String city, Model model) {
        if (city != null) {
            Long woeID = woeIDMap.getWhereOnEarthID(city);

            if (woeID != null)
            {
                Trends trends = twitter.searchOperations().getLocalTrends(woeID);
                List<Trend> searchResults = trends.getTrends();
                model.addAttribute("searchResults", searchResults);
            }

        } return "trend";
    }
}
