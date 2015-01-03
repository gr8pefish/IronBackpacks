package main.ironbackpacks.client.gui;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.container.ContainerBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.inventory.InventoryBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.items.upgrades.UpgradeTypes;
import main.ironbackpacks.network.IronBackpacksMessage;
import main.ironbackpacks.network.PacketHandler;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIBackpack extends GuiContainer {

    //credit where it is due: a lot of this is cpw's code from IronChests
    public enum ResourceList { //TODO - move to constants?

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
    private ContainerBackpack container;

    private ButtonUpgrade backpack_to_inventory_BUTTON;
    private ButtonUpgrade inventory_to_backpack_BUTTON;
    private ButtonUpgrade hotbar_to_backpack_BUTTON;
    private boolean hasAButtonUpgrade;

//    private UpgradeTypes upgrade1;
//    private UpgradeTypes upgrade2;
//    private UpgradeTypes upgrade3;

    private GUIBackpack(GUI type, EntityPlayer player, InventoryBackpack backpack, int upgrade1, int upgrade2, int upgrade3) {
        super(type.makeContainer(player, backpack));
        this.container = (ContainerBackpack) type.makeContainer(player, backpack);
        this.type = type;
        this.xSize = type.xSize;
        this.ySize = type.ySize;
        this.allowUserInput = false;
        this.hasAButtonUpgrade = hasButtonUpgrade(upgrade1, upgrade2, upgrade3);


//        if (upgrade1 != 0){
//            UpgradeMethods.values()[upgrade1].doGuiAlteration(this, this.fontRendererObj, this.xSize, this.ySize);
//        }
//        if (upgrade2 != 0){
//            UpgradeMethods.values()[upgrade1].doGuiAlteration(this, this.fontRendererObj, this.xSize, this.ySize);
//        }
//        if (upgrade3 != 0){
//            UpgradeMethods.values()[upgrade1].doGuiAlteration(this, this.fontRendererObj, this.xSize, this.ySize);
//        }
    }

    @Override
    public void initGui(){
        super.initGui();
        if (this.hasAButtonUpgrade) {

            int xStart = ((width - xSize) / 2) + xSize - 12;
            int yStart = ((height - ySize) / 2) + ySize;

            this.buttonList.add(this.backpack_to_inventory_BUTTON =  new ButtonUpgrade(1, xStart - 19, yStart - 96, 11, 11, ButtonUpgrade.BACKPACK_TO_INVENTORY));
            this.buttonList.add(this.hotbar_to_backpack_BUTTON    =  new ButtonUpgrade(2, xStart - 39, yStart - 96, 11, 11, ButtonUpgrade.HOTBAR_TO_BACKPACK));
            this.buttonList.add(this.inventory_to_backpack_BUTTON =  new ButtonUpgrade(3, xStart - 59, yStart - 96, 11, 11, ButtonUpgrade.INVENTORY_TO_BACKPACK));
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

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == backpack_to_inventory_BUTTON) {
            this.container.backpackToInventory();
            PacketHandler.INSTANCE.sendToServer(new IronBackpacksMessage(IronBackpacksMessage.BACKPACK_TO_INVENTORY));
        } else if (button == inventory_to_backpack_BUTTON) {
            this.container.inventoryToBackpack();
            PacketHandler.INSTANCE.sendToServer(new IronBackpacksMessage(IronBackpacksMessage.INVENTORY_TO_BACKPACK));
        } else if (button == hotbar_to_backpack_BUTTON) {
            this.container.hotbarToBackpack();
            PacketHandler.INSTANCE.sendToServer(new IronBackpacksMessage(IronBackpacksMessage.HOTBAR_TO_BACKPACK));
        }
    }

    private boolean hasButtonUpgrade(int upgrade1, int upgrade2, int upgrade3){
        return (upgrade1 == 1 || upgrade2 == 1 || upgrade3 == 1);
    }
}