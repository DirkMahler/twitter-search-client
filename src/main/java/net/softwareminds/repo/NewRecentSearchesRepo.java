package net.softwareminds.repo;

import java.util.Collection;
import java.util.Collections;

public class NewRecentSearchesRepo implements RecentSearchesRepo {
    @Override
    public void addRecentSearch(String userName, String searchQuery) {
    }

    @Override
    public Collection<String> getRecentSearchesByUser(String userName) {
        return Collections.EMPTY_LIST;
    }
}
