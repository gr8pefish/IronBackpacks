package gr8pefish.ironbackpacks.sounds;

import gr8pefish.ironbackpacks.api.Constants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class IronBackpacksSounds {

    public static SoundEvent open_backpack;
    public static SoundEvent close_backpack;

    public static void registerSounds(){

        //open the backpack sound
        ResourceLocation open_sound = new ResourceLocation(Constants.MODID, "open_backpack");
        open_backpack = GameRegistry.register(new SoundEvent(open_sound).setRegistryName(open_sound));

        //close the backpack sound
        ResourceLocation close_sound = new ResourceLocation(Constants.MODID, "close_backpack");
        close_backpack = GameRegistry.register(new SoundEvent(close_sound).setRegistryName(close_sound));
    }
}