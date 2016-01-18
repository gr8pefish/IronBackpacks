package gr8pefish.ironbackpacks.items.upgrades;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.upgrades.interfaces.IPackUpgrade;
import gr8pefish.ironbackpacks.api.register.ItemUpgradeRegistry;
import gr8pefish.ironbackpacks.util.TextUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

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
        for (int i = 0; i < ItemUpgradeRegistry.getTotalSize(); i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + ItemUpgradeRegistry.getItemUpgrade(stack).getName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.cost", ItemUpgradeRegistry.getItemUpgrade(itemStack).getUpgradeCost(itemStack), ItemUpgradeRegistry.getItemUpgrade(itemStack).getUpgradeCost(itemStack) == 1 ? "" : "s"));
            list.addAll(ItemUpgradeRegistry.getItemUpgrade(itemStack).getTooltip(itemStack));
        } else {
            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.shift"));
        }
    }

//    @Override
//    public String getName(ItemStack stack){
//        return getItemPackUpgrade(stack).getName(stack);
//    }

//    @Override
    public static int getUpgradeCost(ItemStack stack) {
        return ItemUpgradeRegistry.getItemUpgrade(stack).getUpgradeCost(stack);
    }

    public static int getId(ItemStack stack) {
        return stack.getItemDamage(); //TODO: I can do better, dynamic lookup or something
    }

    public static boolean areUpgradesEqual(ItemStack toCheck, IPackUpgrade upgrade){
        return ItemUpgradeRegistry.getItemUpgrade(toCheck).equals(upgrade);
    }

//    @Override
//    public List<String> getTooltip(ItemStack stack){
//        return getItemPackUpgrade(stack).getTooltip(stack);
//    }

//    //necessary?
//    public ItemPackUpgrade getItemPackUpgrade(int meta) {
//        return ItemUpgradeRegistry.getItemUpgrade(meta);
//    }
//
//    public static ItemPackUpgrade getItemPackUpgrade(ItemStack stack) {
//        return ItemUpgradeRegistry.getItemUpgrade(stack.getItemDamage());
//    }
}
