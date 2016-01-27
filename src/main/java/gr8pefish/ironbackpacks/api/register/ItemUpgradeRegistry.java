package gr8pefish.ironbackpacks.api.register;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemAltGuiUpgrade;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemConflictingUpgrade;
import gr8pefish.ironbackpacks.api.item.upgrades.ItemPackUpgrade;
import gr8pefish.ironbackpacks.api.item.upgrades.interfaces.IPackUpgrade;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemUpgradeRegistry {

    private static List<ItemPackUpgrade> itemsPack = new ArrayList<>();
    private static List<ItemConflictingUpgrade> itemsConflicting = new ArrayList<>();
    private static List<ItemAltGuiUpgrade> itemsAltGui = new ArrayList<>();

    public static void registerItemPackUpgrade(ItemPackUpgrade item) {
        if (!itemsPack.contains(item))
            itemsPack.add(item);
    }

    public static void registerItemConflictingUpgrade(ItemConflictingUpgrade item) {
        if (!itemsConflicting.contains(item))
            itemsConflicting.add(item);
    }

    public static void registerItemAltGuiUpgrade(ItemAltGuiUpgrade item) {
        if (!itemsAltGui.contains(item))
            itemsAltGui.add(item);
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemPackUpgradeTexture(ItemPackUpgrade item, String modelName) {
        int meta = getIndexOfPackUpgrade(item);
        ResourceLocation resourceLocation = new ResourceLocation(Constants.DOMAIN + modelName);

        ModelBakery.registerItemVariants(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE), resourceLocation);
        ModelLoader.setCustomModelResourceLocation(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE), meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemConflictingUpgradeTexture(ItemConflictingUpgrade item, String modelName) {
        int meta = getIndexOfConflictingUpgrade(item);
        ResourceLocation resourceLocation = new ResourceLocation(Constants.DOMAIN + modelName);

        ModelBakery.registerItemVariants(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE), resourceLocation);
        ModelLoader.setCustomModelResourceLocation(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE), meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemAltGuiUpgradeTexture(ItemAltGuiUpgrade item, String modelName) {
        int meta = getIndexOfAltGuiUpgrade(item);
        ResourceLocation resourceLocation = new ResourceLocation(Constants.DOMAIN + modelName);

        ModelBakery.registerItemVariants(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE), resourceLocation);
        ModelLoader.setCustomModelResourceLocation(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE), meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    public static boolean isInstanceOfAnyUpgrade(ItemStack stack){
        return (stack.getItemDamage() < getInflatedSizeOfAltGui());
    }

    public static boolean isInstanceOfPackUpgrade(ItemStack stack){
        return (stack.getItemDamage() < itemsPack.size());
    }

    public static boolean isInstanceOfConflictingUpgrade(ItemStack stack){
        return (itemsPack.size() <= stack.getItemDamage() && stack.getItemDamage() < getInflatedSizeOfConflicting());
    }

    public static boolean isInstanceOfAltGuiUpgrade(ItemStack stack){
        return (getInflatedSizeOfConflicting() <= stack.getItemDamage() && stack.getItemDamage() < getInflatedSizeOfAltGui());
    }

    public static IPackUpgrade getItemUpgrade(ItemStack itemStack) {
        if (isInstanceOfPackUpgrade(itemStack))
            return getItemPackUpgrade(itemStack.getItemDamage());
        else if (isInstanceOfConflictingUpgrade(itemStack))
            return getItemConflictingUpgrade(itemStack.getItemDamage());
        else if (isInstanceOfAltGuiUpgrade(itemStack))
            return getItemAltGuiUpgrade(itemStack.getItemDamage());
        else {
            throw new RuntimeException("No item upgrade found here");
        }
    }

    @SuppressWarnings("SuspiciousMethodCalls")
    public static int getIndexOfUpgrade(IPackUpgrade upgrade) {
        if (itemsPack.contains(upgrade)){
            return getIndexOfPackUpgrade((ItemPackUpgrade)upgrade);
        } else if (itemsConflicting.contains(upgrade)) {
            return getIndexOfConflictingUpgrade((ItemConflictingUpgrade)upgrade);
        } else if (itemsAltGui.contains(upgrade)) {
            return getIndexOfAltGuiUpgrade((ItemAltGuiUpgrade)upgrade);
        } else {
            throw new RuntimeException("No index found here");
        }
    }

    public static int getIPackSize(){
        return itemsPack.size();
    }

    public static int getUninflatedIAltGuiSize() {
        return itemsAltGui.size();
    }

    public static ItemPackUpgrade getItemPackUpgrade(int damageValue) {
        return itemsPack.get(damageValue);
    }

    public static ItemConflictingUpgrade getItemConflictingUpgrade(int damageValue) {
        return itemsConflicting.get(damageValue - itemsPack.size());
    }

    public static ItemAltGuiUpgrade getItemAltGuiUpgrade(int damageValue) {
        return itemsAltGui.get(damageValue - getInflatedSizeOfConflicting());
    }

    public static ItemPackUpgrade getItemPackUpgrade(ItemStack stack) {
        return itemsPack.get(stack.getItemDamage());
    }

    public static ItemConflictingUpgrade getItemConflictingUpgrade(ItemStack stack) {
        return itemsConflicting.get(stack.getItemDamage() - itemsPack.size());
    }

    public static ItemAltGuiUpgrade getItemAltGuiUpgrade(ItemStack stack) {
        return itemsAltGui.get(stack.getItemDamage() - getInflatedSizeOfConflicting());
    }

    private static int getInflatedSizeOfConflicting(){
        return itemsPack.size() + itemsConflicting.size();
    }

    private static int getInflatedSizeOfAltGui(){
        return itemsPack.size() + itemsConflicting.size() + itemsAltGui.size();
    }

    public static int getIndexOfPackUpgrade(ItemPackUpgrade item) {
        return itemsPack.indexOf(item);
    }

    public static int getIndexOfConflictingUpgrade(ItemConflictingUpgrade item) {
        return itemsPack.size() + itemsConflicting.indexOf(item); //TODO: careful with this
    }

    public static int getIndexOfAltGuiUpgrade(ItemAltGuiUpgrade item) {
        return itemsPack.size() + itemsConflicting.size() + itemsAltGui.indexOf(item);
    }

    public static int getUninflatedIndexOfAltGuiUpgrade(ItemAltGuiUpgrade upgrade) {
        return itemsAltGui.indexOf(upgrade);
    }

    public static int getTotalSize() {
        return itemsPack.size() + itemsConflicting.size() + itemsAltGui.size();
    }

    public static IRecipe getItemRecipe(int index){
        if (index < itemsPack.size()){
            return itemsPack.get(index).getItemRecipe(null);
        }else if (index < getInflatedSizeOfConflicting()){
            return getItemConflictingUpgrade(index).getItemRecipe(null);
        }else {
            return getItemAltGuiUpgrade(index).getItemRecipe(null);
        }
    }
}
