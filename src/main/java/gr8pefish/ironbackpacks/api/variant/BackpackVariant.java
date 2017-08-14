package gr8pefish.ironbackpacks.api.variant;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.IronBackpacks;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

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
        return type + ":" +specialty;
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
