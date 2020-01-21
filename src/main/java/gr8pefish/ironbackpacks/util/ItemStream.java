package gr8pefish.ironbackpacks.util;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

import java.util.Collection;
import java.util.stream.Stream;

public class ItemStream {
    public static boolean isNotEmpty(ItemStack item) {
        return !item.isEmpty();
    }

    public static <T extends Item> Stream<T> ofIds(Collection<Identifier> ids, Class<T> itemClass) {
        return ids.stream().map(Registry.ITEM::get).map(itemClass::cast);
    }
}
