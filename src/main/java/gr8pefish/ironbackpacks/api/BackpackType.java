package gr8pefish.ironbackpacks.api;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class BackpackType {

    @Nonnull
    private final ResourceLocation identifier;
    @Nonnegative
    private final int tier;
    @Nonnegative
    private final int maxPoints;
    private final boolean hasSpecialties;

    public BackpackType(@Nonnull ResourceLocation identifier, @Nonnegative int tier, @Nonnegative int maxPoints, boolean hasSpecialties) {
        Preconditions.checkNotNull(identifier, "Identifier cannot be null");
        Preconditions.checkArgument(tier >= 0, "Tier cannot be negative");
        Preconditions.checkArgument(maxPoints >= 0, "Max points cannot be negative");

        this.identifier = identifier;
        this.tier = tier;
        this.maxPoints = maxPoints;
        this.hasSpecialties = hasSpecialties;
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
    public int getMaxPoints() {
        return maxPoints;
    }

    public boolean hasSpecialties() {
        return hasSpecialties;
    }

    public boolean isNull() {
        return getIdentifier().equals(IronBackpacksHelper.NULL);
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
}
