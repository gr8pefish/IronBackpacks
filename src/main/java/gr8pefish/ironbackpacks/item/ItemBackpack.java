package gr8pefish.ironbackpacks.item;

import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackType;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import gr8pefish.ironbackpacks.capabilities.ItemBackpackHandler;
import gr8pefish.ironbackpacks.core.RegistrarIronBackpacks;
import gr8pefish.ironbackpacks.network.GuiHandler;
import gr8pefish.ironbackpacks.util.ColorUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
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
        return super.getUnlocalizedName(stack) + "." + backpackInfo.getVariant().getBackpackType().getIdentifier().toString().replace(":", ".");
    }

    @Nonnull
    @Override
    public ICapabilityProvider initCapabilities(ItemStack stack, NBTTagCompound oldCapNbt) {
        return new ItemBackpackHandler(stack);
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

        world.playSound(player.posX, player.posY, player.posZ, RegistrarIronBackpacks.BACKPACK_OPEN, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);

        // GUI opening should be triggered either only on client side or only on server side.
        // When triggered server side the client will show the window when the server sends
        // the appropriate packets.
        // If triggered on both sides the window would be created twice on the client side,
        // causing a short flash of the window in its previous state and possibly desync
        // issues in clients with high latency.
        if (!world.isRemote)
            player.openGui(IronBackpacks.INSTANCE, GuiHandler.OPEN_GUI_BACKPACK_ID, world, hand == EnumHand.OFF_HAND ? 1 : 0, 0, 0);

        return ActionResult.newResult(EnumActionResult.SUCCESS, held);
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!isInCreativeTab(tab))
            return;

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
        for (int i = 0; i < backpackInfo.getInventory().getSlots(); i++) {
            ItemStack invStack = backpackInfo.getInventory().getStackInSlot(i);
            if (!invStack.isEmpty()) {
                full += invStack.getCount();
                total += invStack.getMaxStackSize();
            } else {
                total += 64;
            }
        }

        return 1.0D - ((double) full / (double) total);
    }

    @Override
    public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
        return slotChanged;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        BackpackInfo backpackInfo = getBackpackInfo(stack);
        if (backpackInfo.getVariant().getBackpackType().hasSpecialties())
            tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.emphasis." + backpackInfo.getVariant().getBackpackSpecialty().getName()));
        tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.tier", backpackInfo.getVariant().getBackpackType().getTier() + 1));
        tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.upgrade.used", backpackInfo.getPointsUsed(), backpackInfo.getMaxPoints()));
        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT) && !backpackInfo.getUpgrades().isEmpty()) {
            tooltip.add(TextFormatting.ITALIC + I18n.format("tooltip.ironbackpacks.shift"));
        } else if (!backpackInfo.getUpgrades().isEmpty()) {
            tooltip.add("");
            tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.upgrade.list", backpackInfo.getPointsUsed(), backpackInfo.getMaxPoints()));
            for (BackpackUpgrade upgrade : backpackInfo.getUpgrades())
                tooltip.add("  - " + I18n.format("upgrade.ironbackpacks." + upgrade.getIdentifier().getResourcePath() + ".name"));
        }
    }

    // Implementing getNBTShareTag() causes at least two bugs:
    // 1) Desync issue when opening the bag using the open key; sometimes gui doesn't block clicking
    //    on backpack but server doesn't acknowledge the click; items not duped but instead voided.
    //    ^ FIXED
    // 2) Desync issue with the damage bar upgrade; client doesn't know about items in the backpack
    //    until opened; could be solved by adding metadata to the backpack info that tracks the
    //    fullness of it.

    /*
    // This method is provided as a way to let items decide what's sent of its NBT in network packets.
    @Override
    public NBTTagCompound getNBTShareTag(ItemStack stack) {
        // By not sending the inventory part of the backpack we reduce the size and amount of packets
        // sent to the client. This way both the players holding bags and other players near them
        // won't receive packets updating the state of backpack when its inventory is changed, only
        // when they open it.
        NBTTagCompound shareTag = super.getNBTShareTag(stack).copy();
        shareTag.removeTag("packInv");
        return shareTag;
    }
    */
}
