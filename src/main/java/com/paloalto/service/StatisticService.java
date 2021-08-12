package com.paloalto.service;

import com.paloalto.metric.MetricService;
import com.paloalto.metric.model.Metric;
import com.paloalto.model.Statistic;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class StatisticService {

    private MetricService metricService;
    private AnagramService anagramService;

    @Autowired
    public StatisticService(MetricService metricService, AnagramService anagramService) {
        this.metricService = metricService;
        this.anagramService = anagramService;
    }

    public Statistic getStatistics() {
        Metric metric = metricService.getMetric();
        int totalWords = anagramService.getTotalWords();
        Statistic statistic = new Statistic(totalWords, metric.getTotalRequests(), metric.getAverageTimeProcessingNs());
        log.info("the statistics are {}", statistic);
        return statistic;
    }
}
