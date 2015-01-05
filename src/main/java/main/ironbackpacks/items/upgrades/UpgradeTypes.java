package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.items.upgrades.upgradeItems.ItemButtonUpgrade;
import main.ironbackpacks.items.upgrades.upgradeItems.ItemNestingUpgrade;

public enum UpgradeTypes { //TODO - useless class?

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

    public String getFancyName(){
        return this.fancyName;
    }

//    public void doGuiAlteration(){
//        if (guiMethod != null) {
//            Method alterGui = ReflectionUtil.getMethod(guiMethod, "alterGui", null);
//            ReflectionUtil.invokeMethod(alterGui, guiMethod, null);
//        }
//    }
//
//    public void doContainerAlteration(){
//        if (containerMethod != null) {
//            Method alterContainer = ReflectionUtil.getMethod(containerMethod, "alterContainer", null);
//            ReflectionUtil.invokeMethod(alterContainer, containerMethod, null);
//        }
//    }
}
