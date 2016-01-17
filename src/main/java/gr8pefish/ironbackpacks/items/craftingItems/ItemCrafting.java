package gr8pefish.ironbackpacks.items.craftingItems;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.item.craftingItems.ItemAPICrafting;
import gr8pefish.ironbackpacks.api.register.CraftingItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

/**
 * This class is for all the items that have no special properties, they are simply used in crafting recipes to make other items in the mod.
 */
public class ItemCrafting extends Item {

    public ItemCrafting(){
        setUnlocalizedName(Constants.MODID + "." + IronBackpacksConstants.Items.CRAFTING_ITEM_BASE_NAME + ".");
        setCreativeTab(IronBackpacks.creativeTab);
        setHasSubtypes(true);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void getSubItems(Item id, CreativeTabs creativeTab, List<ItemStack> list) {
        for (int i = 0; i < CraftingItemRegistry.getSize(); i++)
            list.add(new ItemStack(id, 1, i));
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        return super.getUnlocalizedName(stack) + CraftingItemRegistry.getItemCrafting(stack.getItemDamage()).getName();
    }

    //necessary?
    public ItemAPICrafting getItemAPICrafting(int meta) {
        return CraftingItemRegistry.getItemCrafting(meta);
    }
}
