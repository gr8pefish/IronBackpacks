package gr8pefish.ironbackpacks.core;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.upgrade.IUpgrade;
import gr8pefish.ironbackpacks.capabilities.PlayerBackpackHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemShears;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AnvilUpdateEvent;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.player.AnvilRepairEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.items.ItemHandlerHelper;

import javax.annotation.Nonnull;
import java.util.List;

@Mod.EventBusSubscriber
public class EventHandler {

    @SubscribeEvent
    public static void attachPlayerCaps(AttachCapabilitiesEvent<Entity> event) {
        if (!(event.getObject() instanceof EntityPlayer))
            return;

        event.addCapability(new ResourceLocation(IronBackpacks.MODID, "equipped_backpack"), new PlayerBackpackHandler.Default());
    }

    @SubscribeEvent
    public static void onAnvil(AnvilUpdateEvent event) {
        ItemStack upgraded = tryUpgradeBackpack(event.getLeft(), event.getRight());
        if (!upgraded.isEmpty()) {
            event.setOutput(upgraded);
            event.setCost(1);
            event.setMaterialCost(1);
        } else if (event.getRight().getItem() instanceof ItemShears){
            ItemStack removed = tryRemoveUpgrade(event.getLeft());
            if (!removed.isEmpty()) {
                event.setOutput(removed);
                event.setCost(1);
                event.setMaterialCost(0);
            }
        }
    }

    @SubscribeEvent
    public static void onAnvilPost(AnvilRepairEvent event) {
        if (event.getItemResult().isEmpty() || !(event.getItemResult().getItem() instanceof IUpgrade))
            return;

        event.setBreakChance(0.0F); // If we're working with backpacks, let's not damage the anvil

        if (event.getItemInput().isEmpty() || !(event.getItemInput().getItem() instanceof IBackpack))
            return;

        if (event.getIngredientInput().isEmpty() || !(event.getIngredientInput().getItem() instanceof ItemShears))
            return;

        BackpackUpgrade upgrade = ((IUpgrade) event.getItemResult().getItem()).getUpgrade(event.getItemResult());
        IBackpack backpack = ((IBackpack) event.getItemInput().getItem());
        BackpackInfo info = backpack.getBackpackInfo(event.getItemInput());

        info.removeUpgrade(upgrade);
        backpack.updateBackpack(event.getItemInput(), info);

        ItemHandlerHelper.giveItemToPlayer(event.getEntityPlayer(), event.getItemInput());
        ItemHandlerHelper.giveItemToPlayer(event.getEntityPlayer(), event.getIngredientInput());
    }

    @Nonnull
    private static ItemStack tryUpgradeBackpack(ItemStack left, ItemStack right) {
        if (left.isEmpty() || !(left.getItem() instanceof IBackpack))
            return ItemStack.EMPTY;

        if (right.isEmpty() || !(right.getItem() instanceof IUpgrade))
            return ItemStack.EMPTY;

        ItemStack output = left.copy();

        IBackpack backpack = (IBackpack) output.getItem();
        BackpackInfo packInfo = backpack.getBackpackInfo(output);
        BackpackUpgrade upgrade = ((IUpgrade) right.getItem()).getUpgrade(right);

        if (upgrade.isNull() || packInfo.getVariant().getBackpackType().isNull())
            return ItemStack.EMPTY;

        if (packInfo.conflicts(upgrade))
            return ItemStack.EMPTY;

        if (packInfo.getMaxPoints() - packInfo.getPointsUsed() < upgrade.getApplicationCost())
            return ItemStack.EMPTY;

        if (packInfo.hasUpgrade(upgrade))
            return ItemStack.EMPTY;

        if (packInfo.getVariant().getBackpackType().getTier() < upgrade.getMinimumTier())
            return ItemStack.EMPTY;

        packInfo.addUpgrade(upgrade);
        backpack.updateBackpack(output, packInfo);

        return output;
    }

    @Nonnull
    private static ItemStack tryRemoveUpgrade(ItemStack left) {
        if (left.isEmpty() || !(left.getItem() instanceof IBackpack))
            return ItemStack.EMPTY;

        BackpackInfo packInfo = ((IBackpack) left.getItem()).getBackpackInfo(left);
        List<BackpackUpgrade> upgrades = packInfo.getUpgrades();
        if (upgrades.isEmpty())
            return ItemStack.EMPTY;

        BackpackUpgrade upgrade = upgrades.get(upgrades.size() - 1);
        return IronBackpacksAPI.getStack(upgrade);
    }
}
