package gr8pefish.ironbackpacks.api.register;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.craftingItems.ItemAPICrafting;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemCraftingRegistry {

    private static List<ItemAPICrafting> items = new ArrayList<>();

    public static void registerItemCrafting(ItemAPICrafting item) {
        if (!items.contains(item))
            items.add(item);
        //TODO: else {print error in logger}
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemCraftingTexture(ItemAPICrafting item, String modelName) {
        int meta = getIndexOf(item);
        ResourceLocation resourceLocation = new ResourceLocation(Constants.DOMAIN + modelName);

        ModelBakery.registerItemVariants(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_CRAFTING_BASE), resourceLocation);
        ModelLoader.setCustomModelResourceLocation(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_CRAFTING_BASE), meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    public static ItemAPICrafting getItemCrafting(int index) {
        return items.get(index);
    }

    public static int getIndexOf(ItemAPICrafting item) {
        return items.indexOf(item);
    }

    public static int getSize() {
        return items.size();
    }
}
