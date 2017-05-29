package gr8pefish.ironbackpacks.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class IronBackpacksHelper {

    public static final ResourceLocation NULL = new ResourceLocation("null", "null");

    private static final Map<ResourceLocation, BackpackType> BACKPACK_TYPE_REGISTRY = Maps.newHashMap();
    private static final Map<ResourceLocation, BackpackUpgrade> UPGRADE_REGISTRY = Maps.newHashMap();

    @GameRegistry.ObjectHolder("ironbackpacks:backpack")
    public static Item BACKPACK_ITEM;
    @GameRegistry.ObjectHolder("ironbackpacks:upgrade")
    public static Item UPGRADE_ITEM;

    static {
        registerBackpackType(new BackpackType(NULL, 0, 0, false));
        registerUpgrade(new BackpackUpgrade(NULL, 0, 0));
    }

    // Backpacks

    public static void registerBackpackType(@Nonnull BackpackType backpackType) {
        Preconditions.checkNotNull(backpackType, "Backpack type cannot be null");

        BACKPACK_TYPE_REGISTRY.put(backpackType.getIdentifier(), backpackType);
    }

    @Nonnull
    public static BackpackType getBackpackType(@Nullable ResourceLocation identifier) {
        if (!BACKPACK_TYPE_REGISTRY.containsKey(identifier))
            return BACKPACK_TYPE_REGISTRY.get(NULL);
        return BACKPACK_TYPE_REGISTRY.get(identifier);
    }

    @Nonnull
    public static Set<BackpackType> getBackpackTypes() {
        return ImmutableSet.copyOf(BACKPACK_TYPE_REGISTRY.values());
    }

    @Nonnull
    public static ItemStack getStack(@Nonnull BackpackType backpackType, @Nonnull BackpackSpecialty backpackSpecialty) {
        ItemStack stack = new ItemStack(BACKPACK_ITEM);
        BackpackInfo backpackInfo = new BackpackInfo(backpackType, backpackSpecialty, new ItemStackHandler());
        return applyPackInfo(stack, backpackInfo);
    }

    @Nonnull
    public static ItemStack applyPackInfo(@Nonnull ItemStack stack, @Nonnull BackpackInfo backpackInfo) {
        if (!stack.hasTagCompound())
            stack.setTagCompound(new NBTTagCompound());

        stack.getTagCompound().setTag("backpackInfo", backpackInfo.serializeNBT());
        return stack;
    }

    // Upgrades

    public static void registerUpgrade(@Nonnull BackpackUpgrade backpackUpgrade) {
        Preconditions.checkNotNull(backpackUpgrade, "Upgrade cannot be null");

        UPGRADE_REGISTRY.put(backpackUpgrade.getIdentifier(), backpackUpgrade);
    }

    @Nonnull
    public static BackpackUpgrade getUpgrade(@Nullable ResourceLocation identifier) {
        if (!UPGRADE_REGISTRY.containsKey(identifier))
            return UPGRADE_REGISTRY.get(NULL);

        return UPGRADE_REGISTRY.get(identifier);
    }

    @Nonnull
    public static Set<BackpackUpgrade> getUpgrades() {
        return ImmutableSet.copyOf(UPGRADE_REGISTRY.values());
    }

    @Nonnull
    public static ItemStack getStack(@Nonnull BackpackUpgrade backpackUpgrade) {
        ItemStack stack = new ItemStack(UPGRADE_ITEM);
        stack.setTagCompound(new NBTTagCompound());
        stack.getTagCompound().setString("upgrade", backpackUpgrade.getIdentifier().toString());
        return stack;
    }
}
