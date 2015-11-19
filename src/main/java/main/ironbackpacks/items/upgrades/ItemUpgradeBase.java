package main.ironbackpacks.items.upgrades;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.items.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;
import main.ironbackpacks.items.upgrades.IPackUpgrade;
import scala.actors.threadpool.Arrays;

import java.util.List;

/**
 * Abstract base class for all my upgrades
 */
public abstract class ItemUpgradeBase extends ItemBase implements IPackUpgrade{

    private int typeID;
    private String[] tooltip;
    private int upgradeCost;
    private String name;

    public ItemUpgradeBase(String unlocName, int typeID, int upgradeCost, String... descriptions) {
        super(unlocName, unlocName); //unlocName, textureName (they are the same)
        setMaxStackSize(16);
        this.typeID = typeID;
        this.tooltip = descriptions;
        this.upgradeCost = upgradeCost;
        this.name = unlocName;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            list.add("Costs " + getUpgradeCost() + " upgrade point" + (getUpgradeCost() == 1 ? "" : "s"));
            list.addAll(getTooltip());
        } else {
            list.add("Hold shift for more info.");
        }
    }

    @Override
    public String getName(){
        return this.name;
    }

    @Override
    public int getId(){
        return this.typeID;
    }

    @Override
    public int getUpgradeCost() {
        return this.upgradeCost;
    }

    @Override
    public List<String> getTooltip(){
        return Arrays.asList(tooltip);
    }


}
