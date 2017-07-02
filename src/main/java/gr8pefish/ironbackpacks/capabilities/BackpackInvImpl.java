package gr8pefish.ironbackpacks.capabilities;

import gr8pefish.ironbackpacks.api.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.IBackpackProvider;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.EnumMap;
import java.util.Map;

import static gr8pefish.ironbackpacks.IronBackpacks.MODID;

public final class BackpackInvImpl
{

    public static void init()
    {
        CapabilityManager.INSTANCE.register(IBackpackProvider.class, new Capability.IStorage<IBackpackProvider>()
        {
            @Override
            public NBTTagCompound writeNBT(Capability<IBackpackProvider> capability, IBackpackProvider instance, EnumFacing side)
            {
                return instance.serializeNBT();
            }

            @Override
            public void readNBT(Capability<IBackpackProvider> capability, IBackpackProvider instance, EnumFacing side, NBTBase nbt) {
                if (nbt instanceof NBTTagCompound)
                    instance.deserializeNBT(((NBTTagCompound) nbt));
            }
        }, DefaultImpl.class);
    }

    private static class DefaultImpl implements IBackpackProvider
    {
        private final Map<BackpackSpecialty, IItemHandler> inventories = new EnumMap<>(BackpackSpecialty.class);

        @Nonnull
        @Override
        public IItemHandler getInventory(@Nonnull BackpackSpecialty specialty)
        {
            if (!inventories.containsKey(specialty))
            {
                inventories.put(specialty, new ItemStackHandler(BackpackSpecialty.getSize(specialty)));
            }

            return inventories.get(specialty);
        }

//        @Override
//        public void sync(@Nonnull EnumDyeColor color, @Nonnull EntityPlayerMP player)
//        {
//            PacketHandler.sendTo(new SyncBagDataPKT(writeNBT(color)), player);
//        }

        private NBTTagCompound writeNBT(BackpackSpecialty color)
        {
            NBTTagCompound ret = new NBTTagCompound();
            BackpackSpecialty[] colors = color == null ? BackpackSpecialty.values() : new BackpackSpecialty[] { color };
            for (BackpackSpecialty c : colors)
            {
                if (inventories.containsKey(c))
                {
                    NBTBase inv = CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage()
                            .writeNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inventories.get(c), null);
                    ret.setTag(c.getName(), inv);
                }
            }
            return ret;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return writeNBT(null);
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            for (BackpackSpecialty e : BackpackSpecialty.values())
            {
                if (nbt.hasKey(e.getName()))
                {
                    IItemHandler inv = new ItemStackHandler(BackpackSpecialty.getSize(e));
                    CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.getStorage()
                            .readNBT(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, inv, null, nbt.getTag(e.getName()));
                    inventories.put(e, inv);
                }
            }
        }
    }

    public static class Provider implements ICapabilitySerializable<NBTTagCompound>
    {

        public static final ResourceLocation NAME = new ResourceLocation(MODID, "testing_cap_inv");

        private final IBackpackProvider cap = new DefaultImpl();

        @Override
        public boolean hasCapability(@Nonnull Capability<?> capability, EnumFacing facing)
        {
            return capability == IronBackpacksAPI.BACKPACK_INV_CAPABILITY;
        }

        @Override
        public <T> T getCapability(@Nonnull Capability<T> capability, EnumFacing facing)
        {
            if (capability == IronBackpacksAPI.BACKPACK_INV_CAPABILITY)
            {
                return IronBackpacksAPI.BACKPACK_INV_CAPABILITY.cast(cap);
            }

            return null;
        }

        @Override
        public NBTTagCompound serializeNBT()
        {
            return cap.serializeNBT();
        }

        @Override
        public void deserializeNBT(NBTTagCompound nbt)
        {
            cap.deserializeNBT(nbt);
        }
    }

    private BackpackInvImpl() {}

}
