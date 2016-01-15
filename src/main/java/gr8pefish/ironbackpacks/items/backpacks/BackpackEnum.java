package gr8pefish.ironbackpacks.items.backpacks;

import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;

public enum  BackpackEnum {

    BASIC(IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME,
            ConfigHandler.enumBasicBackpack.sizeX.getValue(),
            ConfigHandler.enumBasicBackpack.sizeY.getValue(),
            ConfigHandler.enumBasicBackpack.upgradePoints.getValue(),
            0),
    IRON(IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME,
            ConfigHandler.enumIronBackpack.sizeX.getValue(),
            ConfigHandler.enumIronBackpack.sizeY.getValue(),
            ConfigHandler.enumIronBackpack.upgradePoints.getValue(),
            1),
    GOLD(IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME,
            ConfigHandler.enumGoldBackpack.sizeX.getValue(),
            ConfigHandler.enumGoldBackpack.sizeY.getValue(),
            ConfigHandler.enumGoldBackpack.upgradePoints.getValue(),
            2),
    DIAMOND(IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME,
            ConfigHandler.enumDiamondBackpack.sizeX.getValue(),
            ConfigHandler.enumDiamondBackpack.sizeY.getValue(),
            ConfigHandler.enumDiamondBackpack.upgradePoints.getValue(),
            3);


    private String name; //internal unlocalized name
    private int rowLength; //the length of each row
    private int rowCount; //number of rows
    private int upgradePoints; //the number of total upgrade points

    protected int guiId; //TODO; make dynamic with registering, and change it in the enum above

    BackpackEnum(String name, int rowLength, int rowCount, int upgradePoints, int guiId){
        this.name = name;
        this.rowLength = rowLength;
        this.rowCount = rowCount;
        this.upgradePoints = upgradePoints;
        this.guiId = guiId;
    }

    protected String getName(){
        return this.name;
    }

    protected int getRowLength(){
        return this.rowLength;
    }

    protected int getRowCount(){
        return this.rowCount;
    }

    protected int getUpgradePoints(){
        return this.upgradePoints;
    }


}
