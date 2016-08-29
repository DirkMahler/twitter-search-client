package net.softwareminds;

import org.springframework.http.MediaType;
import org.springframework.social.twitter.api.SearchResults;
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

    @RequestMapping(method = RequestMethod.GET)
    public String helloTwitter(Model model) {
        return "search";
    }

    @RequestMapping(method = RequestMethod.POST, consumes = MediaType
            .APPLICATION_FORM_URLENCODED_VALUE)
    public String formBookingPost(@RequestParam String searchQuery, Model model) {
        SearchResults results = twitter.searchOperations().search(searchQuery);

        List<Tweet> searchResults = results.getTweets();
        model.addAttribute("searchResults", searchResults);

        return "search";
    }
}
