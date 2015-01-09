package main.ironbackpacks.util;

import main.ironbackpacks.ModInformation;
import net.minecraft.util.ResourceLocation;

public class IronBackpacksConstants {

    public static final class General{

    }

    public static final class Backpacks{
        public static final int BASIC_ID = 1;
        public static final int IRON_ID = 2;
        public static final int GOLD_ID = 3;
        public static final int DIAMOND_ID = 4;
    }

    public static final class Upgrades{

        public static final int BUTTON_UPGRADE_ID = 1;
        public static final int NESTING_UPGRADE_ID = 2;
        public static final int DAMAGE_BAR_UPGRADE_ID = 3;

        //Alternate Gui
        public static final int RENAMING_UPGRADE_ID = 4;
        public static final int FILTER_UPGRADE_ID = 5;
        public static final int HOPPER_UPGRADE_ID = 6;
        public static final int CONDENSER_UPGRADE_ID = 7;
    }

    public static final class Gui{
        public static final ResourceLocation BASIC_BACKPACK_LOCATION =
                new ResourceLocation(ModInformation.ID, ""+String.valueOf(ConfigHandler.enumBasicBackpack.sizeY)+"RowsOf"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeX));

    }

    public static final class Resources {
        public static final ResourceLocation WIDGETS = new ResourceLocation(ModInformation.ID, "textures/guis/widgets.png");
    }

    public static final class Miscellaneous{
        public static final String MOST_SIG_UUID = "MostSigUUID";
        public static final String LEAST_SIG_UUID = "LeastSigUUID";
    }

}
