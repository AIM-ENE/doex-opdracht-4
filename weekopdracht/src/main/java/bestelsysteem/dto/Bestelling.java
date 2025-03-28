package bestelsysteem.dto;

import bestelsysteem.model.BestelStatus;

import java.util.List;

public record Bestelling(int bestelnummer, BestelStatus status, List<BestelRegel> gerechten) {}
