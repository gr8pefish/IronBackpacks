package main.ironbackpacks.items.upgrades;

import cpw.mods.fml.relauncher.ReflectionHelper;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.items.ItemRegistry;
import main.ironbackpacks.util.ReflectionUtil;

import java.lang.reflect.Method;

public enum UpgradeTypes {

    NONE("Empty Upgrade Slot", null, null),
    BUTTON("Button Upgrade", ItemButtonUpgrade.class, null),
    NESTING("Nesting Upgrade", null, ItemNestingUpgrade.class);

    public String fancyName;
    public Class guiMethod;
    public Class containerMethod;

    UpgradeTypes(String fancyName, Class guiMethod, Class containerMethod){
        this.fancyName = fancyName;
        this.guiMethod = guiMethod;                 //method to call that changes the gui
        this.containerMethod = containerMethod; //method to call that changes the container
    }

    public void doGuiAlteration(){
        if (guiMethod != null) {
            Method alterGui = ReflectionUtil.getMethod(guiMethod, "alterGui", null);
            ReflectionUtil.invokeMethod(alterGui, guiMethod, null);
        }
    }

    public String getFancyName(){
        return this.fancyName;
    }

    public void doContainerAlteration(){
        if (containerMethod != null) {
            Method alterContainer = ReflectionUtil.getMethod(containerMethod, "alterContainer", null);
            ReflectionUtil.invokeMethod(alterContainer, containerMethod, null);
        }
    }
}
