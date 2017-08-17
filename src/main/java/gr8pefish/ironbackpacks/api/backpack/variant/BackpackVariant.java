package gr8pefish.ironbackpacks.api.backpack.variant;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.IronBackpacks;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Objects;

/**
 * Class to hold the variant of a backpack.
 *
 * Each variant is determined by the backpackType and backpackSpecialty.
 * Using this data, the maxUpgradePoints, backpackSize, and identifier are constructed.
 * There is only one of each variant (e.g. IRON_STORAGE).
 */
public class BackpackVariant {

    // Fields

    @Nonnull
    private final BackpackType backpackType;
    @Nonnull
    private final BackpackSpecialty backpackSpecialty;
    @Nonnull
    private final BackpackSize backpackSize; //This is with the backpackSpecialty modifying it
    @Nonnegative
    private final int maxUpgradePoints; //This is with the backpackSpecialty modifying it
    @Nonnull
    private final ResourceLocation identifier; //TODO: will be used for serialization once implemented

    // Constructor

    public BackpackVariant(@Nonnull BackpackType type, @Nonnull BackpackSpecialty backpackSpecialty) {
        Preconditions.checkNotNull(type, "Type cannot be null");
        Preconditions.checkNotNull(backpackSpecialty, "Specialty cannot be null");

        this.backpackType = type;
        this.maxUpgradePoints = type.applyDefaultUpgradePointModifierFromBackpackSpecialty(backpackSpecialty);
        this.backpackSpecialty = backpackSpecialty;
        this.backpackSize = type.getBaseBackpackSize().applyDefaultSizeModifierFromBackpackSpecialty(backpackSpecialty);

        //Generate a unique identifier from the backpackType and backpackSpecialty, ends up looking like: "ironbackpacks:variant_iron_storage"
        this.identifier = new ResourceLocation(IronBackpacks.MODID,"variant_" + type.getIdentifier().getResourcePath() + "_" + backpackSpecialty.getName());

    }

    // Getters (No setters as all the fields are final)

    @Nonnull
    public BackpackType getBackpackType() {
        return backpackType;
    }

    @Nonnull
    public BackpackSpecialty getBackpackSpecialty() {
        return backpackSpecialty;
    }

    @Nonnull
    public BackpackSize getBackpackSize() {
        return backpackSize;
    }

    @Nonnegative
    public int getMaxUpgradePoints() {
        return maxUpgradePoints;
    }

    @Nonnull
    public ResourceLocation getIdentifier() {
        return identifier;
    }

    // Overrides

    //Override for HashMap in BackpackInvImpl so that changing the inventory (and therefore the nbt value of the key/this) doesn't make map.containsKey() fail
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackpackVariant that = (BackpackVariant) o;
        return Objects.equals(identifier, that.identifier);
    }

    //Only care about the identifier for uniqueness, so just hash that
    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

    //Convenience method for printing
    @Override
    public String toString() {
        return "TYPE: " + backpackType + " --- SPECIALTY: " + backpackSpecialty + " --- UPGRADE POINTS: " + maxUpgradePoints + " --- SIZE: " + backpackSize + " --- IDENTIFIER: " + identifier.toString();
    }

}
