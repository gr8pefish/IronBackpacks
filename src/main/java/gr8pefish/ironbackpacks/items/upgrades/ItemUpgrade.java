package gr8pefish.ironbackpacks.items.upgrades;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemPackUpgrade;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

public class ItemUpgrade extends Item {

    public ItemUpgrade(){
        setUnlocalizedName(Constants.MODID + "." + IronBackpacksAPI.ITEM_UPGRADE_BASE + ".");
        setCreativeTab(IronBackpacks.creativeTab);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List<ItemStack> list) {
        for (int i = 0; i < ItemUpgradeRegistry.getSize(); i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + ItemUpgradeRegistry.getItemUpgrade(stack.getItemDamage()).getName();
    }

    //necessary?
    public ItemPackUpgrade getItemPackUpgrade(int meta) {
        return ItemUpgradeRegistry.getItemUpgrade(meta);
    }

    public static ItemPackUpgrade getItemPackUpgrade(ItemStack stack) {
        return ItemUpgradeRegistry.getItemUpgrade(stack.getItemDamage());
    }
}
