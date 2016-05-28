package no.kevin.innlevering1;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(exclude = "rented") // Don't include rented when calculating hashcode as this can change.
public class Car {
    private final String licenseNumber;
    private boolean rented;
}
