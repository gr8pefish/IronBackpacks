package gr8pefish.ironbackpacks.util;

import net.minecraft.text.TranslatableText;

import static gr8pefish.ironbackpacks.IronBackpacks.MODID;

public class ModTranslatableText extends TranslatableText {
    public ModTranslatableText(String namespace, String path, Object... args) {
        super(String.join(".", namespace, MODID, path), args);
    }
}
