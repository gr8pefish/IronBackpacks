package gr8pefish.ironbackpacks.proxy;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.*;
import gr8pefish.ironbackpacks.core.ModObjects;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
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

        for (BackpackType backpackType : IronBackpacksHelper.getBackpackTypes()) {
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
                return new ModelResourceLocation(location, "inventory");
            }
            return new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "upgrade/null"), "inventory");
        });

        for (BackpackUpgrade backpackUpgrade : IronBackpacksHelper.getUpgrades()) {
            if (!backpackUpgrade.isNull()) {
                ResourceLocation location = new ResourceLocation(backpackUpgrade.getIdentifier().getResourceDomain(), "upgrade/" + backpackUpgrade.getIdentifier().getResourcePath());
                ModelLoader.registerItemVariants(ModObjects.UPGRADE, new ModelResourceLocation(location, "inventory"));
            }
        }

        ModelLoader.registerItemVariants(ModObjects.UPGRADE, new ModelResourceLocation(new ResourceLocation(IronBackpacks.MODID, "upgrade/null"), "inventory"));
    }

    @Override
    public void handleInventoryModel(Item item) {
//        if (!(item instanceof IModeled))
//            return;
//
//        List<String> variants = Lists.newArrayList();
//        ((IModeled) item).getVariants(variants);
//        ResourceLocation stateLoc = item.getRegistryName();
//
//        if (item instanceof IModeled.Advanced) {
//            for (String variant : variants)
//                ModelLoader.registerItemVariants(item, new ModelResourceLocation(stateLoc, variant));
//
//            ModelLoader.setCustomMeshDefinition(item, new MeshDefinitionWrapper((IModeled.Advanced) item));
//            return;
//        }
//
//        for (int i = 0; i < variants.size(); i++)
//            ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(stateLoc, variants.get(i)));
    }

    @Override
    public void handleInventoryModel(Block block) {
//        Item itemBlock = Item.getItemFromBlock(block);
//
//        if (!(block instanceof IModeled)) {
//            if (itemBlock instanceof IModeled)
//                handleInventoryModel(itemBlock);
//
//            return;
//        }
//
//        List<String> variants = Lists.newArrayList();
//        ((IModeled) block).getVariants(variants);
//
//        if (block instanceof IModeled.Advanced) {
//            for (String variant : variants)
//                ModelLoader.registerItemVariants(Item.getItemFromBlock(block), new ModelResourceLocation(block.getRegistryName(), variant));
//
//            ModelLoader.setCustomMeshDefinition(Item.getItemFromBlock(block), new MeshDefinitionWrapper((IModeled.Advanced) block));
//            return;
//        }
//
//        for (int i = 0; i < variants.size(); i++)
//            ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(block), i, new ModelResourceLocation(block.getRegistryName(), variants.get(i)));
    }
}
