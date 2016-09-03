package net.softwareminds.repo;

import com.google.common.collect.EvictingQueue;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
public class InMemoryRecentSearchesRepo implements RecentSearchesRepo {

    private final int maxSearchesPerUser;
    private final Map<String, EvictingQueue> recentSearchMap;

    public InMemoryRecentSearchesRepo(){
        this(5);
    }

    public InMemoryRecentSearchesRepo(int maxSearchesPerUser) {
        this.maxSearchesPerUser = maxSearchesPerUser;
        recentSearchMap = new HashMap();
    }

    @Override
    public void addRecentSearch(String userName, String searchQuery) {
        EvictingQueue userRecentSearches = recentSearchMap.get(userName);
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
