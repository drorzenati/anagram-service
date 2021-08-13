package com.paloalto.service;

import com.paloalto.model.Anagram;
import com.paloalto.repository.AnagramRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Slf4j
@Service
public class AnagramService {

    private AnagramRepository anagramRepository;

    @Autowired
    public AnagramService(AnagramRepository anagramRepository) {
        this.anagramRepository = anagramRepository;
    }

    public Anagram getAllAnagrams(String word) {
        Set<String> anagrams = new HashSet<>(anagramRepository.getAllAnagrams(word));
        anagrams.remove(word);
        log.info("anagrams found for '{}' are {}", word, anagrams);
        return new Anagram(anagrams);
    }

    public int getTotalWords() {
        return anagramRepository.getTotalWords();
    }
}
