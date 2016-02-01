package gr8pefish.ironbackpacks.client.gui.inventory;

import gr8pefish.ironbackpacks.api.client.gui.button.ButtonNames;
import gr8pefish.ironbackpacks.client.gui.buttons.TooltipButton;
import gr8pefish.ironbackpacks.container.backpack.ContainerBackpack;
import gr8pefish.ironbackpacks.container.backpack.InventoryBackpack;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.server.SingleByteMessage;
import gr8pefish.ironbackpacks.registry.GuiButtonRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
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

    private ContainerBackpack container; //the container
    private ItemStack itemStack; //the itemstack backpack
    private ItemBackpack itemBackpack; //the backpack as an items

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

    private EntityPlayer player; //TODO remove

    private GUIBackpack(EntityPlayer player, InventoryBackpack inventoryBackpack) {
        super(new ContainerBackpack(inventoryBackpack, ((ItemBackpack)inventoryBackpack.getBackpackStack().getItem()).getGuiXSize(inventoryBackpack.getBackpackStack()), ((ItemBackpack)inventoryBackpack.getBackpackStack().getItem()).getGuiYSize(inventoryBackpack.getBackpackStack()))); //holy grossness batman

        this.itemStack = inventoryBackpack.getBackpackStack();
        this.itemBackpack = (ItemBackpack)itemStack.getItem();
        this.xSize = itemBackpack.getGuiXSize(itemStack);
        this.ySize = itemBackpack.getGuiYSize(itemStack);
        this.container = new ContainerBackpack(inventoryBackpack, xSize, ySize) ;
        this.allowUserInput = false;
        this.hasAButtonUpgrade = UpgradeMethods.hasButtonUpgrade(IronBackpacksHelper.getUpgradesAppliedFromNBT(itemStack));// IronBackpacksHelper.getUpgradesAppliedFromNBT(itemStack));
        tooltipButtons = new ArrayList<>();
    }

    /**
     * Creates the GUI. Called from GuiHandler.
     * @param player - the player opening the backpack
     * @param backpack - the inventory
     * @return - a GUI
     */
    public static GUIBackpack buildGUI(EntityPlayer player, InventoryBackpack backpack) {
        return new GUIBackpack(player, backpack);
    }



    @Override
    public void initGui(){

        super.initGui();

        buttonList.clear();
        tooltipButtons.clear();

        if (this.hasAButtonUpgrade) { //add all the buttons

            int xStart = ((width - xSize) / 2) + xSize - 12;
            int yStart = ((height - ySize) / 2) + ySize;

            buttonList.add(this.backpack_to_inventory_BUTTON =  new TooltipButton(GuiButtonRegistry.getButton(ButtonNames.BACKPACK_TO_INVENTORY), xStart - 20, yStart - 96));
            buttonList.add(this.hotbar_to_backpack_BUTTON    =  new TooltipButton(GuiButtonRegistry.getButton(ButtonNames.HOTBAR_TO_BACKPACK), xStart - 40, yStart - 96));
            buttonList.add(this.inventory_to_backpack_BUTTON =  new TooltipButton(GuiButtonRegistry.getButton(ButtonNames.INVENTORY_TO_BACKPACK), xStart - 60, yStart - 96));
            buttonList.add(this.condense_backpack_BUTTON     =  new TooltipButton(GuiButtonRegistry.getButton(ButtonNames.SORT_BACKPACK), xStart - 80, yStart - 96));

            tooltipButtons.add(backpack_to_inventory_BUTTON);
            tooltipButtons.add(hotbar_to_backpack_BUTTON);
            tooltipButtons.add(inventory_to_backpack_BUTTON);
            tooltipButtons.add(condense_backpack_BUTTON);
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); //set color to default, just in case

        this.mc.getTextureManager().bindTexture(itemBackpack.getGuiResourceLocation(itemStack));
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x+12, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {

        if (itemStack != null)
            this.fontRendererObj.drawString(StatCollector.translateToLocal(itemStack.getDisplayName()), 20, 6, 4210752); //respects renamed backpacks this way
        else
            this.fontRendererObj.drawString(StatCollector.translateToLocal("misc.ironbackpacks.default.equpped.backpack.name"), 20, 6, 4210752); //default name, should ideally never be used

        this.fontRendererObj.drawString(StatCollector.translateToLocal("container.inventory"), 20, this.ySize - 96 + 2, 4210752);

        int k = (this.width - this.xSize) / 2; //X axis on GUI
        int l = (this.height - this.ySize) / 2; //Y axis on GUI

        //checks to see if the mouse is over a button that has a tooltip, if so it checks the time to see how long the mouse has been hovering and displays the tooltip if said hover time is long enough
        TooltipButton curr = null;
        for (TooltipButton button : tooltipButtons){
            if (button.mouseInButton(mouseX, mouseY)) {
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

}