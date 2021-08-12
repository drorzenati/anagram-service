package com.paloalto.metric;

import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

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
        long startTime = System.nanoTime();
        chain.doFilter(request, response);
        long processingTimeNs = System.nanoTime() - startTime;
        if (isToAddRequestToMetric(httpRequest)) {
            metricService.increaseCount((int) processingTimeNs);
        }
    }

    private boolean isToAddRequestToMetric(HttpServletRequest httpRequest) {
        return metricSupportedAPIs.stream().anyMatch(metricSupportedAPI -> httpRequest.getRequestURI().contains(metricSupportedAPI));
    }

}
