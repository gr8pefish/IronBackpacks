package gr8pefish.ironbackpacks.api.variant;

import com.sun.xml.internal.ws.policy.sourcemodel.AssertionData;
import gr8pefish.ironbackpacks.IronBackpacks;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.ResourceLocation;

import java.util.Locale;

public enum BackpackVariantEnum implements IStringSerializable {
    NONE,
    BASIC,
    IRON_UPGRADE,
    IRON_STORAGE,
    GOLD_UPGRADE,
    GOLD_STORAGE,
    DIAMOND_UPGRADE,
    DIAMOND_STORAGE;


    @Override
    public String getName() {
        return name().toLowerCase(Locale.ENGLISH);
    }

    public static int getSize(BackpackVariantEnum variant) {
        switch (variant) {
            case BASIC:
                return 9;
            case IRON_UPGRADE:
                return 18;
            case IRON_STORAGE:
                return 27;
            case GOLD_STORAGE:
                return 36;
            case GOLD_UPGRADE:
                return 27;
            case DIAMOND_STORAGE:
                return 45;
            case DIAMOND_UPGRADE:
                return 36;
        }
        return 1;
    }

    public String getIdentifier() {
        return getName();
    }

    public static BackpackVariantEnum getVariant(BackpackVariant variant) {
        BackpackSpecialty specialty = variant.getSpecialty();
        BackpackType type = variant.getType();
        //basic
        if (specialty == BackpackSpecialty.NONE) return BASIC;
        //iron
        if (type.getIdentifier().equals(new ResourceLocation(IronBackpacks.MODID, "iron"))) {
            return specialty.equals(BackpackSpecialty.STORAGE) ? IRON_STORAGE : IRON_UPGRADE;
        }
        //gold
        if (type.getIdentifier().equals(new ResourceLocation(IronBackpacks.MODID, "gold"))) {
            return specialty.equals(BackpackSpecialty.STORAGE) ? GOLD_STORAGE : GOLD_UPGRADE;
        }
        //diamond
        if (type.getIdentifier().equals(new ResourceLocation(IronBackpacks.MODID, "diamond"))) {
            return specialty.equals(BackpackSpecialty.STORAGE) ? DIAMOND_STORAGE : DIAMOND_UPGRADE;
        }
        return NONE;
    }
}
