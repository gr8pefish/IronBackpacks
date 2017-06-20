package gr8pefish.ironbackpacks;

import net.minecraftforge.common.config.Config;

@Config(modid = IronBackpacks.MODID)
public class ConfigHandler {

    public static Upgrades upgrades = new Upgrades();
    public static Handholding handholding = new Handholding();

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
}
