package aimene.doex.ensembler_simple.repository;

import aimene.doex.ensembler_simple.model.Member;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface MemberRepository extends CrudRepository<Member, Integer> {
    Optional<Member> findByName(String name);
}
