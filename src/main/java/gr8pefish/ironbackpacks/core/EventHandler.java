package gr8pefish.ironbackpacks.core;

import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.api.upgrade.IUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void onAnvil(AnvilUpdateEvent event) {
        ItemStack upgraded = tryUpgradeBackpack(event.getLeft(), event.getRight());
        if (!upgraded.isEmpty()) {
            event.setOutput(upgraded);
            event.setCost(1);
            event.setMaterialCost(1);
            return;
        }

        // TODO - removing upgrade
    }

    @Nonnull
    private static ItemStack tryUpgradeBackpack(ItemStack left, ItemStack right) {
        if (left.isEmpty() || !(left.getItem() instanceof IBackpack))
            return ItemStack.EMPTY;

        if (right.isEmpty() || !(right.getItem() instanceof IUpgrade))
            return ItemStack.EMPTY;

        ItemStack output = left.copy();

        IBackpack backpack = (IBackpack) output.getItem();
        BackpackInfo packInfo = ((IBackpack) output.getItem()).getBackpackInfo(output);
        BackpackUpgrade upgrade = ((IUpgrade) right.getItem()).getUpgrade(right);

        if (upgrade.isNull() || packInfo.getVariant().getType().isNull())
            return ItemStack.EMPTY;

        if (packInfo.conflicts(upgrade))
            return ItemStack.EMPTY;

        if (packInfo.getMaxPoints() - packInfo.getPointsUsed() < upgrade.getApplicationCost())
            return ItemStack.EMPTY;

        if (packInfo.hasUpgrade(upgrade))
            return ItemStack.EMPTY;

        if (packInfo.getVariant().getType().getTier() < upgrade.getMinimumTier())
            return ItemStack.EMPTY;

        packInfo.addUpgrade(upgrade);
        backpack.updateBackpack(output, packInfo);

        return output;
    }
}
