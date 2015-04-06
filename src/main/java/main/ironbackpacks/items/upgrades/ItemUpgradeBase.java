package main.ironbackpacks.items.upgrades;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.items.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 * Abstract base class for all my upgrades
 */
public abstract class ItemUpgradeBase extends ItemBase{

    private int typeID;
    private String[] tooltip;
    private int upgradeCost;

    public ItemUpgradeBase(String unlocName, String textureName, int typeID, int upgradeCost, String... descriptions) {
        super(unlocName, textureName);
        setMaxStackSize(16);
        this.typeID = typeID;
        this.tooltip = descriptions;
        this.upgradeCost = upgradeCost;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            if (this.upgradeCost == 1)
                list.add("Costs 1 upgrade point");
            else
                list.add("Costs "+this.upgradeCost+" upgrade points");
            for (String line : this.tooltip) {
                list.add(line);
            }
        }else{
            list.add("Hold shift for more info.");
        }
    }

    public int getTypeID(){
        return this.typeID;
    }
    public int getUpgradePoints() {
        return this.upgradeCost;
    }

}
