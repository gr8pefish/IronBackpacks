package gr8pefish.ironbackpacks.capabilities;

import gr8pefish.ironbackpacks.api.BackpackVariant;
import gr8pefish.ironbackpacks.api.IBackpackProvider;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

/**
 * An implementation of the capability for a backpack's inventory.
 * Lots of credit to williewillus for this code's inspiration.
 */
public final class BackpackInvImpl {

    public static void init() {

        CapabilityManager.INSTANCE.register(IBackpackProvider.class, new Capability.IStorage<IBackpackProvider>() {

            @Override
            public NBTTagCompound writeNBT(Capability<IBackpackProvider> capability, IBackpackProvider instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IBackpackProvider> capability, IBackpackProvider instance, EnumFacing side, NBTBase nbt) {
                if (nbt instanceof NBTTagCompound)
                    instance.deserializeNBT(((NBTTagCompound) nbt));
            }
        }, DefaultImpl.class);
    }

    private static class DefaultImpl implements IBackpackProvider {

        private final Map<BackpackVariant, IItemHandler> inventories = new EnumMap<>(BackpackVariant.class);

        @Nonnull
        @Override
        public IItemHandler getInventory(@Nonnull BackpackVariant variant) {
            if (!inventories.containsKey(variant)) {
                inventories.put(variant, new ItemStackHandler(BackpackVariant.getSize(variant))); //ToDo: Custom wrapped one?
            }
            return inventories.get(variant);
        }

        //ToDO: Analyze, essentially just copied code that works
        private NBTTagCompound writeNBT(BackpackVariant variant) {
            NBTTagCompound ret = new NBTTagCompound();
            BackpackVariant[] variants = variant == null ? BackpackVariant.values() : new BackpackVariant[] { variant };
            for (BackpackVariant v : variants) {
                if (inventories.containsKey(v)) {
                    NBTBase inv = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventories.get(v), null);
                    ret.setTag(v.getName(), inv);
                }
            }
            return ret;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return writeNBT(null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            for (BackpackVariant e : BackpackVariant.values()) {
                if (nbt.hasKey(e.getName())) {
                    IItemHandler inv = new ItemStackHandler(BackpackVariant.getSize(e));
                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inv, null, nbt.getTag(e.getName()));
                    inventories.put(e, inv);
                }
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

        private final IBackpackProvider cap = new DefaultImpl();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing) {
            return capability == IronBackpacksAPI.BACKPACK_INV_CAPABILITY;
        }

        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing) {
            if (capability == IronBackpacksAPI.BACKPACK_INV_CAPABILITY) {
                return IronBackpacksAPI.BACKPACK_INV_CAPABILITY.cast(cap);
            }

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT() {
            return cap.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt) {
            cap.deserializeNBT(nbt);
        }
    }

    private BackpackInvImpl() {}

}
