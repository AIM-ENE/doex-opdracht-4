package bestelsysteem.service;

import bestelsysteem.dto.Bestelling;
import bestelsysteem.model.Tafel;

public interface TafelService {
    Bestelling getBestelling(Tafel tafel, int bestelnummer);
}
