package bestelsysteem.dto;

import bestelsysteem.openapi.model.WinkelmandGerecht;

import java.util.List;

public class Winkelmand extends bestelsysteem.openapi.model.Winkelmand {
    public Winkelmand(List<WinkelmandGerecht> gerechten) {
        this.setGerechten(gerechten);
    }
}
