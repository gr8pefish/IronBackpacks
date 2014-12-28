package main.ironbackpacks.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.container.ContainerBackpack;
import main.ironbackpacks.items.ItemRegistry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class BackpackGui extends GuiContainer {

    //256x256

    private ContainerBackpack container;
    private EntityPlayer player;

    //x and y sizes determined by texture size

    private InventoryPlayer inventoryPlayer;

    public BackpackGui(ContainerBackpack container) {
        super(container);
        this.player = container.getPlayer();
        this.container = container;
//        this.xSize = 160;
//        this.ySize = 160;
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal(ItemRegistry.basicBackpack.getUnlocalizedName() + ".name"), 28, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("backpack.inventory"), 8, this.ySize - 96 + 2, 4210752);

//        buttonList.add()
    }

//    @Override
//    protected void actionPerformed(GuiButton button){
//        if (button == distributeItems){
//
//        }else if (button == returnItems){
//            for (itemstack : ItemRegistry.clipboard.) {
//                this.inventoryPlayer.addItemStackToInventory(itemstack);
//            }
//        }
//    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.renderEngine.bindTexture(Textures.Gui.BASIC_BACKPACK);

        int j = (this.width - this.xSize) / 2;
        int k = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(j, k, 0, 0, this.xSize, this.ySize);
    }
}
