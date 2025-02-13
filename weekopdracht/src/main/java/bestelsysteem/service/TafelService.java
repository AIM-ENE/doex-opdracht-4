package bestelsysteem.service;

import bestelsysteem.dto.Bestelling;
import bestelsysteem.model.Gerecht;
import bestelsysteem.model.Tafel;
import bestelsysteem.repository.GerechtRepository;
import bestelsysteem.repository.RestaurantRepository;
import bestelsysteem.repository.TafelRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TafelService {
    private final RestaurantRepository restaurantRepository;
    private final TafelRepository tafelRepository;
    private final GerechtRepository gerechtRepository;

    public TafelService(RestaurantRepository restaurantRepository,
                        TafelRepository tafelRepository,
                        GerechtRepository gerechtRepository) {
        this.restaurantRepository = restaurantRepository;
        this.tafelRepository = tafelRepository;
        this.gerechtRepository = gerechtRepository;
    }

    public Optional<Bestelling> getBestelling(Tafel tafel, int bestelnummer) {
        //TODO: je zou hier heel mooi een custom query voor kunnen schrijven
        Optional<bestelsysteem.model.Bestelling> bestellingOptional = tafel.getBestelling(bestelnummer);
        return bestellingOptional.map(bestelling -> {
            List<String> collect = bestelling.getGerechten().stream()
                    .map(bestellingGerecht -> gerechtRepository.findById(
                                    Objects.requireNonNull(bestellingGerecht.gerecht().getId()))
                            .map(bestelsysteem.model.Gerecht::naam)
                            .orElse("gerecht not found"))
                    .toList();
            return new Bestelling(bestelling.getBestelnummer(), collect);
        });
    }
}
