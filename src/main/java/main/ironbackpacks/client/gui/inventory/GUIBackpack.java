package main.ironbackpacks.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.client.gui.buttons.BasicTooltipButton;
import main.ironbackpacks.client.gui.buttons.ButtonUpgrade;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.backpacks.IronBackpackType;
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

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public class GUIBackpack extends GuiContainer {

    //credit where it is due: a lot of this is based on cpw's code from IronChests
    public enum ResourceList {

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

        //called from GuiHandler
        public static GUIBackpack buildGUI(EntityPlayer player, InventoryBackpack backpack, int[] upgrades) {
            return new GUIBackpack(values()[backpack.getType().ordinal()], player, backpack, upgrades);
        }
    }

    private GUI type;
    private ContainerBackpack container;
    private EntityPlayer player;

    private BasicTooltipButton backpack_to_inventory_BUTTON;
    private BasicTooltipButton inventory_to_backpack_BUTTON;
    private BasicTooltipButton hotbar_to_backpack_BUTTON;
    private BasicTooltipButton condense_backpack_BUTTON;
    private boolean hasAButtonUpgrade;

    private ArrayList<BasicTooltipButton> tooltipButtons;
    private long prevSystemTime;
    private int hoverTime;

    private GUIBackpack(GUI type, EntityPlayer player, InventoryBackpack backpack, int[] upgrades) {
        super(type.makeContainer(player, backpack));
        this.container = (ContainerBackpack) type.makeContainer(player, backpack);
        this.type = type;
        this.player = player;
        this.xSize = type.xSize;
        this.ySize = type.ySize;
        this.allowUserInput = false;
        this.hasAButtonUpgrade = UpgradeMethods.hasButtonUpgrade(upgrades);
        tooltipButtons = new ArrayList<BasicTooltipButton>();
    }


    @Override
    public void initGui(){

        super.initGui();

        if (this.hasAButtonUpgrade) { //add all the buttons

            int xStart = ((width - xSize) / 2) + xSize - 12;
            int yStart = ((height - ySize) / 2) + ySize;

            this.buttonList.add(this.backpack_to_inventory_BUTTON =  new BasicTooltipButton(BasicTooltipButton.BACKPACK_TO_INVENTORY, xStart - 20, yStart - 96, 11, 11, BasicTooltipButton.BACKPACK_TO_INVENTORY, true, "",
                    "Moves items from the", "backpack to your inventory."));
            this.buttonList.add(this.hotbar_to_backpack_BUTTON    =  new BasicTooltipButton(BasicTooltipButton.HOTBAR_TO_BACKPACK, xStart - 40, yStart - 96, 11, 11, BasicTooltipButton.HOTBAR_TO_BACKPACK, true, "",
                    "Moves items from your", "hotbar to the backpack."));
            this.buttonList.add(this.inventory_to_backpack_BUTTON =  new BasicTooltipButton(BasicTooltipButton.INVENTORY_TO_BACKPACK, xStart - 60, yStart - 96, 11, 11, BasicTooltipButton.INVENTORY_TO_BACKPACK, true, "",
                    "Moves items from your", "inventory to the backpack"));
            this.buttonList.add(this.condense_backpack_BUTTON     =  new BasicTooltipButton(BasicTooltipButton.SORT_BACKPACK, xStart - 80, yStart - 96, 11, 11, BasicTooltipButton.SORT_BACKPACK, true, "",
                    "Sorts and condenses the","items in the backpack", "(by localized, alphabetical name)"));
            tooltipButtons.add(backpack_to_inventory_BUTTON);
            tooltipButtons.add(hotbar_to_backpack_BUTTON);
            tooltipButtons.add(inventory_to_backpack_BUTTON);
            tooltipButtons.add(condense_backpack_BUTTON);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); //set color to default, just in case

        this.mc.getTextureManager().bindTexture(type.guiResourceList.location);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x+12, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        ItemStack itemStack = IronBackpacksHelper.getBackpack(this.player);
        this.fontRendererObj.drawString(StatCollector.translateToLocal(itemStack.getDisplayName()), 20, 6, 4210752); //respects renamed backpacks this way
        this.fontRendererObj.drawString(StatCollector.translateToLocal("player.inventory"), 20, this.ySize - 96 + 2, 4210752);

        int k = (this.width - this.xSize) / 2; //X axis on GUI
        int l = (this.height - this.ySize) / 2; //Y axis on GUI

        BasicTooltipButton curr = null;
        for (BasicTooltipButton button : tooltipButtons){
            if (button.mouseInButton(mouseX, mouseY)) { //TODO: add timer check
                curr = button;
                break;
            }
        }
        if (curr != null){
            long systemTime = System.currentTimeMillis();
            if(prevSystemTime != 0) {
                hoverTime += systemTime - prevSystemTime;
            }
            prevSystemTime = systemTime;
            if(hoverTime > curr.getHoverTime()) {
                this.drawHoveringText(curr.getTooltip(), (int) mouseX - k, (int) mouseY - l, fontRendererObj);
            }
        }else{
            hoverTime = 0;
            prevSystemTime = 0;
        }

    }

    @Override
    protected void actionPerformed(GuiButton button) { //called whenever a button is pressed
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
            this.container.sort();
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(ButtonUpgradeMessage.CONDENSE_BACKPACK));
        }
    }

}