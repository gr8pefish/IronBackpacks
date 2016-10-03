package gr8pefish.ironbackpacks.commands;

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
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class IronBackpacksCommands extends CommandBase {

    private final String DELETE_EQUIPPED = "deleteEquippedBackpack";

    @Override
    public String getCommandName() {
        return "ib";
    }

    @Override
    public String getCommandUsage(ICommandSender sender) {
        return "/ib "+DELETE_EQUIPPED+" [player]"; //ToDo: Add backup and restore commands eventually
    }

    @Override
    public int getRequiredPermissionLevel() {
        return 2;
    }

    @Override
    public void execute(MinecraftServer server, ICommandSender sender, String[] params) throws CommandException {
        if (params.length > 0 && params.length <= 2) {
            if (params[0].equalsIgnoreCase(DELETE_EQUIPPED)) {
                if (params.length == 2) { //name supplied
                    try {
                        EntityPlayerMP playerMP = getPlayer(server, sender, params[1]);

                        //reset on the server
                        PlayerWearingBackpackCapabilities.reset(playerMP);

                        //send to client
                        NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(null), playerMP);
                        NetworkingHandler.network.sendTo(new ClientCurrentPackMessage(null), playerMP);

                        //notify with message
                        sender.addChatMessage(new TextComponentString(TextUtils.localizeEffect("chat.ironbackpacks.command.deletedEquippedPack")+" "+playerMP.getName()+"."));

                    } catch (Exception e) {
                        Logger.warn("Couldn't process Iron Backpacks command to delete the user's backpack!");
                    }
                } else { //do it for whoever did the command
                    try {
                        //reset on the server
                        PlayerWearingBackpackCapabilities.reset((EntityPlayer) sender.getCommandSenderEntity());

                        //send to client
                        NetworkingHandler.network.sendTo(new ClientEquippedPackMessage(null), (EntityPlayerMP) sender.getCommandSenderEntity());
                        NetworkingHandler.network.sendTo(new ClientCurrentPackMessage(null), (EntityPlayerMP) sender.getCommandSenderEntity());

                        //notify with message
                        sender.addChatMessage(new TextComponentString(TextUtils.localizeEffect("chat.ironbackpacks.command.deletedYourEquippedPack")));

                    } catch (Exception e) {
                        Logger.warn("Couldn't process Iron Backpacks command to delete this user's backpack!");
                    }
                }
            }
        } else {
            throw new CommandException(getCommandUsage(sender));
        }
    }

    @Override
    public List<String> getTabCompletionOptions(MinecraftServer server, ICommandSender sender, String[] args, @Nullable BlockPos pos) {
        List<String> tabCompletion = new ArrayList<String>();
        if (args.length <= 1) //no name, match string
            tabCompletion.addAll(getListOfStringsMatchingLastWord(args, DELETE_EQUIPPED));
        else //match name
            tabCompletion.addAll(getListOfStringsMatchingLastWord(args, server.getAllUsernames()));
        return tabCompletion;
    }
}
