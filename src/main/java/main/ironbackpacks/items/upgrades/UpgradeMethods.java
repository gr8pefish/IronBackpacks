package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.client.gui.inventory.GUIBackpack;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.gui.FontRenderer;

public enum UpgradeMethods {

    NONE{
        @Override
        public void doGuiAlteration(GUIBackpack gui, FontRenderer fontRenderer, int xSize, int ySize) {

        }
        @Override
        public void doContainerAlteration(){

        }
    },
    BUTTON{
        @Override
        public void doGuiAlteration(GUIBackpack gui, FontRenderer fontRenderer, int xSize, int ySize) {
////            fontRenderer.drawString("Button", xSize - 20, ySize - 96 + 2, 4210752);
//            fontRenderer.drawString(StatCollector.translateToLocal("player.inventor"), 20, ySize - 96 + 2, 4210752);
        }
        @Override
        public void doContainerAlteration(){

        }
    };

    public abstract void doGuiAlteration(GUIBackpack gui, FontRenderer fontRenderer, int xSize, int ySize);
    public abstract void doContainerAlteration();

    public static boolean hasButtonUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int i = 0; i < upgrades.length; i++){
            if (upgrades[i] == IronBackpacksConstants.Upgrades.BUTTON_UPGRADE_ID){
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

    public static boolean hasNestingUpgrade(int[] upgrades){
        boolean hasUpgrade = false;
        for (int i = 0; i < upgrades.length; i++){
            if (upgrades[i] == IronBackpacksConstants.Upgrades.NESTING_UPGRADE_ID){
                hasUpgrade = true;
                break;
            }
        }
        return hasUpgrade;
    }

}
