package gr8pefish.ironbackpacks.integration.jei;

import mezz.jei.api.gui.ITooltipCallback;
import net.minecraft.item.ItemStack;

import java.util.List;

public class TooltipCallback implements ITooltipCallback<ItemStack> {

    @Override
    public void onTooltip(int slotIndex, boolean input, ItemStack ingredient, List<String> tooltip) {
        System.out.println(tooltip.toString());
    }
}
