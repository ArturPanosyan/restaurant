package am.developers.menuservice.service;

import am.developers.menuservice.entity.Ingredient;
import am.developers.menuservice.repository.IngredientRepository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IngredientService {

    private IngredientRepository ingredientRepository;
    private MongoTemplate mongoTemplate;

    public List<Ingredient> getAllIngredients() {
        return mongoTemplate.findAll(Ingredient.class);
    }

    public Ingredient addIngredient(Ingredient ingredient) {
        return mongoTemplate.save(ingredient);
    }

    public void removeIngredient(String ingredientId) {
        mongoTemplate.remove(Query.query(Criteria.where("_id").is(ingredientId)),
                Ingredient.class);
    }
}
