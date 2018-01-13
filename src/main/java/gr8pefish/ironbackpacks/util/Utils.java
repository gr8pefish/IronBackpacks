package gr8pefish.ironbackpacks.util;

import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.api.backpack.BackpackInfo;
import gr8pefish.ironbackpacks.api.backpack.IBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

// Because I don't know where else to put things
public class Utils {

    /**
     * Finds an {@link IBackpack} in the player's inventory. Allows filtering.
     *
     * Result definitions:
     *
     * <ul>
     *     <li>{@link EnumActionResult#FAIL} - No backpack was found for the player.</li>
     *     <li>{@link EnumActionResult#SUCCESS} - A backpack was found and it was on the hotbar.</li>
     *     <li>{@link EnumActionResult#PASS} - A backpack was found and it was not on the hotbar.</li>
     * </ul>
     *
     * @param player       - The player to find a backpack for
     * @param requirements - Requirements the backpack must meet, such as having a specific upgrade
     * @return an ActionResult containing information on the discovered stack
     */
    // TODO - Look for equipped backpack when implemented
    @Nonnull
    public static ActionResult<ItemStack> getBackpack(@Nonnull EntityPlayer player, @Nonnull Predicate<Pair<ItemStack, IBackpack>> requirements) {
        IItemHandler itemHandler = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (itemHandler == null)
            throw new IllegalArgumentException("Player inventory should never be null.");

        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            ItemStack foundStack = itemHandler.getStackInSlot(slot);
            if (!foundStack.isEmpty() && foundStack.getItem() instanceof IBackpack && requirements.test(Pair.of(foundStack, (IBackpack) foundStack.getItem()))) {
                EnumActionResult result = slot >= 0 && slot < 9 ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
                return ActionResult.newResult(result, foundStack);
            }
        }

        return ActionResult.newResult(EnumActionResult.FAIL, ItemStack.EMPTY);
    }

    /**
     * Finds any {@link IBackpack} in the player's inventory.
     *
     * @see #getBackpack(EntityPlayer, Predicate)
     */
    @Nonnull
    public static ActionResult<ItemStack> getBackpack(@Nonnull EntityPlayer player) {
        return getBackpack(player, Predicates.alwaysTrue());
    }

    @Nonnull
    public static List<ActionResult<ItemStack>> getAllBackpacks(@Nonnull EntityPlayer player, @Nonnull Predicate<Pair<ItemStack, IBackpack>> requirements) {
        IItemHandler itemHandler = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (itemHandler == null)
            throw new IllegalArgumentException("Player inventory should never be null.");

        List<ActionResult<ItemStack>> backpacks = Lists.newArrayList();
        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            ItemStack foundStack = itemHandler.getStackInSlot(slot);
            if (!foundStack.isEmpty() && foundStack.getItem() instanceof IBackpack && requirements.test(Pair.of(foundStack, (IBackpack) foundStack.getItem()))) {
                EnumActionResult result = slot >= 0 && slot < 9 ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
                backpacks.add(ActionResult.newResult(result, foundStack));
            }
        }

        return backpacks;
    }

    @Nonnull
    public static List<ActionResult<ItemStack>> getAllBackpacks(@Nonnull EntityPlayer player) {
        return getAllBackpacks(player, Predicates.alwaysTrue());
    }

    /**
     * Gets the {@link BackpackInfo} from a given {@link ItemStack}.
     * NOTE: The ItemStack *must* be a backpack, or it will (intentionally) crash.
     *
     * @param stack - the backpack as an item stack
     * @return - the backpack info
     */
    @Nonnull
    public static BackpackInfo getBackpackInfoFromStack(@Nonnull ItemStack stack) {
        if (!stack.isEmpty() && stack.getItem() instanceof IBackpack) {
            return ((IBackpack) stack.getItem()).getBackpackInfo(stack);
        }
        throw new RuntimeException("Tried to get backpack info from an ItemStack that isn't an IBackpack. Wrong item: "+stack);
    }


}
