package main.ironbackpacks.items.backpacks;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.items.ItemBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemBaseBackpack extends ItemBase {

    protected int id;

    public ItemBaseBackpack(String unlocName, String textureName,int id) {
        super(unlocName, textureName);
        setMaxStackSize(1);
        this.id = id;
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemStack, World world, EntityPlayer player) {
        if (world.isRemote){
            return itemStack;
        }else {
            player.openGui(IronBackpacks.instance, id, world, (int) player.posX, (int) player.posY, (int) player.posZ);
            return itemStack;
        }
    }
}
