package net.softwareminds;

import org.springframework.core.env.Environment;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.inject.Inject;
import java.util.List;

@Controller
@RequestMapping("/")
public class TwitterSearchController {

    @Inject
    private Twitter twitter;

    @RequestMapping(method = RequestMethod.GET)
    public String helloTwitter(Model model) {
        SearchResults results = twitter.searchOperations().search("@majug");

        List<Tweet> searchResults = results.getTweets();
        model.addAttribute("searchResults", searchResults);

        return "searchResults";
    }

}
