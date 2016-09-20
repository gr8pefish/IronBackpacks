package gr8pefish.ironbackpacks.api.register;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.items.craftingItems.ItemICrafting;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemICraftingRegistry {

    private static List<ItemICrafting> items = new ArrayList<>();

    public static void registerItemCrafting(ItemICrafting item) {
        if (!items.contains(item))
            items.add(item);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemCraftingTexture(ItemICrafting item, String modelName) {
        int meta = getIndexOf(item);
        ResourceLocation resourceLocation = new ResourceLocation(Constants.DOMAIN + modelName);

        ModelBakery.registerItemVariants(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_CRAFTING_BASE_NAME), resourceLocation);
        ModelLoader.setCustomModelResourceLocation(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_CRAFTING_BASE_NAME), meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    public static ItemICrafting getItemCrafting(int index) {
        return items.get(index);
    }

    public static int getIndexOf(ItemICrafting item) {
        return items.indexOf(item);
    }

    public static int getSize() {
        return items.size();
    }
}
