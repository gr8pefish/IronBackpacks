package main.ironbackpacks.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.container.ContainerBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.inventory.InventoryBackpack;
import main.ironbackpacks.items.upgrades.UpgradeTypes;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIBackpack extends GuiContainer {

    //credit where it is due: a lot of this is cpw's code from IronChests
    public enum ResourceList {

        SINGLE(new ResourceLocation(ModInformation.ID, "textures/guis/single_chest.png")),
        DOUBLE(new ResourceLocation(ModInformation.ID, "textures/guis/double_chest.png"));

        public final ResourceLocation location;

        private ResourceList(ResourceLocation loc) {
            this.location = loc;
        }
    }

    public enum GUI {

        SINGLE(200, 168, ResourceList.SINGLE, IronBackpackType.SINGLE),
        DOUBLE(200, 222, ResourceList.DOUBLE, IronBackpackType.DOUBLE);

        private int xSize;
        private int ySize;
        private ResourceList guiResourceList;
        private IronBackpackType mainType;

        private GUI(int xSize, int ySize, ResourceList guiResourceList, IronBackpackType mainType) {
            this.xSize = xSize;
            this.ySize = ySize;
            this.guiResourceList = guiResourceList;
            this.mainType = mainType;
        }

        protected Container makeContainer(EntityPlayer player, InventoryBackpack backpack) {
            return new ContainerBackpack(player, backpack, mainType, xSize, ySize);
        }

        public static GUIBackpack buildGUI(EntityPlayer player, InventoryBackpack backpack, int upgrade1, int upgrade2, int upgrade3) {
            return new GUIBackpack(values()[backpack.getType().ordinal()], player, backpack, upgrade1, upgrade2, upgrade3);
        }
    }

    private GUI type;

//    private UpgradeTypes upgrade1;
//    private UpgradeTypes upgrade2;
//    private UpgradeTypes upgrade3;

    private GUIBackpack(GUI type, EntityPlayer player, InventoryBackpack backpack, int upgrade1, int upgrade2, int upgrade3) {
        super(type.makeContainer(player, backpack));
        this.type = type;
        this.xSize = type.xSize;
        this.ySize = type.ySize;
        this.allowUserInput = false;

        if (upgrade1 != 0){
            UpgradeTypes upgrade = UpgradeTypes.values()[upgrade1];
//            upgrade.doGuiAlteration(); TODO
        }
        if (upgrade2 != 0){
            UpgradeTypes upgrade = UpgradeTypes.values()[upgrade2];
//            this.upgrade2 = upgrade;
        }
        if (upgrade3 != 0){
            UpgradeTypes upgrade = UpgradeTypes.values()[upgrade3];
//            this.upgrade3 = upgrade;
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

        this.mc.getTextureManager().bindTexture(type.guiResourceList.location);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x+12, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        this.fontRendererObj.drawString(StatCollector.translateToLocal("item." + ModInformation.ID + ":" + type.mainType.getName() + ".name"), 20, 6, 4210752); //TODO - dynamic sizing
        this.fontRendererObj.drawString(StatCollector.translateToLocal("player.inventory"), 20, this.ySize - 96 + 2, 4210752);
    }

    public void buttonUpgrade(){
        this.fontRendererObj.drawString(StatCollector.translateToLocal("player.inventory"), 20, this.ySize - 30 + 2, 4210752);
    }
}