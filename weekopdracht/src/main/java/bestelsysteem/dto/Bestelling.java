package bestelsysteem.dto;

import java.util.List;

public class Bestelling extends bestelsysteem.openapi.model.Bestelling {
    public Bestelling(int bestelnummer, List<String> gerechten) {
        this.setBestelnummer(bestelnummer);
        this.setGerechten(gerechten);
    }
}
