package gr8pefish.ironbackpacks.client.modelItem;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import gr8pefish.ironbackpacks.api.Constants;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ModelBackpackItem implements IModel, IModelSimpleProperties, IModelUVLock {

    //json for the model
    public static final ResourceLocation BASE_LOC = new ResourceLocation(Constants.MODID, "backpack_basic");
    //empty
    public static final ResourceLocation EMPTY_LOC = new ResourceLocation("minecraft:builtin/generated");

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
        return ImmutableList.of(BASE_LOC, EMPTY_LOC);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableList.of();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

        // Load the two models
        IModel baseModel = ModelLoaderRegistry.getModelOrLogError(BASE_LOC, "Base location for backpack model not found!");
        IModel handModel = ModelLoaderRegistry.getModelOrLogError(EMPTY_LOC, "Empty location for backpack model not found!");

        // For both of them, call gui3d, smoothLighting, and uvLock on them with the stored values
        baseModel = ModelProcessingHelper.gui3d(baseModel, gui3d);
        baseModel = ModelProcessingHelper.smoothLighting(baseModel, smoothLighting);
        baseModel = ModelProcessingHelper.uvlock(baseModel, uvlock);

        handModel = ModelProcessingHelper.gui3d(handModel, gui3d);
        handModel = ModelProcessingHelper.smoothLighting(handModel, smoothLighting);
        handModel = ModelProcessingHelper.uvlock(handModel, uvlock);

        // Bake both models with the given IModelState, VertexFormat, and textureGetter
        IBakedModel bakedBase = baseModel.bake(state, format, bakedTextureGetter);
        IBakedModel bakedHand = handModel.bake(state, format, bakedTextureGetter);

        // Use IPerspectiveAwareModel.MapWrapper.getTransforms on the IModelState to get an ImmutableMap
        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = IPerspectiveAwareModel.MapWrapper.getTransforms(state);

        // Create an instance of your custom IPerspectiveAwareModel containing the other two IBakedModels, the ImmutableMap, gui3d, smoothLighting, and uvLock and return it.
        return new BakedBackpack(bakedBase, bakedHand, transforms, gui3d, smoothLighting);

    }

    @Override
    public IModelState getDefaultState() {
        return ModelRotation.X0_Y0;
    }


    //IBaked Model as a private static inner class

    private static class BakedBackpack implements IBakedModel, IPerspectiveAwareModel {

        protected final boolean gui3d;
        protected final boolean smoothLighting;
        private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;
        private final IBakedModel bakedBase;
        private final IBakedModel bakedHand;

        //For instantiation via ModelBackpackItem (IModel)
        public BakedBackpack(IBakedModel base, IBakedModel hand, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> immutableMap, boolean gui3d, boolean smoothLighting){
            this.gui3d = gui3d;
            this.smoothLighting = smoothLighting;
            this.transforms = immutableMap;
            this.bakedBase = base;
            this.bakedHand = hand;

        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType transformType) {

            // Depending on the TransformType, choose the IBakedModel to return
            final IBakedModel bakedModel;
            if(transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_RIGHT_HAND || transformType == ItemCameraTransforms.TransformType.FIRST_PERSON_LEFT_HAND || transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_RIGHT_HAND || transformType == ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND) {
                bakedModel = bakedHand;
            } else {
                bakedModel = bakedBase;
            }

            // Store a pair depending on modelInstance
            final Pair<? extends IBakedModel, Matrix4f> transformed;
            // If that model is also an IPerspectiveAwareModel, use handlePerspective and store the returned Pair
            if(bakedModel instanceof IPerspectiveAwareModel) {
                transformed = ((IPerspectiveAwareModel)bakedModel).handlePerspective(transformType);
            } else { // Otherwise:
                TRSRTransformation baseTRSR = new TRSRTransformation(bakedModel.getItemCameraTransforms().getTransform(transformType));
                TRSRTransformation properTRSR = TRSRTransformation.blockCenterToCorner(baseTRSR);
                transformed = Pair.of(bakedModel, properTRSR.getMatrix());
            }

            // Get Matrix from pair
            Matrix4f baseMatrix = transformed.getRight();
            // Multiply the matrix in the pair with the matrix from the map
            baseMatrix.mul(transforms.getOrDefault(transformType, TRSRTransformation.identity()).getMatrix()); // Stores back into itself

            return transformed;

        }

        @Override
        public List<BakedQuad> getQuads(@Nullable IBlockState state, @Nullable EnumFacing side, long rand) {
            return new ArrayList<BakedQuad>();
        }

        @Override
        public boolean isAmbientOcclusion() {
            return smoothLighting;
        }

        @Override
        public boolean isGui3d() {
            return gui3d;
        }

        @Override
        public boolean isBuiltInRenderer() {
            return false;
        }

        @Override
        public TextureAtlasSprite getParticleTexture() {
//        return new TextureAtlasSprite(new ResourceLocation("missingno")); //can't do this as it is a protected instantiation call
            return null;
        }

        @Override
        public ItemCameraTransforms getItemCameraTransforms() {
            return ItemCameraTransforms.DEFAULT;
        }

        @Override
        public ItemOverrideList getOverrides() {
            return ItemOverrideList.NONE;
        }
    }
}