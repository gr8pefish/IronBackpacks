package main.ironbackpacks.entity;

import cpw.mods.fml.common.registry.EntityRegistry;
import main.ironbackpacks.IronBackpacks;

public class Entities {

    public static void init(){
//        EntityRegistry.registerModEntity(EntityTest.class, "EntityTest", 0, IronBackpacks.instance, 80, 3, true);
        EntityRegistry.registerModEntity(EntityBackpack.class, "EntityBackpack", 0, IronBackpacks.instance, 80, 3, true);
//        EntityRegistry.registerModEntity(EntityBackpack.class, "EntityBackpackTest", 2, IronBackpacks.instance, 80, 3, true);
    }
}
