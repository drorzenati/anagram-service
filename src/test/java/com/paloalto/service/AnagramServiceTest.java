package com.paloalto.service;

import com.paloalto.model.Anagram;
import com.paloalto.repository.AnagramRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnagramServiceTest {

    public static String WORD = "apple";
    public static Set<String> EXPECTED_REPOSITORY_RESPONSE = new HashSet<>(Arrays.asList("apple", "appel", "pepla"));
    public static Anagram EXPECTED_ANAGRAM = Anagram.builder().similar(new HashSet<>(Arrays.asList("appel", "pepla"))).build();
    public static int EXPECTED_TOTAL_WORDS = 100000;

    @InjectMocks
    AnagramService anagramService;

    @Mock
    AnagramRepository anagramRepository;


    @Test
    public void getAllAnagrams() {
        when(anagramRepository.getAllAnagrams(WORD)).thenReturn(EXPECTED_REPOSITORY_RESPONSE);
        Anagram anagram = anagramService.getAllAnagrams(WORD);
        assertThat(anagram).isEqualTo(EXPECTED_ANAGRAM);
        verify(anagramRepository, times(1)).getAllAnagrams(WORD);
    }

    @Test
    public void getTotalWords() {
        when(anagramRepository.getTotalWords()).thenReturn(EXPECTED_TOTAL_WORDS);
        int totalWords = anagramService.getTotalWords();
        assertThat(totalWords).isEqualTo(EXPECTED_TOTAL_WORDS);
        verify(anagramRepository, times(1)).getTotalWords();
    }
}