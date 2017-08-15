package gr8pefish.ironbackpacks.api.backpack.variant;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * A class to represent the type of backpacks. These are "BASIC, IRON, GOLD, DIAMOND" by default.
 *
 * For more information, check out the comments in the constructor explaining what each field represents.
 */
public class BackpackType extends IForgeRegistryEntry.Impl<BackpackType> {

    // Fields

    @Nonnull
    private final ResourceLocation identifier;
    @Nonnegative
    private final int tier;
    @Nonnegative
    private final int baseMaxUpgradePoints;
    private final boolean hasSpecialties;
    @Nonnull
    private final BackpackSize baseSize;

    // Constructors

    /**
     * "Main" constructor for a BackpackType
     *
     * @param identifier - A unique identifier for the backpack. Typically something like: ResourceLocation(IronBackpacks.MODID, "basic").
     * @param tier - The tier of the backpack. Starts at 0 for BASIC backpacks, and then goes up by 1, ending with DIAMOND at tier 3.
     * @param baseMaxUpgradePoints - The maximum number of upgrade points that can be applied to this type of backpack, before specialties.
     * @param hasSpecialties - If the backpack has specialties. True for all types except BASIC.
     * @param baseSize - The size of this type of backpack, before specialties.
     */
    public BackpackType(@Nonnull ResourceLocation identifier, @Nonnegative int tier, @Nonnegative int baseMaxUpgradePoints, boolean hasSpecialties, @Nonnull BackpackSize baseSize) {
        Preconditions.checkNotNull(identifier, "Identifier cannot be null");
        Preconditions.checkArgument(tier >= 0, "Tier cannot be negative");
        Preconditions.checkArgument(baseMaxUpgradePoints >= 0, "Max points cannot be negative");

        this.identifier = identifier;
        this.tier = tier;
        this.baseMaxUpgradePoints = baseMaxUpgradePoints;
        this.hasSpecialties = hasSpecialties;
        this.baseSize = baseSize;

        setRegistryName(identifier);
    }

    /** Convenience constructor allowing you to specify the rows and columns of the size, and having that BackpackSize parameter be constructed for you. */
    public BackpackType(@Nonnull ResourceLocation identifier, @Nonnegative int tier, @Nonnegative int maxPoints, boolean hasSpecialties, @Nonnegative int baseSizeCols, @Nonnegative int baseSizeRows) {
        this(identifier, tier, maxPoints, hasSpecialties, new BackpackSize(baseSizeCols, baseSizeRows));
    }

    // Getters (all final, so no setters needed)

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

    // Overrides

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

    // Helper methods

    /**
     * Checks if a type is "NULL", as in if it's identifier is the resource location "ironbackpacks:null"
     *
     * @return - boolean result
     */
    public boolean isNull() {
        return getIdentifier().equals(IronBackpacksAPI.NULL);
    }

    /**
     * Alters the maximum upgrade points of the backpack depending on the {@link BackpackSpecialty}.
     *
     * Currently only alters for the {@link BackpackSpecialty#UPGRADE} specialty.
     *
     * @param specialty - The {@link BackpackSpecialty} to modify the size with.
     * @param pointIncrease - The number of points to increase by.
     * @return - The updated maximumUpgradePoints
     */
    @Nonnegative
    public int applyUpgradePointsSpecialtyModifier(@Nonnull BackpackSpecialty specialty, @Nonnegative int pointIncrease) {
        if (specialty == BackpackSpecialty.UPGRADE) {
            return baseMaxUpgradePoints + pointIncrease; //TODO: Use this method?
        }
        return baseMaxUpgradePoints;
    }

    /** Helper method to alter the upgrade points by the default amount, which is an increase by 5. */
    @Nonnegative
    public int applyDefaultUpgradePointsSpecialtyModifier(@Nonnull BackpackSpecialty specialty) {
        return applyUpgradePointsSpecialtyModifier(specialty, 5); //default 5
    }

}
