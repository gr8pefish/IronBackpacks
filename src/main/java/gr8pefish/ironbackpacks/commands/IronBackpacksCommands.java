package gr8pefish.ironbackpacks.commands;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import gr8pefish.ironbackpacks.capabilities.player.PlayerDeathBackpackCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.client.ClientCurrentPackMessage;
import gr8pefish.ironbackpacks.network.client.ClientEquippedPackMessage;
import gr8pefish.ironbackpacks.util.Logger;
import gr8pefish.ironbackpacks.util.TextUtils;
import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;

public class IronBackpacksCommands extends CommandBase {

    private final String FORCE_REMOVE_EQUIPPED = "forceRemoveEquippedBackpack";

    @Nonnull
    @Override
    public String getName() {
        return "ib";
    }

    @Nonnull
    @Override
    public String getUsage(ICommandSender sender) {
        return "/ib "+FORCE_REMOVE_EQUIPPED+" [player]"; //ToDo: Add backup and restore commands eventually
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
        if (params.length > 0 && params.length <= 2) {
            if (params[0].equalsIgnoreCase(FORCE_REMOVE_EQUIPPED)) {
                if (params.length == 2) { //name supplied
                    try {
                        EntityPlayerMP playerMP = getPlayer(server, sender, params[1]);

                        ItemStack stack = PlayerWearingBackpackCapabilities.getEquippedBackpack(playerMP);

                        //check for empty stack and fix it if needed
                        if (stack.isEmpty())  {
                            ItemStack newStack = stack.copy();
                            newStack.grow(1);
                            PlayerWearingBackpackCapabilities.setEquippedBackpack(playerMP, newStack);
                            stack = newStack;
                        }

                        //set backpack to offhand on the server
                        playerMP.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);

                        //remove backpack on server
                        PlayerWearingBackpackCapabilities.reset(playerMP);

                        //reset death properties just in case
                        PlayerDeathBackpackCapabilities.reset(playerMP);

                        //send to client
                        NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(ItemStack.EMPTY), playerMP);
                        NetworkingHandler.network.sendTo(new ClientCurrentPackMessage(ItemStack.EMPTY), playerMP);

                        //notify with message
                        sender.sendMessage(new TextComponentString(TextUtils.localizeEffect("chat.ironbackpacks.command.removedEquippedPack")+" "+playerMP.getName()+"."));

                    } catch (Exception e) {
                        Logger.warn("Couldn't process Iron Backpacks command to delete the user's backpack!");
                    }
                } else { //do it for whoever did the command
                    try {
                        EntityPlayer player = (EntityPlayer)sender.getCommandSenderEntity();

                        ItemStack stack = PlayerWearingBackpackCapabilities.getEquippedBackpack(player);

                        //check for empty stack and fix it if needed
                        if (stack.isEmpty())  {
                            ItemStack newStack = stack.copy();
                            newStack.grow(1);
                            PlayerWearingBackpackCapabilities.setEquippedBackpack(player, newStack);
                            stack = newStack;
                        }

                        //set backpack to offhand on the server
                        player.setItemStackToSlot(EntityEquipmentSlot.OFFHAND, stack);

                        //remove backpack on server
                        PlayerWearingBackpackCapabilities.reset(player);

                        //reset death properties just in case
                        PlayerDeathBackpackCapabilities.reset(player);

                        //send to client
                        NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(ItemStack.EMPTY), (EntityPlayerMP) sender.getCommandSenderEntity());
                        NetworkingHandler.network.sendTo(new ClientCurrentPackMessage(ItemStack.EMPTY), (EntityPlayerMP) sender.getCommandSenderEntity());

                        //notify with message
                        sender.sendMessage(new TextComponentTranslation("chat.ironbackpacks.command.removedYourEquippedPack"));

                    } catch (Exception e) {
                        Logger.warn("Couldn't process Iron Backpacks command to remove this user's backpack!");
                    }
                }
            }
        } else {
            throw new CommandException(getUsage(sender));
        }
    }

    @Nonnull
    @Override
    public List<String> getTabCompletions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        List<String> tabCompletion = new ArrayList<String>();
        if (args.length <= 1) //no name, match string
            tabCompletion.addAll(getListOfStringsMatchingLastWord(args, FORCE_REMOVE_EQUIPPED));
        else //match name
            tabCompletion.addAll(getListOfStringsMatchingLastWord(args, server.getOnlinePlayerNames()));
        return tabCompletion;
    }
}
