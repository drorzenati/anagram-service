package com.paloalto.persistence;


import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;


public class AnagramCacheTest {

    AnagramCache anagramCache = new AnagramCache();

    public static Set<String> ANAGRAM_SET_1 = new HashSet<>(Arrays.asList("least", "setal", "slate", "stale", "steal",
            "stela", "taels", "tales", "teals", "tesla"));
    public static Set<String> ANAGRAM_SET_2 = new HashSet<>(Arrays.asList("ates", "east", "eats", "etas", "sate", "seat", "seta", "teas"));
    public static Set<String> ANAGRAM_SET_3 = new HashSet<>(Arrays.asList("STOP", "SPOT", "POST", "OPTS", "POTS", "TOPS"));

    @Test
    public void testCache() {
        addToCache(ANAGRAM_SET_1);
        addToCache(ANAGRAM_SET_2);
        addToCache(ANAGRAM_SET_3);
        assertThat(anagramCache.getAnagrams("least")).isEqualTo(ANAGRAM_SET_1);
        assertThat(anagramCache.getAnagrams("ates")).isEqualTo(ANAGRAM_SET_2);
        assertThat(anagramCache.getAnagrams("STOP")).isEqualTo(ANAGRAM_SET_3);
        assertThat(anagramCache.getAnagrams("apple").isEmpty());
        assertThat(anagramCache.getAnagrams(null).isEmpty());
    }

    private void addToCache(Set<String> words) {
        words.forEach(word -> anagramCache.add(word));
    }
}