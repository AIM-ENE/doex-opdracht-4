package aimene.doex.ensembler_simple.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;
import org.springframework.data.jdbc.core.mapping.AggregateReference;

import java.util.HashSet;
import java.util.Set;

public class Ensemble {

    @Id @JsonIgnore
    private Integer id;

    private static final int MAX_PARTICIPANTS = 3;

    private String name;

    private Set<Participant> participants;

    public Ensemble(String name) {
        this.name = name;
        participants = new HashSet<>();
    }

    public void joinAsParticipant(AggregateReference<Member, Integer> member) {
        if (participants.size() < MAX_PARTICIPANTS) {
            participants.add(new Participant(member));
        } else {
            throw new IllegalArgumentException("Ensemble is full");
        }
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Participant> getParticipants() {
        return participants;
    }

    public void setName(String name) {
        this.name = name;
    }
}

