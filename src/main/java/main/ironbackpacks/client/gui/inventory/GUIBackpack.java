package main.ironbackpacks.client.gui.inventory;

import main.ironbackpacks.ModInformation;
import main.ironbackpacks.client.gui.buttons.ButtonTypes;
import main.ironbackpacks.client.gui.buttons.TooltipButton;
import main.ironbackpacks.container.backpack.ContainerBackpack;
import main.ironbackpacks.container.backpack.InventoryBackpack;
import main.ironbackpacks.items.backpacks.BackpackTypes;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.network.SingleByteMessage;
import main.ironbackpacks.util.ConfigHandler;
import main.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;

/**
 * The main gui that holds the backpack's primary inventory
 */
@SideOnly(Side.CLIENT)
public class GUIBackpack extends GuiContainer {

    private GUI type; //the GUI type (of the enum above)
    private ContainerBackpack container; //the container
    private ItemStack itemStack; //the itemstack backpack
    //the buttons in this GUI
    private TooltipButton backpack_to_inventory_BUTTON;
    private TooltipButton inventory_to_backpack_BUTTON;
    private TooltipButton hotbar_to_backpack_BUTTON;
    private TooltipButton condense_backpack_BUTTON;
    private boolean hasAButtonUpgrade;
    //fields used to show/hide the tooltips
    private ArrayList<TooltipButton> tooltipButtons; //all the buttons that have a tooltip
    private long prevSystemTime;
    private int hoverTime;

    private GUIBackpack(GUI type, EntityPlayer player, InventoryBackpack backpack, int[] upgrades, ItemStack itemStack) {
        super(type.makeContainer(player, backpack));
        this.container = (ContainerBackpack) type.makeContainer(player, backpack);
        this.type = type;
        this.xSize = type.xSize;
        this.ySize = type.ySize;
        this.allowUserInput = false;
        this.hasAButtonUpgrade = UpgradeMethods.hasButtonUpgrade(upgrades);
        this.itemStack = itemStack;
        tooltipButtons = new ArrayList<TooltipButton>();
    }

    @Override
    @SuppressWarnings("unchecked")
    public void initGui() {

        super.initGui();

        buttonList.clear();
        tooltipButtons.clear();

        if (this.hasAButtonUpgrade) { //add all the buttons

            int xStart = ((width - xSize) / 2) + xSize - 12;
            int yStart = ((height - ySize) / 2) + ySize;

            buttonList.add(this.backpack_to_inventory_BUTTON = new TooltipButton(ButtonTypes.BACKPACK_TO_INVENTORY, xStart - 20, yStart - 96));
            buttonList.add(this.hotbar_to_backpack_BUTTON = new TooltipButton(ButtonTypes.HOTBAR_TO_BACKPACK, xStart - 40, yStart - 96));
            buttonList.add(this.inventory_to_backpack_BUTTON = new TooltipButton(ButtonTypes.INVENTORY_TO_BACKPACK, xStart - 60, yStart - 96));
            buttonList.add(this.condense_backpack_BUTTON = new TooltipButton(ButtonTypes.SORT_BACKPACK, xStart - 80, yStart - 96));

            tooltipButtons.add(backpack_to_inventory_BUTTON);
            tooltipButtons.add(hotbar_to_backpack_BUTTON);
            tooltipButtons.add(inventory_to_backpack_BUTTON);
            tooltipButtons.add(condense_backpack_BUTTON);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); //set color to default, just in case

        this.mc.getTextureManager().bindTexture(type.guiResourceList.location);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x + 12, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        this.fontRendererObj.drawString(StatCollector.translateToLocal(itemStack.getDisplayName()), 20, 6, 4210752); //respects renamed backpacks this way
        this.fontRendererObj.drawString(StatCollector.translateToLocal("player.inventory"), 20, this.ySize - 96 + 2, 4210752);

        int k = (this.width - this.xSize) / 2; //X axis on GUI
        int l = (this.height - this.ySize) / 2; //Y axis on GUI

        //checks to see if the mouse is over a button that has a tooltip, if so it checks the time to see how long the mouse has been hovering and displays the tooltip if said hover time is long enough
        TooltipButton curr = null;
        for (TooltipButton button : tooltipButtons) {
            if (button.mouseInButton(mouseX, mouseY)) {
                curr = button;
                break;
            }
        }
        if (curr != null) {
            long systemTime = System.currentTimeMillis();
            if (prevSystemTime != 0) {
                hoverTime += systemTime - prevSystemTime;
            }
            prevSystemTime = systemTime;
            if (hoverTime > curr.getHoverTime()) {
                this.drawHoveringText(curr.getTooltip(), mouseX - k, mouseY - l, fontRendererObj);
            }
        } else {
            hoverTime = 0;
            prevSystemTime = 0;
        }

    }

    @Override
    protected void actionPerformed(GuiButton button) { //called whenever a button is pressed, sorts on both sides (client and server)
        if (button == backpack_to_inventory_BUTTON) {
            this.container.backpackToInventory();
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.BACKPACK_TO_INVENTORY));
        } else if (button == inventory_to_backpack_BUTTON) {
            this.container.inventoryToBackpack();
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.INVENTORY_TO_BACKPACK));
        } else if (button == hotbar_to_backpack_BUTTON) {
            this.container.hotbarToBackpack();
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.HOTBAR_TO_BACKPACK));
        } else if (button == condense_backpack_BUTTON) {
            this.container.sort();
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.SORT_BACKPACK));
        }
    }

    /**
     * A variable texture of the gui depending on the tier of the backpack being opened. Dependent on config options.
     * Note: Credit goes to cpw here, a lot of this is based on his IronChests' code.
     */
    public enum ResourceList {

        BASIC(new ResourceLocation(ModInformation.ID,
                "textures/guis/backpacks/" + String.valueOf(ConfigHandler.enumBasicBackpack.sizeY.getValue()) + "RowsOf" + String.valueOf(ConfigHandler.enumBasicBackpack.sizeX.getValue()) + ".png")),
        IRON(new ResourceLocation(ModInformation.ID,
                "textures/guis/backpacks/" + String.valueOf(ConfigHandler.enumIronBackpack.sizeY.getValue()) + "RowsOf" + String.valueOf(ConfigHandler.enumIronBackpack.sizeX.getValue()) + ".png")),
        GOLD(new ResourceLocation(ModInformation.ID,
                "textures/guis/backpacks/" + String.valueOf(ConfigHandler.enumGoldBackpack.sizeY.getValue()) + "RowsOf" + String.valueOf(ConfigHandler.enumGoldBackpack.sizeX.getValue()) + ".png")),
        DIAMOND(new ResourceLocation(ModInformation.ID,
                "textures/guis/backpacks/" + String.valueOf(ConfigHandler.enumDiamondBackpack.sizeY.getValue()) + "RowsOf" + String.valueOf(ConfigHandler.enumDiamondBackpack.sizeX.getValue()) + ".png"));

        public final ResourceLocation location; //the texture's file's path

        ResourceList(ResourceLocation loc) {
            this.location = loc;
        }
    }

    /**
     * The GUI's details, once again based on the tier of backpack being opened.
     */
    public enum GUI {

        BASIC(ConfigHandler.enumBasicBackpack.sizeX.getValue() == 9 ? 200 : 236,
                114 + (18 * ConfigHandler.enumBasicBackpack.sizeY.getValue()),
                ResourceList.BASIC, BackpackTypes.BASIC),
        IRON(ConfigHandler.enumIronBackpack.sizeX.getValue() == 9 ? 200 : 236,
                114 + (18 * ConfigHandler.enumIronBackpack.sizeY.getValue()),
                ResourceList.IRON, BackpackTypes.IRON),
        GOLD(ConfigHandler.enumGoldBackpack.sizeX.getValue() == 9 ? 200 : 236,
                114 + (18 * ConfigHandler.enumGoldBackpack.sizeY.getValue()),
                ResourceList.GOLD, BackpackTypes.GOLD),
        DIAMOND(ConfigHandler.enumDiamondBackpack.sizeX.getValue() == 9 ? 200 : 236,
                114 + (18 * ConfigHandler.enumDiamondBackpack.sizeY.getValue()),
                ResourceList.DIAMOND, BackpackTypes.DIAMOND);

        private int xSize; //width
        private int ySize; //height
        private ResourceList guiResourceList; //texture to bind
        private BackpackTypes mainType; //tier of backpack

        GUI(int xSize, int ySize, ResourceList guiResourceList, BackpackTypes mainType) {
            this.xSize = xSize;
            this.ySize = ySize;
            this.guiResourceList = guiResourceList;
            this.mainType = mainType;
        }

        /**
         * Creates the GUI. Called from GuiHandler.
         *
         * @param player    - the player opening the backpack
         * @param backpack  - the inventory
         * @param upgrades  - the upgrades on the backpack
         * @param itemStack - the backpack
         * @return - a GUI
         */
        public static GUIBackpack buildGUI(EntityPlayer player, InventoryBackpack backpack, int[] upgrades, ItemStack itemStack) {
            return new GUIBackpack(values()[backpack.getType().ordinal()], player, backpack, upgrades, itemStack);
        }

        /**
         * Creates a container instance for the GUI to use.
         *
         * @param player   - the player opening the backpack
         * @param backpack - the inventory
         * @return - a containerBackpack
         */
        private Container makeContainer(EntityPlayer player, InventoryBackpack backpack) {
            return new ContainerBackpack(player, backpack, mainType, xSize, ySize);
        }
    }

}