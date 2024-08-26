package bestelsysteem.dto;

import java.util.List;

public record Winkelmand(List<WinkelmandGerecht> gerechten) {
    public record WinkelmandGerecht(int id, String naam) {
    }
}
