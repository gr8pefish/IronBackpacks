package gr8pefish.ironbackpacks.client.gui.inventory;

import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.api.client.gui.button.ButtonNames;
import gr8pefish.ironbackpacks.client.gui.buttons.TooltipButton;
import gr8pefish.ironbackpacks.config.ConfigHandler;
import gr8pefish.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import gr8pefish.ironbackpacks.container.alternateGui.InventoryAlternateGui;
import gr8pefish.ironbackpacks.container.slot.GhostSlot;
import gr8pefish.ironbackpacks.items.upgrades.UpgradeMethods;
import gr8pefish.ironbackpacks.network.NetworkingHandler;
import gr8pefish.ironbackpacks.network.server.AdvFilterTypesMessage;
import gr8pefish.ironbackpacks.network.server.RenameMessage;
import gr8pefish.ironbackpacks.network.server.SingleByteMessage;
import gr8pefish.ironbackpacks.registry.GuiButtonRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.TextUtils;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Called when the backpack is shift-right clicked to open the alternate gui.
 */
@SideOnly(Side.CLIENT)
public class GUIBackpackAlternate extends GuiContainer {

    /**
     * The file location of the textures.
     * Note: the two lists in this enum are due to the fact that the renaming upgrade is a config option and can shift the location of the gui.
     */
    public enum ResourceList {
        ZERO(new ResourceLocation(Constants.MODID, "textures/guis/alternateGui/ZERO_alternateGui.png")),
        ONE(new ResourceLocation(Constants.MODID, "textures/guis/alternateGui/ONE_alternateGui.png")),
        TWO(new ResourceLocation(Constants.MODID, "textures/guis/alternateGui/TWO_alternateGui.png")),
        THREE(new ResourceLocation(Constants.MODID, "textures/guis/alternateGui/THREE_alternateGui.png")),

        RENAMING_ONE(new ResourceLocation(Constants.MODID, "textures/guis/alternateGui/RENAMING_ONE_alternateGui.png")),
        RENAMING_TWO(new ResourceLocation(Constants.MODID, "textures/guis/alternateGui/RENAMING_TWO_alternateGui.png")),
        RENAMING_THREE(new ResourceLocation(Constants.MODID, "textures/guis/alternateGui/RENAMING_THREE_alternateGui.png"));

        public final ResourceLocation location; //file's texture path

        private ResourceList(ResourceLocation loc) {
            this.location = loc;
        }

    }

    /**
     * Once again, the renaming upgrade can shift things around, so it is accounted for here.
     */
    public enum GUI {

        ZERO( 200, 114 + 18, ResourceList.ZERO),
        ONE(  200, 114 + (18*2), ResourceList.ONE),
        TWO(  200, 114 + (18*4), ResourceList.TWO),
        THREE(200, 114 + (18*6), ResourceList.THREE),

        RENAMING_ZERO( 200, 114 + 18, ResourceList.ZERO),
        RENAMING_ONE(  200, 114 + (18*3), ResourceList.RENAMING_ONE),
        RENAMING_TWO(  200, 114 + (18*5), ResourceList.RENAMING_TWO),
        RENAMING_THREE(200, 114 + (18*7), ResourceList.RENAMING_THREE);

        private int xSize; //width
        private int ySize; //height
        private ResourceList guiResourceList; //texture

        private GUI(int xSize, int ySize, ResourceList guiResourceList) {
            this.xSize = xSize;
            this.ySize = ySize;
            this.guiResourceList = guiResourceList;
        }

        /**
         * Called from GuiHandler to create the GUI.
         * @param inv - the backpack's inventory
         * @return - the GUI built
         */
        public static GUIBackpackAlternate buildGUIAlternate(InventoryAlternateGui inv) {
            ArrayList<ItemStack> upgrades = IronBackpacksHelper.getUpgradesAppliedFromNBT(inv.getBackpackStack());
            GUI gui;
            if (ConfigHandler.renamingUpgradeRequired)
                gui = UpgradeMethods.hasRenamingUpgrade(upgrades) ? values()[UpgradeMethods.getAltGuiUpgradesApplied(upgrades) + 3] : values()[UpgradeMethods.getAltGuiUpgradesApplied(upgrades)]; //shifts to correct index if renaming
            else {
                gui = values()[UpgradeMethods.getAltGuiUpgradesApplied(upgrades) + 4];
            }
            return new GUIBackpackAlternate(gui, inv.getPlayer(), inv, upgrades, inv.getBackpackStack());
        }

        /**
         * Makes a container instance of a backpack.
         * @param inv - the backpack's inventory
         * @return - the Container
         */
        private Container makeContainer(InventoryAlternateGui inv) {
            return new ContainerAlternateGui(inv, xSize, ySize);
        }
    }

    private GUI type; //The Gui's type (enum above)
    private ContainerAlternateGui container; //the backpack's container
    private EntityPlayer player; //the player opening the backpack
    private ItemStack itemStack;

    //The buttons
    private GuiTextField textField; //to type in the new backpack name
    private TooltipButton renameButton;
    private TooltipButton moveLeft;
    private TooltipButton moveRight;
    private ArrayList<TooltipButton> advFilters = new ArrayList<TooltipButton>(); //the advanced filter buttons
    private ArrayList<TooltipButton> tooltipButtons = new ArrayList<TooltipButton>(); //buttons with a tooltip
    private TooltipButton[] rowIndeces = new TooltipButton[4]; //for use on the dynamic clear buttons

    //the tooltip data
    private long prevSystemTime;
    private int hoverTime;

    //the upgrades (fields for quicker access)
    private boolean hasButtonUpgrade;
    private boolean hasNoUpgrades;
    private boolean hasRenamingUpgrade;
    private boolean hasFilterBasicUpgrade;
    private boolean hasFilterFuzzyUpgrade;
    private boolean hasFilterOreDictUpgrade;
    private boolean hasFilterModSpecificUpgrade;
    private boolean hasRestockingUpgrade;
    private boolean hasCraftingUpgrade;
    private boolean hasCraftingSmallUpgrade;
    private boolean hasCraftingTinyUpgrade;
    private boolean hasFilterAdvancedUpgrade;
    private boolean hasFilterMiningUpgrade;
    private boolean hasFilterVoidUpgrade;

    private GUIBackpackAlternate(GUI type, EntityPlayer player, InventoryAlternateGui inv, ArrayList<ItemStack> upgrades, ItemStack backpack) {
        super(type.makeContainer(inv));
        this.player = player;
        this.container = (ContainerAlternateGui) type.makeContainer(inv);
        this.type = type;

        this.xSize = type.xSize;
        this.ySize = type.ySize;

        this.hasNoUpgrades = type.equals(GUI.ZERO);
        this.hasButtonUpgrade = UpgradeMethods.hasButtonUpgrade(upgrades);
        this.hasRenamingUpgrade = ConfigHandler.renamingUpgradeRequired ? UpgradeMethods.hasRenamingUpgrade(upgrades) : true;
        this.hasCraftingUpgrade = UpgradeMethods.hasCraftingUpgrade(upgrades);
        this.hasCraftingSmallUpgrade = UpgradeMethods.hasCraftingSmallUpgrade(upgrades);
        this.hasCraftingTinyUpgrade = UpgradeMethods.hasCraftingTinyUpgrade(upgrades);
        this.hasFilterBasicUpgrade = UpgradeMethods.hasFilterBasicUpgrade(upgrades);
        this.hasFilterFuzzyUpgrade = UpgradeMethods.hasFilterFuzzyUpgrade(upgrades);
        this.hasFilterOreDictUpgrade = UpgradeMethods.hasFilterOreDictUpgrade(upgrades);
        this.hasFilterModSpecificUpgrade = UpgradeMethods.hasFilterModSpecificUpgrade(upgrades);
        this.hasFilterVoidUpgrade = UpgradeMethods.hasFilterVoidUpgrade(upgrades);
        this.hasFilterAdvancedUpgrade = UpgradeMethods.hasFilterAdvancedUpgrade(upgrades);
        this.hasFilterMiningUpgrade = UpgradeMethods.hasFilterMiningUpgrade(upgrades);
        this.hasRestockingUpgrade = UpgradeMethods.hasRestockingUpgrade(upgrades);

        this.itemStack = backpack;
    }


    @Override
    public void initGui(){
        super.initGui();

        int xStart = ((width - xSize) / 2);
        int yStart = ((height - ySize) / 2);

        if (this.hasRenamingUpgrade){ //add text field to rename
            this.allowUserInput = true;

            this.textField = new GuiTextField(0, this.fontRendererObj, xStart + 20, yStart + 21, 103, 12);  //fontRenderer,x,y,width,height

            this.textField.setTextColor(-1); //TODO - play around with colors? - set background color
            this.textField.setDisabledTextColour(-1);
            this.textField.setMaxStringLength(29);

            Keyboard.enableRepeatEvents(true);
        }

        drawButtons();

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j){
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); //resets colors

        mc.getTextureManager().bindTexture(type.guiResourceList.location);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x+12, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawInfoStrings();
        drawHoveringOverTooltipButton(mouseX, mouseY);
    }

    /**
     * Draws the required buttons to the GUI.
     */
    private void drawButtons(){

        buttonList.clear();
        tooltipButtons.clear();
        Arrays.fill(rowIndeces, null);
        int rowIndex = 0;

        //draw all the buttons if you have the correct upgrade(s)
        if (hasRenamingUpgrade){
            int xStart = ((width - xSize) / 2);
            int yStart = ((height - ySize) / 2);
            buttonList.add(renameButton = new TooltipButton(GuiButtonRegistry.getButton(ButtonNames.RENAME), xStart + xSize - 57, yStart + 22));
            tooltipButtons.add(renameButton);
        }

        int yStartButton = ((height - ySize) / 2) + (hasRenamingUpgrade ? 40 : 21);
        int xStart = ((width - xSize) / 2) + xSize - 12 - 19;

        //If have button upgrade add the clear row buttons
        if (hasButtonUpgrade) {
            if (hasCraftingUpgrade){
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.condenser.tooltip"))));
                rowIndex++;
                yStartButton += 36;
            }
            if (hasCraftingSmallUpgrade){
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.condenser.small.tooltip"))));
                rowIndex++;
                yStartButton += 36;
            }
            if (hasCraftingTinyUpgrade){
                rowIndex++;
                yStartButton += 36;
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.condenser.tiny.tooltip"))));
            }
            if (hasFilterBasicUpgrade) {
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.basicFilter.tooltip"))));
                rowIndex++;
                yStartButton += 36;
            }
            if (hasFilterFuzzyUpgrade) {
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.fuzzyFilter.tooltip"))));
                rowIndex++;
                yStartButton += 36;
            }
            if (hasFilterOreDictUpgrade) {
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.oreDictFilter.tooltip"))));
                rowIndex++;
                yStartButton += 36;
            }
            if (hasFilterModSpecificUpgrade) {
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.modSpecificFilter.tooltip"))));
                rowIndex++;
                yStartButton += 36;
            }
            if (hasFilterVoidUpgrade) {
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.voidFilter.tooltip"))));
                rowIndex++;
                yStartButton += 36;
            }
        } else { //no button upgrade, move the staring position of the button to be at the advanced filter
            if (hasCraftingUpgrade) yStartButton += 36;
            if (hasCraftingSmallUpgrade) yStartButton += 36;
            if (hasCraftingTinyUpgrade) yStartButton += 36;
            if (hasFilterBasicUpgrade) yStartButton += 36;
            if (hasFilterFuzzyUpgrade) yStartButton += 36;
            if (hasFilterOreDictUpgrade) yStartButton += 36;
            if (hasFilterModSpecificUpgrade) yStartButton += 36;
            if (hasFilterVoidUpgrade) yStartButton += 36;
        }

        //If you have the advanced filter add the relevant buttons
        if (hasFilterAdvancedUpgrade){

            //Add the left and right buttons
            buttonList.add(moveLeft = new TooltipButton(GuiButtonRegistry.getButton(ButtonNames.MOVE_LEFT), guiLeft + 15, yStartButton + 17));
            buttonList.add(moveRight = new TooltipButton(GuiButtonRegistry.getButton(ButtonNames.MOVE_RIGHT), xStart + 12, yStartButton + 17));
            tooltipButtons.add(moveLeft);
            tooltipButtons.add(moveRight);

            //Add the buttons underneath the slots that display the type of filter each slot is
            advFilters.clear();
            TooltipButton temp;
            int xPositionStart = 20;

            if (container.getInventoryAlternateGui().getAdvFilterButtonStartPoint() + 9 > 18) { //if you have to wrap around

                int overlap = 9 - (18 - container.getInventoryAlternateGui().getAdvFilterButtonStartPoint());

                for (int i = container.getInventoryAlternateGui().getAdvFilterButtonStartPoint(); i < 18; i++) {
                    buttonList.add(temp = new TooltipButton(GuiButtonRegistry.getAdvFilterButtons()[container.getInventoryAlternateGui().getAdvFilterButtonStates()[i]-1], guiLeft + xPositionStart, yStartButton + 31));
                    advFilters.add(temp);
                    tooltipButtons.add(temp);
                    xPositionStart += 18;
                }

                for (int i = 0; i < overlap; i++) {
                    buttonList.add(temp = new TooltipButton(GuiButtonRegistry.getAdvFilterButtons()[container.getInventoryAlternateGui().getAdvFilterButtonStates()[i]-1], guiLeft + xPositionStart, yStartButton + 31));
                    advFilters.add(temp);
                    tooltipButtons.add(temp);
                    xPositionStart += 18;

                }
            }else {
                for (int i = container.getInventoryAlternateGui().getAdvFilterButtonStartPoint(); i < container.getInventoryAlternateGui().getAdvFilterButtonStartPoint() + 9; i++) {
                    buttonList.add(temp = new TooltipButton(GuiButtonRegistry.getAdvFilterButtons()[container.getInventoryAlternateGui().getAdvFilterButtonStates()[i]-1], guiLeft + xPositionStart, yStartButton + 31));
                    advFilters.add(temp);
                    tooltipButtons.add(temp);
                    xPositionStart += 18;
                }
            }
            //Add the clear button if have the button upgrade
            if (hasButtonUpgrade){
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.advancedFilter.tooltip"))));
                rowIndex++;
            }
            yStartButton += 36;
        }

        //Add the remaining clear row buttons if necessary
        if (hasButtonUpgrade){
            if (hasFilterMiningUpgrade) {
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.miningFilter.tooltip"))));
                rowIndex++;
                yStartButton += 36;
            }
            if (hasRestockingUpgrade){
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, GuiButtonRegistry.getButton(ButtonNames.CLEAR_ROW), xStart, yStartButton, TextUtils.cutLongString(TextUtils.localizeEffect("button.ironbackpacks.clear.hopper.tooltip"))));
                rowIndex++;
            }
        }

        //Add the clear row buttons to the tooltipButtons array
        for (int i = 0; i <= rowIndex; i++){
            if (rowIndeces[i] != null) {
                tooltipButtons.add(rowIndeces[i]);
            }
        }
    }

    /**
     * Draw the info strings in the GUI so each row has a label
     */
    private void drawInfoStrings(){

        String displayName = (itemStack == null) ? "Open and close again" : itemStack.getDisplayName(); //TODO: localize/fix

        fontRendererObj.drawString(TextUtils.localize(displayName), 20, 6, 4210752);
        int counter = hasFilterAdvancedUpgrade ? 5 : 4;
        fontRendererObj.drawString(TextUtils.localize("container.inventory"), 20, ySize - 96 + counter, 4210752);

        //draw the titles of all the upgrades in their correct positions
        if (hasNoUpgrades)
            fontRendererObj.drawString(TextUtils.localize("gui.ironbackpacks.noValidUpgradesFound"), 20, 22, 4210752);
        int yStart = hasRenamingUpgrade ? 44 : 25;
        if (hasCraftingUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.recipes.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasCraftingSmallUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.craftingSmall.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasCraftingTinyUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.craftingTiny.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterBasicUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.filterBasic.name"), 20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterFuzzyUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.filterFuzzy.name"), 20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterOreDictUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.filterOreDict.name"), 20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterModSpecificUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.filterModSpecific.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterVoidUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.filterVoid.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterAdvancedUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.filterAdvanced.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterMiningUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.filterMining.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasRestockingUpgrade) {
            fontRendererObj.drawString(TextUtils.localize("item.ironbackpacks.upgrade.restocking.name"),20, yStart, 4210752);
        }

    }

    /**
     * Checks if a tooltip should be drawn if the mouse has been hovering over an ITooltipButton for long enough, and if so it draws it.
     * @param mouseX - the mouse's X position
     * @param mouseY - the mouse's Y position
     */
    private void drawHoveringOverTooltipButton(int mouseX, int mouseY){
        int w = (this.width - this.xSize) / 2; //X axis on GUI
        int h = (this.height - this.ySize) / 2; //Y axis on GUI

        TooltipButton curr = null;
        for (TooltipButton button : tooltipButtons){
            if (button != null && button.mouseInButton(mouseX, mouseY)) {
                curr = button;
                break;
            }
        }
        if (curr != null){
            if (curr.getHoverTime() == 0)
                this.drawHoveringText(curr.getTooltip(), (int) mouseX - w, (int) mouseY - h, fontRendererObj);
            else {
                long systemTime = System.currentTimeMillis();
                if (prevSystemTime != 0)
                    hoverTime += systemTime - prevSystemTime;
                prevSystemTime = systemTime;

                if (hoverTime > curr.getHoverTime())
                    this.drawHoveringText(curr.getTooltip(), (int) mouseX - w, (int) mouseY - h, fontRendererObj);
            }
        }else{
            hoverTime = 0;
            prevSystemTime = 0;
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) { //called whenever a button is pressed.
        if (button == renameButton) {
            String textToChangeTo = textField.getText();
            if (textToChangeTo.length() > 0) { //have to actually enter a character
                container.renameBackpack(textToChangeTo);
                NetworkingHandler.network.sendToServer(new RenameMessage(textToChangeTo));
                textField.setText(""); //clears/resets the textField
                textField.setFocused(true);
            }
        }else if(button == moveLeft) {
            container.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_LEFT);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.MOVE_LEFT));
            drawButtons();
        }else if(button == moveRight) {
            container.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_RIGHT);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.MOVE_RIGHT));
            drawButtons();
        }else if (advFilters.contains(button)) { //An advanced filter's 'filter type' button has been pressed
            byte slot = (byte) container.getWraparoundIndex(advFilters.indexOf(button));
            byte changeTo = (byte) TooltipButton.incrementType(button);
            container.setAdvFilterButtonType(slot, changeTo);
            NetworkingHandler.network.sendToServer(new AdvFilterTypesMessage(slot, changeTo));
            drawButtons();
        }else if (button == rowIndeces[0]) {
            container.removeSlotsInRow(1);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.CLEAR_ROW_1));
            drawButtons();
        }else if (button == rowIndeces[1]) {
            container.removeSlotsInRow(2);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.CLEAR_ROW_2));
            drawButtons();
        }else if (button == rowIndeces[2]) {
            container.removeSlotsInRow(3);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.CLEAR_ROW_3));
            drawButtons();
        }
    }

    @Override //TODO: exceptions are bad mmkay
    protected void keyTyped(char char1, int int1) throws IOException {
        if (hasRenamingUpgrade) {
            if (textField.textboxKeyTyped(char1, int1)){
                //I seem to need to call this to process the key
            }else {
                super.keyTyped(char1, int1);
            }
        }else{
            super.keyTyped(char1, int1);
        }
    }

    @Override
    protected void mouseClicked(int int1, int int2, int int3) throws IOException {
        super.mouseClicked(int1, int2, int3);
        if (hasRenamingUpgrade) {
            textField.mouseClicked(int1, int2, int3);
        }
    }

    @Override
    public void drawScreen(int int1, int int2, float float1) {
        super.drawScreen(int1, int2, float1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        if (hasRenamingUpgrade) {
            textField.drawTextBox();
        }
    }

    /**
     * Allows you to use the scroll wheel to move through the advanced filter slots.
     */
    @Override
    public void handleMouseInput() throws IOException { //TODO: causes bugs (visual duping of empty items) if you scroll too fast
        super.handleMouseInput();
        if (hasFilterAdvancedUpgrade){
            int x = Mouse.getEventX() * this.width / this.mc.displayWidth;
            int y = this.height - Mouse.getEventY() * this.height / this.mc.displayHeight - 1;

            for (int i = 0; i < 9; i++) {
                GhostSlot slot = (GhostSlot) container.getSlot(container.getFilterAdvSlotIdStart() + i);
                if (isMouseOverSlot(slot, x, y)) {
                    int wheelState = Mouse.getEventDWheel();
                    if (wheelState != 0) {
                        if ((wheelState / 120) == 1) {
                            container.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_RIGHT);
                            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.MOVE_RIGHT));
                            drawButtons();
                        } else {
                            container.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_LEFT);
                            NetworkingHandler.network.sendToServer(new SingleByteMessage(IronBackpacksConstants.Messages.SingleByte.MOVE_LEFT));
                            drawButtons();
                        }
                    }
                }
            }
        }
    }

    //Private helper method that accounts for the gui size
    private boolean isMouseOverSlot(Slot slot, int mPosX, int mPosY) {
        mPosX -= this.guiLeft;
        mPosY -= this.guiTop;
        return mPosX >= slot.xDisplayPosition - 1 && mPosX < slot.xDisplayPosition + 16 + 1 && mPosY >= slot.yDisplayPosition - 1 && mPosY < slot.yDisplayPosition + 16 + 1;
    }


}
