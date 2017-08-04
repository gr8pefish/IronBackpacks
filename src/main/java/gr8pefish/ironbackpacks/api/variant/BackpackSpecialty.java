package gr8pefish.ironbackpacks.api.variant;

import net.minecraft.util.IStringSerializable;

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

    public static BackpackSpecialty getSpecialty(String name) {
        for (BackpackSpecialty backpackSpecialty : BackpackSpecialty.values())
            if (backpackSpecialty.getName().equalsIgnoreCase(name))
                return backpackSpecialty;

        return NONE;
    }

    //TODO
//    public static ? getEffect() {
//        case STORAGE:
//            return storageSize + 9;
//        case UPGRADE:
//            return upgradePoints + 5;
//    }

    public static int getSize(BackpackSpecialty specialty) {
        if (specialty == BackpackSpecialty.NONE) {
            return 9;
        } else if (specialty == BackpackSpecialty.UPGRADE) {
            return 18;
        } else if (specialty == BackpackSpecialty.STORAGE) {
            return 27;
        } else {
            return -1;
        }
    }

}
