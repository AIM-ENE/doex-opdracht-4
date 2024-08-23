package bestelsysteem.dto;

public class Voorraad extends bestelsysteem.openapi.model.Voorraad {
    public Voorraad(String gerecht, int aantal) {
        this.setGerecht(gerecht);
        this.setAantal(aantal);
    }
}
