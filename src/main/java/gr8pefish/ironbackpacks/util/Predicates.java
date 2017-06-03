package gr8pefish.ironbackpacks.util;

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
}
