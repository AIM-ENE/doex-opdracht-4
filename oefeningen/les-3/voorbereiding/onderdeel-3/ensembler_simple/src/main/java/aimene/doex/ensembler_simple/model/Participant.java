package aimene.doex.ensembler_simple.model;

import org.springframework.data.jdbc.core.mapping.AggregateReference;

public record Participant(AggregateReference<Member, Integer> member) {
}
