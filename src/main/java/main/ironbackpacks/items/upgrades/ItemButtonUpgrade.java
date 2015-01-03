package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.client.gui.GUIBackpack;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.util.StatCollector;

import java.lang.reflect.Method;

public class ItemButtonUpgrade extends ItemUpgradeBase {

    public ItemButtonUpgrade(){
        super("buttonUpgrade", "card_arrow", IronBackpacksConstants.Upgrades.BUTTON_UPGRADE_ID);
    }
}
