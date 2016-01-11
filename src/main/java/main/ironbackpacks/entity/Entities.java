package main.ironbackpacks.entity;

import main.ironbackpacks.IronBackpacks;
import net.minecraftforge.fml.common.registry.EntityRegistry;

public class Entities {

    public static void init(){
        EntityRegistry.registerModEntity(EntityBackpack.class, "EntityBackpack", 0, IronBackpacks.instance, 80, 3, true);
    }
}
