package gr8pefish.ironbackpacks.item;

import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.*;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
        return super.getUnlocalizedName(stack) + "." + backpackInfo.getBackpackType().getIdentifier().toString().replace(":", ".");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item item, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        List<BackpackType> sortedTypes = Lists.newArrayList(IronBackpacksHelper.getBackpackTypes());
        sortedTypes.sort(Comparator.comparingInt(BackpackType::getTier));

        for (BackpackType backpackType : sortedTypes) {
            if (backpackType.getIdentifier().equals(IronBackpacksHelper.NULL))
                continue;

            if (!backpackType.hasSpecialties()) {
                ItemStack stack = new ItemStack(item);
                BackpackInfo backpackInfo = new BackpackInfo(backpackType, BackpackSpecialty.NONE);
                stack.setTagCompound(new NBTTagCompound());
                stack.getTagCompound().setTag("backpackInfo", backpackInfo.serializeNBT());
                subItems.add(stack);
            } else {
                for (BackpackSpecialty specialty : BackpackSpecialty.values()) {
                    if (specialty == BackpackSpecialty.NONE)
                        continue;

                    ItemStack stack = new ItemStack(item);
                    BackpackInfo backpackInfo = new BackpackInfo(backpackType, specialty);
                    stack.setTagCompound(new NBTTagCompound());
                    stack.getTagCompound().setTag("backpackInfo", backpackInfo.serializeNBT());
                    subItems.add(stack);
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer player, List<String> tooltip, boolean advanced) {
        BackpackInfo backpackInfo = getBackpackInfo(stack);
        if (backpackInfo.getBackpackType().hasSpecialties())
            tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.emphasis." + backpackInfo.getSpecialty().getName()));
        tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.tier", backpackInfo.getBackpackType().getTier()));
        tooltip.add(I18n.format("tooltip.ironbackpacks.backpack.upgrade.used", backpackInfo.getPointsUsed(), backpackInfo.getMaxPoints()));
    }

    // IBackpack

    @Nonnull
    @Override
    public BackpackInfo getBackpackInfo(ItemStack stack) {
        return BackpackInfo.fromStack(stack);
    }
}
