package gr8pefish.ironbackpacks.util;

import com.google.common.collect.ImmutableSet;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;

import java.util.ArrayList;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.BinaryOperator;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collector;

public class ListTagCollector implements Collector<Tag, ArrayList<Tag>, ListTag> {
    @Override
    public Supplier<ArrayList<Tag>> supplier() {
        return ArrayList::new;
    }

    @Override
    public BiConsumer<ArrayList<Tag>, Tag> accumulator() {
        return ArrayList::add;
    }

    @Override
    public BinaryOperator<ArrayList<Tag>> combiner() {
        return (left, right) -> {
            left.addAll(right);
            return left;
        };
    }

    @Override
    public Function<ArrayList<Tag>, ListTag> finisher() {
        return tags -> {
            ListTag tag = new ListTag();
            tag.addAll(tags);
            return tag;
        };
    }

    @Override
    public Set<Characteristics> characteristics() {
        return ImmutableSet.of();
    }

    public static ListTagCollector toListTag() {
        return new ListTagCollector();
    }
}
