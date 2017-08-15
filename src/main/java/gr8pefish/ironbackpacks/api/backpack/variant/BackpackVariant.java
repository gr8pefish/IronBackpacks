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
 * Each variant is determined by the type and specialty.
 * Using this data, the maxUpgradePoints, size, and identifier are constructed.
 * There is only one of each variant (e.g. IRON_STORAGE).
 */
public class BackpackVariant {

    // Fields

    @Nonnull
    private final BackpackType type;
    @Nonnull
    private final BackpackSpecialty specialty;
    @Nonnull
    private final BackpackSize size; //This is with the specialty modifying it
    @Nonnegative
    private final int maxUpgradePoints; //This is with the specialty modifying it
    @Nonnull
    private final ResourceLocation identifier; //TODO: will be used for serialization once implemented

    // Constructor

    public BackpackVariant(@Nonnull BackpackType type, @Nonnull BackpackSpecialty specialty) {
        Preconditions.checkNotNull(type, "Type cannot be null");
        Preconditions.checkNotNull(specialty, "Specialty cannot be null");

        this.type = type;
        this.maxUpgradePoints = type.applyDefaultUpgradePointsSpecialtyModifier(specialty);
        this.specialty = specialty;
        this.size = type.getBaseSize().applyDefaultSizeSpecialtyModifier(specialty);

        //Generate a unique identifier from the type and specialty, ends up looking like: "ironbackpacks:variant_iron_storage"
        this.identifier = new ResourceLocation(IronBackpacks.MODID,"variant_" + type.getIdentifier().getResourcePath() + "_" + specialty.getName());

    }

    // Getters (No setters as all the fields are final)

    @Nonnull
    public BackpackType getType() {
        return type;
    }

    @Nonnull
    public BackpackSpecialty getSpecialty() {
        return specialty;
    }

    @Nonnull
    public BackpackSize getBackpackSize() {
        return size;
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

    @Override
    public String toString() {
        return "TYPE: " + type + " --- SPECIALTY: " + specialty + " --- UPGRADE POINTS: " + maxUpgradePoints + " --- SIZE: " + size + " --- IDENTIFIER: " + identifier.toString();
    }

}
