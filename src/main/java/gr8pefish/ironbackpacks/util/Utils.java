package gr8pefish.ironbackpacks.util;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import gr8pefish.ironbackpacks.api.IBackpack;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nonnull;

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
     *     <li>{@link EnumActionResult#PASS} - A backpack was found and it was not on the hotbar..</li>
     * </ul>
     *
     * @param player - The player to find a backpack for
     * @param requirements - Requirements the backpack must meet, such as having a specific upgrade
     * @return an ActionResult containing information on the discovered stack
     */
    // TODO - Look for equipped backpack when implemented
    @Nonnull
    public static ActionResult<ItemStack> getBackpack(@Nonnull EntityPlayer player, Predicate<Pair<ItemStack, IBackpack>> requirements) {
        IItemHandler itemHandler = player.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        assert itemHandler != null;

        for (int slot = 0; slot < itemHandler.getSlots(); slot++) {
            ItemStack foundStack = itemHandler.getStackInSlot(slot);
            if (!foundStack.isEmpty() && foundStack.getItem() instanceof IBackpack && requirements.apply(Pair.of(foundStack, (IBackpack) foundStack.getItem()))) {
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
}
