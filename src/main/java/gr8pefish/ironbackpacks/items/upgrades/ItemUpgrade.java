package gr8pefish.ironbackpacks.items.upgrades;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.items.upgrades.interfaces.IUpgrade;
import gr8pefish.ironbackpacks.api.register.ItemIUpgradeRegistry;
import gr8pefish.ironbackpacks.util.TextUtils;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.Arrays;
import java.util.List;

public class ItemUpgrade extends Item {

    public ItemUpgrade(){
        setUnlocalizedName(Constants.MODID + "." + IronBackpacksAPI.ITEM_UPGRADE_BASE_NAME + ".");
        setCreativeTab(IronBackpacks.creativeTab);
        setHasSubtypes(true);
        setMaxStackSize(16);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List<ItemStack> list) {
        for (int i = 0; i < ItemIUpgradeRegistry.getTotalSize(); i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + ItemIUpgradeRegistry.getItemUpgrade(stack).getName(stack);
    }

    @Override
    @SideOnly(Side.CLIENT)
    @SuppressWarnings("unchecked")
    public void addInformation(ItemStack itemStack, EntityPlayer player, List list, boolean advanced) {
        if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) || Keyboard.isKeyDown(Keyboard.KEY_RSHIFT)) {
            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.cost", ItemIUpgradeRegistry.getItemUpgrade(itemStack).getUpgradeCost(itemStack), ItemIUpgradeRegistry.getItemUpgrade(itemStack).getUpgradeCost(itemStack) == 1 ? "" : "s"));
            list.addAll(Arrays.asList(TextUtils.cutLongString(TextUtils.localizeEffect("tooltip.ironbackpacks.upgrade.minimumTier", getMinimumTierBackpackName(ItemIUpgradeRegistry.getItemUpgrade(itemStack).getTier(itemStack))))));
            list.add(""); //spacing for clarity
            list.addAll(ItemIUpgradeRegistry.getItemUpgrade(itemStack).getTooltip(itemStack));
        } else {
            list.add(TextUtils.localizeEffect("tooltip.ironbackpacks.shift"));
        }
    }

    //HARDCODED to my tiers
    private String getMinimumTierBackpackName(int tier){
        switch (tier){
            case 0:
                return TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.tier.basicBackpack.name");
            case 1:
                return TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.tier.ironBackpack.name");
            case 2:
                return TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.tier.goldBackpack.name");
            case 3:
                return TextUtils.localizeEffect("tooltip.ironbackpacks.backpack.tier.diamondBackpack.name");
        }
        return "ERROR, report to mod author";
    }

    public static int getUpgradeCost(ItemStack stack) {
        return ItemIUpgradeRegistry.getItemUpgrade(stack).getUpgradeCost(stack);
    }

    public static int getId(ItemStack stack) {
        return stack.getItemDamage(); //TODO: I can do better, dynamic lookup or something
    }

    public static boolean areUpgradesEqual(ItemStack toCheck, IUpgrade upgrade){
        return ItemIUpgradeRegistry.getItemUpgrade(toCheck).equals(upgrade);
    }

}
