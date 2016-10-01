package gr8pefish.ironbackpacks.client.renderer;

import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.capabilities.IronBackpacksCapabilities;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.client.model.ModelBackpack;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.entity.EntityBackpack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LayerBackpack implements LayerRenderer<AbstractClientPlayer> {

    //see LayerCape

    private final RenderPlayer playerRenderer;
    private final ModelBackpack modelBackpack;

    public LayerBackpack(RenderPlayer playerRendererIn) {
        this.playerRenderer = playerRendererIn;
        this.modelBackpack = null;
    }

    public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {

        if (!entitylivingbaseIn.isInvisible() && !ConfigHandler.disableRendering) {
            ItemStack pack = IronBackpacksCapabilities.getWornBackpack(entitylivingbaseIn);
            if (pack != null) {
                System.out.println("got backpack");
                renderPack(entitylivingbaseIn, new ModelBackpack(), pack);
            }
        }

        if (entitylivingbaseIn.getName().equals("gr8pefish")) { // && entitylivingbaseIn.hasSkin() && !entitylivingbaseIn.isInvisible()) {

            //System.out.println("GOT A GRAPE");
//            this.playerRenderer.bindTexture(entitylivingbaseIn.getLocationSkin()); //unnecessary?

            ModelPlayer mainModel = this.playerRenderer.getMainModel();

            //doesn't work
//            ModelBackpack modelBackpack = new ModelBackpack();
//            modelBackpack.render(entitylivingbaseIn, f1, f2, f3, f4, f5, scale);


//            renderPack(entitylivingbaseIn, mainModel); //this breaks things as-is
//            mainModel.setModelAttributes();
//            .showModel



//            T t = this.field_177186_d;
//            t.func_178686_a(this.renderer.func_177087_b());
//            t.func_78086_a(entitylivingbaseIn, f1, f2, partialTicks);
//            t = getBackpackModel(scale);
//
//            this.renderer.func_110776_a(this.getModelResource());
//
//            GlStateManager.func_179131_c(this.colorR, this.colorG, this.colorB, this.alpha);
//            t.func_78088_a(entitylivingbaseIn, f1, f2, p_177182_5_, p_177182_6_, p_177182_7_, scale);



//            for (int i = 0; i < 2; ++i)
//            {
//                float f = entitylivingbaseIn.prevRotationYaw + (entitylivingbaseIn.rotationYaw - entitylivingbaseIn.prevRotationYaw) * partialTicks - (entitylivingbaseIn.prevRenderYawOffset + (entitylivingbaseIn.renderYawOffset - entitylivingbaseIn.prevRenderYawOffset) * partialTicks);
//                float f1 = entitylivingbaseIn.prevRotationPitch + (entitylivingbaseIn.rotationPitch - entitylivingbaseIn.prevRotationPitch) * partialTicks;
//                GlStateManager.pushMatrix();
//                GlStateManager.rotate(f, 0.0F, 1.0F, 0.0F);
//                GlStateManager.rotate(f1, 1.0F, 0.0F, 0.0F);
//                GlStateManager.translate(0.375F * (float)(i * 2 - 1), 0.0F, 0.0F);
//                GlStateManager.translate(0.0F, -0.375F, 0.0F);
//                GlStateManager.rotate(-f1, 1.0F, 0.0F, 0.0F);
//                GlStateManager.rotate(-f, 0.0F, 1.0F, 0.0F);
//                float f2 = 1.3333334F;
//                GlStateManager.scale(f2, f2, f2);
////                this.playerRenderer.getMainModel().renderDeadmau5Head(0.0625F);
//                GlStateManager.popMatrix();
//            }
        }
    }

    @Override
    public boolean shouldCombineTextures() {
        return false;
    }

    private void renderPack(AbstractClientPlayer player, ModelBackpack modelBackpack, ItemStack pack) {
        if (player == null) return;

        final Minecraft minecraft = Minecraft.getMinecraft();
        final boolean isLocalPlayer = player == minecraft.thePlayer;
        final boolean isFpp = minecraft.gameSettings.thirdPersonView == 0;

        if (isLocalPlayer && isFpp) {
            if (ConfigHandler.disableFPPrendering)
                return; //don't render if in first person view on client and the config option allows it
        }else if (player.isInvisible()){
            return; //don't render when invisible
        }

        final float rotation = interpolateRotation(pack.prevRotationYaw, pack.rotationYaw, f1);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        GlStateManager.rotate(180.0F - rotation, 0.0F, 1.0F, 0.0F); //rotates it to the back of the player
        GlStateManager.rotate(180, 1, 0, 0); //flips it right side up
        GlStateManager.scale(0.8f, 0.7f, 0.8f); //scale slightly smaller
        GlStateManager.translate(0, -1.6, -.25); //move it into the correct location

        if (player.inventory.armorInventory[2] != null) //if has chest armor slot
            GlStateManager.translate(0, 0, -.1); //move it backwards a little

        if (player.isSneaking()){
            GlStateManager.rotate(-25F, 1, 0, 0); //rotate it forward to still be on the player's back
            GlStateManager.translate(0, .2, -.1); //move it down and back a little
        }

        if (isFpp) {
            GlStateManager.translate(0, -.25, -.6); //move up a little up and a little back
        }

        playerRenderer.bindTexture(((IBackpack)pack.getItem()).getModelTexture(pack));
        modelBackpack.render(pack, 0, 0, 0, 0, 0, 1.0f / 8.0f);

        GlStateManager.popMatrix();
    }

    private void renderPlayer(AbstractClientPlayer clientPlayer, ModelPlayer modelPlayer){

//        final EntityPlayer owner = pack.getPlayer();
        final EntityPlayer owner = clientPlayer;
        if (owner == null) return;

        final Minecraft minecraft = Minecraft.getMinecraft();
        final boolean isLocalPlayer = owner == minecraft.thePlayer;
        final boolean isFpp = minecraft.gameSettings.thirdPersonView == 0;

        if (isLocalPlayer && isFpp) {
            if (ConfigHandler.disableFPPrendering)
                return; //don't render if in first person view on client and the config option allows it
        }else if (owner.isInvisible()){
            return; //don't render when invisible
        }

        final float rotation = interpolateRotation(clientPlayer.prevRotationYaw, clientPlayer.rotationYaw, 1);

        GlStateManager.pushMatrix();
        GlStateManager.translate(clientPlayer.posX, clientPlayer.posY, clientPlayer.posZ);

        GlStateManager.rotate(180.0F - rotation, 0.0F, 1.0F, 0.0F); //rotates it to the back of the player
        GlStateManager.rotate(180, 1, 0, 0); //flips it right side up
        GlStateManager.scale(0.8f, 0.7f, 0.8f); //scale slightly smaller
        GlStateManager.translate(0, -1.6, -.25); //move it into the correct location

        if (owner.inventory.armorInventory[2] != null) //if has chest armor slot
            GlStateManager.translate(0, 0, -.1); //move it backwards a little

        if (owner.isSneaking()){
            GlStateManager.rotate(-25F, 1, 0, 0); //rotate it forward to still be on the player's back
            GlStateManager.translate(0, .2, -.1); //move it down and back a little
        }

        if (isFpp) {
            GlStateManager.translate(0, -.25, -.6); //move up a little up and a little back
        }

//        bindEntityTexture(pack);
//        new ModelRenderer(modelPlayer);
//        modelBackpack.render(pack, 0, 0, 0, 0, 0, 1.0f / 8.0f);
//
//
//
//        GlStateManager.popMatrix();
//
//        super.doRender(pack, x, y, z, f, f1);
    }

    private static float interpolateRotation(float prevRotation, float nextRotation, float modifier) {
        float rotation = nextRotation - prevRotation;

        while (rotation < -180.0F)
            rotation += 360.0F;

        while (rotation >= 180.0F) {
            rotation -= 360.0F;
        }

        return prevRotation + modifier * rotation;
    }

    //    @Override
    protected ResourceLocation getEntityTexture(EntityBackpack entity) {
        //safe call to make sure it doesn't crash
        if (entity.getPlayer() != null && entity.getPlayer() instanceof EntityPlayer) {
            ItemStack pack = PlayerWearingBackpackCapabilities.getEquippedBackpack(entity.getPlayer());
            if (pack != null) {
                return ((IBackpack)pack.getItem()).getModelTexture(pack);
            }
        }
        return null;
    }

//    public void renderDeadmau5Head(float p_178727_1_)
//    {
//        ModelPlayer.copyModelAngles(this.playerRenderer.getMainModel().bipedBody, this.playerRenderer.getMainModel());
//        this.bipedDeadmau5Head.rotationPointX = 0.0F;
//        this.bipedDeadmau5Head.rotationPointY = 0.0F;
//        this.bipedDeadmau5Head.render(p_178727_1_);
//    }
}