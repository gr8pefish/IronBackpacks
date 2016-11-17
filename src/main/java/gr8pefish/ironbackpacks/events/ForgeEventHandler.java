package gr8pefish.ironbackpacks.events;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerDeathBackpackCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackMessage;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.server.SPacketCustomSound;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.tuple.ImmutablePair;

import java.util.ArrayList;

/**
 * All the events used that fire on the Forge Event bus
 */
public class ForgeEventHandler {


    //====================================================================== Player Capability Events =======================================================================

    /**
     * Attaches a capability to the entity. Works on both the client and server players.
     * @param event - attach capability event
     */
    @SubscribeEvent
    public void onAttachCapability(AttachCapabilitiesEvent.Entity event) {
        if (event.getEntity() instanceof EntityPlayer) {
            if (!event.getEntity().hasCapability(IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null)) {
                event.addCapability(new ResourceLocation(Constants.MODID + "." + Constants.WEARING_BACKPACK_CAPABILITY_STRING), new PlayerWearingBackpackCapabilities());
            }
            if (!event.getEntity().hasCapability(IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY, null)) {
                event.addCapability(new ResourceLocation(Constants.MODID + "." + Constants.DEATH_BACKPACK_CAPABILITY_STRING), new PlayerDeathBackpackCapabilities());
            }
        }
    }


    /**
     * When a player dies and respawns we need to clone their old data over
     * @param event - the clone event
     */
    @SubscribeEvent
    public void onPlayerCloning(net.minecraftforge.event.entity.player.PlayerEvent.Clone event) {
        if (event.isWasDeath()) { //deal with dumb returning from the end code, have to only clone data when the player actually dies
            if (event.getOriginal().hasCapability(IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY, null)) {
                PlayerDeathBackpackCapabilities oldCap = event.getOriginal().getCapability(IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY, null);
//                NBTTagCompound tagCompound = oldCap.serializeNBT();
                PlayerDeathBackpackCapabilities newCap = event.getEntityPlayer().getCapability(IronBackpacksCapabilities.DEATH_BACKPACK_CAPABILITY, null);
//                newCap.deserializeNBT(tagCompound); //ToDo: Test inclusion, as better than manually copying over?

                //update new data with old
                newCap.setEternityBackpacks(oldCap.getEternityBackpacks());
                newCap.setEquippedBackpack(oldCap.getEquippedBackpack());
            }
        } else { //return from end
            if (event.getOriginal().hasCapability(IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null)) {
                PlayerWearingBackpackCapabilities oldCap = event.getOriginal().getCapability(IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null);
//                NBTTagCompound tagCompound = oldCap.serializeNBT();
                PlayerWearingBackpackCapabilities newCap = event.getEntityPlayer().getCapability(IronBackpacksCapabilities.WEARING_BACKPACK_CAPABILITY, null);
//                newCap.deserializeNBT(tagCompound);

                //update new data with old
                newCap.setCurrentBackpack(oldCap.getCurrentBackpack());
                newCap.setEquippedBackpack(oldCap.getEquippedBackpack());
            }
        }
    }

    /**
     * When a player dies, check if player has any backpacks with keepOnDeathUpgrade so then they are saved for when they spawn
     * @param event - the event
     */
    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void onDeath(LivingDeathEvent event){
        if (!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayer){ //server side
            IronBackpacksHelper.saveBackpackOnDeath((EntityPlayer) event.getEntity());
        }
    }

    /**
     * When a player respawns, check if player had any backpacks with keepOnDeathUpgrade so then they spawn with them
     * @param event - the event
     */
    @SubscribeEvent
    public void onEntityJoinWorld(EntityJoinWorldEvent event){
        if (!event.getEntity().worldObj.isRemote && event.getEntity() instanceof EntityPlayer){ //server side
            IronBackpacksHelper.loadBackpackOnDeath((EntityPlayer) event.getEntity());
        }
    }


    /**
     * Used to make sure the player's equipped backpack is shown correctly
     * @param event - the player logged in event
     */
    @SubscribeEvent
    public void onPlayerLogIn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event){

        ItemStack backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(event.player);

        if (backpack != null) { //ToDo: even update if null?

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
//            PlayerBackpackProperties.setEquippedBackpack(event.player, backpack); //update server on correct pack //TODO: unnecessary?

//            if (!ConfigHandler.disableRendering) {
//                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
//            }
        }
    }


    /**
     * Used to make sure the player respawns with an equipped backpack if they should
     * @param event - the player respawn event
     */
    @SubscribeEvent
    public void onPlayerRespawn(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerRespawnEvent event){
        ItemStack backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(event.player);
        if (backpack != null) {

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
//            PlayerBackpackProperties.setEquippedBackpack(event.player, backpack); //update server on correct pack

//            if (!ConfigHandler.disableRendering)
//                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player);
        }
    }

    /**
     * Used to make sure the equipped backpack transfers over correctly between dimensions
     * @param event - the change dimension event
     */
    @SubscribeEvent
    public void onPlayerDimChange(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerChangedDimensionEvent event){
        ItemStack backpack = PlayerWearingBackpackCapabilities.getEquippedBackpack(event.player);
        if (backpack != null) {

            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(backpack), (EntityPlayerMP) event.player); //update client on correct pack
//            PlayerBackpackProperties.setEquippedBackpack(event.player, backpack); //TODO: test with these removed

//            if (!ConfigHandler.disableRendering)
//                IronBackpacksHelper.spawnEntityBackpack(backpack, event.player); //spawn new pack
        } else {
            NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(null), (EntityPlayerMP) event.player); //update client on correct pack
//            PlayerBackpackProperties.setEquippedBackpack(event.player, null);
        }
    }

    //====================================================================== Upgrade pseudo-tick Events =======================================================================

    /**
     * Called whenever an items is picked up by a player. The basis for all the filters, and the event used for the hopper/restocking and crafter/recipes upgrades too so it doesn't check too much and causes lag..
     * @param event - the event fired
     */
    //ToDo: Make this functional for offhand restocking
    @SubscribeEvent
    public void onItemPickupEvent(EntityItemPickupEvent event) {
        if (!event.isCanceled()) {
            ArrayList<ArrayList<ItemStack>> backpacks = IronBackpacksEventHelper.getFilterCrafterAndRestockerBackpacks(event.getEntityPlayer());
            boolean doFilter = IronBackpacksEventHelper.checkRestockingUpgradeItemPickup(event, backpacks.get(4)); //doFilter is false if the itemEntity is in the restockerUpgrade's slots and the itemEntity's stackSize < refillSize
            if (doFilter) {
                IronBackpacksEventHelper.checkFilterUpgrade(event, backpacks.get(0)); //beware creative testing takes the itemstack still
            }
            for (int i = 1; i < 4; i++) {
                IronBackpacksEventHelper.checkCrafterUpgrade(event, backpacks.get(i), i);//1x1, 2x2, and 3x3 crafters/crafters
            }
        }
    }

    /**
     * Called whenever the player uses an items. Used for the restocking(hopper) upgrade.
     * @param event - the event fired
     */
    //ToDo: Make this functional for offhand restocking
    @SubscribeEvent
    public void onPlayerItemUseEvent(PlayerInteractEvent.RightClickItem event){
        ItemStack resuppliedStack;
        if (!event.isCanceled()){ //only do it for main hand clicks
            ArrayList<ArrayList<ItemStack>> backpacks = IronBackpacksEventHelper.getFilterCrafterAndRestockerBackpacks(event.getEntityPlayer());
            resuppliedStack = IronBackpacksEventHelper.checkRestockerUpgradeItemUse(event, backpacks.get(4)); //reduce the stack in the backpack if you can refill and send back the refilled itemStack
            if (resuppliedStack != null) {
                event.getItemStack().stackSize = resuppliedStack.stackSize; //set the new stack size (as you can't/don't need to directly replace the stack)
            }
        }
    }

    /**
     * Called whenever the player places a block. Used for the restocking(hopper) upgrade.
     * @param event - the event fired
     */
    @SubscribeEvent
    public void onBlockPlacedEvent(BlockEvent.PlaceEvent event) {
        ItemStack resuppliedStack;
        if (!event.isCanceled()){ //only do it for main hand clicks
            ArrayList<ArrayList<ItemStack>> backpacks = IronBackpacksEventHelper.getFilterCrafterAndRestockerBackpacks(event.getPlayer());
            resuppliedStack = IronBackpacksEventHelper.checkRestockerUpgradeItemPlace(event.getPlayer(), event.getHand(), event.getItemInHand(), backpacks.get(4)); //reduce the stack in the backpack if you can refill and send back the refilled itemStack
            if (resuppliedStack != null) {
                if (event.getHand() == EnumHand.MAIN_HAND) { //if main hand item should be incremented
                    event.getPlayer().setItemStackToSlot(EntityEquipmentSlot.MAINHAND, resuppliedStack);
                } else { //offhand replacement
                    event.getPlayer().setItemStackToSlot(EntityEquipmentSlot.OFFHAND, resuppliedStack);
                }
            }
        }
    }

    @SubscribeEvent
    public void onArrowLoose(ArrowLooseEvent event) {
        if (!event.isCanceled()){ //only do it for main hand clicks
            ArrayList<ArrayList<ItemStack>> backpacks = IronBackpacksEventHelper.getFilterCrafterAndRestockerBackpacks(event.getEntityPlayer());
            ImmutablePair<ItemStack, Slot> pair = IronBackpacksEventHelper.checkRestockerUpgradeArrowLoose(event.getEntityPlayer(), backpacks.get(4)); //reduce the stack in the backpack if you can refill and send back the refilled itemStack
            if (pair != null) {
                event.getEntityPlayer().inventory.setInventorySlotContents(pair.getRight().getSlotIndex(), pair.getLeft());
//                event.getItemStack().stackSize = resuppliedStack.stackSize; //set the new stack size (as you can't/don't need to directly replace the stack)
            }
        }
    }

    //====================================================================== Misc. Events =======================================================================

    /**
     * Fires whenever an item is placed in both slots of an anvil. Used to disable processing if one of the items is a backpack.
     * @param event - anvil update event
     */
    @SubscribeEvent
    public void onAnvilUpdate(AnvilUpdateEvent event) {
        if ((event.getLeft().getItem() instanceof ItemBackpack) || (event.getRight().getItem() instanceof ItemBackpack)) { //if a backpack in an anvil slot
            //probably overkill, but making sure it can't process
            event.setOutput(null);
            event.setResult(Event.Result.DENY);
            event.setCanceled(true);
        }
    }

    /**
     *  When the config is changed this will reload the changes to ensure it is correctly updated
     * @param event - the event
     */
    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(Constants.MODID)) {
            ConfigHandler.syncConfig(false);
        }
    }

}
