package gr8pefish.ironbackpacks.core.recipe;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackType;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.ShapedOreRecipe;

import javax.annotation.Nonnull;

public class BackpackTierRecipe extends ShapedOreRecipe {

    private final BackpackType resultType;
    private final BackpackSpecialty resultSpecialty;

    public BackpackTierRecipe(@Nonnull BackpackType resultType, @Nonnull BackpackSpecialty resultSpecialty, Object... recipe) {
        super(new ResourceLocation(IronBackpacks.MODID, "tier"), IronBackpacksAPI.getStack(resultType, resultSpecialty), recipe);

        this.resultType = resultType;
        this.resultSpecialty = resultSpecialty;
    }

    @Nonnull
    @Override
    public ItemStack getCraftingResult(@Nonnull InventoryCrafting matrix) {
        ItemStack backpack = matrix.getStackInRowAndColumn(1, 1);
        if (!(backpack.getItem() instanceof IBackpack))
            return super.getCraftingResult(matrix);

        ItemStack upgraded = getRecipeOutput().copy();
        BackpackInfo upgradedInfo = BackpackInfo.upgradeTo(((IBackpack) backpack.getItem()).getBackpackInfo(backpack), getResultType(), getResultSpecialty());
        return IronBackpacksAPI.applyPackInfo(upgraded, upgradedInfo);
    }

    public BackpackType getResultType() {
        return resultType;
    }

    public BackpackSpecialty getResultSpecialty() {
        return resultSpecialty;
    }
}
