package net.softwareminds;

import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping("/")
public class TwitterSearchController {

    @Inject
    private Twitter twitter;

    @RequestMapping(method = RequestMethod.GET)
    public String formBookingPost(@RequestParam(required = false) String searchQuery, Model model) {
        if (searchQuery != null) {
            SearchResults results = twitter.searchOperations().search(searchQuery);

            List<Tweet> searchResults = results.getTweets();
            model.addAttribute("searchResults", searchResults);
        }
        return "search";
    }
}
