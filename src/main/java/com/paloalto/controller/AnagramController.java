package com.paloalto.controller;

import com.paloalto.model.Anagram;
import com.paloalto.model.Statistic;
import com.paloalto.service.AnagramService;
import com.paloalto.service.StatisticService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping(value = "/api")
public class AnagramController {

    private AnagramService anagramService;
    private StatisticService statisticService;

    @Autowired
    public AnagramController(AnagramService anagramService, StatisticService statisticService) {
        this.anagramService = anagramService;
        this.statisticService = statisticService;
    }

    @GetMapping(value = "/v1/similar", produces = "application/json")
    public Anagram getAllAnagrams(@RequestParam(value = "word") Optional<String> word) {
        if (!word.isPresent() || word.get().length() <= 1) {
            return new Anagram();
        }
        return anagramService.getAllAnagrams(word.get());
    }

    @GetMapping(value = "/v1/stats", produces = "application/json")
    public Statistic getStatistics() {
        return statisticService.getStatistics();
    }
}