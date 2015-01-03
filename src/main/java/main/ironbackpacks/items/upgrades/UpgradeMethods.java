package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.client.gui.GUIBackpack;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.StatCollector;

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

}
