package gr8pefish.ironbackpacks.item;

import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.*;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.variant.BackpackType;
import gr8pefish.ironbackpacks.capabilities.BackpackInvImpl;
import gr8pefish.ironbackpacks.core.RegistrarIronBackpacks;
import gr8pefish.ironbackpacks.network.GuiHandler;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.*;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import javax.annotation.Nonnull;
import java.util.Comparator;
import java.util.List;

public class ItemBackpack extends Item implements IBackpack {

    public ItemBackpack() {
        setUnlocalizedName(IronBackpacks.MODID + ".backpack");
        setHasSubtypes(true);
        setCreativeTab(IronBackpacks.TAB_IB);
        setMaxStackSize(1);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        BackpackInfo backpackInfo = getBackpackInfo(stack);
        return super.getUnlocalizedName(stack) + "." + backpackInfo.getBackpackVariant().getIdentifier().toString().replace(":", ".");
    }

    /**
     * Initialize the capability for the custom IItemHandler which acts as the inventory for the backpack.
     * @param stack - the stack to set it for
     * @param oldCapNbt - the old capability in NBT
     * @return aAnew implementation of the capability's provider
     */
    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound oldCapNbt) {
        return new BackpackInvImpl.Provider();
    }


    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack held = player.getHeldItem(hand);

        BackpackInfo info = getBackpackInfo(held);
        if (info.getOwner() == null) {
            info.setOwner(player.getGameProfile().getId());
            updateBackpack(held, info);
        }

        if (info.hasUpgrade(RegistrarIronBackpacks.UPGRADE_LOCK) && !player.getGameProfile().getId().equals(info.getOwner()))
            return ActionResult.newResult(EnumActionResult.FAIL, held);

        if (held.hasTagCompound()) {
            String tagString = held.getTagCompound().toString();
            IronBackpacks.LOGGER.info("Item tag: (" + tagString.getBytes().length + " bytes) " + held.getTagCompound());
        }
        world.playSound(player.posX, player.posY, player.posZ, RegistrarIronBackpacks.BACKPACK_OPEN, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);

        if (!world.isRemote)
            player.openGui(IronBackpacks.INSTANCE, GuiHandler.OPEN_GUI_BACKPACK_ID, world, hand == EnumHand.OFF_HAND ? 1 : 0, 0, 0);

        return super.onItemRightClick(world, player, hand);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        List<BackpackType> sortedTypes = Lists.newArrayList(IronBackpacksAPI.getBackpackTypes());
        sortedTypes.sort(Comparator.comparingInt(BackpackType::getTier));

        for (BackpackType backpackType : sortedTypes) {
            if (backpackType.getIdentifier().equals(IronBackpacksAPI.NULL))
                continue;

            if (!backpackType.hasSpecialties()) {
                subItems.add(IronBackpacksAPI.getStack(backpackType, BackpackSpecialty.NONE));
            } else {
                for (BackpackSpecialty specialty : BackpackSpecialty.values()) {
                    if (specialty == BackpackSpecialty.NONE)
                        continue;

                    subItems.add(IronBackpacksAPI.getStack(backpackType, specialty));
                }
            }
        }
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return getBackpackInfo(stack).hasUpgrade(RegistrarIronBackpacks.UPGRADE_DAMAGE_BAR);
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        int total = 0;
        int full = 0;

        BackpackInfo backpackInfo = getBackpackInfo(stack);
        for (int i = 0; i < backpackInfo.getStackHandler().getSlots(); i++) {
            ItemStack invStack = backpackInfo.getStackHandler().getStackInSlot(i);
            if (!invStack.isEmpty()) {
                full += invStack.getCount();
                total += invStack.getMaxStackSize();
            } else {
                total += 64;
            }
        }

        return 1.0D - ((double) full / (double) total);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        BackpackInfo backpackInfo = getBackpackInfo(stack);
        if (backpackInfo.getBackpackVariant().hasSpecialties())
            tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.emphasis." + backpackInfo.getSpecialty().getName()));
        tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.tier", backpackInfo.getBackpackVariant().getTier() + 1));
        tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.upgrade.used", backpackInfo.getPointsUsed(), backpackInfo.getMaxPoints()));
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !backpackInfo.getApplied().isEmpty()) {
            tooltip.add(TextFormatting.ITALIC + I18n.format("tooltip.ironbackpacks.shift"));
        } else if (!backpackInfo.getApplied().isEmpty()){
            tooltip.add("");
            tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.upgrade.list", backpackInfo.getPointsUsed(), backpackInfo.getMaxPoints()));
            for (BackpackUpgrade upgrade : backpackInfo.getApplied())
                tooltip.add("  - " + I18n.format("upgrade.ironbackpacks." + upgrade.getIdentifier().getResourcePath() + ".name"));
        }
    }
}
