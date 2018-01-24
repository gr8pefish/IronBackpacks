package gr8pefish.ironbackpacks.capabilities;

import gr8pefish.ironbackpacks.api.backpack.inventory.IBackpackInventoryProvider;
import net.minecraft.nbt.NBTBase;
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
    }
}
