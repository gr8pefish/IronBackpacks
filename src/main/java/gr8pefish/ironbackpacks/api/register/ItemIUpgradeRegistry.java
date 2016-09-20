package gr8pefish.ironbackpacks.api.register;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.items.upgrades.ItemIConfigurableUpgrade;
import gr8pefish.ironbackpacks.api.items.upgrades.ItemIConflictingUpgrade;
import gr8pefish.ironbackpacks.api.items.upgrades.ItemIUpgrade;
import gr8pefish.ironbackpacks.api.items.upgrades.interfaces.IUpgrade;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

public class ItemIUpgradeRegistry {

    private static List<ItemIUpgrade> upgradesIUpgrade = new ArrayList<>(); //the IUpgrade upgrades registered
    private static List<ItemIConflictingUpgrade> upgradesIConflictingUpgrade = new ArrayList<>(); //the IConflictingUpgrades registered
    private static List<ItemIConfigurableUpgrade> upgradesIConfigurableUpgrade = new ArrayList<>(); //the IConfigurableUpgrades registered

    //=======================Register the items==============================

    public static void registerItemPackUpgrade(ItemIUpgrade item) {
        if (!upgradesIUpgrade.contains(item))
            upgradesIUpgrade.add(item);
    }

    public static void registerItemConflictingUpgrade(ItemIConflictingUpgrade item) {
        if (!upgradesIConflictingUpgrade.contains(item))
            upgradesIConflictingUpgrade.add(item);
    }

    public static void registerItemConfigurableUpgrade(ItemIConfigurableUpgrade item) {
        if (!upgradesIConfigurableUpgrade.contains(item))
            upgradesIConfigurableUpgrade.add(item);
    }

    //=====================Register the textures for the items================

    @SideOnly(Side.CLIENT)
    public static void registerItemIUpgradeTexture(ItemIUpgrade item, String modelName) {
        int meta = getIndexOfIUpgrade(item);
        ResourceLocation resourceLocation = new ResourceLocation(Constants.DOMAIN + modelName);

        ModelBakery.registerItemVariants(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE_NAME), resourceLocation);
        ModelLoader.setCustomModelResourceLocation(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE_NAME), meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemIConflictingUpgradeTexture(ItemIConflictingUpgrade item, String modelName) {
        int meta = getIndexOfIConflictingUpgrade(item);
        ResourceLocation resourceLocation = new ResourceLocation(Constants.DOMAIN + modelName);

        ModelBakery.registerItemVariants(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE_NAME), resourceLocation);
        ModelLoader.setCustomModelResourceLocation(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE_NAME), meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    @SideOnly(Side.CLIENT)
    public static void registerItemIConfigurableUpgradeTexture(ItemIConfigurableUpgrade item, String modelName) {
        int meta = getIndexOfIConfigurableUpgrade(item);
        ResourceLocation resourceLocation = new ResourceLocation(Constants.DOMAIN + modelName);

        ModelBakery.registerItemVariants(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE_NAME), resourceLocation);
        ModelLoader.setCustomModelResourceLocation(IronBackpacksAPI.getItem(IronBackpacksAPI.ITEM_UPGRADE_BASE_NAME), meta, new ModelResourceLocation(resourceLocation, "inventory"));
    }

    //======================================================================Helper Methods==================================================================================


    //================isInstanceOfXUpgrade============================

    public static boolean isInstanceOfAnyUpgrade(ItemStack stack){
        return (stack.getItemDamage() < getInflatedSizeOfConfigurable());
    }

    public static boolean isInstanceOfIUpgrade(ItemStack stack){
        return (stack.getItemDamage() < upgradesIUpgrade.size());
    }

    public static boolean isInstanceOfIConflictingUpgrade(ItemStack stack){
        return (upgradesIUpgrade.size() <= stack.getItemDamage() && stack.getItemDamage() < getInflatedSizeOfConflicting());
    }

    public static boolean isInstanceOfIConfigurableUpgrade(ItemStack stack){
        return (getInflatedSizeOfConflicting() <= stack.getItemDamage() && stack.getItemDamage() < getInflatedSizeOfConfigurable());
    }

    //========================getXUpgrade===============================

    public static IUpgrade getItemUpgrade(ItemStack itemStack) {
        if (isInstanceOfIUpgrade(itemStack))
            return getItemIUpgrade(itemStack.getItemDamage());
        else if (isInstanceOfIConflictingUpgrade(itemStack))
            return getItemIConflictingUpgrade(itemStack.getItemDamage());
        else if (isInstanceOfIConfigurableUpgrade(itemStack))
            return getItemIConfingurableUpgrade(itemStack.getItemDamage());
        else {
            throw new RuntimeException("No items upgrade found here");
        }
    }

    //item stack specific methods

    public static ItemIUpgrade getItemIUpgrade(ItemStack stack) {
        return upgradesIUpgrade.get(stack.getItemDamage());
    }

    public static ItemIConflictingUpgrade getItemIConflictingUpgrade(ItemStack stack) {
        return upgradesIConflictingUpgrade.get(stack.getItemDamage() - upgradesIUpgrade.size());
    }

    public static ItemIConfigurableUpgrade getItemIConfingurableUpgrade(ItemStack stack) {
        return upgradesIConfigurableUpgrade.get(stack.getItemDamage() - getInflatedSizeOfConflicting());
    }

    //damage value specific methods

    public static ItemIUpgrade getItemIUpgrade(int damageValue) {
        return upgradesIUpgrade.get(damageValue);
    }

    public static ItemIConflictingUpgrade getItemIConflictingUpgrade(int damageValue) {
        return upgradesIConflictingUpgrade.get(damageValue - upgradesIUpgrade.size());
    }

    public static ItemIConfigurableUpgrade getItemIConfingurableUpgrade(int damageValue) {
        return upgradesIConfigurableUpgrade.get(damageValue - getInflatedSizeOfConflicting());
    }

    //=================================Helper Index Methods==================================

    public static int getIndexOfIUpgrade(ItemIUpgrade item) {
        return upgradesIUpgrade.indexOf(item);
    }

    public static int getIndexOfIConflictingUpgrade(ItemIConflictingUpgrade item) {
        return upgradesIUpgrade.size() + upgradesIConflictingUpgrade.indexOf(item); //TODO: careful with this
    }

    public static int getIndexOfIConfigurableUpgrade(ItemIConfigurableUpgrade item) {
        return upgradesIUpgrade.size() + upgradesIConflictingUpgrade.size() + upgradesIConfigurableUpgrade.indexOf(item);
    }

    private static int getInflatedSizeOfConflicting(){
        return upgradesIUpgrade.size() + upgradesIConflictingUpgrade.size();
    }

    private static int getInflatedSizeOfConfigurable(){
        return upgradesIUpgrade.size() + upgradesIConflictingUpgrade.size() + upgradesIConfigurableUpgrade.size();
    }

    public static int getUninflatedIndexOfConfigurableUpgrade(ItemIConfigurableUpgrade upgrade) {
        return upgradesIConfigurableUpgrade.indexOf(upgrade);
    }

    public static int getIPackSize(){
        return upgradesIUpgrade.size();
    }

    public static int getTotalSize() {
        return upgradesIUpgrade.size() + upgradesIConflictingUpgrade.size() + upgradesIConfigurableUpgrade.size();
    }

    //======================================Miscellaneous Helper Methods==========================================

    public static IRecipe getItemRecipe(int index){
        if (index < upgradesIUpgrade.size()){
            return upgradesIUpgrade.get(index).getItemRecipe(null);
        }else if (index < getInflatedSizeOfConflicting()){
            return getItemIConflictingUpgrade(index).getItemRecipe(null);
        }else {
            return getItemIConfingurableUpgrade(index).getItemRecipe(null);
        }
    }
}
