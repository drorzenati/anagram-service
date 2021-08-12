package com.paloalto.repository;

import com.paloalto.persistence.AnagramCache;
import com.paloalto.repository.utils.RepositoryManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.annotation.PostConstruct;
import java.util.Set;

@Repository
public class AnagramRepository {

    private RepositoryManager repositoryManager;
    private AnagramCache anagramCache = new AnagramCache();

    @Autowired
    public AnagramRepository(RepositoryManager repositoryManager) {
        this.repositoryManager = repositoryManager;
    }

    public Set<String> getAllAnagrams(String word) {
        return anagramCache.getAnagrams(word);
    }

    public int getTotalWords() {
        return anagramCache.getSize();
    }

    @PostConstruct
    private void initializeRepository() {
        repositoryManager.initializeRepository(anagramCache);
    }
}
