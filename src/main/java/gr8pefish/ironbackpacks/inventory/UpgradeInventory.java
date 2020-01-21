package gr8pefish.ironbackpacks.inventory;

import gr8pefish.ironbackpacks.item.BackpackItem;
import gr8pefish.ironbackpacks.item.UpgradeItem;
import gr8pefish.ironbackpacks.util.ItemStream;
import gr8pefish.ironbackpacks.util.ListTagCollector;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.BasicInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.util.DefaultedList;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.List;

public class UpgradeInventory extends BasicInventory {
    private final ItemStack backpackStack;
    private final BackpackItem backpackItem;

    public UpgradeInventory(ItemStack backpackStack) {
        // If a backpack has N upgrade points, then the upgrade inventory must be able to fit
        // at most N upgrades worth 1 point; therefore we create the upgrade inventory with N slots
        super(((BackpackItem) backpackStack.getItem()).getMaxUpgradePoints(backpackStack));
        this.backpackItem = (BackpackItem) backpackStack.getItem();
        this.backpackStack = backpackStack;
        List<Identifier> upgrades = backpackItem.getUpgrades(backpackStack);
        if (!upgrades.isEmpty()) {
            for (int slot = 0; slot < upgrades.size(); slot++) {
                setInvStack(slot, new ItemStack(Registry.ITEM.get(upgrades.get(slot))));
            }
        }
    }

    private DefaultedList<ItemStack> getItems() {
        DefaultedList<ItemStack> items = DefaultedList.ofSize(getInvSize(), ItemStack.EMPTY);
        for (int slot = 0; slot < getInvSize(); slot++) {
            items.set(slot, getInvStack(slot));
        }
        return items;
    }

    @Override
    public boolean isValidInvStack(int slot, ItemStack stack) {
        if (!(stack.getItem() instanceof UpgradeItem)) return false;
        UpgradeItem item = (UpgradeItem) stack.getItem();
        return item.getPoints() <= backpackItem.getFreeUpgradePoints(backpackStack)
                && backpackItem.getUpgradeTier() >= item.getMinTier();
    }

    @Override
    public void markDirty() {
        backpackStack.putSubTag("Upgrades", serializeNbt());
    }

    @Override
    public boolean canPlayerUseInv(PlayerEntity player) {
        return true;
    }

    private ListTag serializeNbt() {
        return getItems().stream()
                .filter(ItemStream::isNotEmpty)
                .map(ItemStack::getItem)
                .map(Registry.ITEM::getId)
                .map(Identifier::toString)
                .map(StringTag::of)
                .collect(ListTagCollector.toListTag());
    }

    @Override
    public int getInvMaxStackAmount() {
        return 1;
    }
}
