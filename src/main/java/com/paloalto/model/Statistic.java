package com.paloalto.model;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@Getter
@ToString
public class Statistic {
    int totalWords;
    int totalRequests;
    int avgProcessingTimeNs;
}