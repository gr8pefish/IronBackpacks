package gr8pefish.ironbackpacks.api.variant;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public enum BackpackSpecialty implements IStringSerializable {
    NONE,
    STORAGE,
    UPGRADE
    ;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    @Nonnull
    public static BackpackSpecialty getSpecialty(@Nullable String name) {
        for (BackpackSpecialty backpackSpecialty : BackpackSpecialty.values())
            if (backpackSpecialty.getName().equalsIgnoreCase(name))
                return backpackSpecialty;

        return NONE;
    }

}
