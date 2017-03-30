package gr8pefish.ironbackpacks.stolenfromnut.model;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * An interface for Items and Blocks to register inventory models based on ItemStack meta in a clean and efficient manner.
 */
public interface IModeled {

    /**
     * Used to build the variant list for this particular Item or Block. The index in the list is used as the meta of the
     * item, so add them in the same order you add your items in {@link Item#getSubItems(Item, CreativeTabs, NonNullList)}
     *
     * This is backed by the ForgeBlockstate format, so BlockState-style variants can be used along with the standard
     * {@code inventory} variant.
     *
     * Items will look for their "blockstate" json in {@code assets/$MODID/blockstates/item}. Blocks will use their normal
     * blockstate location.
     *
     * Variant examples:
     * <ul>
     *     <li>powered=true</li>
     *     <li>color=red</li>
     *     <li>tool=pickaxe</li>
     *     <li>inventory</li>
     * </ul>
     *
     * For edge-case users: If your sub-items skip over meta, provide either a previously used variant or {@code inventory}
     * and provide an empty default item model.
     */
    void getVariants(List<String> variants);

    /**
     * An interface for Items and Blocks to register inventory models based on ItemStack NBT in a clean and efficient manner.
     */
    interface Advanced extends IModeled {

        /**
         * Variants used must still be added in {@link #getVariants(List)}, however that changes from a meta-based list to
         * a list of variants that need to be added.
         *
         * Functions exactly the same as {@link net.minecraft.client.renderer.ItemMeshDefinition#getModelLocation(ItemStack)}
         * except it is side-safe. This gets forwarded to a mesh definition wrapper.
         *
         * @param stack - The stack being drawn
         * @return the location of the model based on the stack data
         */
        @Nonnull
        ModelResourceLocation getModelLocation(ItemStack stack);
    }
}
