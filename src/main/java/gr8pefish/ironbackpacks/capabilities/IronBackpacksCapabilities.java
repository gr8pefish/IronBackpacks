package gr8pefish.ironbackpacks.capabilities;

import gr8pefish.ironbackpacks.api.backpack.inventory.IBackpackInventoryProvider;
import gr8pefish.ironbackpacks.capabilities.player.PlayerDeathBackpackCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;

import javax.annotation.Nullable;

public class IronBackpacksCapabilities {

    //TODO: Refactor to API eventually

    //Inject capabilities
    @CapabilityInject(PlayerWearingBackpackCapabilities.class)
    public static final Capability<PlayerWearingBackpackCapabilities> WEARING_BACKPACK_CAPABILITY = null;

    @CapabilityInject(PlayerDeathBackpackCapabilities.class)
    public static final Capability<PlayerDeathBackpackCapabilities> DEATH_BACKPACK_CAPABILITY = null;

    @CapabilityInject(BackpackHandler.class)
    public static final Capability<BackpackHandler> BACKPACK_HANDLER_CAPABILITY = null;

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

        CapabilityManager.INSTANCE.register(IBackpackInventoryProvider.class, new Capability.IStorage<IBackpackInventoryProvider>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IBackpackInventoryProvider> capability, IBackpackInventoryProvider instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<IBackpackInventoryProvider> capability, IBackpackInventoryProvider instance, EnumFacing side, NBTBase nbt) {

            }
        }, BackpackHandler.Default::new);
    }

    //Useful methods for other classes
    public static ItemStack getWornBackpack(EntityPlayer player) {
        PlayerWearingBackpackCapabilities cap = getWearingBackpackCapability(player);
        if (cap != null)
            return cap.getEquippedBackpack();
        return ItemStack.EMPTY;
    }

}
