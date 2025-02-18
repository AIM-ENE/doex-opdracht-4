package bestelsysteem.service;

import bestelsysteem.dto.Bestelling;
import bestelsysteem.model.Tafel;

import java.util.Optional;

public interface TafelService {
    Optional<Bestelling> getBestelling(Tafel tafel, int bestelnummer);
}
