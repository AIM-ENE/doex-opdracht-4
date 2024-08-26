package bestelsysteem.service;

import bestelsysteem.dto.VoedingRestrictie;

import java.util.Set;

public interface AllergenenService {
    Set<VoedingRestrictie> getVoedingRestrictie(String ingredientNaam);
}
