package gr8pefish.ironbackpacks.container;

import gr8pefish.ironbackpacks.IronBackpacks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

//*WIP* Class for normal GUI of backpack inventories (lots of hardcoding)
public class GuiBackpack extends GuiContainer {

    private static final ResourceLocation texture = new ResourceLocation(IronBackpacks.MODID, "textures/guis/backpacks/2RowsOf9.png"); //ToDo: Hardcoded
    public static String name;

    public GuiBackpack(InventoryPlayer playerInv, EnumHand hand, IItemHandlerModifiable inventoryBackpack) {
        super(new BackpackContainer(playerInv, hand, inventoryBackpack));
        name = playerInv.getCurrentItem().getDisplayName(); //TODO; Will break
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        this.fontRenderer.drawString(name, xSize / 2 - this.fontRenderer.getStringWidth(name) / 2, 6, 4210752);
        this.fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 - 14, 4210752);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        mc.getTextureManager().bindTexture(texture);
        int k = (width - xSize) / 2;
        int l = (height - ySize) / 2;
        drawTexturedModalRect(k, l, 0, 0, xSize, ySize);
    }

}