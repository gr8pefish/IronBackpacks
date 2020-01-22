package gr8pefish.ironbackpacks.item;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.util.ModTranslatableText;
import lombok.Getter;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

@Getter
// TODO: Certain upgrades should stack, while others should only ever exist once in a backpack
public class BaseUpgradeItem extends Item implements UpgradeItem {
    private final int points;
    private final int minTier;

    public BaseUpgradeItem(Settings settings, @Nonnegative int points, @Nonnegative int minTier) {
        super(settings);
        this.points = points;
        this.minTier = minTier;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        String translatedDesc = new TranslatableText(super.getTranslationKey() + ".desc").asString();
        for (String desc : translatedDesc.split("\n")) {
            tooltip.add(new LiteralText(desc).formatted(Formatting.AQUA, Formatting.ITALIC));
        }
        tooltip.add(new ModTranslatableText("tooltip", "upgrade.cost", points).formatted(Formatting.DARK_GRAY));
        if (minTier > 0) {
            tooltip.add(new ModTranslatableText("tooltip", "upgrade.minimum_tier", minTier).formatted(Formatting.DARK_GRAY));
        }
    }

    @Override
    public String getTranslationKey(@Nonnull ItemStack stack) {
        return super.getTranslationKey() + ".name";
    }
}
