package gr8pefish.ironbackpacks.api.variant;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.ConfigHandler;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import net.minecraft.util.IStringSerializable;
import net.minecraftforge.fml.common.registry.IForgeRegistryEntry;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;


public class BackpackVariant extends IForgeRegistryEntry.Impl<BackpackVariant> {

    @Nonnull
    BackpackType type;
    @Nonnull
    BackpackSpecialty specialty;
    @Nullable
    BackpackSize size;

    public BackpackVariant(@Nonnull BackpackType type, @Nonnull BackpackSpecialty specialty) {
        Preconditions.checkNotNull(type, "Type cannot be null");
        Preconditions.checkNotNull(specialty, "Specialty cannot be null");

        this.type = type;
        this.specialty = specialty;
        this.size = new BackpackSize(type.getSizeRows(), type.getSizeCols()).applySizeSpecialtyModifier(specialty);
    }

    @Nonnegative
    public int getTotalSize() {
        if (size != null) {
            return size.getTotalSize();
        }
        return -1; //error
    }

}
