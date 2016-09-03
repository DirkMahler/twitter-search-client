package net.softwareminds.repo;

import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;

public class InMemoryRecentSearchesRepoTest {
    private InMemoryRecentSearchesRepo recentSearchesRepo;

    @Before
    public void setup() {
        recentSearchesRepo = new InMemoryRecentSearchesRepo(5);
    }

    @Test
    public void addRecentSearch_hitMaxLimit_deleteOldestQuery() {
        recentSearchesRepo.addRecentSearch("user_1", "query_1");
        recentSearchesRepo.addRecentSearch("user_1", "query_2");
        recentSearchesRepo.addRecentSearch("user_1", "query_3");
        recentSearchesRepo.addRecentSearch("user_1", "query_4");
        recentSearchesRepo.addRecentSearch("user_1", "query_5");
        recentSearchesRepo.addRecentSearch("user_1", "query_6");

        Collection<String> recentSearches = recentSearchesRepo.getRecentSearchesByUser("user_1");

        assertThat(recentSearches, contains("query_2", "query_3", "query_4", "query_5", "query_6"));
    }

    @Test
    public void addRecentSearch_separatesRequestsByUser() {
        recentSearchesRepo.addRecentSearch("user_1", "query_1");
        recentSearchesRepo.addRecentSearch("user_1", "query_2");
        recentSearchesRepo.addRecentSearch("user_1", "query_3");
        recentSearchesRepo.addRecentSearch("user_2", "query_4");
        recentSearchesRepo.addRecentSearch("user_2", "query_5");
        recentSearchesRepo.addRecentSearch("user_2", "query_6");

        Collection<String> recentSearchesUser1 = recentSearchesRepo.getRecentSearchesByUser("user_1");
        Collection<String> recentSearchesUser2 = recentSearchesRepo.getRecentSearchesByUser("user_2");

        assertThat(recentSearchesUser1, contains("query_1", "query_2", "query_3"));
        assertThat(recentSearchesUser2, contains("query_4", "query_5", "query_6"));
    }

    @Test
    public void addRecentSearch_removeSameAlreadyAddedEntry() {
        recentSearchesRepo.addRecentSearch("user_1", "query_1");
        recentSearchesRepo.addRecentSearch("user_1", "query_2");
        recentSearchesRepo.addRecentSearch("user_1", "query_3");
        recentSearchesRepo.addRecentSearch("user_1", "query_2");

        Collection<String> recentSearchesUser1 = recentSearchesRepo.getRecentSearchesByUser("user_1");

        assertThat(recentSearchesUser1, contains("query_1", "query_3", "query_2"));
    }
}
