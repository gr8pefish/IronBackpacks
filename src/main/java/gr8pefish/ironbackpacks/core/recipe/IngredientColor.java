package gr8pefish.ironbackpacks.core.recipe;

import net.minecraft.init.Items;
import net.minecraft.item.ItemBucket;
import net.minecraft.item.crafting.Ingredient;

import javax.annotation.Nonnull;

/**
 * Class that holds valid items for coloring backpacks.
 *
 * Currently accepted:
 *  - Any "dye" -> {@link String} data type
 *  - A WATER_BUCKET -> {@link ItemBucket} data type
 *
 * It will crash if an invalid item is supplied.
 */
public class IngredientColor extends Ingredient {

    private Object ingredient;

    public IngredientColor(@Nonnull Object ingredient) {

        //only accept valid items
        boolean valid = false;

        //accept any "dye"
        if (ingredient.getClass().equals(String.class)) {
            if (ingredient.equals("dye")) {
                valid = true;
            }
        }

        //accept a bucket of water
        if (ingredient.getClass().equals(ItemBucket.class)) {
            if (ingredient.equals(Items.WATER_BUCKET)) { //TODO: Use COFH fluid container -> https://github.com/CoFH/CoFHCore/blob/1.12/src/main/java/cofh/core/util/crafting/FluidIngredientFactory.java
                valid = true;
            }
        }
        //crash if invalid ingredient
        if (!valid) throw new RuntimeException("Invalid ingredient for coloring backpacks: "+ ingredient);

        //didn't crash, good to go
        this.ingredient = ingredient;
    }

    public Object get() {
        return ingredient;
    }

}
