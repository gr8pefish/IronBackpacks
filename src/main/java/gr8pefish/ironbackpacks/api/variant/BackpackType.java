package gr8pefish.ironbackpacks.api.variant;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class BackpackType extends IForgeRegistryEntry.Impl<BackpackType> {

    @Nonnull
    private final ResourceLocation identifier;
    @Nonnegative
    private final int tier;
    @Nonnegative
    private final int baseMaxUpgradePoints;
    private final boolean hasSpecialties;
    @Nonnull
    private final BackpackSize baseSize;


    public BackpackType(@Nonnull ResourceLocation identifier, @Nonnegative int tier, @Nonnegative int maxPoints, boolean hasSpecialties, @Nonnull BackpackSize baseSize) {
        Preconditions.checkNotNull(identifier, "Identifier cannot be null");
        Preconditions.checkArgument(tier >= 0, "Tier cannot be negative");
        Preconditions.checkArgument(maxPoints >= 0, "Max points cannot be negative");

        this.identifier = identifier;
        this.tier = tier;
        this.baseMaxUpgradePoints = maxPoints;
        this.hasSpecialties = hasSpecialties;
        this.baseSize = baseSize;

        setRegistryName(identifier);
    }

    public BackpackType(@Nonnull ResourceLocation identifier, @Nonnegative int tier, @Nonnegative int maxPoints, boolean hasSpecialties, @Nonnegative int baseSizeCols, @Nonnegative int baseSizeRows) {
        this(identifier, tier, maxPoints, hasSpecialties, new BackpackSize(baseSizeCols, baseSizeRows));
    }

    @Nonnull
    public ResourceLocation getIdentifier() {
        return identifier;
    }

    @Nonnegative
    public int getTier() {
        return tier;
    }

    @Nonnegative
    public int getBaseMaxUpgradePoints() {
        return baseMaxUpgradePoints;
    }

    public boolean hasSpecialties() {
        return hasSpecialties;
    }

    @Nonnull
    public BackpackSize getBaseSize() {
        return baseSize;
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
        if (!(o instanceof BackpackType)) return false;

        BackpackType that = (BackpackType) o;

        return identifier.equals(that.identifier);
    }

    @Override
    public int hashCode() {
        return identifier.hashCode();
    }

    @Nonnegative
    public int applyUpgradePointsSpecialtyModifier(@Nonnull BackpackSpecialty specialty) {
        if (specialty == BackpackSpecialty.UPGRADE) {
            return baseMaxUpgradePoints + 5; //TODO: Hardcoded, more configurable (/complex?)
        }
        return baseMaxUpgradePoints;
    }
}
