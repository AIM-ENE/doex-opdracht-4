package bestelsysteem.dto;

public record Betaling(double bedrag) {

    public Betaling(double bedrag) {
        if(isAfgerondOp005(bedrag)) {
            this.bedrag = bedrag;
        } else {
            throw new RuntimeException("betaal met een veelvoud van 5 cent");
        }
    }

    private static boolean isAfgerondOp005(double value) {
        double rounded = Math.round(value * 20) / 20.0; // Round to nearest 0.05
        return Math.abs(value - rounded) < 1e-9; // Check if value is already rounded
    }
}
