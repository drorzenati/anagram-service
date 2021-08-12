package com.paloalto.persistence;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Getter
@Setter
public class AnagramCache {

    private int size;
    private Map<Character, AnagramTrie> subTrie = new ConcurrentHashMap<>();

    public void add(String word) {
        char[] sortedWordCharArray = sort(word).toCharArray();
        Map<Character, AnagramTrie> subTrie = getSubTrie();
        subTrie.putIfAbsent(sortedWordCharArray[0], new AnagramTrie());
        AnagramTrie anagramTrie = subTrie.get(sortedWordCharArray[0]);
        for (int i = 1; i < sortedWordCharArray.length; i++) {
            anagramTrie = anagramTrie.getOrDefaultAnagramTrieNode(sortedWordCharArray[i]);
            if (sortedWordCharArray.length - 1 == i) {
                anagramTrie.addToAnagrams(word);
            }
        }
    }

    public Set<String> getAnagrams(String word) {
        if (word == null) {
            return new HashSet<>();
        }
        char[] sortedWordCharArray = sort(word).toCharArray();
        Map<Character, AnagramTrie> subTrie = getSubTrie();
        AnagramTrie anagramTrie = subTrie.get(sortedWordCharArray[0]);
        for (int i = 1; i < sortedWordCharArray.length && anagramTrie != null; i++) {
            anagramTrie = anagramTrie.getAnagramTrieNode(sortedWordCharArray[i]);
            if (anagramTrie == null) {
                return Collections.EMPTY_SET;
            }
        }
        Set<String> anagrams = anagramTrie == null ? Collections.EMPTY_SET : anagramTrie.getAnagrams();
        return anagrams;
    }

    private String sort(String word) {
        char[] chars = word.toCharArray();
        Arrays.sort(chars);
        return new String(chars);
    }

}
