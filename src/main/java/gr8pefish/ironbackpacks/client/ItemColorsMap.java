package gr8pefish.ironbackpacks.client;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.color.item.ItemColorProvider;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.item.ItemConvertible;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
public class ItemColorsMap {
    public static final ItemColorsMap INSTANCE = new ItemColorsMap();

    private ItemColors backedMap;
    private final List<Object[]> queue = new ArrayList<>();

    public void register(ItemColorProvider mapper, ItemConvertible... items) {
        if (backedMap == null) {
            queue.add(new Object[]{ mapper, items });
            return;
        }
        backedMap.register(mapper, items);
    }

    public void setBackedMap(ItemColors map) {
        backedMap = map;
        if (backedMap != null && !queue.isEmpty()) {
            for (Object[] args : queue) {
                backedMap.register((ItemColorProvider) args[0], (ItemConvertible[]) args[1]);
            }
            queue.clear();
        }
    }
}
