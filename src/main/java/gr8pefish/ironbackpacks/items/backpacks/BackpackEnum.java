package gr8pefish.ironbackpacks.items.backpacks;

import gr8pefish.ironbackpacks.config.ConfigHandler;

public enum  BackpackEnum {

    BASIC("basicBackpack", ConfigHandler.enumBasicBackpack.sizeX.getValue(), ConfigHandler.enumBasicBackpack.sizeY.getValue(), ConfigHandler.enumBasicBackpack.upgradePoints.getValue()),
    IRON("ironBackpack", ConfigHandler.enumIronBackpack.sizeX.getValue(), ConfigHandler.enumIronBackpack.sizeY.getValue(), ConfigHandler.enumIronBackpack.upgradePoints.getValue()),
    GOLD("goldBackpack", ConfigHandler.enumGoldBackpack.sizeX.getValue(), ConfigHandler.enumGoldBackpack.sizeY.getValue(), ConfigHandler.enumGoldBackpack.upgradePoints.getValue()),
    DIAMOND("diamondBackpack", ConfigHandler.enumDiamondBackpack.sizeX.getValue(), ConfigHandler.enumDiamondBackpack.sizeY.getValue(), ConfigHandler.enumDiamondBackpack.upgradePoints.getValue());


    private String name; //internal unlocalized name
    private int rowLength; //the length of each row
    private int rowCount; //number of rows
    private int upgradePoints; //the number of total upgrade points

    BackpackEnum(String name, int rowLength, int rowCount, int upgradePoints){
        this.name = name;
        this.rowLength = rowLength;
        this.rowCount = rowCount;
        this.upgradePoints = upgradePoints;
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
