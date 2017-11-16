package gr8pefish.ironbackpacks;

import net.minecraftforge.common.config.Config;

@Config(modid = IronBackpacks.MODID, name = IronBackpacks.MODID + "/" + IronBackpacks.MODID)
public class ConfigHandler {

    public static Upgrades upgrades = new Upgrades();
    public static Handholding handholding = new Handholding();
    public static BackpackSizes sizes = new BackpackSizes();

    public static class Handholding {
        @Config.RangeInt(min = 1)
        @Config.Comment({"The maximum depth that can be used for Nesting Upgrades", "This restriction is here to protect against stupidly large NBT sizes."})
        public int maxNests = 1;
    }

    public static class Upgrades {
        @Config.Comment({ "Enables the Damage Bar upgrade" })
        public boolean enableDamageBar = true;

        @Config.Comment({ "Enables the Latch upgrade" })
        public boolean enablePackLatch = true;
    }

    public static class BackpackSizes {
        @Config.RangeInt(min = 1, max = 54)
        @Config.Comment({ "Size of the basic backpack"} )
        public int sizeBasic = 18;

        @Config.RangeInt(min = 1, max = 54)
        @Config.Comment({ "Size of the iron backpack - storage emphasis"} )
        public int sizeIronStorage = 36;

        @Config.RangeInt(min = 1, max = 54)
        @Config.Comment({ "Size of the iron backpack - upgrade emphasis"} )
        public int sizeIronUpgrade = 27;

        @Config.RangeInt(min = 1, max = 54)
        @Config.Comment({ "Size of the iron backpack - storage emphasis"} )
        public int sizeGoldStorage = 45;

        @Config.RangeInt(min = 1, max = 54)
        @Config.Comment({ "Size of the iron backpack - upgrade emphasis"} )
        public int sizeGoldUpgrade = 36;

        @Config.RangeInt(min = 1, max = 54)
        @Config.Comment({ "Size of the iron backpack - storage emphasis"} )
        public int sizeDiamondStorage = 54;

        @Config.RangeInt(min = 1, max = 54)
        @Config.Comment({ "Size of the iron backpack - upgrade emphasis"} )
        public int sizeDiamondUpgrade = 45;
    }

}
