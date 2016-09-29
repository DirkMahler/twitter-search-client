package net.softwareminds.repo;

import com.google.common.collect.EvictingQueue;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class InMemoryRecentSearchesRepo implements RecentSearchesRepo {

    private final int maxSearchesPerUser;
    private final Map<String, EvictingQueue<String>> recentSearchMap;

    public InMemoryRecentSearchesRepo(){
        this(5);
    }

    public InMemoryRecentSearchesRepo(int maxSearchesPerUser) {
        this.maxSearchesPerUser = maxSearchesPerUser;
        recentSearchMap = new HashMap<>();
    }

    @Override
    public void addRecentSearch(String userName, String searchQuery) {
        EvictingQueue<String> userRecentSearches = recentSearchMap.get(userName);
        if(userRecentSearches==null){
            userRecentSearches = EvictingQueue.create(maxSearchesPerUser);
            recentSearchMap.put(userName, userRecentSearches);
        }
        userRecentSearches.remove(searchQuery);
        userRecentSearches.add(searchQuery);
    }

    @Override
    public Collection<String> getRecentSearchesByUser(String userName) {
        return recentSearchMap.get(userName);
    }
}
