package gr8pefish.ironbackpacks.client.modelItem;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.*;

public class ModelBackpackItem implements IModel, IModelSimpleProperties, IModelUVLock {

    protected boolean smoothLighting;
    protected boolean gui3d;
    protected boolean uvlock;

    @Override
    public IModel smoothLighting(boolean value) {
        smoothLighting = value;
        return new ModelBackpackItem();
    }

    @Override
    public IModel gui3d(boolean value) {
        gui3d = value;
        return new ModelBackpackItem();
    }

    @Override
    public IModel uvlock(boolean value) {
        uvlock = value;
        return new ModelBackpackItem();
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        List<ResourceLocation> list = new ArrayList<>(Arrays.asList(CMLBackpack.resourceLocationBackpack, CMLBackpack.resourceLocationBackpackHand));
        return Collections.unmodifiableList(list);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableList.of();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

        // Load the other two models with ModelLoader.getModel
        try {
            IModel groundModelIModel = ModelLoaderRegistry.getModel(CMLBackpack.resourceLocationBackpack);
            IModel handModelIModel = ModelLoaderRegistry.getModel(CMLBackpack.resourceLocationBackpackHand);

            // For both of them, do some instanceof checks and call gui3d, smoothLighting, and uvLock on them with the stored values
            if (groundModelIModel instanceof ModelBackpackItem && handModelIModel instanceof ModelBackpackItem){ //instanceof checks likely wrong

                ((ModelBackpackItem) groundModelIModel).gui3d(((ModelBackpackItem) groundModelIModel).gui3d);
                ((ModelBackpackItem) groundModelIModel).smoothLighting(((ModelBackpackItem) groundModelIModel).smoothLighting);
                ((ModelBackpackItem) groundModelIModel).uvlock(((ModelBackpackItem) groundModelIModel).uvlock);

                ((ModelBackpackItem) handModelIModel).gui3d(((ModelBackpackItem) handModelIModel).gui3d);
                ((ModelBackpackItem) handModelIModel).smoothLighting(((ModelBackpackItem) handModelIModel).smoothLighting);
                ((ModelBackpackItem) handModelIModel).uvlock(((ModelBackpackItem) handModelIModel).uvlock);
                // don't save the above changes anywhere?

                // Two new IModels go into local vars
                ModelBackpackItem groundModelBackpack = new ModelBackpackItem(); //completely new?
                ModelBackpackItem handModelBackpack = new ModelBackpackItem();

                // Bake both models with the given IModelState, VertexFormat, and textureGetter
                groundModelBackpack.bake(state, format, bakedTextureGetter);
                handModelBackpack.bake(state, format, bakedTextureGetter);

                // Use IPerspectiveAwareModel.MapWrapper.getTransforms on the IModelState to get an ImmutableMap
                ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> immutableMap = IPerspectiveAwareModel.MapWrapper.getTransforms(state);

                // Create an instance of your custom IPerspectiveAwareModel containing the other two IBakedModels, the ImmutableMap, gui3d, smoothLighting, and uvLock and return it.
                PerspectiveModelBackpack perspectiveModelBackpack = new PerspectiveModelBackpack(groundModelBackpack, handModelBackpack, immutableMap, gui3d, smoothLighting, uvlock);
                return perspectiveModelBackpack;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public IModelState getDefaultState() {
        return ModelRotation.X0_Y0;
    }
}
