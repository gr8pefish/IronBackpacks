package gr8pefish.ironbackpacks.client.renderer;

import gr8pefish.ironbackpacks.api.item.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.client.model.ModelBackpack;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.entity.EntityBackpack;
import gr8pefish.ironbackpacks.entity.extendedProperties.PlayerBackpackProperties;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

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
            if (ConfigHandler.disableFPPrendering)
                return; //don't render if in first person view on client and the config option allows it
        }else if (owner.isInvisible()){
            return; //don't render when invisible
        }

        final float rotation = interpolateRotation(pack.prevRotationYaw, pack.rotationYaw, f1);

        GlStateManager.pushMatrix();
        GlStateManager.translate(x, y, z);

        GlStateManager.rotate(180.0F - rotation, 0.0F, 1.0F, 0.0F); //rotates it to the back of the player
        GlStateManager.rotate(180, 1, 0, 0); //flips it right side up
        GlStateManager.scale(0.8f, 0.7f, 0.8f); //scale slightly smaller
        GlStateManager.translate(0, -1.6, -.25); //move it into the correct location

        if (owner.inventory.armorInventory[2] != null) //if has chest armor slot
            GlStateManager.translate(0, 0, -.1); //move it backwards a little

        if (owner.isSneaking()){
            GlStateManager.rotate(-25F, 1, 0, 0); //rotate it forward to still be on the player's back
            GlStateManager.translate(0, .2, -.1); //move it back and down a little
        }

        bindEntityTexture(pack);
        modelBackpack.render(pack, 0, 0, 0, 0, 0, 1.0f / 8.0f);

        GlStateManager.popMatrix();

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
        //safe call to make sure it doesn't crash
        if (entity.getPlayer() != null && entity.getPlayer() instanceof EntityPlayer) {
            ItemStack pack = PlayerBackpackProperties.getEquippedBackpack(entity.getPlayer());
            if (pack != null) {
                return ((IBackpack)pack.getItem()).getModelTexture(pack);
            }
        }
        return null;
    }
}
