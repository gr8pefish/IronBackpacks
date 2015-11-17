package main.ironbackpacks.items.backpacks;

import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;

/**
 * Enumeration for the values stored in each backpack
 */
public enum BackpackTypes {

    BASIC(IronBackpacksConstants.Backpacks.BASIC_ID, //id
            ConfigHandler.enumBasicBackpack.sizeX.getValue() * ConfigHandler.enumBasicBackpack.sizeY.getValue(), //size
            ConfigHandler.enumBasicBackpack.sizeX.getValue(), //rowLength
            "basicBackpack", //name
            ConfigHandler.enumBasicBackpack.upgradePoints.getValue(), //upgradePoints
            ConfigHandler.useAlternateBackpackTextures ? "backpack_basic_alt" : "backpack_basic"), //texture
    IRON(IronBackpacksConstants.Backpacks.IRON_ID,
            ConfigHandler.enumIronBackpack.sizeX.getValue() * ConfigHandler.enumIronBackpack.sizeY.getValue(),
            ConfigHandler.enumIronBackpack.sizeX.getValue(),
            "ironBackpack",
            ConfigHandler.enumIronBackpack.upgradePoints.getValue(),
            ConfigHandler.useAlternateBackpackTextures ? "backpack_iron_alt" : "backpack_iron"),
    GOLD(IronBackpacksConstants.Backpacks.GOLD_ID,
            ConfigHandler.enumGoldBackpack.sizeX.getValue() * ConfigHandler.enumGoldBackpack.sizeY.getValue(),
            ConfigHandler.enumGoldBackpack.sizeX.getValue(),
            "goldBackpack",
            ConfigHandler.enumGoldBackpack.upgradePoints.getValue(),
            ConfigHandler.useAlternateBackpackTextures ? "backpack_gold_alt" : "backpack_gold"),
    DIAMOND(IronBackpacksConstants.Backpacks.DIAMOND_ID,
            ConfigHandler.enumDiamondBackpack.sizeX.getValue() * ConfigHandler.enumDiamondBackpack.sizeY.getValue(),
            ConfigHandler.enumDiamondBackpack.sizeX.getValue(),
            "diamondBackpack",
            ConfigHandler.enumDiamondBackpack.upgradePoints.getValue(),
            ConfigHandler.useAlternateBackpackTextures ? "backpack_diamond_alt" : "backpack_diamond");

    private int id; //id int value of number in enum
    private int size; //number of slots
    private int rowLength; //number of rows
    public String name; //display name
    public int upgradePoints; //the number of total upgrade points
    public String texture; //the texture of the item to display

    BackpackTypes(int id, int size, int rowLength, String fancyName, int upgradePoints, String texture){
        this.id = id;
        this.size = size;
        this.rowLength = rowLength;
        this.name = fancyName;
        this.upgradePoints = upgradePoints;
        this.texture = texture;
    }

    public int getRowCount() {
        return size / rowLength;
    }

    public int getRowLength() {
        return rowLength;
    }

    public int getSize() { return size; }

    public String getName() {return name; }

    public int getId() { return id; }

    public int getUpgradePoints() {
        return upgradePoints;
    }

    public String getTexture(){
        return texture;
    }
}
