package main.ironbackpacks.items;

import main.ironbackpacks.IronBackpacks;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBaseBackpack extends ItemBase {

    public ItemBaseBackpack(String unlocName, String textureName) {
        super(unlocName, textureName);
        setMaxStackSize(1);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote){
            return itemStack;
        }else {
            player.openGui(IronBackpacks.instance, 1, world, (int) player.posX, (int) player.posY, (int) player.posZ); //TODO - gui id of 1 may change
            return itemStack;
        }
    }
}
