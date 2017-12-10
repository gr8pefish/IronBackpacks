package gr8pefish.ironbackpacks.capabilities;

import gr8pefish.ironbackpacks.api.backpack.inventory.IBackpackInventoryProvider;
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
    @CapabilityInject(ItemBackpackHandler.class)
    public static final Capability<ItemBackpackHandler> ITEM_BACKPACK_HANDLER_CAPABILITY = null;
    @CapabilityInject(PlayerBackpackHandler.class)
    public static final Capability<PlayerBackpackHandler> PLAYER_BACKPACK_HANDLER_CAPABILITY = null;

    //Registration
    public static void registerAllCapabilities() {
        CapabilityManager.INSTANCE.register(IBackpackInventoryProvider.class, new Capability.IStorage<IBackpackInventoryProvider>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<IBackpackInventoryProvider> capability, IBackpackInventoryProvider instance, EnumFacing side) {
                return null;
            }

            @Override
            public void readNBT(Capability<IBackpackInventoryProvider> capability, IBackpackInventoryProvider instance, EnumFacing side, NBTBase nbt) {

            }
        }, ItemBackpackHandler.Default::new);

        CapabilityManager.INSTANCE.register(PlayerBackpackHandler.class, new Capability.IStorage<PlayerBackpackHandler>() {
            @Nullable
            @Override
            public NBTBase writeNBT(Capability<PlayerBackpackHandler> capability, PlayerBackpackHandler instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<PlayerBackpackHandler> capability, PlayerBackpackHandler instance, EnumFacing side, NBTBase nbt) {
                instance.deserializeNBT((NBTTagCompound) nbt);
            }
        }, PlayerBackpackHandler.Default::new);
    }
}
