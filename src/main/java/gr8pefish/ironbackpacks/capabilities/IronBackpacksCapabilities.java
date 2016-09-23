package gr8pefish.ironbackpacks.capabilities;

import gr8pefish.ironbackpacks.capabilities.player.PlayerDeathBackpackCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;

public class IronBackpacksCapabilities {

    //TODO: Refactor to API eventually?

    //Inject capabilities
    @CapabilityInject(PlayerWearingBackpackCapabilities.class)
    public static final Capability<PlayerWearingBackpackCapabilities> WEARING_BACKPACK_CAPABILITY = null;

    @CapabilityInject(PlayerDeathBackpackCapabilities.class)
    public static final Capability<PlayerDeathBackpackCapabilities> DEATH_BACKPACK_CAPABILITY = null;

    //Get capabilities
    public static PlayerWearingBackpackCapabilities getWearingBackpackCapability(EntityPlayer player) {
        return player.getCapability(WEARING_BACKPACK_CAPABILITY, null);
    }

    public static PlayerDeathBackpackCapabilities getDeathBackpackCapability(EntityPlayer player) {
        return player.getCapability(DEATH_BACKPACK_CAPABILITY, null);
    }

    //Registration
    public static void registerAllCapabilities(){
        PlayerWearingBackpackCapabilities.register();
        PlayerDeathBackpackCapabilities.register();
    }

    //Links:
    //https://github.com/McJty/RFTools/tree/1.10/src/main/java/mcjty/rftools/playerprops
    //https://github.com/DiabolicaTrix/MinecraftLifeRPG/tree/master/src/main/java/me/diabolicatrix/other
    //https://mcforge.readthedocs.io/en/latest/datastorage/capabilities/


//    <gr8pefish> Hi all. I want to store some data on the client, an itemstack specifically, so that it can be referenced quickly to render something on the client player. What is the best way to go about this (in 1.10)?
//    <gigaherz> gr8pefish: rendere where?
//    <gr8pefish> I need to render an entity if the player has item x. Currently that data is stored in the server player via capabilities, but I need to get it on the client side. I could send a bunch of messages, but I'd rather not do that every render tick
//    <Subaraki> gr8pefish, then send it once
//    <Subaraki> and once again when it is changed
//    <Subaraki> :)
//    <Ordinastie> gr8pefish, just send when it's changed
//    <Subaraki> that's the only way
//    <Subaraki> that's the way vanilla does it for armor as well
//    <gr8pefish> Agreed, I need to send it once, but how do I store that returned itemstack on the client player?
//    <gr8pefish> You can tell me to just look at armor code if needed ^
//    <Subaraki> same as you do server side
//    <gigaherz> you store it in the capability
//    <gigaherz> just the client copy of that capability
//    <gr8pefish> oh okay
//    <gr8pefish> that makes sense, I can do that
//    <gigaherz> since the client entity is a whole new copy of the entity
//    <gigaherz> you can just synchronize the capability objects
//    <gr8pefish> perfect
//    <gigaherz> and then query it from rendering
//    <Subaraki> don't check for client or so though. just send a message to the client, and acces player capabilty from there
//    <gigaherz> and in fact ,that's for the best
//    <Subaraki> yes :) ^
//    <gigaherz> because that way the rendering doesn't have to know about netowrking
//    <Subaraki> i'm still puzzled on how to make an area dark again ...
//    <gr8pefish> exactly, that's what I wanted to avoid
//    <gr8pefish> thanks fellas
}
