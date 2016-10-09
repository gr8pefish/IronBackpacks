package gr8pefish.ironbackpacks.client.modelItem;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.block.model.ModelRotation;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Collection;

public class ModelBackpackItem implements IModel, IModelSimpleProperties, IModelUVLock {

    protected final boolean smoothLighting;
    protected final boolean gui3d;
    protected final boolean uvlock;

    public ModelBackpackItem(boolean smoothLighting, boolean gui3d, boolean uvlock){
        this.smoothLighting = smoothLighting;
        this.gui3d = gui3d;
        this.uvlock = uvlock;
    }

    @Override
    public IModel smoothLighting(boolean value) {
        return new ModelBackpackItem(value, gui3d, uvlock);
    }

    @Override
    public IModel gui3d(boolean value) {
        return new ModelBackpackItem(smoothLighting, value, uvlock);
    }

    @Override
    public IModel uvlock(boolean value) {
        return new ModelBackpackItem(smoothLighting, gui3d, value);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return ImmutableList.of(CMLBackpack.resourceLocationBackpack, CMLBackpack.resourceLocationBackpackHand);
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
            IModel handModelIModel = ModelLoaderRegistry.getModel(new ResourceLocation("minecraft:builtin/generated"));

            // For both of them, do some instanceof checks and call gui3d, smoothLighting, and uvLock on them with the stored values
            IModel ground1 = ModelProcessingHelper.gui3d(groundModelIModel, gui3d);
            IModel ground2 = ModelProcessingHelper.smoothLighting(ground1, smoothLighting);
            IModel ground3 = ModelProcessingHelper.uvlock(ground2, uvlock);

            IModel hand1 = ModelProcessingHelper.gui3d(handModelIModel, gui3d);
            IModel hand2 = ModelProcessingHelper.smoothLighting(hand1, smoothLighting);
            IModel hand3 = ModelProcessingHelper.uvlock(hand2, uvlock);

            // Bake both models with the given IModelState, VertexFormat, and textureGetter
            IBakedModel groundBaked = ground3.bake(state, format, bakedTextureGetter);
            IBakedModel handBaked = hand3.bake(state, format, bakedTextureGetter);

            // Use IPerspectiveAwareModel.MapWrapper.getTransforms on the IModelState to get an ImmutableMap
            ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> immutableMap = IPerspectiveAwareModel.MapWrapper.getTransforms(state);

            // Create an instance of your custom IPerspectiveAwareModel containing the other two IBakedModels, the ImmutableMap, gui3d, smoothLighting, and uvLock and return it.
            PerspectiveModelBackpack perspectiveModelBackpack = new PerspectiveModelBackpack((ModelBackpackItem)groundBaked, (ModelBackpackItem)handBaked, immutableMap, gui3d, smoothLighting, uvlock);
            return perspectiveModelBackpack;

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
