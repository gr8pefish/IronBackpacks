package gr8pefish.ironbackpacks.util;

import gr8pefish.ironbackpacks.api.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.IBackpack;
import net.minecraft.item.ItemStack;
import org.apache.commons.lang3.tuple.Pair;

import java.util.Objects;
import java.util.function.Predicate;

public class Predicates {

    public static <T> Predicate<T> alwaysTrue() {
        return o -> true;
    }

    public static <T> Predicate<T> alwaysFalse() {
        return o -> false;
    }

    public static <T> Predicate<T> isNull() {
        return Objects::isNull;
    }

    public static <T> Predicate<T> notNull() {
        return Objects::nonNull;
    }

    public static Predicate<Pair<ItemStack, IBackpack>> hasUpgrade(BackpackUpgrade upgrade) {
        return pack -> pack.getRight().getBackpackInfo(pack.getLeft()).hasUpgrade(upgrade);
    }
}
