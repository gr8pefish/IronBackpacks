package gr8pefish.ironbackpacks.items.backpacks;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.util.ResourceLocation;

public enum  BackpackEnum {

    BASIC(IronBackpacksConstants.Backpacks.BASIC_BACKPACK_NAME,
            ConfigHandler.enumBasicBackpack.sizeX.getValue(),
            ConfigHandler.enumBasicBackpack.sizeY.getValue(),
            ConfigHandler.enumBasicBackpack.upgradePoints.getValue(),
            0,
            new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeX.getValue())+".png"),
            (ConfigHandler.enumBasicBackpack.sizeX.getValue() == 9 ? 200: 236),
            (114 + (18 * ConfigHandler.enumBasicBackpack.sizeY.getValue()))),

    IRON(IronBackpacksConstants.Backpacks.IRON_BACKPACK_NAME,
            ConfigHandler.enumIronBackpack.sizeX.getValue(),
            ConfigHandler.enumIronBackpack.sizeY.getValue(),
            ConfigHandler.enumIronBackpack.upgradePoints.getValue(),
            1,
            new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumIronBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumIronBackpack.sizeX.getValue())+".png"),
            (ConfigHandler.enumIronBackpack.sizeX.getValue() == 9 ? 200: 236),
            (114 + (18 * ConfigHandler.enumIronBackpack.sizeY.getValue()))),

    GOLD(IronBackpacksConstants.Backpacks.GOLD_BACKPACK_NAME,
            ConfigHandler.enumGoldBackpack.sizeX.getValue(),
            ConfigHandler.enumGoldBackpack.sizeY.getValue(),
            ConfigHandler.enumGoldBackpack.upgradePoints.getValue(),
            2,
            new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumGoldBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumGoldBackpack.sizeX.getValue())+".png"),
            (ConfigHandler.enumGoldBackpack.sizeX.getValue() == 9 ? 200: 236),
            (114 + (18 * ConfigHandler.enumGoldBackpack.sizeY.getValue()))),

    DIAMOND(IronBackpacksConstants.Backpacks.DIAMOND_BACKPACK_NAME,
            ConfigHandler.enumDiamondBackpack.sizeX.getValue(),
            ConfigHandler.enumDiamondBackpack.sizeY.getValue(),
            ConfigHandler.enumDiamondBackpack.upgradePoints.getValue(),
            3,
            new ResourceLocation(Constants.MODID, "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumDiamondBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumDiamondBackpack.sizeX.getValue())+".png"),
            (ConfigHandler.enumDiamondBackpack.sizeX.getValue() == 9 ? 200: 236),
            (114 + (18 * ConfigHandler.enumDiamondBackpack.sizeY.getValue())));



    private String name; //internal unlocalized name
    private int rowLength; //the length of each row
    private int rowCount; //number of rows
    private int upgradePoints; //the number of total upgrade points

    protected int guiId; //TODO; make dynamic with registering, and change it in the enum above

    private final ResourceLocation guiLocation; //the location of the normal gui textures
    private int guiXSize; //width of the gui
    private int guiYSize; //height of the gui

    BackpackEnum(String name, int rowLength, int rowCount, int upgradePoints, int guiId, ResourceLocation location, int guiXSize, int guiYSize){
        this.name = name;
        this.rowLength = rowLength;
        this.rowCount = rowCount;
        this.upgradePoints = upgradePoints;
        this.guiId = guiId;
        this.guiLocation = location;
        this.guiXSize = guiXSize;
        this.guiYSize = guiYSize;
    }

    protected String getName(){
        return name;
    }

    protected int getRowLength(){
        return rowLength;
    }

    protected int getRowCount(){
        return rowCount;
    }

    protected int getUpgradePoints(){
        return upgradePoints;
    }

    protected ResourceLocation getGuiResourceLocation(){
        return guiLocation;
    }

    protected int getGuiXSize(){
        return guiXSize;
    }

    protected int getGuiYSize(){
        return guiYSize;
    }


}
