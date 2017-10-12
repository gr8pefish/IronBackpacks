package gr8pefish.ironbackpacks.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackType;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackVariant;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;

public class IronBackpacksAPI {

    // Fields

    /** Public object holder references to all the items in Iron Backpacks. */
    @GameRegistry.ObjectHolder("ironbackpacks:backpack")
    public static Item BACKPACK_ITEM = Items.AIR;
    @GameRegistry.ObjectHolder("ironbackpacks:upgrade")
    public static Item UPGRADE_ITEM = Items.AIR;

    /** Private registries for internal use (can be accessed publicly via methods below). */
    private static IForgeRegistry<BackpackType> backpackTypeRegistry = null;
    private static IForgeRegistry<BackpackUpgrade> upgradeRegistry = null;

    /** Public {@link ResourceLocation} for NULL identifiers. */
    public static final ResourceLocation NULL = new ResourceLocation("ironbackpacks", "null");

    /** Private list containing all {@link BackpackVariant} possibilities. See {@link IronBackpacksAPI#initBackpackVariantList()} below for initialization. */
    private static ArrayList<BackpackVariant> backpackVariantList = new ArrayList<>();

    // Types

    /** Public facing method for getting the {@link BackpackType} registry. */
    @Nonnull
    public static IForgeRegistry<BackpackType> getBackpackTypeRegistry() {
        return backpackTypeRegistry == null ? backpackTypeRegistry = GameRegistry.findRegistry(BackpackType.class) : backpackTypeRegistry;
    }

    /**
     * Get the {@link BackpackType} from the {@link GameRegistry} given the BackpackType's identifier.
     * //TODO: Null safety here?
     * @param identifier - The unique {@link ResourceLocation} of the BackpackType
     * @return - The BackpackType found
     */
    @Nonnull
    public static BackpackType getBackpackType(@Nonnull ResourceLocation identifier) {
        Preconditions.checkNotNull(identifier, "Identifier cannot be null");
        return getBackpackTypeRegistry().getValue(identifier);
    }

    /**
     * Gets an immutable Set of the BackpackTypes present in the {@link IronBackpacksAPI#getBackpackTypeRegistry()}.
     *
     * @return - An ImmutableSet filled with all the {@link BackpackType}s.
     */
    @Nonnull
    public static Set<BackpackType> getBackpackTypes() {
        return ImmutableSet.copyOf(getBackpackTypeRegistry().getValues());
    }

    // Upgrades

    /** Public facing method for getting the {@link BackpackUpgrade} registry. */
    @Nonnull
    public static IForgeRegistry<BackpackUpgrade> getUpgradeRegistry() {
        return upgradeRegistry == null ? upgradeRegistry = GameRegistry.findRegistry(BackpackUpgrade.class) : upgradeRegistry;
    }

    /**
     * Get the {@link BackpackUpgrade} from the {@link GameRegistry} given the BackpackUpgrade's identifier.
     * //TODO: Null safety here?
     * @param identifier - The unique {@link ResourceLocation} of the BackpackUpgrade
     * @return - The BackpackUpgrade found
     */
    @Nonnull
    public static BackpackUpgrade getUpgrade(@Nonnull ResourceLocation identifier) {
        Preconditions.checkNotNull(identifier, "Identifier cannot be null");
        return getUpgradeRegistry().getValue(identifier);
    }

    /**
     * Gets an immutable Set of the BackpackUpgrades present in the {@link IronBackpacksAPI#getUpgradeRegistry()}.
     *
     * @return - An ImmutableSet filled with all the {@link BackpackUpgrade}s.
     */
    @Nonnull
    public static Set<BackpackUpgrade> getUpgrades() {
        return ImmutableSet.copyOf(getUpgradeRegistry().getValues());
    }

    /**
     * Gets the {@link ItemStack} representation from a {@link BackpackUpgrade}.
     *
     * @param backpackUpgrade - The backpack upgrade to get an ItemStack of.
     * @return - An ItemStack for the Upgrade
     */
    @Nonnull
    public static ItemStack getStack(@Nonnull BackpackUpgrade backpackUpgrade) {
        Preconditions.checkNotNull(backpackUpgrade, "BackpackUpgrade cannot be null");

        ItemStack stack = new ItemStack(UPGRADE_ITEM);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setString("upgrade", backpackUpgrade.getIdentifier().toString());
        return stack;
    }

    // Misc

    /**
     * Gets the Backpack as an {@link ItemStack} from a {@link BackpackVariant}.
     *
     * @param backpackVariant - The variant to construct the itemStack from.
     * @return - An ItemStack for the backpack.
     */
    @Nonnull
    public static ItemStack getStack(@Nonnull BackpackVariant backpackVariant) {
        Preconditions.checkNotNull(backpackVariant, "BackpackVariant cannot be null");

        ItemStack stack = new ItemStack(BACKPACK_ITEM);
        BackpackInfo backpackInfo = new BackpackInfo(backpackVariant);
        return applyPackInfo(stack, backpackInfo);
    }

    /**
     * Gets the Backpack as an {@link ItemStack} from a {@link BackpackType} and a {@link BackpackSpecialty}.
     *
     * @param backpackType - The type to construct the itemStack from.
     * @param backpackSpecialty - The specialty to construct the itemStack from.
     * @return - An ItemStack for the backpack.
     */
    @Nonnull
    public static ItemStack getStack(@Nonnull BackpackType backpackType, @Nonnull BackpackSpecialty backpackSpecialty) {
        Preconditions.checkNotNull(backpackType, "BackpackType cannot be null");
        Preconditions.checkNotNull(backpackSpecialty, "BackpackSpecialty cannot be null");

        ItemStack stack = new ItemStack(BACKPACK_ITEM);
        BackpackInfo backpackInfo = new BackpackInfo(new BackpackVariant(backpackType, backpackSpecialty));
        return applyPackInfo(stack, backpackInfo);
    }

    /**
     * Applies the {@link BackpackInfo} to an {@link ItemStack} of a backpack, giving it the necessary NBT.
     *
     * @param stack - The backpack as an ItemStack.
     * @param backpackInfo - The backpackInfo to apply.
     * @return - The updated itemStack with the info applied.
     */
    @Nonnull
    public static ItemStack applyPackInfo(@Nonnull ItemStack stack, @Nonnull BackpackInfo backpackInfo) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");
        Preconditions.checkNotNull(backpackInfo, "BackpackInfo cannot be null");

        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());

        stack.getTagCompound().setTag("packInfo", backpackInfo.serializeNBT());
        if (backpackInfo.getInventory() != null) {
            NBTTagList invTag = new NBTTagList();
            for (int i = 0; i < backpackInfo.getInventory().getSlots(); i++)
                invTag.appendTag(backpackInfo.getInventory().getStackInSlot(i).serializeNBT());

            stack.getTagCompound().setTag("packInv", invTag);
        }

        return stack;
    }

    // Variants

    /**
     * Creates a list of each {@link BackpackVariant} possible.
     *
     * NOTE: Does *NOT* include the [Type.NULL, Specialty.NONE] variant, only "valid" variants
     * NOTE: Populated in init.
     */
    public static void initBackpackVariantList() {

        List<BackpackType> sortedTypes = Lists.newArrayList(IronBackpacksAPI.getBackpackTypes());
        sortedTypes.sort(Comparator.comparingInt(BackpackType::getTier));

        for (BackpackType backpackType : sortedTypes) {
            if (backpackType.getIdentifier().equals(IronBackpacksAPI.NULL))
                continue;

            if (!backpackType.hasSpecialties()) {
                backpackVariantList.add(new BackpackVariant(backpackType, BackpackSpecialty.NONE));
            } else {
                for (BackpackSpecialty specialty : BackpackSpecialty.values()) {
                    if (specialty == BackpackSpecialty.NONE)
                        continue;

                    backpackVariantList.add(new BackpackVariant(backpackType, specialty));
                }
            }
        }

    }

    /**
     * Gets the list of all possible {@link BackpackVariant}s.
     *
     * @return - An immutable copy of the list of variants
     */
    @Nonnull
    public static List<BackpackVariant> getBackpackVariantList() {
        return ImmutableList.copyOf(backpackVariantList);
    }

    /**
     * Get the {@link BackpackVariant} from the variant list.
     * TODO: Unused, remove?
     * @param type - The {@link BackpackType}
     * @param specialty - The {@link BackpackSpecialty}
     * @return - The BackpackVariant if found, null otherwise (should almost never realistically return null)
     */
    @Nullable
    public static BackpackVariant getBackpackVariantFromList(@Nonnull BackpackType type, @Nonnull BackpackSpecialty specialty) {
        Preconditions.checkNotNull(specialty, "Specialty cannot be null");
        Preconditions.checkNotNull(type, "Type cannot be null");

        List<BackpackVariant> list = getBackpackVariantList();
        for (BackpackVariant backpackVariant : list) {
            if (backpackVariant.getBackpackType() == type && backpackVariant.getBackpackSpecialty() == specialty) {
                return backpackVariant;
            }
        }
        return null;
    }

}
