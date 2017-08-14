package gr8pefish.ironbackpacks.api.variant;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.IronBackpacks;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Objects;

public class BackpackVariant {

    @Nonnull
    private final BackpackType type;
    @Nonnull
    private final BackpackSpecialty specialty;
    @Nonnull
    private BackpackSize size;
    @Nonnegative
    private int maxUpgradePoints;
    @Nonnull
    private ResourceLocation identifier; //will be used for serialization once implemented

    public BackpackVariant(@Nonnull BackpackType type, @Nonnull BackpackSpecialty specialty) {
        Preconditions.checkNotNull(type, "Type cannot be null");
        Preconditions.checkNotNull(specialty, "Specialty cannot be null");

        this.type = type;
        this.maxUpgradePoints = type.applyUpgradePointsSpecialtyModifier(specialty);
        this.specialty = specialty;
        this.size = type.getBaseSize().applySizeSpecialtyModifier(specialty);

        this.identifier = new ResourceLocation(IronBackpacks.MODID,"variant_" + type.getIdentifier().getResourcePath() + "_" + specialty.getName());

    }

    //Override for HashMap in BackpackInvImpl so that changing the inventory (and therefore the nbt value of the key/this) doesn't make map.containsKey() fail
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BackpackVariant that = (BackpackVariant) o;
        return Objects.equals(identifier, that.identifier);
    }

    @Override
    public int hashCode() {
        return Objects.hash(identifier);
    }

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

    /** Sets the size. Returns the updated backpack variant */
    @Nonnull
    public BackpackVariant setSize(@Nonnull BackpackSize size) {
        this.size = size;
        return this;
    }

    public String getName() {
        return type + "_" +specialty;
    }

    @Override
    public String toString() {
        return "TYPE: " + type + " --- SPECIALTY: " + specialty + " --- UPGRADE POINTS: " + maxUpgradePoints + " --- SIZE: " + size + " --- IDENTIFIER: " + identifier.toString();
    }

    public int getMaxUpgradePoints() {
        return maxUpgradePoints;
    }

    public void setMaxUpgradePoints(int maxUpgradePoints) {
        this.maxUpgradePoints = maxUpgradePoints;
    }

    @Nonnull
    public ResourceLocation getIdentifier() {
        return identifier;
    }

    public void setIdentifier(@Nonnull ResourceLocation identifier) {
        this.identifier = identifier;
    }
}
