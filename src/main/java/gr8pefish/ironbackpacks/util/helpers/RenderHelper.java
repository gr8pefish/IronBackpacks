package gr8pefish.ironbackpacks.util.helpers;

import com.google.common.base.Function;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IReloadableResourceManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.crash.CrashReport;
import net.minecraft.util.ReportedException;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.Attributes;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.client.model.pipeline.LightUtil;
import org.lwjgl.opengl.GL11;

import javax.annotation.Nullable;
import java.io.IOException;
import java.util.Map;

public class RenderHelper {

    static Map<String, IFlexibleBakedModel> loadedModels = Maps.newHashMap();

    public static void init() {
        IResourceManager rm = Minecraft.getMinecraft().getResourceManager();
        if (rm instanceof IReloadableResourceManager) {
            ((IReloadableResourceManager) rm).registerReloadListener(ignored -> loadedModels.clear());
        }
    }

    public static void renderModel(IFlexibleBakedModel model, int color) {
        Tessellator tessellator = Tessellator.getInstance();
        WorldRenderer worldrenderer = tessellator.getWorldRenderer();
        worldrenderer.begin(GL11.GL_QUADS, model.getFormat());
        for (BakedQuad bakedquad : model.getGeneralQuads()) {
            LightUtil.renderQuadColor(worldrenderer, bakedquad, color);
        }
        tessellator.draw();
    }

    public static IFlexibleBakedModel loadModel(String resourceName) {
        IFlexibleBakedModel model = loadedModels.get(resourceName);
        if (model != null)
            return model;

        try {
            final TextureMap textures = Minecraft.getMinecraft().getTextureMapBlocks();
            IModel mod = ModelLoaderRegistry.getModel(new ResourceLocation(resourceName));
            model = mod.bake(mod.getDefaultState(), Attributes.DEFAULT_BAKED_FORMAT,
                    new Function<ResourceLocation, TextureAtlasSprite>() {
                        @Nullable
                        @Override
                        public TextureAtlasSprite apply(@Nullable ResourceLocation location) {
                            if (location == null)
                                return null;
                            return textures.getAtlasSprite(location.toString());
                        }
                    });
            return model;
        }
        catch (IOException e) {
            throw new ReportedException(new CrashReport("Error loading custom model " + resourceName, e));
        }
    }
}
