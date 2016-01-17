package gr8pefish.ironbackpacks.api.register;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.craftingItems.ItemAPICrafting;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemPackUpgrade;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemUpgradeRegistry {

    private static List<ItemPackUpgrade> items = new ArrayList<>(); //TODO; differentiate types

    public static void registerItemUpgrade(ItemPackUpgrade item) {
        if (!items.contains(item)) {
            System.out.println("adding item: " + item.getName());
            items.add(item);
        }else {
            System.out.println("can't add item " + item.getName());
            //TODO: else {print error in logger}
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemUpgradeTexture(ItemPackUpgrade item, String modelName) {
        int meta = getIndexOf(item);
        ResourceLocation resourceLocation = new ResourceLocation(Constants.DOMAIN + modelName);

        ModelBakery.registerItemVariants(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE), resourceLocation);
        ModelLoader.setCustomModelResourceLocation(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE), meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    public static ItemPackUpgrade getItemUpgrade(int index) {
        return items.get(index);
    }

    public static int getIndexOf(ItemPackUpgrade item) {
        return items.indexOf(item);
    }

    public static int getSize() {
        return items.size();
    }
}
