package main.ironbackpacks.items.backpacks;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemEnderBackpack extends Item implements IBackpack {

    public ItemEnderBackpack() {
        super();

        setUnlocalizedName(ModInformation.ID + ":" + getName());
        setMaxStackSize(1);
        setCreativeTab(IronBackpacks.creativeTab);
    }

    @Override
    public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player) {
        if (!world.isRemote)
            player.displayGUIChest(player.getInventoryEnderChest());

        return stack;
    }

    // IBackpack

    @Override
    public double getFullness(ItemStack stack) {
        return 0;
    }

    @Override
    public int getId() {
        return IronBackpacksConstants.Backpacks.ENDER_ID;
    }

    @Override
    public int getSize() {
        return 3 * 9;
    }

    @Override
    public int getRowLength() {
        return 9;
    }

    @Override
    public int getUpgradeSlots() {
        return 0;
    }

    @Override
    public String getName() {
        return "enderBackpack";
    }

    @Override
    public int getGuiId() {
        return -1;
    }
}
