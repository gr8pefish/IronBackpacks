package gr8pefish.ironbackpacks.core.recipe;

import gr8pefish.ironbackpacks.api.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.BackpackType;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

public class BackpackTierRecipe extends ShapedOreRecipe {

    private final BackpackType resultType;
    private final BackpackSpecialty resultSpecialty;

    public BackpackTierRecipe(@Nonnull BackpackType resultType, @Nonnull BackpackSpecialty resultSpecialty, Object... recipe) {
        super(IronBackpacksAPI.getStack(resultType, resultSpecialty), recipe);

        this.resultType = resultType;
        this.resultSpecialty = resultSpecialty;
    }

    public BackpackType getResultType() {
        return resultType;
    }

    public BackpackSpecialty getResultSpecialty() {
        return resultSpecialty;
    }
}
