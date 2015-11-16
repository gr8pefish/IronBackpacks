package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.List;

/**
 * Abstract base class for all my upgrades
 */
public abstract class ItemUpgradeBase extends Item {

    private int typeID;
    private String[] tooltip;
    private int upgradeCost;

    public ItemUpgradeBase(String unlocName, String textureName, int typeID, int upgradeCost, String... descriptions) {
        super();
        setUnlocalizedName(ModInformation.ID + ":" + unlocName);
        setCreativeTab(IronBackpacks.creativeTab);
        setMaxStackSize(16);
        this.typeID = typeID;
        this.tooltip = descriptions;
        this.upgradeCost = upgradeCost;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            if (this.upgradeCost == 1)
                list.add("Costs 1 upgrade point");
            else
                list.add("Costs " + this.upgradeCost + " upgrade points");
            for (String line : this.tooltip) {
                list.add(line);
            }
        } else {
            list.add("Hold shift for more info.");
        }
    }

    public int getTypeID() {
        return this.typeID;
    }

    public int getUpgradePoints() {
        return this.upgradeCost;
    }

}
