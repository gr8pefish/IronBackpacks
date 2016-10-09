package gr8pefish.ironbackpacks.client.modelItem;

import gr8pefish.ironbackpacks.api.Constants;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

public class CMLBackpack implements ICustomModelLoader {

    //Possibly incorrect RL
    public static ResourceLocation resourceLocationBackpack = new ResourceLocation(Constants.MODID, "backpackBasic");
    public static ResourceLocation resourceLocationBackpackHand = new ResourceLocation(Constants.MODID, "ItemBackpackBasic");

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return (modelLocation == resourceLocationBackpack);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        return new ModelBackpackItem();
    }

    @Override
    public void onResourceManagerReload(IResourceManager resourceManager) {

    }
}
