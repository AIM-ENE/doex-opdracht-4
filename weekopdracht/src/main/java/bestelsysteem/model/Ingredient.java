package bestelsysteem.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.data.annotation.Id;

public record Ingredient(@Id Integer id, String naam) {

}
