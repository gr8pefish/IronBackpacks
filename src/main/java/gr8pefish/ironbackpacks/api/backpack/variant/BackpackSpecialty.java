package gr8pefish.ironbackpacks.api.backpack.variant;

import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

/**
 * An enum to hold the possible specialties of a backpack.
 *
 * These are assumed to be mutually exclusive, at least when the backpack has a specialty (i.e. not NONE).
 */
public enum BackpackSpecialty implements IStringSerializable {
    NONE, //No specialty ont he backpack (e.g. Basic/Leather backpack)
    STORAGE, //Storage specialty, increases raw storage capacity
    UPGRADE //Upgrade specialty, increases max upgrade points
    ;

    /**
     * Gets the name of the specialty.
     *
     * @return - a String representation of the specialty.
     */
    @Override
    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    /**
     * Gets the specialty given the name of it.
     * The name can be obtained from {@link BackpackSpecialty#getName()}
     *
     * @param name - the name of the specialty to return.
     * @return - the specialty if found, NONE otherwise.
     */
    @Nonnull
    public static BackpackSpecialty getSpecialty(@Nullable String name) {
        for (BackpackSpecialty backpackSpecialty : BackpackSpecialty.values())
            if (backpackSpecialty.getName().equalsIgnoreCase(name))
                return backpackSpecialty;

        return NONE;
    }

}
