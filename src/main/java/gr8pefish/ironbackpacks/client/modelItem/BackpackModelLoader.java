package gr8pefish.ironbackpacks.client.modelItem;

import gr8pefish.ironbackpacks.api.Constants;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ICustomModelLoader;
import net.minecraftforge.client.model.IModel;

import javax.annotation.Nonnull;

public final class BackpackModelLoader implements ICustomModelLoader {

    // Combined model
    public static final ResourceLocation MODEL_LOCATION = new ResourceLocation(Constants.MODID, "backpack");

    @Override
    public boolean accepts(ResourceLocation modelLocation) {
        return MODEL_LOCATION.equals(modelLocation);
    }

    @Override
    public IModel loadModel(ResourceLocation modelLocation) throws Exception {
        return new ModelBackpackItem(false, false, false);
    }

    @Override
    public void onResourceManagerReload(@Nonnull IResourceManager resourceManager) {

    }
}
