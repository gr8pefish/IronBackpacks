package gr8pefish.ironbackpacks.api.upgrade;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.ModifyMethod;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Set;

public class BackpackUpgrade extends IForgeRegistryEntry.Impl<BackpackUpgrade> {

    private final ResourceLocation identifier;
    private final int applicationCost;
    private final int minimumTier;
    private final Set<BackpackUpgrade> conflicting;

    public BackpackUpgrade(@Nonnull ResourceLocation identifier, int applicationCost, @Nonnegative int minimumTier) {
        Preconditions.checkNotNull(identifier, "Identifier cannot be null");
        Preconditions.checkArgument(minimumTier >= 0, "Minimum tier cannot be negative");

        this.applicationCost = applicationCost;
        this.minimumTier = minimumTier;
        this.identifier = identifier;
        this.conflicting = Sets.newHashSet();

        setRegistryName(identifier);
    }

    /**
     * Called during valid points where the player's inventory has been modified. Adjust your logic according to the
     * ModifyMethod.
     *
     * @param method - How the inventory was modified
     * @param stack - The stack that was modified
     * @param player - The player who's inventory was modified
     * @param backpackInfo - All data relevant to the active backpack
     */
    public void onInventory(@Nonnull ModifyMethod method, @Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull BackpackInfo backpackInfo) {
        // No-op
    }

    public final int getApplicationCost() {
        return applicationCost;
    }

    @Nonnegative
    public final int getMinimumTier() {
        return minimumTier;
    }

    @Nonnull
    public ResourceLocation getIdentifier() {
        return identifier;
    }

    public boolean isConflicting(@Nullable BackpackUpgrade backpackUpgrade) {
        return backpackUpgrade == null || conflicting.contains(backpackUpgrade);
    }

    @Nonnull
    public BackpackUpgrade addConflicting(@Nonnull BackpackUpgrade backpackUpgrade) {
        Preconditions.checkNotNull(backpackUpgrade, "BackpackUpgrade cannot be null");

        conflicting.add(backpackUpgrade);
        return this;
    }

    public boolean isNull() {
        return getIdentifier().equals(IronBackpacksAPI.NULL);
    }

    @Override
    public String toString() {
        return Objects.toStringHelper(this)
                .add("identifier", identifier)
                .toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BackpackUpgrade)) return false;

        BackpackUpgrade that = (BackpackUpgrade) o;

        return identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }
}
