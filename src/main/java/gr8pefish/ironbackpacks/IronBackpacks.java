package gr8pefish.ironbackpacks;

import gr8pefish.ironbackpacks.util.registry.BackpackRegistry;
import gr8pefish.ironbackpacks.util.registry.UpgradeRegistry;
import gr8pefish.ironbackpacks.gui.BackpackController;
import gr8pefish.ironbackpacks.gui.UpgradeController;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.container.ContainerProviderRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class IronBackpacks implements ModInitializer {
	public static final String MODID = "ironbackpacks";

	public static final Identifier GUI_BACKPACK = new Identifier(MODID, "gui/backpack");
	public static final Identifier GUI_UPGRADE = new Identifier(MODID, "gui/upgrade");

	public static final Identifier UPGRADE_DAMAGE_BAR = new Identifier(MODID, "upgrade/damage_bar");
	public static final Identifier UPGRADE_LOCK = new Identifier(MODID, "upgrade/lock");
	public static final Identifier UPGRADE_EVERLASTING = new Identifier(MODID, "upgrade/everlasting");
	public static final Identifier UPGRADE_FIREPROOF = new Identifier(MODID, "upgrade/fireproof");

	@Override
	public void onInitialize() {
		ContainerProviderRegistry.INSTANCE.registerFactory(GUI_BACKPACK,
				(syncId, id, player, buf) -> new BackpackController(syncId, player.inventory, buf.readInt()));
		ContainerProviderRegistry.INSTANCE.registerFactory(GUI_UPGRADE,
				(syncId, id, player, buf) -> new UpgradeController(syncId, player.inventory, buf.readInt()));

		BackpackRegistry.create()
				.tier(0).size(9, 2).upgradePoints(4)
				.dyeable(true).defaultColorProvider(stack -> 0xc97440)
				.register(new Identifier(MODID, "backpack/basic"));

		BackpackRegistry.create()
				.tier(1).size(9, 4).upgradePoints(7)
				.dyeable(false)
				.register(new Identifier(MODID, "backpack/iron_storage"));
		BackpackRegistry.create()
				.tier(1).size(9, 3).upgradePoints(12)
				.dyeable(false)
				.register(new Identifier(MODID, "backpack/iron_upgrade"));

		BackpackRegistry.create()
				.tier(2).size(9, 5).upgradePoints(12)
				.dyeable(false)
				.register(new Identifier(MODID, "backpack/gold_storage"));
		BackpackRegistry.create()
				.tier(2).size(9, 4).upgradePoints(15)
				.dyeable(false)
				.register(new Identifier(MODID, "backpack/gold_upgrade"));

		BackpackRegistry.create()
				.tier(3).size(9, 7).upgradePoints(15)
				.dyeable(false)
				.register(new Identifier(MODID, "backpack/diamond_storage"));
		BackpackRegistry.create()
				.tier(3).size(9, 6).upgradePoints(18)
				.dyeable(false)
				.register(new Identifier(MODID, "backpack/diamond_upgrade"));

		UpgradeRegistry.create().points(1).tier(0).register(UPGRADE_DAMAGE_BAR);
		UpgradeRegistry.create().points(4).tier(0).register(UPGRADE_LOCK);
		UpgradeRegistry.create().points(8).tier(1).register(UPGRADE_EVERLASTING);
		UpgradeRegistry.create().points(1).tier(0).register(UPGRADE_FIREPROOF);

		Registry.register(Registry.ITEM, new Identifier(MODID, "upgrade/blank"),
				new Item(new Item.Settings().group(ItemGroup.MISC)));
	}
}
