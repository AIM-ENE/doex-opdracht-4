package bestelsysteem.service;

import bestelsysteem.dto.VoedingRestrictie;

import java.util.HashSet;
import java.util.Set;

public class AllergenenServiceImpl implements AllergenenService {
    @Override
    public Set<VoedingRestrictie> getVoedingRestrictie(String ingredientNaam) {
        //TODO: verdere invulling aangeven
        Set<VoedingRestrictie> restrictiesIngredient = new HashSet<>();
        switch (ingredientNaam) {
            case "zalm" -> restrictiesIngredient.add(VoedingRestrictie.LACTOSE);
            case "rib-eye" -> {
                restrictiesIngredient.add(VoedingRestrictie.NOTEN);
                restrictiesIngredient.add(VoedingRestrictie.GLUTEN);
            }
            case "salade" -> restrictiesIngredient.add(VoedingRestrictie.NOTEN);
        }
        return restrictiesIngredient;
    }
}
