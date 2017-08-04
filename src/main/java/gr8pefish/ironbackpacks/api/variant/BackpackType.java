package gr8pefish.ironbackpacks.api.variant;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

public class BackpackType extends IForgeRegistryEntry.Impl<BackpackType> {

    @Nonnull
    private final ResourceLocation identifier;
    @Nonnegative
    private final int tier;
    @Nonnegative
    private final int maxPoints;
    private final boolean hasSpecialties;
    @Nonnegative
    private final int sizeCols;
    @Nonnegative
    private final int sizeRows;


    public BackpackType(@Nonnull ResourceLocation identifier, @Nonnegative int tier, @Nonnegative int maxPoints, boolean hasSpecialties, @Nonnegative int sizeCols, @Nonnegative int sizeRows) {
        Preconditions.checkNotNull(identifier, "Identifier cannot be null");
        Preconditions.checkArgument(tier >= 0, "Tier cannot be negative");
        Preconditions.checkArgument(maxPoints >= 0, "Max points cannot be negative");
        Preconditions.checkArgument(sizeCols > 0, "Column count must be greater than 0" );
        Preconditions.checkArgument(sizeRows > 0, "Row count must be greater than 0" );

        this.identifier = identifier;
        this.tier = tier;
        this.maxPoints = maxPoints;
        this.hasSpecialties = hasSpecialties;
        this.sizeCols = sizeCols;
        this.sizeRows = sizeRows;

        setRegistryName(identifier);
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

    @Nonnegative
    public int getSizeCols() {
        return sizeCols;
    }

    @Nonnegative
    public int getSizeRows() {
        return sizeRows;
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
}
