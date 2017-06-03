package gr8pefish.ironbackpacks.proxy;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.*;
import gr8pefish.ironbackpacks.core.ModObjects;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);

        // Backpack
        ModelLoader.setCustomMeshDefinition(ModObjects.BACKPACK, stack -> {
            if (stack.getItem() instanceof IBackpack) {
                IBackpack backpack = (IBackpack) stack.getItem();
                BackpackInfo backpackInfo = backpack.getBackpackInfo(stack);
                ResourceLocation location = new ResourceLocation(backpackInfo.getBackpackType().getIdentifier().getResourceDomain(), "backpack/" + backpackInfo.getBackpackType().getIdentifier().getResourcePath());
                return new ModelResourceLocation(location, "inventory");
            }
            return new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "backpack/null"), "inventory");
        });

        for (BackpackType backpackType : IronBackpacksAPI.getBackpackTypes()) {
            if (!backpackType.isNull()) {
                ResourceLocation location = new ResourceLocation(backpackType.getIdentifier().getResourceDomain(), "backpack/" + backpackType.getIdentifier().getResourcePath());
                ModelLoader.registerItemVariants(ModObjects.BACKPACK, new ModelResourceLocation(location, "inventory"));
            }
        }

        ModelLoader.registerItemVariants(ModObjects.BACKPACK, new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "backpack/null"), "inventory"));

        // Upgrades
        ModelLoader.setCustomMeshDefinition(ModObjects.UPGRADE, stack -> {
            if (stack.getItem() instanceof IUpgrade) {
                IUpgrade upgrade = (IUpgrade) stack.getItem();
                BackpackUpgrade backpackUpgrade = upgrade.getUpgrade(stack);
                ResourceLocation location = new ResourceLocation(backpackUpgrade.getIdentifier().getResourceDomain(), "upgrade/" + backpackUpgrade.getIdentifier().getResourcePath());
                if (backpackUpgrade.isNull())
                    location = new ResourceLocation(IronBackpacks.MODID, "upgrade/null");
                return new ModelResourceLocation(location, "inventory");
            }
            return new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "upgrade/null"), "inventory");
        });

        for (BackpackUpgrade backpackUpgrade : IronBackpacksAPI.getUpgrades()) {
            if (!backpackUpgrade.isNull()) {
                ResourceLocation location = new ResourceLocation(backpackUpgrade.getIdentifier().getResourceDomain(), "upgrade/" + backpackUpgrade.getIdentifier().getResourcePath());
                ModelLoader.registerItemVariants(ModObjects.UPGRADE, new ModelResourceLocation(location, "inventory"));
            }
        }

        ModelLoader.registerItemVariants(ModObjects.UPGRADE, new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "upgrade/null"), "inventory"));
    }
}
