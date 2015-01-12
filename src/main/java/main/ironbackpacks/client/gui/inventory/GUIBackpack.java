package main.ironbackpacks.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.client.gui.buttons.ButtonUpgrade;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.network.ButtonUpgradeMessage;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.opengl.GL11;

@SideOnly(Side.CLIENT)
public class GUIBackpack extends GuiContainer {

    //credit where it is due: a lot of this is based on cpw's code from IronChests
    public enum ResourceList { //TODO - move to constants?

        BASIC(new ResourceLocation(ModInformation.ID,
                "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumBasicBackpack.sizeX.getValue())+".png")),
        IRON(new ResourceLocation(ModInformation.ID,
                "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumIronBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumIronBackpack.sizeX.getValue())+".png")),
        GOLD(new ResourceLocation(ModInformation.ID,
                "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumGoldBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumGoldBackpack.sizeX.getValue())+".png")),
        DIAMOND(new ResourceLocation(ModInformation.ID,
                "textures/guis/backpacks/"+String.valueOf(ConfigHandler.enumDiamondBackpack.sizeY.getValue())+"RowsOf"+String.valueOf(ConfigHandler.enumDiamondBackpack.sizeX.getValue())+".png"));

        public final ResourceLocation location;

        private ResourceList(ResourceLocation loc) {
            this.location = loc;
        }
    }

    public enum GUI {

        BASIC(ConfigHandler.enumBasicBackpack.sizeX.getValue() == 9 ? 200: 236,
                114 + (18 * ConfigHandler.enumBasicBackpack.sizeY.getValue()),
                ResourceList.BASIC, IronBackpackType.BASIC),
        IRON(ConfigHandler.enumIronBackpack.sizeX.getValue() == 9 ? 200: 236,
                114 + (18 * ConfigHandler.enumIronBackpack.sizeY.getValue()),
                ResourceList.IRON, IronBackpackType.IRON),
        GOLD(ConfigHandler.enumGoldBackpack.sizeX.getValue() == 9 ? 200: 236,
                114 + (18 * ConfigHandler.enumGoldBackpack.sizeY.getValue()),
                ResourceList.GOLD, IronBackpackType.GOLD),
        DIAMOND(ConfigHandler.enumDiamondBackpack.sizeX.getValue() == 9 ? 200: 236,
                114 + (18 * ConfigHandler.enumDiamondBackpack.sizeY.getValue()),
                ResourceList.DIAMOND, IronBackpackType.DIAMOND);

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

        public static GUIBackpack buildGUI(EntityPlayer player, InventoryBackpack backpack, int[] upgrades) {
            return new GUIBackpack(values()[backpack.getType().ordinal()], player, backpack, upgrades);
        }
    }

    private GUI type;
    private ContainerBackpack container;
    private EntityPlayer player;

    private ButtonUpgrade backpack_to_inventory_BUTTON;
    private ButtonUpgrade inventory_to_backpack_BUTTON;
    private ButtonUpgrade hotbar_to_backpack_BUTTON;
    private ButtonUpgrade condense_backpack_BUTTON;
    private boolean hasAButtonUpgrade;

//    private int[] upgrades;

    private GUIBackpack(GUI type, EntityPlayer player, InventoryBackpack backpack, int[] upgrades) {
        super(type.makeContainer(player, backpack));
        this.container = (ContainerBackpack) type.makeContainer(player, backpack);
        this.type = type;
        this.player = player;
        this.xSize = type.xSize;
        this.ySize = type.ySize;
        this.allowUserInput = false;
        this.hasAButtonUpgrade = UpgradeMethods.hasButtonUpgrade(upgrades);

//        for (int item: upgrades){
//            UpgradeMethods.values()[item].doGuiAlteration(this, this.fontRendererObj, this.xSize, this.ySize);
//        }

    }

    @Override
    public void initGui(){
        super.initGui();
        if (this.hasAButtonUpgrade) {

            int xStart = ((width - xSize) / 2) + xSize - 12;
            int yStart = ((height - ySize) / 2) + ySize;

            this.buttonList.add(this.backpack_to_inventory_BUTTON =  new ButtonUpgrade(1, xStart - 20, yStart - 96, 11, 11, ButtonUpgrade.BACKPACK_TO_INVENTORY));
            this.buttonList.add(this.hotbar_to_backpack_BUTTON    =  new ButtonUpgrade(2, xStart - 40, yStart - 96, 11, 11, ButtonUpgrade.HOTBAR_TO_BACKPACK));
            this.buttonList.add(this.inventory_to_backpack_BUTTON =  new ButtonUpgrade(3, xStart - 60, yStart - 96, 11, 11, ButtonUpgrade.INVENTORY_TO_BACKPACK));
            this.buttonList.add(this.condense_backpack_BUTTON     =  new ButtonUpgrade(4, xStart - 80, yStart - 96, 11, 11, ButtonUpgrade.CONDENSE_BACKPACK));
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
        ItemStack itemStack = IronBackpacksHelper.getBackpack(this.player);
        this.fontRendererObj.drawString(StatCollector.translateToLocal(itemStack.getDisplayName()), 20, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("player.inventory"), 20, this.ySize - 96 + 2, 4210752);
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == backpack_to_inventory_BUTTON) {
            this.container.backpackToInventory();
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(ButtonUpgradeMessage.BACKPACK_TO_INVENTORY));
        } else if (button == inventory_to_backpack_BUTTON) {
            this.container.inventoryToBackpack();
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(ButtonUpgradeMessage.INVENTORY_TO_BACKPACK));
        } else if (button == hotbar_to_backpack_BUTTON) {
            this.container.hotbarToBackpack();
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(ButtonUpgradeMessage.HOTBAR_TO_BACKPACK));
        } else if (button == condense_backpack_BUTTON) {
            System.out.println("CLIENT SIDE");
            this.container.condenseBackpack(this.player);
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(ButtonUpgradeMessage.CONDENSE_BACKPACK));
        }
    }

}