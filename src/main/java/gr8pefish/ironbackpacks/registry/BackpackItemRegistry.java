package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.api.item.backpacks.BackpackNames;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpackSubItems;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

import java.util.HashMap;
import java.util.Map;

public class BackpackItemRegistry {

    private static Map<BackpackNames, ItemBackpackSubItems> backpacksHashMap = new HashMap<>();
//    public static final String[] names = {"basicBackpack", "ironBackpack", "goldBackpack", "diamondBackpack"};

    /**
     * Initializes the buttons
     */
    public static void registerButtons(){

        //register all the backpacks

        //basic
        registerBackpack(BackpackNames.BASIC, new ItemBackpackSubItems(
                IronBackpacksConstants.Backpacks.BASIC_ID,
                "basicBackpack",
                ConfigHandler.enumBasicBackpack.sizeY.getValue(),
                ConfigHandler.enumBasicBackpack.sizeX.getValue(),
                ConfigHandler.enumBasicBackpack.upgradePoints.getValue()));

        //iron
        registerBackpack(BackpackNames.IRON, new ItemBackpackSubItems(
                IronBackpacksConstants.Backpacks.BASIC_ID,
                "ironBackpack",
                ConfigHandler.enumIronBackpack.sizeY.getValue(),
                ConfigHandler.enumIronBackpack.sizeX.getValue(),
                ConfigHandler.enumIronBackpack.upgradePoints.getValue()));

        //gold
        registerBackpack(BackpackNames.GOLD, new ItemBackpackSubItems(IronBackpacksConstants.Backpacks.BASIC_ID,
                "goldBackpack",
                ConfigHandler.enumGoldBackpack.sizeY.getValue(),
                ConfigHandler.enumGoldBackpack.sizeX.getValue(),
                ConfigHandler.enumGoldBackpack.upgradePoints.getValue()));

        //diamond
        registerBackpack(BackpackNames.DIAMOND, new ItemBackpackSubItems(
                IronBackpacksConstants.Backpacks.BASIC_ID,
                "diamondBackpack",
                ConfigHandler.enumDiamondBackpack.sizeY.getValue(),
                ConfigHandler.enumDiamondBackpack.sizeX.getValue(),
                ConfigHandler.enumDiamondBackpack.upgradePoints.getValue()));
    }

    private static void registerBackpack(BackpackNames type, ItemBackpackSubItems backpack){
        backpacksHashMap.put(type, backpack);
    }

    public static ItemBackpackSubItems getBackpack(BackpackNames type){
        return backpacksHashMap.get(type);
    }

}
