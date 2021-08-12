package com.paloalto.persistence;

import lombok.Getter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Getter
class AnagramTrie {
    private Set<String> anagrams = new HashSet<>();
    private Map<Character, AnagramTrie> treeNodes = new ConcurrentHashMap<>();

    public void addToAnagrams(String word) {
        anagrams.add(word);
    }

    public AnagramTrie getOrDefaultAnagramTrieNode(char c) {
        treeNodes.putIfAbsent(c, new AnagramTrie());
        return treeNodes.get(c);
    }

    public AnagramTrie getAnagramTrieNode(char c) {
        return treeNodes.get(c);
    }
}
