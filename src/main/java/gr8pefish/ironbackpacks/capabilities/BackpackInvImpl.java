package gr8pefish.ironbackpacks.capabilities;

import gr8pefish.ironbackpacks.api.inventory.IBackpackInventoryProvider;
import gr8pefish.ironbackpacks.api.variant.BackpackVariant;
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
import java.util.*;

/**
 * An implementation of the capability for a backpack's inventory.
 * Lots of credit to williewillus for this code's inspiration.
 */
public final class BackpackInvImpl {

    public static void init() {

        CapabilityManager.INSTANCE.register(IBackpackInventoryProvider.class, new Capability.IStorage<IBackpackInventoryProvider>() {

            @Override
            public NBTTagCompound writeNBT(Capability<IBackpackInventoryProvider> capability, IBackpackInventoryProvider instance, EnumFacing side) {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IBackpackInventoryProvider> capability, IBackpackInventoryProvider instance, EnumFacing side, NBTBase nbt) {
                if (nbt instanceof NBTTagCompound)
                    instance.deserializeNBT(((NBTTagCompound) nbt));
            }
        }, DefaultImpl.class);
    }

    private static class DefaultImpl implements IBackpackInventoryProvider {

        //hashmap to store mapping of variants and IItemHandlers, initialized to size of the number of variants //TODO: Error here??
        private final Map<BackpackVariant, IItemHandler> inventories = new HashMap<>(IronBackpacksAPI.getVariantList().size());

        @Nonnull
        @Override
        public IItemHandler getInventory(@Nonnull BackpackVariant variant) {
            if (!inventories.containsKey(variant)) {
                inventories.put(variant, new ItemStackHandler(variant.getBackpackSize().getTotalSize())); //ToDo: Custom wrapped ItemStackHandler?
            }
            return inventories.get(variant);
        }

        //ToDO: Analyze, essentially just copied code that works
        private NBTTagCompound writeNBT(BackpackVariant variant) {
            NBTTagCompound ret = new NBTTagCompound();

            List<BackpackVariant> variants; // = variant == null ? IronBackpacksAPI.getVariantList() : Collections.singletonList(variant);
            if (variant == null) {
                variants = IronBackpacksAPI.getVariantList();
            } else {
                variants = Collections.singletonList(variant);
            }

            for (BackpackVariant v : variants) {
                if (inventories.containsKey(v)) {
                    NBTBase inv = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventories.get(v), null);
                    ret.setTag(v.getIdentifier().toString(), inv);
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
            for (BackpackVariant variant : IronBackpacksAPI.getVariantList()) {
                if (nbt.hasKey(variant.getIdentifier().toString())) {
                    IItemHandler inv = new ItemStackHandler(variant.getBackpackSize().getTotalSize());
                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage().readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inv, null, nbt.getTag(variant.getName()));
                    inventories.put(variant, inv);
                }
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound> {

        private final IBackpackInventoryProvider cap = new DefaultImpl();

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
