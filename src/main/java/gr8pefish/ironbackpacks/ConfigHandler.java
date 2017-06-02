package gr8pefish.ironbackpacks;

import net.minecraftforge.common.config.Config;

@Config(modid = IronBackpacks.MODID)
public class ConfigHandler {

    public static Upgrades upgrades = new Upgrades();

    public static class Upgrades {

        @Config.Comment({ "Enables the Damage Bar upgrade" })
        public boolean enableDamageBar = true;

        @Config.Comment({ "Enables the Latch upgrade" })
        public boolean enablePackLatch = true;
    }
}
