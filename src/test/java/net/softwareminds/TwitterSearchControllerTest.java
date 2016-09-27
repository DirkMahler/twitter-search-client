package net.softwareminds;

import net.softwareminds.config.TwitterFeatureToggles;
import net.softwareminds.repo.RecentSearchesRepo;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Answers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.social.twitter.api.SearchResults;
import org.springframework.social.twitter.api.Trend;
import org.springframework.social.twitter.api.Trends;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.togglz.junit.TogglzRule;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.hamcrest.collection.IsIterableContainingInOrder.contains;
import static org.hamcrest.core.IsNull.nullValue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

@RunWith(SpringRunner.class)
@WebMvcTest(TwitterSearchController.class)
@WithMockUser("user")
public class TwitterSearchControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean(answer = Answers.RETURNS_DEEP_STUBS)
    private Twitter twitter;

    @MockBean
    private RecentSearchesRepo recentSearchesRepo;

    @MockBean
    private WhereOnEarthIDMapper woeIDMap;

    @Rule
    public TogglzRule togglzRule = TogglzRule.allEnabled(TwitterFeatureToggles.class);


    @Test
    public void testSearch() throws Exception {
        when(recentSearchesRepo.getRecentSearchesByUser("user")).thenReturn(emptyList());
        SearchResults searchResultsMock = mock(SearchResults.class);

        Tweet firstTweet = mock(Tweet.class);
        Tweet secondTweet = mock(Tweet.class);
        when(searchResultsMock.getTweets()).thenReturn(Arrays.asList(firstTweet, secondTweet));
        when(twitter.searchOperations().search("latest news")).thenReturn(searchResultsMock);

        Collection<String> recentSearches = Arrays.asList("latest news", "majug");
        when(recentSearchesRepo.getRecentSearchesByUser("user")).thenReturn(recentSearches);


        //@formatter:off
        mockMvc.perform(get("/").param("searchQuery", "latest news"))
                   .andExpect(status().isOk())
                   .andExpect(view().name("search"))
                   .andExpect(model().attribute("searchResults", contains(firstTweet, secondTweet)))
                   .andExpect(model().attribute("recentSearches", contains("latest news", "majug")));
        //@formatter:on


        verify(recentSearchesRepo).addRecentSearch("user", "latest news");
    }

    @Test
    public void testSearchEmptyRequest() throws Exception {
        when(recentSearchesRepo.getRecentSearchesByUser("user")).thenReturn(emptyList());

        //@formatter:off
        mockMvc.perform(get("/"))
                   .andExpect(status().isOk())
                   .andExpect(view().name("search"))
                   .andExpect(model().attribute("searchResults", nullValue()))
                   .andExpect(model().attribute("recentSearches", emptyList()));
        //@formatter:on
    }

    @Test
    public void testTrendEmptyRequest() throws Exception {
        //@formatter:off
        mockMvc.perform(get("/trend"))
                   .andExpect(status().isOk())
                   .andExpect(view().name("trend"))
                   .andExpect(model().attribute("searchResults", nullValue()));
        //@formatter:on
    }

    @Test
    public void testTrend() throws Exception {
        when(woeIDMap.getWhereOnEarthID("Hamburg")).thenReturn(4711L);

        Trends trendsMock = mock(Trends.class);
        Trend trend1 = mock(Trend.class);
        Trend trend2 = mock(Trend.class);
        List<Trend> trendsList = Arrays.asList(trend1, trend2);
        when(trendsMock.getTrends()).thenReturn(trendsList);
        when(twitter.searchOperations().getLocalTrends(4711L)).thenReturn(trendsMock);


        //@formatter:off
        mockMvc.perform(get("/trend").param("city", "Hamburg"))
                   .andExpect(status().isOk())
                   .andExpect(view().name("trend"))
                   .andExpect(model().attribute("searchResults", contains(trend1, trend2)));
        //@formatter:on
    }

    @Test
    public void testTrendToggleInactive() throws Exception {
        togglzRule.disable(TwitterFeatureToggles.TWITTER_TRENDS);

        //@formatter:off
        mockMvc.perform(get("/trend"))
                   .andExpect(status().isNotFound());
        //@formatter:on
    }

    @Test
    public void testProfileEmptyRequest() throws Exception {
        //@formatter:off
        mockMvc.perform(get("/profile"))
                   .andExpect(status().isOk())
                   .andExpect(view().name("profile"))
                   .andExpect(model().attribute("twitterProfile", nullValue()));
        //@formatter:on
    }

    @Test
    public void testProfileToggleInactive() throws Exception {
        togglzRule.disable(TwitterFeatureToggles.TWITTER_PROFILE);

        //@formatter:off
        mockMvc.perform(get("/profile"))
                   .andExpect(status().isNotFound());
        //@formatter:on
    }
}
