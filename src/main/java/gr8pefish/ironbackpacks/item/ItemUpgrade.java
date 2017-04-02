package gr8pefish.ironbackpacks.item;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.IUpgrade;
import gr8pefish.ironbackpacks.api.IronBackpacksHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemUpgrade extends Item implements IUpgrade {

    public ItemUpgrade() {
        setUnlocalizedName(IronBackpacks.MODID + ".upgrade");
        setHasSubtypes(true);
        setCreativeTab(IronBackpacks.TAB_IB);
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        BackpackUpgrade backpackUpgrade = getUpgrade(stack);
        if (backpackUpgrade.isNull())
            return super.getUnlocalizedName(stack);

        return "upgrade.ironbackpacks." + backpackUpgrade.getIdentifier().getResourcePath();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(Item itemIn, CreativeTabs tab, NonNullList<ItemStack> subItems) {
        for (BackpackUpgrade upgrade : IronBackpacksHelper.getUpgrades())
            if (!upgrade.isNull())
                subItems.add(IronBackpacksHelper.getStack(upgrade));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, EntityPlayer playerIn, List<String> tooltip, boolean advanced) {
        BackpackUpgrade backpackUpgrade = getUpgrade(stack);
        if (backpackUpgrade.isNull())
            return;

        tooltip.add(I18n.format("tooltip.ironbackpacks.upgrade.cost", backpackUpgrade.getApplicationCost()));
        tooltip.add(I18n.format("upgrade.ironbackpacks." + backpackUpgrade.getIdentifier().getResourcePath() + ".desc")); // TODO - cut when too long

        super.addInformation(stack, playerIn, tooltip, advanced);
    }

    // IUpgrade

    @Nonnull
    @Override
    public BackpackUpgrade getUpgrade(ItemStack stack) {
        if (stack.hasTagCompound() && stack.getTagCompound().hasKey("upgrade"))
            return IronBackpacksHelper.getUpgrade(new ResourceLocation(stack.getTagCompound().getString("upgrade")));

        return IronBackpacksHelper.getUpgrade(IronBackpacksHelper.NULL);
    }
}
