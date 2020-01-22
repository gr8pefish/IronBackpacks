package gr8pefish.ironbackpacks.item;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.IronBackpacksClient;
import gr8pefish.ironbackpacks.inventory.VirtualInventory;
import gr8pefish.ironbackpacks.util.ListTagCollector;
import gr8pefish.ironbackpacks.util.ModTranslatableText;
import gr8pefish.ironbackpacks.util.registry.UpgradeRegistry;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.fabricmc.fabric.api.util.NbtType;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.sound.SoundCategory;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.*;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.*;
import java.util.stream.Collectors;

import static gr8pefish.ironbackpacks.IronBackpacks.*;

/**
 * Describes a basic backpack item, implementing core functionality.
 * It is recommended that this class be used as a base for implementing
 * custom backpack behaviour.
 *
 * @author EngiN33R
 * @since 4.0.0
 */
@Getter
public class BaseBackpackItem extends Item implements BackpackItem {
    private final int baseTier;
    private final int baseUpgradePoints;
    private final Size baseSize;

    public BaseBackpackItem(Settings settings, int baseTier, int baseUpgradePoints, int cols, int rows) {
        super(settings);
        this.baseTier = baseTier;
        this.baseUpgradePoints = baseUpgradePoints;
        this.baseSize = new Size(cols, rows);
    }

    // Internal utils

    // TODO: Apply upgrade modifiers once they're in
    private Size getSize(ItemStack stack) {
        return PackData.fromStack(stack).applySizeModifier(baseSize);
    }

    private PackData getData(@Nonnull ItemStack stack) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");
        return PackData.fromStack(stack);
    }

    private void putData(@Nonnull ItemStack stack, @Nonnull PackData data) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");
        Preconditions.checkNotNull(data, "BackpackInfo cannot be null");
        stack.getOrCreateTag().copyFrom(data.serializeNbt());
    }

    // Item behavior

    @Override
    public void appendTooltip(ItemStack stack, World world, List<Text> tooltip, TooltipContext context) {
        if (!(stack.getItem() instanceof BaseBackpackItem)) return;
        PackData data = getData(stack);
        if (Screen.hasShiftDown()) {
            tooltip.add(new ModTranslatableText("tooltip", "backpack.tier", baseTier).formatted(Formatting.DARK_AQUA));
            if (data.getOwner() != null) {
                String name = data.getOwnerName() != null ? data.getOwnerName() : new LiteralText("Unknown").formatted(Formatting.ITALIC).asFormattedString();
                tooltip.add(new ModTranslatableText("tooltip", "backpack.owner", name).formatted(Formatting.DARK_GRAY));
            }
            tooltip.add(new ModTranslatableText("tooltip", "backpack.upgrade.points", getUsedUpgradePoints(stack), getMaxUpgradePoints(stack)).formatted(Formatting.DARK_GRAY));
            tooltip.add(new ModTranslatableText("tooltip", "backpack.upgrade.list").formatted(Formatting.DARK_GRAY, Formatting.UNDERLINE));
            if (data.getUpgrades().isEmpty()) {
                String none = new ModTranslatableText("tooltip", "backpack.upgrade.none").asString();
                tooltip.add(new LiteralText(" - " + none).formatted(Formatting.DARK_GRAY, Formatting.ITALIC));
            } else {
                data.getUpgrades().stream().map(UpgradeRegistry::asItem).filter(Objects::nonNull).forEach(item -> {
                    String name = new TranslatableText(item.getTranslationKey(stack)).asString();
                    tooltip.add(new LiteralText(" - " + new LiteralText(name).formatted(Formatting.AQUA).asFormattedString() + " (" + item.getPoints() + ")"));
                });
            }
        } else {
            tooltip.add(new ModTranslatableText("tooltip", "shift").formatted(Formatting.DARK_GRAY));
        }
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        ItemStack held = user.getStackInHand(hand);
        PackData data = getData(held);

        if (data.hasUpgrade(UPGRADE_LOCK)) {
            if (data.getOwner() == null) {
                user.addChatMessage(new ModTranslatableText("message", "owner.set"), true);
                data.setOwner(user.getGameProfile().getId());
                data.setOwnerName(user.getGameProfile().getName());
            } else {
                if (!data.getOwner().equals(user.getGameProfile().getId())) {
                    if (world.isClient) {
                        world.playSound(user.getX(), user.getY(), user.getZ(), IronBackpacksClient.BACKPACK_CLOSE, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
                    }
                    return TypedActionResult.pass(held);
                }
            }
        } else {
            if (data.getOwner() != null) {
                user.addChatMessage(new ModTranslatableText("message", "owner.clear"), true);
                data.setOwner(null);
                data.setOwnerName(null);
            }
        }
        putData(held, data);

        if (world.isClient) {
            world.playSound(user.getX(), user.getY(), user.getZ(), IronBackpacksClient.BACKPACK_OPEN, SoundCategory.NEUTRAL, 1.0F, 1.0F, false);
            return TypedActionResult.success(held);
        }
        Integer slot = null;
        for (int invSlot = 0; invSlot < user.inventory.getInvSize(); invSlot++) {
            if (user.inventory.getInvStack(invSlot) == held) {
                slot = invSlot;
            }
        }
        if (slot == null) {
            return TypedActionResult.fail(held);
        }
        int backpackSlot = slot;

        if (user.isSneaking()) {
            ContainerProviderRegistry.INSTANCE.openContainer(
                    IronBackpacks.GUI_UPGRADE, user, (buf) -> buf
                            .writeInt(backpackSlot));
        } else {
            ContainerProviderRegistry.INSTANCE.openContainer(
                    IronBackpacks.GUI_BACKPACK, user, (buf) -> buf
                            .writeInt(backpackSlot));
        }

        return TypedActionResult.success(held);
    }

    // Durability bar rendering

    public boolean showDurability(ItemStack stack) {
        PackData data = getData(stack);
        Inventory inv = data.getInventory();
        return data.hasUpgrade(UPGRADE_DAMAGE_BAR) && inv != null && !inv.isInvEmpty();
    }

    public int getDurability(ItemStack stack) {
        Inventory inv = getData(stack).getInventory();
        int slotsOccupied = 0;
        for (int slot = 0; slot < inv.getInvSize(); slot++) {
            if (inv.getInvStack(slot) != ItemStack.EMPTY) {
                slotsOccupied += inv.getInvStack(slot).getCount();
            }
        }
        return slotsOccupied;
    }

    public int getMaxDurability(ItemStack stack) {
        Inventory inv = getData(stack).getInventory();
        int slotsPossible = 0;
        for (int slot = 0; slot < inv.getInvSize(); slot++) {
            if (inv.getInvStack(slot) != ItemStack.EMPTY) {
                slotsPossible += inv.getInvStack(slot).getMaxCount();
            } else {
                slotsPossible += 64;
            }
        }
        return slotsPossible;
    }

    public int getDurabilityColor(ItemStack stack) {
        float damage = (float) getDurability(stack);
        float maxDamage = (float) getMaxDurability(stack);
        float hue = Math.max(0.0F, (maxDamage - damage) / maxDamage);
        return MathHelper.hsvToRgb(hue / 3.0F, 1.0F, 1.0F);
    }

    // BackpackItem implementation

    @Override
    public int getUpgradeTier() {
        return getBaseTier();
    }

    @Override
    public int getMaxUpgradePoints(ItemStack stack) {
        return PackData.fromStack(stack).applyUpgradePointModifier(baseUpgradePoints);
    }

    @Override
    public int getUsedUpgradePoints(ItemStack stack) {
        return PackData.fromStack(stack).getUpgrades().stream()
                .map(UpgradeRegistry::asItem)
                .filter(Objects::nonNull)
                .mapToInt(UpgradeItem::getPoints)
                .sum();
    }

    @Override
    public int getFreeUpgradePoints(ItemStack stack) {
        return getMaxUpgradePoints(stack) - getUsedUpgradePoints(stack);
    }

    @Override
    public List<Identifier> getUpgrades(ItemStack stack) {
        return getData(stack).getUpgrades();
    }

    @Override
    public boolean hasUpgrade(ItemStack stack, Identifier id) {
        return getData(stack).hasUpgrade(id);
    }

    @Override
    public int getContainerColumns(ItemStack stack) {
        return getSize(stack).getColumns();
    }

    @Override
    public int getContainerRows(ItemStack stack) {
        return getSize(stack).getRows();
    }

    @Override
    public int getCapacity(ItemStack stack) {
        return getSize(stack).getTotalSize();
    }

    // Internal utils (classes)

    /**
     * Represents the information stored on a backpack ItemStack.
     *
     * @author grp8pefish
     * @author EngiN33R
     * @since 4.0.0
     */
    @Getter
    @Setter
    protected static class PackData {
        private final List<Identifier> upgrades = new ArrayList<>();
        private VirtualInventory inventory;
        private UUID owner;
        // Fetching names associated with UUIDs is a pain if you just use them
        // for rendering purposes, so just store it in a tag
        private String ownerName;

        static PackData fromStack(ItemStack stack) {
            Preconditions.checkNotNull(stack, "ItemStack cannot be null");
            PackData data = new PackData();
            if (stack.isEmpty() || !stack.hasTag() || !(stack.getItem() instanceof BaseBackpackItem)) return data;
            CompoundTag tag = stack.getOrCreateTag();
            if (tag.containsUuid("Owner")) {
                data.setOwner(tag.getUuid("Owner"));
            }
            if (tag.contains("OwnerName")) {
                data.setOwnerName(tag.getString("OwnerName"));
            }
            data.setInventory(VirtualInventory.fromTag(tag.getCompound("Inventory")));
            data.setUpgrades(tag.getList("Upgrades", NbtType.STRING).stream()
                    .map(Tag::asString).map(Identifier::new).collect(Collectors.toList()));
            return data;
        }

        boolean hasUpgrade(Identifier upgrade) {
            return upgrades.contains(upgrade);
        }

        void setUpgrades(Collection<Identifier> upgrades) {
            this.upgrades.clear();
            this.upgrades.addAll(upgrades);
        }

        /**
         * Given the base upgrade point amount, applies modifiers from
         * per-item properties (e.g. upgrades).
         *
         * @param base base upgrade point amount
         * @return modified upgrade point amount
         * @since 4.0.0
         */
        // TODO: Upgrades for more upgrades etc.
        int applyUpgradePointModifier(int base) {
            return base;
        }

        /**
         * Given the base backpack size, applies modifiers from
         * per-item properties (e.g. upgrades).
         *
         * @param base base size
         * @return modified size
         * @since 4.0.0
         */
        // TODO: Upgrades for more space
        Size applySizeModifier(Size base) {
            return base;
        }

        CompoundTag serializeNbt() {
            CompoundTag tag = new CompoundTag();

            if (owner != null) {
                tag.putUuid("Owner", owner);
            } else {
                tag.remove("Owner");
            }
            if (ownerName != null) {
                tag.putString("OwnerName", ownerName);
            } else {
                tag.remove("OwnerName");
            }

            if (inventory != null) {
                VirtualInventory.toTag(tag.getCompound("Inventory"), inventory);
            }

            tag.put("Upgrades", upgrades.stream()
                    .map(Identifier::toString)
                    .map(StringTag::of)
                    .collect(ListTagCollector.toListTag()));

            return tag;
        }
    }

    /**
     * An immutable description of a backpack's size, essentially for
     * storing columns and rows as one object, but also doing some checks
     * against maximum permissible values for them.
     *
     * @author gr8pefish
     * @author EngiN33R
     * @since 4.0.0
     */
    @Data
    protected static class Size {
        private static final int MAX_COL = 17;
        private static final int MAX_ROW = 7;

        private final int columns;
        private final int rows;

        Size(@Nonnegative int columns, @Nonnegative int rows) {
            Preconditions.checkArgument(columns > 0, "Column count must be greater than 0");
            Preconditions.checkArgument(columns <= MAX_COL, "Column count must be less than the maximum");
            Preconditions.checkArgument(rows > 0, "Row count must be greater than 0");
            Preconditions.checkArgument(rows <= MAX_ROW, "Row count must be less than the maximum");

            this.columns = columns;
            this.rows = rows;
        }

        int getTotalSize() {
            return columns * rows;
        }

        @Override
        public String toString() {
            return columns + "x" + rows + " => " + getTotalSize();
        }
    }
}