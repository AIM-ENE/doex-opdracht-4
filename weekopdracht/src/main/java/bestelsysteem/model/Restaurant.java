package bestelsysteem.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Version;

import java.util.ArrayList;
import java.util.Date;
import java.util.Optional;
import java.util.Set;

public record Restaurant(@Id Integer id,
                         @Version long versie,
                         Set<Winkelmand> winkelmanden) {
    public Optional<Winkelmand> getWinkelmand(int winkelmandId) {
        return winkelmanden.stream().filter(winkelmand -> winkelmand.id().equals(winkelmandId)).findFirst();
    }

    public Optional<Winkelmand> getWinkelmandOnDate(Date datumTijd) {
        return winkelmanden.stream().filter(winkelmand -> winkelmand.datumTijd().equals(datumTijd)).findFirst();
    }

    public Winkelmand maakWinkelmand() {
        Winkelmand winkelmand = new Winkelmand(new Date(), new ArrayList<>());
        winkelmanden.add(winkelmand);
        return winkelmand;
    }

    public void verwijderWinkelmand(Winkelmand winkelmand) {
        winkelmanden.remove(winkelmand);
    }
}
