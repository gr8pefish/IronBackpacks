package gr8pefish.ironbackpacks.util;

import com.google.common.collect.Sets;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import gr8pefish.ironbackpacks.IronBackpacks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.oredict.OreDictionary;

import javax.annotation.Nonnull;
import java.io.File;
import java.lang.reflect.Type;
import java.util.Set;

public class InventoryBlacklist {

    public static final InventoryBlacklist INSTANCE = new InventoryBlacklist();

    private final Set<ItemPair> itemBlacklist;
    private final Set<String> nbtBlacklist;

    private InventoryBlacklist() {
        this.itemBlacklist = Sets.newHashSet();
        this.nbtBlacklist = Sets.newHashSet();
    }

    /**
     * Blacklists an item based only on it's meta value
     *
     * @param stack Item to blacklist
     */
    public void blacklist(@Nonnull ItemStack stack) {
        ItemPair pair = new ItemPair(stack.getItem(), stack.getItemDamage());
        if (!itemBlacklist.contains(pair))
            itemBlacklist.add(pair);
    }

    /**
     * Blacklists an NBT key. Any item with this key will be caught.
     *
     * @param tagKey NBT key to blacklist
     */
    public void blacklist(@Nonnull String tagKey) {
        if (!nbtBlacklist.contains(tagKey))
            nbtBlacklist.add(tagKey);
    }

    public boolean isBlacklisted(@Nonnull ItemStack stack) {
        for (ItemPair pair : itemBlacklist)
            if (pair.getItem() == stack.getItem())
                if (pair.getMeta() == stack.getItemDamage() || pair.getMeta() == OreDictionary.WILDCARD_VALUE)
                    return true;

        NBTTagCompound tag = stack.getTagCompound();
        if (tag == null || tag.hasNoTags())
            return false;

        for (String key : nbtBlacklist) {
            if (tag.hasKey(key))
                return true; // Short circuit if main tag has key

            String[] subTags = key.split("\\."); // Allow sub tags defined as `foo.bar` to look for a
            for (String subTag : subTags) {
                if (tag.hasNoTags() || tag.getTagId(subTag) != 10)
                    return tag.hasKey(subTag);

                tag = tag.getCompoundTag(subTag);
            }
        }

        return false;
    }

    public static void initBlacklist() {
        File jsonConfig = new File(Loader.instance().getConfigDir(), IronBackpacks.MODID + File.separator + "blacklist.json");
        JsonUtil.setCustomGson(new GsonBuilder().setPrettyPrinting().serializeNulls().disableHtmlEscaping().registerTypeAdapter(Item.class, new ItemPair.Serializer()).create());
        InventoryBlacklist blacklist = JsonUtil.fromJson(new TypeToken<InventoryBlacklist>(){}, jsonConfig, new InventoryBlacklist());

        INSTANCE.itemBlacklist.addAll(blacklist.itemBlacklist);
        INSTANCE.nbtBlacklist.addAll(blacklist.nbtBlacklist);
    }

    public static class ItemPair {
        private final Item item;
        private final int meta;

        public ItemPair(Item item, int meta) {
            this.item = item;
            this.meta = meta;
        }

        public Item getItem() {
            return item;
        }

        public int getMeta() {
            return meta;
        }

        public static class Serializer implements JsonSerializer<Item>, JsonDeserializer<Item> {
            @Override
            public Item deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                return ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.getAsString()));
            }

            @Override
            public JsonElement serialize(Item src, Type typeOfSrc, JsonSerializationContext context) {
                return new JsonPrimitive(src.getRegistryName().toString());
            }
        }
    }
}
