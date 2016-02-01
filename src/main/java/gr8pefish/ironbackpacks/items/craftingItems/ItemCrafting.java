package gr8pefish.ironbackpacks.items.craftingItems;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.register.ItemICraftingRegistry;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * This class is for all the items that have no special properties, they are simply used in recipes recipes to make other items in the mod.
 */
public class ItemCrafting extends Item {

    public ItemCrafting(){
        setUnlocalizedName(Constants.MODID + "." + IronBackpacksAPI.ITEM_CRAFTING_BASE_NAME + ".");
        setCreativeTab(IronBackpacks.creativeTab);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List<ItemStack> list) {
        for (int i = 0; i < ItemICraftingRegistry.getSize(); i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        if (stack.getItemDamage() > (ItemICraftingRegistry.getSize()-1)) return super.getUnlocalizedName(stack); //for others that are updating before I removed the recipes items bloat
        return super.getUnlocalizedName(stack) + ItemICraftingRegistry.getItemCrafting(stack.getItemDamage()).getName();
    }

}
