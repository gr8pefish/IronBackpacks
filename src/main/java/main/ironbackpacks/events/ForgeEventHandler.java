package main.ironbackpacks.events;

import cpw.mods.fml.common.eventhandler.Event;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.backpacks.ItemBaseBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;

public class ForgeEventHandler {

    @SubscribeEvent
    public void onItemPickupEvent(EntityItemPickupEvent event) { //TODO - broken, picks up item regardless
        if (event.isCanceled())
            return;
        ItemStack backpackStack = IronBackpacksHelper.getFilterBackpack(event.entityPlayer);
        if (backpackStack != null){
            Item[] filterItems = UpgradeMethods.getFilterItems(backpackStack);
            for (Item filterItem : filterItems){
                if (filterItem != null) {
                    if (event.item.getEntityItem().getItem().equals(filterItem)) {
                        IronBackpackType type = IronBackpackType.values()[((ItemBaseBackpack) backpackStack.getItem()).getTypeId()];
                        ContainerBackpack container = new ContainerBackpack(event.entityPlayer, new InventoryBackpack(event.entityPlayer, backpackStack, type), type);
                        container.transferStackInSlot(event.item.getEntityItem());
                        container.onContainerClosed(event.entityPlayer);
                    }
                }
            }
        }else{
            System.out.println("Normal pickup");
////          event.entityPlayer.inventory.addItemStackToInventory(event.item.getEntityItem());
//            event.setResult(Event.Result.DEFAULT); //not working as I thought it would (which was do vanilla handling)
            return;
        }
    }
}
