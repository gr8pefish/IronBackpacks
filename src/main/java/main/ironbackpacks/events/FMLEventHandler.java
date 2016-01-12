package main.ironbackpacks.events;

import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.handlers.ConfigHandler;
import main.ironbackpacks.network.ClientPackMessage;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

/**
 * FML bus events
 * Needs to eventually be moved over to ForgeEventHandler, but getting an odd NPE right now
 */
public class FMLEventHandler {

}
