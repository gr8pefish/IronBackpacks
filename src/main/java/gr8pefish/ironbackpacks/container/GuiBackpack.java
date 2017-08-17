package gr8pefish.ironbackpacks.container;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSize;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandlerModifiable;

/** All hail copygirl, code essentially copied verbatim from @see <a = https://github.com/copygirl/WearableBackpacks/blob/feature/gui-rewrite/src/main/java/net/mcft/copy/backpacks/client/GuiBackpack.java>here</a> with permission. */
public class GuiBackpack extends GuiContainer {

    private static final GuiTextureResource CONTAINER_TEX = new GuiTextureResource("backpack", 512, 512);

    private static final ResourceLocation texture = new ResourceLocation(IronBackpacks.MODID, "textures/gui/backpacks/2RowsOf9.png"); //ToDo: Hardcoded
    public static String name;

    private BackpackContainer container;

    public GuiBackpack(InventoryPlayer playerInv, EnumHand hand, IItemHandlerModifiable inventoryBackpack, BackpackSize backpackSize) {
        super(new BackpackContainer(playerInv, hand, inventoryBackpack, backpackSize));
        container = new BackpackContainer(playerInv, hand, inventoryBackpack, backpackSize); //TODO: 2 calls? not ideal, pass in container as param to constructor
        name = playerInv.getCurrentItem().getDisplayName(); //TODO; Will break
        xSize = container.getWidth();
        ySize = container.getHeight();
    }

//    @Override
//    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
//        this.fontRenderer.drawString(name, xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
//        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 - 14, 4210752);
//    }


    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        fontRenderer.drawString(name, container.getBorderSide() + 1, 6, 0x404040);
        int invTitleX = container.getPlayerInvXOffset() + 1;
        int invTitleY = container.getBorderTop() + container.getContainerInvHeight() + 3;
        fontRenderer.drawString(I18n.format("container.inventory"), invTitleX, invTitleY, 0x404040);
    }

//    @Override
//    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
//        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
//        mc.getTextureManager().bindTexture(texture);
//        int k = (width - xSize) / 2;
//        int l = (height - ySize) / 2;
//        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
//    }

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
        CONTAINER_TEX.drawQuad(x + container.getContainerInvXOffset(), y, tx2, 256,
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