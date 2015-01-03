package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.client.gui.GUIBackpack;
import main.ironbackpacks.util.ReflectionUtil;
import net.minecraft.util.StatCollector;

import java.lang.reflect.Method;

public class ItemButtonUpgrade extends ItemUpgradeBase {

    public ItemButtonUpgrade(){
        super("buttonUpgrade", "card_arrow", 1);
    }

    public void alterGui(){
        Method method = ReflectionUtil.getMethod(GUIBackpack.class, "buttonUpgrade", null);
        ReflectionUtil.invokeMethod(method,GUIBackpack.class, null);
    }

    public void alterContainer(){

    }



}
