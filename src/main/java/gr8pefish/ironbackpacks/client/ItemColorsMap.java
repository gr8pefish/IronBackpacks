package gr8pefish.ironbackpacks.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.ItemConvertible;
import net.minecraft.util.Pair;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ItemColorsMap {
    public static final ItemColorsMap INSTANCE = new ItemColorsMap();

    private ItemColors backedMap;
    private final List<Pair<ItemColorProvider, ItemConvertible[]>> queue = new ArrayList<>();

    public void register(ItemColorProvider mapper, ItemConvertible... items) {
        if (backedMap == null) {
            queue.add(new Pair<>(mapper, items));
            return;
        }
        backedMap.register(mapper, items);
    }

    public void setBackedMap(ItemColors map) {
        backedMap = map;
        if (backedMap != null && !queue.isEmpty()) {
            for (Pair<ItemColorProvider, ItemConvertible[]> args : queue) {
                backedMap.register(args.getLeft(), args.getRight());
            }
            queue.clear();
        }
    }
}
