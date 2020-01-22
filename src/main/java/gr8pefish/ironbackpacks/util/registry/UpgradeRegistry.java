package gr8pefish.ironbackpacks.util.registry;

import gr8pefish.ironbackpacks.item.BaseUpgradeItem;
import gr8pefish.ironbackpacks.item.UpgradeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static gr8pefish.ironbackpacks.IronBackpacks.MODID;

/**
 * Public registry class used for simple creation and registration
 * of new upgrades. To easily register a new upgrade, use the
 * {@link #create()} method to get an instance of an upgrade builder,
 * then chain methods to override default values, and finally call
 * <tt>register</tt> with an {@link Identifier} to register the upgrade
 * as a valid in-game item.
 * <p>
 * If you have a custom upgrade implementation that differs from the
 * base mod's, you should call {@link #register(Identifier, UpgradeItem)}
 * instead and construct your item instance as you see fit.
 *
 * @see Builder
 * @see #register(Identifier, UpgradeItem)
 * @author EngiN33R
 * @since 4.0.0
 */
@SuppressWarnings({"WeakerAccess", "unused"})
public class UpgradeRegistry {
    private static final Map<Identifier, UpgradeItem> UPGRADES = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Upgrade item builder. Allows creating items using a clean inteface with
     * sane defaults for avoiding boilerplate. See methods and their javadocs for
     * details on customizable properties.
     *
     * @author EngiN33R
     * @since 4.0.0
     */
    public static class Builder {
        private static final Item.Settings SETTINGS = new Item.Settings().group(ItemGroup.MISC);

        private Item.Settings settings = SETTINGS;
        private int points = 1;
        private int tier = 0;

        /**
         * Sets the upgrade point cost for this upgrade. Default is 1.
         *
         * @param points upgrade point cost
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder points(@Nonnegative int points) {
            this.points = points;
            return this;
        }

        /**
         * Sets the minimum tier of backpack for this upgrade. Default is 0.
         *
         * @param tier minimum backpack tier
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder tier(@Nonnegative int tier) {
            this.tier = tier;
            return this;
        }

        /**
         * Sets the item settings to pass to the item on creation. Defaults to
         * being stackable and categorized under Miscellaneous in the creative menu.
         *
         * @param settings item settings
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder settings(Item.Settings settings) {
            this.settings = settings;
            return this;
        }

        private BaseUpgradeItem build() {
            return new BaseUpgradeItem(settings, points, tier);
        }

        /**
         * Registers the upgrade specification as an actual item. The item ID
         * will match the upgrade ID. This is the terminal operation in a builder chain.
         *
         * @param id identifier to register the item under
         * @since 4.0.0
         */
        public void register(@Nonnull Identifier id) {
            UpgradeRegistry.register(id, build());
        }
    }

    /**
     * Instantiates a builder for an upgrade.
     *
     * @see Builder
     * @return builder instance
     * @since 4.0.0
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * Registers the provided upgrade item instance in the upgrade registry and
     * as an item.
     *
     * @param id upgrade and item ID
     * @param item item instance
     */
    public static void register(@Nonnull Identifier id, @Nonnull UpgradeItem item) {
        if (id.equals(new Identifier(MODID, "upgrade/blank"))) {
            LOGGER.warn("Trying to overwrite blank upgrade! Skipping...");
            return;
        }
        if (UPGRADES.putIfAbsent(id, item) != null) {
            LOGGER.warn("Upgrade with ID {} already exists! Skipping...", id);
            return;
        }
        Registry.register(Registry.ITEM, id, item.asItem());
    }

    /**
     * Returns all currently registered upgrades as identifiers.
     *
     * @return all registered backpacks
     * @since 4.0.0
     */
    public static Set<Identifier> all() {
        return UPGRADES.keySet();
    }

    /**
     * Returns all currently registered upgrades as items.
     *
     * @return all registered backpacks
     * @since 4.0.0
     */
    public static Collection<UpgradeItem> items() {
        return UPGRADES.values();
    }

    /**
     * Returns the item associated with the given upgrade ID.
     *
     * @return item associated with ID or null
     * @since 4.0.0
     */
    @Nullable
    public static UpgradeItem asItem(@Nullable Identifier id) {
        return UPGRADES.get(id);
    }
}
