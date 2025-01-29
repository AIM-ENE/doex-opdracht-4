package aimene.doex.ensembler_simple.repository;

import aimene.doex.ensembler_simple.model.Ensemble;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface EnsembleRepository extends CrudRepository<Ensemble, Integer> {
    Optional<Ensemble> findByName(String name);
}
