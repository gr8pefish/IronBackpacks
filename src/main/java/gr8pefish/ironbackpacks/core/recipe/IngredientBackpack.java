package gr8pefish.ironbackpacks.core.recipe;

import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackType;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntComparators;
import it.unimi.dsi.fastutil.ints.IntList;
import net.minecraft.client.util.RecipeItemHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.stream.Collectors;

public class IngredientBackpack extends Ingredient {

    private final int packTier;
    private final BackpackSpecialty specialty;
    private final NonNullList<BackpackType> types;
    private IntList itemIds = null;
    private ItemStack[] items;

    public IngredientBackpack(int packTier, BackpackSpecialty specialty) {
        super();

        this.packTier = packTier;
        this.specialty = specialty;
        // Gathers all BackpackTypes from the API that match our required tier.
        this.types = NonNullList.from(
                IronBackpacksAPI.getBackpackType(IronBackpacksAPI.NULL),
                IronBackpacksAPI.getBackpackTypes()
                        .stream()
                        .filter(b -> !b.getIdentifier().equals(IronBackpacksAPI.NULL) && b.getTier() == packTier)
                        .collect(Collectors.toList())
                        .toArray(new BackpackType[0])
        );
    }

    public IngredientBackpack(BackpackType type, BackpackSpecialty specialty) {
        this(type.getTier(), specialty);
    }

    @Override
    public ItemStack[] getMatchingStacks() {
        if (this.items == null) {
            NonNullList<ItemStack> stacks = NonNullList.withSize(types.size(), ItemStack.EMPTY);
            for (int i = 0; i < types.size(); i++) {
                ItemStack stack = IronBackpacksAPI.getStack(types.get(i), specialty);
                stacks.set(i, stack);
            }
            this.items = stacks.toArray(new ItemStack[0]);
        }

        return this.items;
    }

    @Override
    @Nonnull
    @SideOnly(Side.CLIENT)
    public IntList getValidItemStacksPacked() {
        if (this.itemIds == null || itemIds.size() != types.size()) {
            this.itemIds = new IntArrayList(types.size());

            for (BackpackType type : types)
                this.itemIds.add(RecipeItemHelper.pack(IronBackpacksAPI.getStack(type, specialty)));

            this.itemIds.sort(IntComparators.NATURAL_COMPARATOR);
        }

        return this.itemIds;
    }

    @Override
    public boolean apply(@Nullable ItemStack input) {
        if (input == null || input.isEmpty())
            return false;

        if (!input.hasTagCompound())
            return false;

        if (!(input.getItem() instanceof IBackpack))
            return false;

        BackpackInfo info = ((IBackpack) input.getItem()).getBackpackInfo(input);
        return info.getVariant().getBackpackType().getTier() == packTier && info.getVariant().getBackpackSpecialty() == specialty;
    }

    @Override
    protected void invalidate() {
        this.itemIds = null;
    }
}
