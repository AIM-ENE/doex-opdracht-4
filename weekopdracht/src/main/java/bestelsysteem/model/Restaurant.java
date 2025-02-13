package bestelsysteem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Set;

public record Restaurant(@Id Integer id,
                         @Version long versie,
                         Set<Winkelmand> winkelmanden) {
    public Optional<Winkelmand> getWinkelmand(int winkelmandId) {
        return winkelmanden.stream().filter(winkelmand -> winkelmand.id().equals(winkelmandId)).findFirst();
    }

    public Winkelmand maakWinkelmand() {
        bestelsysteem.model.Winkelmand winkelmand = new bestelsysteem.model.Winkelmand(winkelmanden.size()+1, new ArrayList<>());
        winkelmanden.add(winkelmand);
        return winkelmand;
    }

    public void verwijderWinkelmand(Winkelmand winkelmand) {
        winkelmanden.remove(winkelmand);
    }
}
