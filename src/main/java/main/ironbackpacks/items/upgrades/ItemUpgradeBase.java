package main.ironbackpacks.items.upgrades;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.util.TextUtils;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

/**
 * Abstract base class for all my upgrades
 */
public abstract class ItemUpgradeBase extends Item implements IPackUpgrade {

    private int typeID;
    private String[] tooltip;
    private int upgradeCost;
    private String name;

    public ItemUpgradeBase(String unlocName, int typeID, int upgradeCost, String... descriptions) {
        super();
        setUnlocalizedName(ModInformation.ID + ":" + unlocName);
        setCreativeTab(IronBackpacks.creativeTab);
        setMaxStackSize(16);
        this.name = unlocName;
        this.typeID = typeID;
        this.tooltip = descriptions;
        this.upgradeCost = upgradeCost;
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean par4) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.cost", getUpgradeCost(), getUpgradeCost() == 1 ? "" : "s"));
            list.addAll(getTooltip());
        } else {
            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.shift"));
        }
    }

    @Override
    public int getId() {
        return this.typeID;
    }

    @Override
    public int getUpgradeCost() {
        return this.upgradeCost;
    }

    @Override
    public List<String> getTooltip() {
        return Arrays.asList(tooltip);
    }

    @Override
    public String getName() {
        return name;
    }
}
