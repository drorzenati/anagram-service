package com.paloalto.model;

import lombok.*;

import java.util.HashSet;
import java.util.Set;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class Anagram {

    Set<String> similar = new HashSet<>();
}
