package gr8pefish.ironbackpacks.item;

import gr8pefish.ironbackpacks.client.StackColorProvider;
import lombok.Getter;
import net.minecraft.item.DyeableItem;
import net.minecraft.item.ItemStack;

@Getter
public class BaseDyeableBackpackItem extends BaseBackpackItem implements DyeableBackpackItem {
    private final StackColorProvider defaultColorProvider;
    private final StackColorProvider colorProvider;

    public BaseDyeableBackpackItem(Settings settings, int tier, int baseUpgradePoints, int cols, int rows,
                                   StackColorProvider colorProvider, StackColorProvider defaultColorProvider) {
        super(settings, tier, baseUpgradePoints, cols, rows);
        this.defaultColorProvider = defaultColorProvider;
        this.colorProvider = colorProvider;
    }
}
