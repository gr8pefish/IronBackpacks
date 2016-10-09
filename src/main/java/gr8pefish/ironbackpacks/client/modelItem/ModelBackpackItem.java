package gr8pefish.ironbackpacks.client.modelItem;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
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
        return ImmutableList.of(CMLBackpack.resourceLocationBackpackGround, CMLBackpack.resourceLocationBackpackHand);
    }

    @Override
    public Collection<ResourceLocation> getTextures() {
        return ImmutableList.of();
    }

    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {

        // Load the other two models with ModelLoader.getModel
        try {
            IModel groundModelIModel = ModelLoaderRegistry.getModel(CMLBackpack.resourceLocationBackpackGround);
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
            return new PerspectiveModelBackpack(groundBaked, handBaked, immutableMap, gui3d, smoothLighting, uvlock);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public IModelState getDefaultState() {
        return ModelRotation.X0_Y0;
    }


    //IBaked Model as a private static inner class

    private static class PerspectiveModelBackpack implements IPerspectiveAwareModel {

        protected final boolean gui3d;
        protected final boolean smoothLighting;
        protected final boolean uvlock;
        private final ImmutableMap immutableMap;
        private final IBakedModel handModelBackpack;
        private final IBakedModel groundModelBackpack;

        //For instantiation via ModelBackpackItem (IModel)
        public PerspectiveModelBackpack(IBakedModel groundModelBackpack, IBakedModel handModelBackpack, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> immutableMap, boolean gui3d, boolean smoothLighting, boolean uvlock){
            this.gui3d = gui3d;
            this.smoothLighting = smoothLighting;
            this.uvlock = uvlock;
            this.immutableMap = immutableMap;
            this.handModelBackpack = handModelBackpack;
            this.groundModelBackpack = groundModelBackpack;
        }

        @Override
        public Pair<? extends IBakedModel, Matrix4f> handlePerspective(ItemCameraTransforms.TransformType cameraTransformType) {
            // Depending on the TransformType, choose the IBakedModel to return
            IBakedModel modelToReturn;

            // Store a pair depending
            Pair<? extends IBakedModel, Matrix4f> pairStored;

            if (cameraTransformType.equals(ItemCameraTransforms.TransformType.GROUND)) {
                modelToReturn = groundModelBackpack;
            } else {
                modelToReturn = handModelBackpack;
            }
            // If that model is also an IPerspectiveAwareModel (instanceof), use handlePerspective and store the returned Pair
            if (modelToReturn instanceof IPerspectiveAwareModel) {
                pairStored = ((IPerspectiveAwareModel) modelToReturn).handlePerspective(cameraTransformType);
            } else { // Otherwise:
                // Use getItemCameraTransforms().getTransforms() to get ItemTransformVec3f
                ItemTransformVec3f vec3f = getItemCameraTransforms().getTransform(cameraTransformType); //deprecated
                // Convert that to TRSRTransformation with the TRSRT constructor
                TRSRTransformation trsrTransformation = new TRSRTransformation(vec3f); //deprecated also
                // Use TRSRT.blockCenterToCorner on it
                TRSRTransformation transformation = TRSRTransformation.blockCenterToCorner(trsrTransformation);
                // Then convert THAT into a Matrix4f with getMatrix
                Matrix4f matrix4f = transformation.getMatrix();
                // Pair the model and matrix with Pair.of
                pairStored = Pair.of(modelToReturn, matrix4f);
            }

            // From the stored ImmutableMap<TransformType, TRSRTransformation>, get the relevant transform and get its matrix
            TRSRTransformation trsrTransformationFromImmutable = (TRSRTransformation)this.immutableMap.get(cameraTransformType);
            Matrix4f matrix4fFromImmutable = trsrTransformationFromImmutable.getMatrix();
            // Get Matrix from pair
            Matrix4f matrix4fFromPair = pairStored.getRight();
            // Multiply the matrix in the pair with the matrix from the map
            matrix4fFromPair.mul(matrix4fFromImmutable); //stores it back in itself
            //Return a pair of the model and the new matrix
            return Pair.of(modelToReturn, matrix4fFromPair);

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
