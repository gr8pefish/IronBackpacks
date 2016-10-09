package gr8pefish.ironbackpacks.client.modelItem;

import com.google.common.collect.ImmutableMap;
import gr8pefish.ironbackpacks.client.model.ModelBackpack;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.TRSRTransformation;
import org.apache.commons.lang3.tuple.Pair;

import javax.annotation.Nullable;
import javax.vecmath.Matrix4f;
import java.util.ArrayList;
import java.util.List;

public class PerspectiveModelBackpack implements IPerspectiveAwareModel {

    protected boolean gui3d;
    protected boolean smoothLighting;
    protected boolean uvlock;
    private ImmutableMap immutableMap;
    private ModelBackpackItem handModelBackpack;
    private ModelBackpackItem groundModelBackpack;

    private Pair perspective;

    //For instantiation via ModelBackpackItem (IModel)
    public PerspectiveModelBackpack(ModelBackpackItem groundModelBackpack, ModelBackpackItem handModelBackpack, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> immutableMap, boolean gui3d, boolean smoothLighting, boolean uvlock){
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
        if (cameraTransformType.equals(ItemCameraTransforms.TransformType.GROUND)) {
            modelToReturn = (IBakedModel) groundModelBackpack;
        } else {
            modelToReturn = (IBakedModel) handModelBackpack;
        }
        // If that model is also an IPerspectiveAwareModel (instanceof), use handlePerspective and store the returned Pair
        if (modelToReturn instanceof IPerspectiveAwareModel) {
            this.perspective = handlePerspective(cameraTransformType);
        } else{ // Otherwise:
            // Use getItemCameraTransforms().getTransforms() to get ItemTransformVec3f
            ItemTransformVec3f vec3f = getItemCameraTransforms().getTransform(cameraTransformType); //deprecated
            // Convert that to TRSRTransformation with the TRSRT constructor
            TRSRTransformation trsrTransformation = new TRSRTransformation(vec3f); //deprecated also
            // Use TRSRT.blockCenterToCorner on it
            TRSRTransformation transformation = TRSRTransformation.blockCenterToCorner(trsrTransformation);
            // Then convert THAT into a Matrix4f with getMatrix
            Matrix4f matrix4f = transformation.getMatrix();
            // Pair the model and matrix with Pair.of
            Pair myPair = Pair.of(modelToReturn, matrix4f);
            // From the stored ImmutableMap<TransformType, TRSRTransformation>, get the relevant transform and get its matrix
            TRSRTransformation trsrTransformationFromImmutable = (TRSRTransformation)this.immutableMap.get(cameraTransformType);
            Matrix4f matrix4fFromImmutable = trsrTransformationFromImmutable.getMatrix();
            // Multiply the matrix in the pair with the matrix from the map
            matrix4f.mul(matrix4fFromImmutable); //does it store it back in itself?
            //Return a pair of the model and the new matrix
            return new Pair<IBakedModel, Matrix4f>() {
                @Override
                public IBakedModel getLeft() {
                    return modelToReturn;
                }

                @Override
                public Matrix4f getRight() {
                    return matrix4f;
                }

                @Override
                public Matrix4f setValue(Matrix4f value) {
                    matrix4f.set(value);
                    return matrix4f;
                }
            };

        }

//

//
//
//
//
//
//
//
        return null;
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
