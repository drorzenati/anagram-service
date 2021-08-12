package com.paloalto.metric;


import com.paloalto.metric.model.Metric;
import org.junit.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

public class MetricServiceTest {

    public static final int TOTAL_REQUESTS = 10;

    MetricService metricService = new MetricService();

    @Test
    public void increaseCount() {
        int sum = 0;
        Random r = new Random();
        for (int i = 1; i <= TOTAL_REQUESTS; i++) {
            int randomInt = r.nextInt(100) + 1;
            sum = sum + randomInt;
            metricService.increaseCount(randomInt);
            assertThat(metricService.getMetric().getTotalRequests()).isEqualTo(i);
            assertThat(metricService.getMetric().getAverageTimeProcessingNs()).isEqualTo(sum / i);
        }
    }

    @Test
    public void getMetricWithoutPreviousCallsThenMetricShouldBeEmpty() {
        assertThat(metricService.getMetric()).isEqualTo(new Metric());
    }
}