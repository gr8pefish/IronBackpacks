package gr8pefish.ironbackpacks.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.inventory.IBackpackInventoryProvider;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.variant.BackpackType;
import gr8pefish.ironbackpacks.api.variant.BackpackVariant;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class IronBackpacksAPI {


    /**
     * The capability object for IBackpackInventoryProvider
     */
    @CapabilityInject(IBackpackInventoryProvider.class)
    public static final Capability<IBackpackInventoryProvider> BACKPACK_INV_CAPABILITY = null;


    private static ArrayList<BackpackVariant> variantList = new ArrayList<>(); //change to HashMap<RLIdentifier, BackpackVariant> ?

    public static final ResourceLocation NULL = new ResourceLocation("ironbackpacks", "null");

    @GameRegistry.ObjectHolder("ironbackpacks:backpack")
    public static Item BACKPACK_ITEM = null;
    @GameRegistry.ObjectHolder("ironbackpacks:upgrade")
    public static Item UPGRADE_ITEM = null;

    private static IForgeRegistry<BackpackType> typeRegistry = null;
    private static IForgeRegistry<BackpackUpgrade> upgradeRegistry = null;

    // Backpacks

    public static IForgeRegistry<BackpackType> getTypeRegistry() {
        return typeRegistry == null ? typeRegistry = GameRegistry.findRegistry(BackpackType.class) : typeRegistry;
    }

    @Nonnull
    public static BackpackType getBackpackType(@Nullable ResourceLocation identifier) {
        return getTypeRegistry().getValue(identifier);
    }

    @Nonnull
    public static Set<BackpackType> getBackpackTypes() {
        return ImmutableSet.copyOf(getTypeRegistry().getValues());
    }

    @Nonnull
    public static ItemStack getStack(@Nonnull BackpackType backpackType, @Nonnull BackpackSpecialty backpackSpecialty) {
        Preconditions.checkNotNull(backpackType, "BackpackType cannot be null");
        Preconditions.checkNotNull(backpackSpecialty, "BackpackSpecialty cannot be null");

        ItemStack stack = new ItemStack(BACKPACK_ITEM);
        BackpackInfo backpackInfo = new BackpackInfo(new BackpackVariant(backpackType, backpackSpecialty));
        return applyPackInfo(stack, backpackInfo);
    }

    @Nonnull
    public static ItemStack applyPackInfo(@Nonnull ItemStack stack, @Nonnull BackpackInfo backpackInfo) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");
        Preconditions.checkNotNull(backpackInfo, "BackpackInfo cannot be null");

        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());

        stack.getTagCompound().setTag("packInfo", backpackInfo.serializeNBT());
        return stack;
    }

    // Upgrades

    public static IForgeRegistry<BackpackUpgrade> getUpgradeRegistry() {
        return upgradeRegistry == null ? upgradeRegistry = GameRegistry.findRegistry(BackpackUpgrade.class) : upgradeRegistry;
    }


    @Nonnull
    public static BackpackUpgrade getUpgrade(@Nullable ResourceLocation identifier) {
        return getUpgradeRegistry().getValue(identifier);
    }

    @Nonnull
    public static Set<BackpackUpgrade> getUpgrades() {
        return ImmutableSet.copyOf(getUpgradeRegistry().getValues());
    }

    @Nonnull
    public static ItemStack getStack(@Nonnull BackpackUpgrade backpackUpgrade) {
        Preconditions.checkNotNull(backpackUpgrade, "BackpackUpgrade cannot be null");

        ItemStack stack = new ItemStack(UPGRADE_ITEM);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setString("upgrade", backpackUpgrade.getIdentifier().toString());
        return stack;
    }

    /**
     * Creates a list of each {@link BackpackVariant} possible.
     *
     * Populated in init.
     */
    public static void initVariantList() {

        List<BackpackType> sortedTypes = Lists.newArrayList(IronBackpacksAPI.getBackpackTypes());
        sortedTypes.sort(Comparator.comparingInt(BackpackType::getTier));

        for (BackpackType backpackType : sortedTypes) {
            if (backpackType.getIdentifier().equals(IronBackpacksAPI.NULL))
                continue;

            if (!backpackType.hasSpecialties()) {
                variantList.add(new BackpackVariant(backpackType, BackpackSpecialty.NONE));
            } else {
                for (BackpackSpecialty specialty : BackpackSpecialty.values()) {
                    if (specialty == BackpackSpecialty.NONE)
                        continue;

                    variantList.add(new BackpackVariant(backpackType, specialty));
                }
            }
        }

    }

    /**
     * Gets the list of all possible {@link BackpackVariant}s.
     *
     * @return - An immutable copy of the list of variants
     */
    public static List<BackpackVariant> getVariantList() {
        return ImmutableList.copyOf(variantList);
    }

}
