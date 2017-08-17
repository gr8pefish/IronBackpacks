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
    NONE, //No specialty on the backpack (e.g. Basic backpack)
    STORAGE, //Increases raw storage capacity
    UPGRADE //Increases max upgrade points
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
    public static BackpackSpecialty getBackpackSpecialty(@Nullable String name) {
        for (BackpackSpecialty backpackSpecialty : BackpackSpecialty.values())
            if (backpackSpecialty.getName().equalsIgnoreCase(name))
                return backpackSpecialty;

        return NONE;
    }

}