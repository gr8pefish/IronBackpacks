package main.ironbackpacks.client.gui.inventory;

import cpw.mods.fml.client.config.GuiUtils;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.client.gui.buttons.TooltipButton;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.container.alternateGui.InventoryAlternateGui;
import main.ironbackpacks.container.slot.GhostSlot;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.network.AdvFilterTypesMessage;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.network.RenameMessage;
import main.ironbackpacks.network.SingleByteMessage;
import main.ironbackpacks.util.IronBackpacksConstants;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.Arrays;

@SideOnly(Side.CLIENT)
public class GUIBackpackAlternate extends GuiContainer { //extend GuiScreen?

    public enum ResourceList {
        //The two lists here in ResourceList and in GUI below are because of the option to either include
        //the renaming upgrade or not (via config), which shifts the locations of everything in the gui.
        ZERO(new ResourceLocation(ModInformation.ID, "textures/guis/alternateGui/ZERO_alternateGui.png")),
        ONE(new ResourceLocation(ModInformation.ID, "textures/guis/alternateGui/ONE_alternateGui.png")),
        TWO(new ResourceLocation(ModInformation.ID, "textures/guis/alternateGui/TWO_alternateGui.png")),
        THREE(new ResourceLocation(ModInformation.ID, "textures/guis/alternateGui/THREE_alternateGui.png")),

        RENAMING_ONE(new ResourceLocation(ModInformation.ID, "textures/guis/alternateGui/RENAMING_ONE_alternateGui.png")),
        RENAMING_TWO(new ResourceLocation(ModInformation.ID, "textures/guis/alternateGui/RENAMING_TWO_alternateGui.png")),
        RENAMING_THREE(new ResourceLocation(ModInformation.ID, "textures/guis/alternateGui/RENAMING_THREE_alternateGui.png"));

        public final ResourceLocation location;

        private ResourceList(ResourceLocation loc) {
            this.location = loc;
        }

    }

    public enum GUI {

        ZERO( 200, 114 + 18, ResourceList.ZERO),
        ONE(  200, 114 + (18*2), ResourceList.ONE),
        TWO(  200, 114 + (18*4), ResourceList.TWO),
        THREE(200, 114 + (18*6), ResourceList.THREE),

        RENAMING_ZERO( 200, 114 + 18, ResourceList.ZERO),
        RENAMING_ONE(  200, 114 + (18*3), ResourceList.RENAMING_ONE),
        RENAMING_TWO(  200, 114 + (18*5), ResourceList.RENAMING_TWO),
        RENAMING_THREE(200, 114 + (18*7), ResourceList.RENAMING_THREE);

        private int xSize;
        private int ySize;
        private ResourceList guiResourceList;

        private GUI(int xSize, int ySize, ResourceList guiResourceList) {
            this.xSize = xSize;
            this.ySize = ySize;
            this.guiResourceList = guiResourceList;
        }

        public Container makeContainer(EntityPlayer player, InventoryAlternateGui inv) {
            return new ContainerAlternateGui(player, inv, xSize, ySize);
        }

        //called from GuiHandler
        public static GUIBackpackAlternate buildGUIAlternate(EntityPlayer player, InventoryAlternateGui inv, int[] upgrades, IronBackpackType backpackType) {
            GUI gui = UpgradeMethods.hasRenamingUpgrade(upgrades) ? values()[UpgradeMethods.getAlternateGuiUpgradesCount(upgrades) + 3] : values()[UpgradeMethods.getAlternateGuiUpgradesCount(upgrades)]; //shifts to correct index if renaming
            return new GUIBackpackAlternate(gui, player, inv, upgrades, backpackType);
        }
    }

    private GUI type;
    private ContainerAlternateGui container;
    private EntityPlayer player;

    private GuiTextField textField;
    private TooltipButton renameButton;
    private TooltipButton moveLeft;
    private TooltipButton moveRight;

    private ArrayList<TooltipButton> advFilters = new ArrayList<TooltipButton>();
    private ArrayList<TooltipButton> tooltipButtons = new ArrayList<TooltipButton>();
    private TooltipButton[] rowIndeces = new TooltipButton[3];

    private long prevSystemTime;
    private int hoverTime;

    private boolean hasButtonUpgrade;
    private boolean hasNoUpgrades;
    private boolean hasRenamingUpgrade;
    private boolean hasFilterBasicUpgrade;
    private boolean hasFilterFuzzyUpgrade;
    private boolean hasFilterOreDictUpgrade;
    private boolean hasFilterModSpecificUpgrade;
    private boolean hasHopperUpgrade;
    private boolean hasCondenserUpgrade;
    private boolean hasFilterAdvancedUpgrade;

    private GUIBackpackAlternate(GUI type, EntityPlayer player, InventoryAlternateGui inv, int[] upgrades, IronBackpackType backpackType) {
        super(type.makeContainer(player, inv));
        this.player = player;
        this.container = (ContainerAlternateGui) type.makeContainer(player, inv);
        this.type = type;

        this.xSize = type.xSize;
        this.ySize = type.ySize;

        this.hasNoUpgrades = type.equals(GUI.ZERO);
        this.hasButtonUpgrade = UpgradeMethods.hasButtonUpgrade(upgrades);
        this.hasRenamingUpgrade = UpgradeMethods.hasRenamingUpgrade(upgrades);
        this.hasFilterBasicUpgrade = UpgradeMethods.hasFilterBasicUpgrade(upgrades);
        this.hasFilterFuzzyUpgrade = UpgradeMethods.hasFilterFuzzyUpgrade(upgrades);
        this.hasFilterOreDictUpgrade = UpgradeMethods.hasFilterOreDictUpgrade(upgrades);
        this.hasFilterModSpecificUpgrade = UpgradeMethods.hasFilterModSpecificUpgrade(upgrades);
        this.hasHopperUpgrade = UpgradeMethods.hasHopperUpgrade(upgrades);
        this.hasCondenserUpgrade = UpgradeMethods.hasCondenserUpgrade(upgrades);
        this.hasFilterAdvancedUpgrade = UpgradeMethods.hasFilterAdvancedUpgrade(upgrades);


    }

    @Override
    public void initGui(){
        super.initGui();

        int xStart = ((width - xSize) / 2);
        int yStart = ((height - ySize) / 2);

        if (this.hasRenamingUpgrade){
            this.allowUserInput = true;

            this.textField = new GuiTextField(this.fontRendererObj, xStart + 20, yStart + 21, 103, 12);  //x,y,width,height

            this.textField.setTextColor(-1); //TODO - play around with colors? - set background color
            this.textField.setDisabledTextColour(-1);
            this.textField.setMaxStringLength(29);

            Keyboard.enableRepeatEvents(true);
        }

        drawButtons();

    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); //rests colors

        mc.getTextureManager().bindTexture(type.guiResourceList.location);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x+12, y, 0, 0, xSize, ySize);

//        drawButtons();
    }

    private void drawButtons(){

        buttonList.clear();
        tooltipButtons.clear();
        Arrays.fill(rowIndeces, null);
        int rowIndex = 0;

        //draw all the buttons if you have the correct upgrade(s)
        if (hasRenamingUpgrade){
            int xStart = ((width - xSize) / 2);
            int yStart = ((height - ySize) / 2);
            buttonList.add(renameButton = new TooltipButton(TooltipButton.RENAME, xStart + xSize - 57, yStart + 22, 25, 10, TooltipButton.RENAME, true, "", "Renames the backpack."));
            tooltipButtons.add(renameButton);
        }

        int yStartButton = ((height - ySize) / 2) + (hasRenamingUpgrade ? 40 : 21);
        int xStart = ((width - xSize) / 2) + xSize - 12 - 19;

        if (hasFilterBasicUpgrade) {
            if (hasButtonUpgrade) {
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, xStart, yStartButton, 11, 11, TooltipButton.CLEAR_ROW, true, "", "Clears the basic","filter items."));
                rowIndex++;
            }
            yStartButton += 36;
        }
        if (hasFilterFuzzyUpgrade) {
            if (hasButtonUpgrade) {
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, xStart, yStartButton, 11, 11, TooltipButton.CLEAR_ROW, true, "", "Clears the fuzzy","filter items."));
                rowIndex++;
            }
            yStartButton += 36;
        }
        if (hasFilterOreDictUpgrade) {
            if (hasButtonUpgrade) {
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, xStart, yStartButton, 11, 11, TooltipButton.CLEAR_ROW, true, "", "Clears the ore dictionary", "filter items."));
                rowIndex++;
            }
            yStartButton += 36;
        }
        if (hasFilterModSpecificUpgrade){
            if (hasButtonUpgrade){
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, xStart, yStartButton, 11,11, TooltipButton.CLEAR_ROW, true, "", "Clears the mod specific", "filter items."));
                rowIndex++;
            }
            yStartButton += 36;
        }
        if (hasFilterAdvancedUpgrade){
            buttonList.add(moveLeft = new TooltipButton(TooltipButton.MOVE_LEFT, guiLeft + 15, yStartButton + 17, 4, 9, TooltipButton.MOVE_LEFT, true, "", "Shift to the left."));
            buttonList.add(moveRight = new TooltipButton(TooltipButton.MOVE_RIGHT, xStart + 12, yStartButton + 17, 4, 9, TooltipButton.MOVE_RIGHT, true, "", "Shift to the right."));
            tooltipButtons.add(moveLeft);
            tooltipButtons.add(moveRight);

            int xPositionStart = 20;
            advFilters.clear();
            TooltipButton temp;
            if (container.inventory.advFilterButtonStartPoint + 9 > 18) {
                int overlap = 9 - (18 - container.inventory.advFilterButtonStartPoint);
                for (int i = container.inventory.advFilterButtonStartPoint; i < 18; i++) {
                    int type = container.inventory.advFilterButtonStates[i];
                    buttonList.add(temp = new TooltipButton(6 + i, guiLeft + xPositionStart, yStartButton + 31, 16, 5, type, false, "", TooltipButton.advTooltipsArray[type-1]));//IronBackpacksHelper.getAdvFilterTypeFromNBT(advFilterStartPoint + i, backpackStack)));
                    advFilters.add(temp);
                    xPositionStart += 18;
                    tooltipButtons.add(temp);
                }
                for (int i = 0; i < overlap; i++) {
                    int type = container.inventory.advFilterButtonStates[i];
                    buttonList.add(temp = new TooltipButton(6 + i, guiLeft + xPositionStart, yStartButton + 31, 16, 5, type, false, "", TooltipButton.advTooltipsArray[type-1]));//IronBackpacksHelper.getAdvFilterTypeFromNBT(advFilterStartPoint + i, backpackStack)));
                    advFilters.add(temp);
                    xPositionStart += 18;
                    tooltipButtons.add(temp);
                }
            }else {
                for (int i = container.inventory.advFilterButtonStartPoint; i < container.inventory.advFilterButtonStartPoint + 9; i++) {
                    int type = container.inventory.advFilterButtonStates[i];
                    buttonList.add(temp = new TooltipButton(6 + i, guiLeft + xPositionStart, yStartButton + 31, 16, 5, type, false, "", TooltipButton.advTooltipsArray[type-1]));//IronBackpacksHelper.getAdvFilterTypeFromNBT(advFilterStartPoint + i, backpackStack)));
                    advFilters.add(temp);
                    xPositionStart += 18;
                    tooltipButtons.add(temp);
                }
            }
            if (hasButtonUpgrade){
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, xStart, yStartButton, 11,11, TooltipButton.CLEAR_ROW, true, "", "Resets the advanced","filter (clears items","and button filters)."));
                rowIndex++;
            }
            yStartButton += 36;
        }
        if (hasHopperUpgrade){
            if (hasButtonUpgrade){
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, xStart, yStartButton, 11,11, TooltipButton.CLEAR_ROW, true, "", "Clears the", "restocking items."));
                rowIndex++;
            }
            yStartButton += 36;
        }
        if (hasCondenserUpgrade){
            if (hasButtonUpgrade){
                buttonList.add(rowIndeces[rowIndex] = new TooltipButton(rowIndex, xStart, yStartButton, 11,11, TooltipButton.CLEAR_ROW, true, "", "Clears the", "crafting items."));
            }
        }

        for (int i = 0; i <= rowIndex; i++){
            if (rowIndeces[i] != null) {
                tooltipButtons.add(rowIndeces[i]);
            }
        }
    }

    private void drawInfoStrings(){
        ItemStack itemStack = IronBackpacksHelper.getBackpack(player);
        fontRendererObj.drawString(StatCollector.translateToLocal(itemStack.getDisplayName()), 20, 6, 4210752);
        int counter = hasFilterAdvancedUpgrade ? 5 : 4;
        fontRendererObj.drawString(StatCollector.translateToLocal("player.inventory"), 20, ySize - 96 + counter, 4210752);

        //draw the titles of all the upgrades in their correct positions
        if (hasNoUpgrades)
            fontRendererObj.drawString(StatCollector.translateToLocal("noValidUpgradesFound"), 20, 22, 4210752);
        int yStart = hasRenamingUpgrade ? 44 : 25;
        if (hasFilterBasicUpgrade) {
            fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterBasicUpgrade.name"), 20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterFuzzyUpgrade) {
            fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterFuzzyUpgrade.name"), 20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterOreDictUpgrade) {
            fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterOreDictUpgrade.name"), 20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterModSpecificUpgrade) {
            fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterModSpecificUpgrade.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasFilterAdvancedUpgrade) {
            fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterAdvancedUpgrade.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasHopperUpgrade) {
            fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:hopperUpgrade.name"),20, yStart, 4210752);
            yStart += 36;
        }
        if (hasCondenserUpgrade) {
            fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:condenserUpgrade.name"),20, yStart, 4210752);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        drawInfoStrings();
        drawHoveringOverTooltipButton(mouseX, mouseY);
    }

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
    protected void actionPerformed(GuiButton button) {
        if (button == renameButton) {
            container.renameBackpack(textField.getText());
            NetworkingHandler.network.sendToServer(new RenameMessage(textField.getText()));
        }else if(button == moveLeft) {
            container.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_LEFT);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(SingleByteMessage.MOVE_LEFT));
            drawButtons();
        }else if(button == moveRight) {
            container.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_RIGHT);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(SingleByteMessage.MOVE_RIGHT));
            drawButtons();
        }else if (advFilters.contains(button)) {
            byte slot = (byte) container.getWraparoundIndex(advFilters.indexOf(button));
            byte changeTo = (byte) TooltipButton.incrementType(button);
            container.setAdvFilterButtonType(slot, changeTo);
            NetworkingHandler.network.sendToServer(new AdvFilterTypesMessage(slot, changeTo));
            drawButtons();
        }else if (button == rowIndeces[0]) {
            container.removeSlotsInRow(1);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(SingleByteMessage.CLEAR_ROW_1));
            drawButtons();
        }else if (button == rowIndeces[1]) {
            container.removeSlotsInRow(2);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(SingleByteMessage.CLEAR_ROW_2));
            drawButtons();
        }else if (button == rowIndeces[2]) {
            container.removeSlotsInRow(3);
            NetworkingHandler.network.sendToServer(new SingleByteMessage(SingleByteMessage.CLEAR_ROW_3));
            drawButtons();
        }
    }

    @Override
    protected void keyTyped(char char1, int int1)
    {
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
    protected void mouseClicked(int int1, int int2, int int3)
    {
        super.mouseClicked(int1, int2, int3);
        if (hasRenamingUpgrade) {
            textField.mouseClicked(int1, int2, int3);
        }
    }

    @Override
    public void drawScreen(int int1, int int2, float float1)
    {
        super.drawScreen(int1, int2, float1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        if (hasRenamingUpgrade) {
            textField.drawTextBox();
        }
    }

    private boolean isMouseOverSlot(Slot slot, int mPosX, int mPosY) {
        mPosX -= this.guiLeft;
        mPosY -= this.guiTop;
        return mPosX >= slot.xDisplayPosition - 1 && mPosX < slot.xDisplayPosition + 16 + 1 && mPosY >= slot.yDisplayPosition - 1 && mPosY < slot.yDisplayPosition + 16 + 1;
    }

    @Override
    public void handleMouseInput() {
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
                            NetworkingHandler.network.sendToServer(new SingleByteMessage(SingleByteMessage.MOVE_RIGHT));
                            drawButtons();
                        } else {
                            container.changeAdvFilterSlots(IronBackpacksConstants.Miscellaneous.MOVE_LEFT);
                            NetworkingHandler.network.sendToServer(new SingleByteMessage(SingleByteMessage.MOVE_LEFT));
                            drawButtons();
                        }
                    }
                }
            }
        }
    }


}
