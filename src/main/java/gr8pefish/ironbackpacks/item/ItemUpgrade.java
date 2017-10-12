package gr8pefish.ironbackpacks.item;

import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.upgrade.IUpgrade;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.util.Comparator;
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

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
        if (!isInCreativeTab(tab))
            return;

        subItems.add(new ItemStack(this));

        List<BackpackUpgrade> sortedUpgrades = Lists.newArrayList(IronBackpacksAPI.getUpgrades());
        sortedUpgrades.sort(Comparator.comparingInt(BackpackUpgrade::getMinimumTier));

        for (BackpackUpgrade upgrade : sortedUpgrades)
            if (!upgrade.isNull())
                subItems.add(IronBackpacksAPI.getStack(upgrade));
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(ItemStack stack, World world, List<String> tooltip, ITooltipFlag advanced) {
        BackpackUpgrade backpackUpgrade = getUpgrade(stack);
        if (backpackUpgrade.isNull())
            return;

        if (!Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)) {
            tooltip.add(TextFormatting.ITALIC + I18n.format("tooltip.ironbackpacks.shift"));
            return;
        }

        tooltip.add(I18n.format("tooltip.ironbackpacks.upgrade.cost", backpackUpgrade.getApplicationCost()));
        tooltip.add(I18n.format("tooltip.ironbackpacks.upgrade.minimum_tier", backpackUpgrade.getMinimumTier() + 1));
        tooltip.add("");
        FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
        List<String> cutDesc = fontRenderer.listFormattedStringToWidth(I18n.format("upgrade.ironbackpacks." + backpackUpgrade.getIdentifier().getResourcePath() + ".desc"), 200);
        tooltip.addAll(cutDesc);

        super.addInformation(stack, world, tooltip, advanced);
    }
}
