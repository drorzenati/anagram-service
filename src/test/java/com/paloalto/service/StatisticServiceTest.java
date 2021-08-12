package com.paloalto.service;

import com.paloalto.metric.MetricService;
import com.paloalto.metric.model.Metric;
import com.paloalto.model.Statistic;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class StatisticServiceTest {

    public static final int TOTAL_REQUESTS = 10;
    public static final int AVERAGE_TIME_PROCESSING = 200000;
    public static Metric EXPECTED_METRIC = Metric.builder().totalRequests(TOTAL_REQUESTS).averageTimeProcessingNs(AVERAGE_TIME_PROCESSING).build();
    public static int EXPECTED_TOTAL_WORDS = 100000;
    @InjectMocks
    StatisticService statisticService;

    @Mock
    AnagramService anagramService;

    @Mock
    MetricService metricService;

    @Test
    public void getStatistics() {
        when(anagramService.getTotalWords()).thenReturn(EXPECTED_TOTAL_WORDS);
        when(metricService.getMetric()).thenReturn(EXPECTED_METRIC);
        Statistic expectedStatistic = Statistic.builder().totalWords(EXPECTED_TOTAL_WORDS).totalRequests(TOTAL_REQUESTS).avgProcessingTimeNs(AVERAGE_TIME_PROCESSING).build();
        Statistic actualStatistics = statisticService.getStatistics();
        assertThat(actualStatistics).isEqualTo(expectedStatistic);
        verify(anagramService, times(1)).getTotalWords();
        verify(metricService, times(1)).getMetric();
    }
}