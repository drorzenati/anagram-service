package com.paloalto.metric.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Metric {
    int totalRequests;
    int averageTimeProcessingNs;
    @JsonIgnore
    int totalTimeOfProcessingNs;
}
