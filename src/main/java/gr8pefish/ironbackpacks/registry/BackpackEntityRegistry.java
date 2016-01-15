package gr8pefish.ironbackpacks.registry;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.entity.EntityBackpack;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class BackpackEntityRegistry {

    public static void init(){
        EntityRegistry.registerModEntity(EntityBackpack.class, "EntityBackpack", 0, IronBackpacks.instance, 80, 3, true);
    }
}
