package bestelsysteem.dto;

import java.util.List;

public record Bestelling(int bestelnummer, List<String> gerechten) {}
