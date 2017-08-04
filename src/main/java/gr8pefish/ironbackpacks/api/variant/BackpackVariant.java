package gr8pefish.ironbackpacks.api.variant;

import gr8pefish.ironbackpacks.ConfigHandler;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import net.minecraft.util.IStringSerializable;

import javax.annotation.Nonnull;
import java.util.Locale;

//ToDo: Need to have a GUI for each, and a size of that gui. Put it in here or in BackpackInfo?

//TODO: TehNut, feel free to refactor this code to be better/move it around;
// I just wanted to limit my own complexity with the inventory stuff, so I added this.

public enum BackpackVariant implements IStringSerializable {

    NONE,
    BASIC,
    IRON_STORAGE,
    IRON_UPGRADE,
    GOLD_STORAGE,
    GOLD_UPGRADE,
    DIAMOND_STORAGE,
    DIAMOND_UPGRADE;

    @Override
    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    @Nonnull
    public static BackpackVariant getVariant(BackpackSpecialty specialty, BackpackType type) {
        //basic
        if (specialty == BackpackSpecialty.NONE) return BASIC;
        //iron
        if (type.getTypeEnum().equals(BackpackType.BackpackTypesEnum.IRON)) {
            return specialty.equals(BackpackSpecialty.STORAGE) ? IRON_STORAGE : IRON_UPGRADE;
        }
        //gold
        if (type.getTypeEnum().equals(BackpackType.BackpackTypesEnum.GOLD)) {
            return specialty.equals(BackpackSpecialty.STORAGE) ? GOLD_STORAGE : GOLD_UPGRADE;
        }
        //diamond
        if (type.getTypeEnum().equals(BackpackType.BackpackTypesEnum.DIAMOND)) {
            return specialty.equals(BackpackSpecialty.STORAGE) ? DIAMOND_STORAGE : DIAMOND_UPGRADE;
        }
        return NONE;
    }

    @Nonnull
    public static BackpackVariant getVariant(BackpackInfo info) {
        return getVariant(info.getSpecialty(), info.getBackpackVariant());
    }

    public static int getSize(BackpackVariant variant) {
        switch (variant) {
            case BASIC:
                return ConfigHandler.sizes.sizeBasic; //TODO: use BackpackSize class (rows and columns supported)
            case IRON_STORAGE:
                return ConfigHandler.sizes.sizeIronStorage;
            case IRON_UPGRADE:
                return ConfigHandler.sizes.sizeIronUpgrade;
            case GOLD_STORAGE:
                return ConfigHandler.sizes.sizeGoldStorage;
            case GOLD_UPGRADE:
                return ConfigHandler.sizes.sizeGoldUpgrade;
            case DIAMOND_STORAGE:
                return ConfigHandler.sizes.sizeDiamondStorage;
            case DIAMOND_UPGRADE:
                return ConfigHandler.sizes.sizeDiamondUpgrade;
        }
        return -1; //error
    }

    public static int getSize(BackpackSpecialty specialty, BackpackType type) {
        return getSize(getVariant(specialty, type));
    }

    public static int getSize(BackpackInfo info) {
        return getSize(info.getSpecialty(), info.getBackpackVariant());
    }

}
