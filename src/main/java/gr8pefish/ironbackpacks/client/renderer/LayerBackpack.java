package gr8pefish.ironbackpacks.client.renderer;

import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.client.model.ModelBackpack;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerArmorBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;

@SideOnly(Side.CLIENT)
public class LayerBackpack implements LayerRenderer<AbstractClientPlayer> {

    /** Instance of the player renderer. */
    private final RenderPlayer playerRenderer;
    /** The model used by the backpack. */
    private final ModelBackpack modelBackpack = new ModelBackpack();

    public LayerBackpack(RenderPlayer playerRendererIn) {
        this.playerRenderer = playerRendererIn;
    }

    public void doRenderLayer(@Nonnull AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        if (!entitylivingbaseIn.isInvisible() && !ConfigHandler.disableRendering) { //if not invisible and should render

            ItemStack pack = IronBackpacksCapabilities.getWornBackpack(entitylivingbaseIn); //get the equipped backpack
            if (pack != null) { //if there is one

                final Minecraft minecraft = Minecraft.getMinecraft();
                final boolean isLocalPlayer = entitylivingbaseIn == minecraft.player;
                final boolean isFpp = minecraft.gameSettings.thirdPersonView == 0;

                if (isLocalPlayer && isFpp) {
                    if (ConfigHandler.disableFPPrendering) {
                        return; //don't render if local player in first person perspective
                    }
                }

                //bind texture of the current backpack
                this.playerRenderer.bindTexture(((IBackpack)pack.getItem()).getModelTexture(pack));

                //push matrix
                GlStateManager.pushMatrix();

                //set rotation angles of the backpack and render it
                this.modelBackpack.setRotationAngles(limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale, entitylivingbaseIn);
                this.modelBackpack.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);

                //special glint for me xD
                if (entitylivingbaseIn.getName().equals("gr8pefish")) {
                    LayerArmorBase.renderEnchantedGlint(this.playerRenderer, entitylivingbaseIn, this.modelBackpack, limbSwing, limbSwingAmount, partialTicks, ageInTicks, netHeadYaw, headPitch, scale);
                }

                //pop matrix
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

}