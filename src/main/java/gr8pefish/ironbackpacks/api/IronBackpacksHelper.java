package gr8pefish.ironbackpacks.api;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Map;
import java.util.Set;

public class IronBackpacksHelper {

    public static final ResourceLocation NULL = new ResourceLocation("null", "null");

    private static final Map<ResourceLocation, BackpackType> BACKPACK_TYPE_REGISTRY = Maps.newHashMap();
    private static final Map<ResourceLocation, BackpackUpgrade> UPGRADE_REGISTRY = Maps.newHashMap();

    static {
        registerBackpackType(new BackpackType(NULL, 0, 0, false));
        registerUpgrade(new BackpackUpgrade(NULL, 0, 0));
    }

    // Backpacks

    public static void registerBackpackType(@Nonnull BackpackType backpackType) {
        Preconditions.checkNotNull(backpackType);

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

    // Upgrades

    public static void registerUpgrade(@Nonnull BackpackUpgrade backpackUpgrade) {
        Preconditions.checkNotNull(backpackUpgrade);

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
}
