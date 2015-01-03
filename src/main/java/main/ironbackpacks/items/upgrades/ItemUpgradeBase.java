package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.items.ItemBase;

public abstract class ItemUpgradeBase extends ItemBase{

    public int typeID;

    public ItemUpgradeBase(String unlocName, String textureName, int typeID) {
        super(unlocName, textureName);
        setMaxStackSize(16);
        this.typeID = typeID;
    }

    public abstract void alterGui(); //changes the Gui by adding whatever the upgrade does, if anything

    public abstract void alterContainer(); //changes the container by adding whatever the upgrade does, if anything

    public int getTypeID(){
        return this.typeID;
    }

}
