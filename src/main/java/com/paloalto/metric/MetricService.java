package com.paloalto.metric;

import com.paloalto.metric.model.Metric;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class MetricService {

    private static final String STATISTICS = "statistics";

    Map<String, Metric> metricCounter = new ConcurrentHashMap<>();

    public MetricService() {
        metricCounter.put(STATISTICS, new Metric());
    }

    public synchronized void increaseCount(int processingTimeNs) {
        Metric metric = metricCounter.get(STATISTICS);
        int totalRequests = metric.getTotalRequests() + 1;
        int totalTimeOfProcessing = metric.getTotalTimeOfProcessingNs() + processingTimeNs;
        metricCounter.put(STATISTICS, new Metric(totalRequests, totalTimeOfProcessing / totalRequests, totalTimeOfProcessing));
    }

    public Metric getMetric() {
        return metricCounter.get(STATISTICS);
    }

}
