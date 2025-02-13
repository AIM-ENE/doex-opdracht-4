package bestelsysteem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum VoedingRestrictie {
    @JsonProperty("LACTOSE")
    LACTOSE,
    @JsonProperty("NOTEN")
    NOTEN,
    @JsonProperty("GLUTEN")
    GLUTEN,
    @JsonProperty("NIKS")
    NIKS
}
