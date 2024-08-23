package bestelsysteem.model;

import org.springframework.data.annotation.Id;

public record Ingredient(@Id Integer id, String naam) {

}
