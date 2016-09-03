package net.softwareminds.repo;

import java.util.Collection;

public interface RecentSearchesRepo {
    void addRecentSearch(String userName, String searchQuery);
    Collection<String> getRecentSearchesByUser(String userName);
}
