package com.paloalto.controller;

import com.paloalto.model.Anagram;
import com.paloalto.model.Statistic;
import com.paloalto.service.AnagramService;
import com.paloalto.service.StatisticService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AnagramControllerTest {

    public static String WORD = "apple";
    public static Anagram EXPECTED_ANAGRAM = Anagram.builder().similar(new HashSet<>(Arrays.asList("appel", "pepla"))).build();
    public static Statistic EXPECTED_STATISTICS = Statistic.builder().totalWords(100).totalRequests(200).avgProcessingTimeNs(300).build();

    @InjectMocks
    AnagramController anagramController;

    @Mock
    AnagramService anagramService;

    @Mock
    StatisticService statisticService;


    @Test
    public void getAllAnagrams() {
        when(anagramService.getAllAnagrams(WORD)).thenReturn(EXPECTED_ANAGRAM);
        assertThat(anagramController.getAllAnagrams(WORD)).isEqualTo(EXPECTED_ANAGRAM);
        verify(anagramService, times(1)).getAllAnagrams(WORD);
    }

    @Test
    public void getAllAnagramsGivenEmptyWordThenServiceShouldNotBeCalled() {
        Anagram anagram = anagramController.getAllAnagrams(null);
        assertThat(anagram.getSimilar() != null && anagram.getSimilar().isEmpty()).isTrue();
        verify(anagramService, times(0)).getAllAnagrams(any());
    }

    @Test
    public void getAllAnagramsGivenWordWithInvalidThenServiceShouldNotBeCalled() {
        Anagram anagram = anagramController.getAllAnagrams("a");
        assertThat(anagram.getSimilar() != null && anagram.getSimilar().isEmpty()).isTrue();
        verify(anagramService, times(0)).getAllAnagrams(any());

        anagram = anagramController.getAllAnagrams("");
        assertThat(anagram.getSimilar() != null && anagram.getSimilar().isEmpty()).isTrue();
        verify(anagramService, times(0)).getAllAnagrams(any());
    }

    @Test
    public void getStatistics() {
        when(statisticService.getStatistics()).thenReturn(EXPECTED_STATISTICS);
        assertThat(anagramController.getStatistics()).isEqualTo(EXPECTED_STATISTICS);
        verify(statisticService, times(1)).getStatistics();
    }
}