package com.paloalto.metric;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StopWatch;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

import static com.paloalto.utils.StopWatchUtils.getStopWatch;
import static com.paloalto.utils.StopWatchUtils.getTotalTimeNanos;

@Component
@Setter
public class MetricFilter implements Filter {

    private MetricService metricService;
    @Value(value = "${metric.supported.apis}")
    private List<String> metricSupportedAPIs;

    @Autowired
    public MetricFilter(MetricService metricService) {
        this.metricService = metricService;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws java.io.IOException, ServletException {
        HttpServletRequest httpRequest = ((HttpServletRequest) request);
        StopWatch stopWatch = getStopWatch();
        chain.doFilter(request, response);
        long processingTimeNs = getTotalTimeNanos(stopWatch);
        if (isToAddRequestToMetric(httpRequest)) {
            metricService.increaseCount((int) processingTimeNs);
        }
    }

    private boolean isToAddRequestToMetric(HttpServletRequest httpRequest) {
        return metricSupportedAPIs.stream().anyMatch(metricSupportedAPI -> httpRequest.getRequestURI().contains(metricSupportedAPI));
    }

}
