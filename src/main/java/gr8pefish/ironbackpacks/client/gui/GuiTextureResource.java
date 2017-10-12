package gr8pefish.ironbackpacks.client.gui;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.IronBackpacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;

/**
 * Class used as a helper to facilitate building the backpacks' GUIs
 * Credit goes to copygirl for this code, copied with permission.
 */
@SideOnly(Side.CLIENT)
public class GuiTextureResource extends ResourceLocation {

    @Nonnegative
    public final int defaultWidth;
    @Nonnegative
    public final int defaultHeight;

    public GuiTextureResource(@Nonnull String location, @Nonnegative int defaultWidth, @Nonnegative int defaultHeight) {
        super(IronBackpacks.MODID, "textures/gui/" + location + ".png");

        Preconditions.checkNotNull(location, "Location cannot be null");
        Preconditions.checkArgument(defaultWidth >= 0, "defaultWidth cannot be negative");
        Preconditions.checkArgument(defaultHeight >= 0, "defaultHeight cannot be negative");

        this.defaultWidth = defaultWidth;
        this.defaultHeight = defaultHeight;
    }

    public void bind() {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this);
    }

    /**
     * Draws part of the texture to the screen.
     */
    public void drawQuad(int x, int y, int u, int v, int w, int h, float zLevel) {
        float scaleX = 1.0F / defaultWidth;
        float scaleY = 1.0F / defaultHeight;
        Tessellator tess = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tess.getBuffer();
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferBuilder.pos(x + 0, y + h, zLevel).tex((u + 0) * scaleX, (v + h) * scaleY).endVertex();
        bufferBuilder.pos(x + w, y + h, zLevel).tex((u + w) * scaleX, (v + h) * scaleY).endVertex();
        bufferBuilder.pos(x + w, y + 0, zLevel).tex((u + w) * scaleX, (v + 0) * scaleY).endVertex();
        bufferBuilder.pos(x + 0, y + 0, zLevel).tex((u + 0) * scaleX, (v + 0) * scaleY).endVertex();
        tess.draw();
    }

    /**
     * Draws part of the texture to the screen.
     */
    public void drawQuad(int x, int y, int u, int v, int w, int h) {
        drawQuad(x, y, u, v, w, h, 0);
    }

}

