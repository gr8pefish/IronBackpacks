package gr8pefish.ironbackpacks.util.registry;

import gr8pefish.ironbackpacks.item.BackpackItem;
import gr8pefish.ironbackpacks.item.BaseBackpackItem;
import gr8pefish.ironbackpacks.item.BaseDyeableBackpackItem;
import gr8pefish.ironbackpacks.client.StackColorProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Public registry class used for simple creation and registration
 * of new backpack types. To easily register a new backpack, use the
 * {@link #create()} method to get an instance of a backpack builder,
 * then chain methods to override default values, and finally call
 * <tt>register</tt> with an {@link Identifier} to register the backpack
 * as a valid in-game item.
 * <p>
 * If you have a custom backpack implementation that differs from the
 * base mod's, you should call {@link #register(Identifier, BackpackItem)}
 * instead and construct your item instance as you see fit.
 *
 * @see Builder
 * @see #register(Identifier, BackpackItem)
 * @author EngiN33R
 * @since 4.0.0
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class BackpackRegistry {
    private static final Map<Identifier, BackpackItem> BACKPACKS = new HashMap<>();
    private static final Logger LOGGER = LogManager.getLogger();

    /**
     * Backpack item builder. Allows creating items using a clean inteface with
     * sane defaults for avoiding boilerplate. See methods and their javadocs for
     * details on customizable properties.
     *
     * @author EngiN33R
     * @since 4.0.0
     */
    public static class Builder {
        private static final Item.Settings SETTINGS = new Item.Settings().group(ItemGroup.TOOLS);

        private Item.Settings settings = SETTINGS;
        private boolean dyeable = false;
        private int tier = 0;
        private int upgradePoints = 4;
        private int cols = 9;
        private int rows = 1;
        private StackColorProvider colorProvider;
        private StackColorProvider defaultColorProvider;

        /**
         * Should this backpack be dyeable? Dyeing is performed as with
         * leather armor in vanilla Minecraft. Default is <tt>false</tt>.
         *
         * @param dyeable should the backpack be dyeable
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder dyeable(boolean dyeable) {
            this.dyeable = dyeable;
            return this;
        }

        /**
         * Sets the base tier for this backpack. In the base mod,
         * tier 0 is the basic backpack, tier 1 is iron, tier 2 is
         * gold and tier 3 is diamond. Default is <tt>0</tt>.
         *
         * @param tier backpack base tier
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder tier(int tier) {
            this.tier = tier;
            return this;
        }

        /**
         * Sets the base upgrade point amount for this backpack.
         * Default is <tt>4</tt>.
         *
         * @param upgradePoints upgrade point amount
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder upgradePoints(int upgradePoints) {
            this.upgradePoints = upgradePoints;
            return this;
        }

        /**
         * Sets the base size for this backpack.
         * Default is 9x1.
         *
         * @param cols backpack container columns
         * @param rows backpack container rows
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder size(int cols, int rows) {
            this.cols = cols;
            this.rows = rows;
            return this;
        }

        /**
         * Sets the color provider for this backpack. Only applicable
         * to dyeable backpacks.
         * <p>
         * Defaults to vanilla Minecraft behavior for dyeable items,
         * i.e. leather armor - the color is read from the stack's
         * <tt>display</tt> compound NBT under the <tt>color</tt> key.
         *
         * @param colorProvider color provider implementation
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder colorProvider(StackColorProvider colorProvider) {
            this.colorProvider = colorProvider;
            return this;
        }

        /**
         * Sets the default color provider for this backpack. Only applicable
         * to dyeable backpacks.
         * <p>
         * Provides the default color for a backpack when undyed. Defaults to
         * vanilla Minecraft behavior for dyeable items, i.e. leather armor -
         * the color is leather armor brown (<tt>#A06540</tt>).
         *
         * @param defaultColorProvider default color provider implementation
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder defaultColorProvider(StackColorProvider defaultColorProvider) {
            this.defaultColorProvider = defaultColorProvider;
            return this;
        }

        /**
         * Sets the item settings to pass to the item on creation. Defaults to
         * being categorized under Tools in the creative menu. The created item
         * will always be non-stackable.
         *
         * @param settings item settings
         * @return builder instance for chaining
         * @since 4.0.0
         */
        public Builder settings(Item.Settings settings) {
            this.settings = settings;
            return this;
        }

        private BaseBackpackItem build() {
            if (dyeable) {
                return new BaseDyeableBackpackItem(settings.maxCount(1), tier, upgradePoints, cols, rows,
                        colorProvider, defaultColorProvider);
            } else {
                return new BaseBackpackItem(settings.maxCount(1), tier, upgradePoints, cols, rows);
            }
        }

        /**
         * Registers the item specification as an actual item. This is the terminal
         * operation in a builder chain.
         *
         * @param id identifier to register the item under
         * @since 4.0.0
         */
        public void register(Identifier id) {
            BackpackRegistry.register(id, build());
        }
    }

    /**
     * Instantiates a builder for a backpack.
     *
     * @see Builder
     * @return builder instance
     * @since 4.0.0
     */
    public static Builder create() {
        return new Builder();
    }

    /**
     * Registers a backpack item directly. You should use this if your
     * backpack item implementation differs from base.
     *
     * @param id identifier for the item
     * @param item backpack item
     * @since 4.0.0
     */
    public static void register(Identifier id, BackpackItem item) {
        if (BACKPACKS.putIfAbsent(id, item) != null) {
            LOGGER.warn("Backpack with ID " + id.toString() + " already exists! Skipping...");
            return;
        }
        Registry.register(Registry.ITEM, id, item.asItem());
    }

    /**
     * Returns all currently registered backpacks.
     *
     * @return all registered backpacks
     * @since 4.0.0
     */
    public static Collection<BackpackItem> all() {
        return BACKPACKS.values();
    }
}
