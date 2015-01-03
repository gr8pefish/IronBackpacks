package main.ironbackpacks.util;

import main.ironbackpacks.ModInformation;
import net.minecraft.util.ResourceLocation;

public class IronBackpacksConstants {

    public static final class General{

    }

    public static final class Backpacks{
        public static final int BASIC_ID = 1;
        public static final int ADVANCED_ID = 2;
    }

    public static final class Upgrades{
        public static final int BUTTON_UPGRADE_ID = 1;
        public static final int NESTING_UPGRADE_ID = 2;
    }

    public static final class Gui{

    }

    public static final class Resources {
        public static final ResourceLocation WIDGETS = new ResourceLocation(ModInformation.ID, "textures/guis/widgets.png");
    }

    public static final class Miscellaneous{
        public static final String MOST_SIG_UUID = "MostSigUUID";
        public static final String LEAST_SIG_UUID = "LeastSigUUID";
    }

}
