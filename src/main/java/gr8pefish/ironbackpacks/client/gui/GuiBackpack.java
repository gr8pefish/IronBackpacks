package gr8pefish.ironbackpacks.client.gui;

import com.google.common.base.Preconditions;
import gr8pefish.ironbackpacks.container.ContainerBackpack;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;

import javax.annotation.Nonnull;

/**
 * Class to generate the normal GUI of a backpack.
 *
 * All hail copygirl, code copied nearly verbatim (with permission) from @see <a = https://github.com/copygirl/WearableBackpacks/blob/feature/gui-rewrite/src/main/java/net/mcft/copy/backpacks/client/GuiBackpack.java>here</a>.
 */
public class GuiBackpack extends GuiContainer {

    /** The texture of the container for the backpack. */
    private static final GuiTextureResource CONTAINER_TEX = new GuiTextureResource("backpack_gui", 512, 512);

    /** The container of the backpack. */
    @Nonnull
    private ContainerBackpack container;

    /**
     * Creates the GUI of the backpack.
     *
     * @param containerBackpack - The container to initialize with.
     */
    public GuiBackpack(@Nonnull ContainerBackpack containerBackpack) {
        super(containerBackpack);

        Preconditions.checkNotNull(containerBackpack, "ContainerBackpack cannot be null");

        container = containerBackpack;
        xSize = container.getWidth();
        ySize = container.getHeight();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.renderHoveredToolTip(mouseX, mouseY);
    }

    /** Draw the strings(backpack name, and "Inventory") on the GUI. */
    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(container.getName(), container.getBorderSide() + 1, 6, 0x404040);
        int invTitleX = container.getPlayerInvXOffset() + 1;
        int invTitleY = container.getBorderTop() + container.getContainerInvHeight() + 3;
        fontRenderer.drawString(I18n.format("container.inventory"), invTitleX, invTitleY, 0x404040);
    }

    /** Draw the entire GUI background, slots and everything.
     * This code is all copygirl's, minus one small change I made to allow it to be 7 rows.
     * TODO: Make bottom area sides "full" when >9 columns wide */
    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);

        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;

        // Miiight have gone *a little* overboard with the local variables...
        int b = container.getBorderSide();
        int bTop = container.getBorderTop();
        int bBot = container.getBorderBottom();
        int w = xSize - b * 2;
        int h = container.getContainerInvHeight();
        int pw = container.getPlayerInvWidth();
        int ph = container.getPlayerInvHeight();
        int maxw = container.getMaxColumns() * 18;
        int maxh = container.getMaxRows() * 18;
        int bufi = container.getBufferInventory();

        int x1 = x;
        int x2 = x + b;
        int x3 = x + xSize - b;
        int px = x + container.getPlayerInvXOffset();

        int tx1 = 4;
        int tx2 = tx1 + b + 2;
        int tx3 = tx2 + maxw + 2;
        int ty = 4;
        int tpx = 2 + b + 2 + (maxw - (pw + b * 2)) / 2 + 2;

        CONTAINER_TEX.bind();

        // Top
        CONTAINER_TEX.drawQuad(x1, y, tx1, ty, b, bTop);
        CONTAINER_TEX.drawQuad(x2, y, tx2, ty, w, bTop);
        CONTAINER_TEX.drawQuad(x3, y, tx3, ty, b, bTop);
        y += bTop; ty += bTop + 2;

        // Container background
        CONTAINER_TEX.drawQuad(x1, y, tx1, ty, b, h);
        CONTAINER_TEX.drawQuad(x2, y, tx2, ty, w, h);
        CONTAINER_TEX.drawQuad(x3, y, tx3, ty, b, h);
        // Container slots
        CONTAINER_TEX.drawQuad(x + container.getContainerInvXOffset(), y, tx2, 274, //Changed v to allow for 7 rows, as the texture location changed with my additions.
                container.getContainerInvWidth(), h);
        y += h; ty += maxh + 2;

        // Space between container and player inventory
        if (container.getBackpackSize().getColumns() > 9) {
            int sw = (w - (pw + b * 2)) / 2;
            CONTAINER_TEX.drawQuad(x1, y, tx1 - 2, ty, b, bBot);
            CONTAINER_TEX.drawQuad(x2, y, tx1 + b, ty, sw, bBot);
            CONTAINER_TEX.drawQuad(px - b, y, tpx, ty, pw + b * 2, bufi);
            CONTAINER_TEX.drawQuad(px + pw + b, y, tx3 - sw, ty, sw, bBot);
            CONTAINER_TEX.drawQuad(x3, y, tx3 + 2, ty, b, bBot);
        }
        ty += bufi + 2;
        if (container.getBackpackSize().getColumns() <= 9)
            CONTAINER_TEX.drawQuad(x, y, tpx, ty, pw + b * 2, bufi);
        y += bufi; ty += bufi + 2;

        // Player inventory
        CONTAINER_TEX.drawQuad(px - b, y, tpx, ty, pw + b * 2, ph + bBot);
    }
}