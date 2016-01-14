package main.ironbackpacks.client.renderer;

import main.ironbackpacks.client.model.ModelBackpack;
import main.ironbackpacks.entity.EntityBackpack;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class RenderEntityBackpack extends Render<EntityBackpack> {

    private ModelBackpack modelBackpack;

    public RenderEntityBackpack(RenderManager renderManager){
        super(renderManager);
        modelBackpack = new ModelBackpack();
    }

    @Override
    public void doRender(EntityBackpack pack, double x, double y, double z, float f, float f1) {

        final EntityPlayer owner = pack.getPlayer();
        if (owner == null) return;

        final Minecraft minecraft = Minecraft.getMinecraft();
        final boolean isLocalPlayer = owner == minecraft.thePlayer;
        final boolean isFpp = minecraft.gameSettings.thirdPersonView == 0;

        if (isLocalPlayer && isFpp) {
            return; //don't render if in first person view on client
        }else if (owner.isInvisible()){
            return; //don't render when invisible
        }

        final float rotation = interpolateRotation(pack.prevRotationYaw, pack.rotationYaw, f1);

        GL11.glPushMatrix();
        GL11.glTranslated(x, y, z);

        GL11.glRotatef(180.0F - rotation, 0.0F, 1.0F, 0.0F); //rotates it to the back of the player
        GL11.glRotatef(180, 1, 0, 0); //flips it right side up
        GL11.glScalef(0.8f, 0.7f, 0.8f); //scale slightly smaller
        GL11.glTranslated(0, -1.6, -.25); //move it into the correct location

        if (owner.inventory.armorInventory[2] != null) //if has chest armor slot
            GL11.glTranslated(0, 0, -.1); //move it backwards a little

        if (owner.isSneaking()){
            GL11.glRotatef(-25F, 1, 0, 0); //rotate it forward to still be on the player's back
            GL11.glTranslated(0, .2, -.1); //move it back and down a little
        }

        bindEntityTexture(pack);
        modelBackpack.render(pack, 0, 0, 0, 0, 0, 1.0f / 8.0f);

        GL11.glPopMatrix();

        super.doRender(pack, x, y, z, f, f1);
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

    @Override
    protected ResourceLocation getEntityTexture(EntityBackpack entity) {
        return IronBackpacksConstants.Resources.MODEL_TEXTURES[(entity).getTextureId()];
    }
}
