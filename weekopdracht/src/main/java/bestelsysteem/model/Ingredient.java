package bestelsysteem.model;

import org.springframework.data.annotation.Id;

public class Ingredient extends bestelsysteem.openapi.model.Ingredient {
    @Id
    @Override
    public Integer getId() {
        return super.getId();
    }
}
