package main.ironbackpacks.client.gui.inventory;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.client.gui.buttons.AdvancedFilterButtons;
import main.ironbackpacks.client.gui.buttons.ButtonUpgrade;
import main.ironbackpacks.client.gui.buttons.RenameButton;
import main.ironbackpacks.container.alternateGui.ContainerAlternateGui;
import main.ironbackpacks.container.alternateGui.InventoryAlternateGui;
import main.ironbackpacks.items.backpacks.IronBackpackType;
import main.ironbackpacks.items.upgrades.UpgradeMethods;
import main.ironbackpacks.network.AdvancedFilterMessage;
import main.ironbackpacks.network.ButtonUpgradeMessage;
import main.ironbackpacks.network.NetworkingHandler;
import main.ironbackpacks.network.RenameMessage;
import main.ironbackpacks.util.IronBackpacksHelper;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.util.ArrayList;
import java.util.List;

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
    private IronBackpackType backpackType;
    private EntityPlayer player;

    private GuiTextField textField;
    private RenameButton renameButton;

    private ButtonUpgrade row1;
    private ButtonUpgrade row2;
    private ButtonUpgrade row3;
    private ButtonUpgrade row4;
    private int rowToClear;
    private AdvancedFilterButtons moveLeft;
    private AdvancedFilterButtons moveRight;
    private ArrayList<AdvancedFilterButtons> advFilters = new ArrayList<AdvancedFilterButtons>();

    private int idRow;
    private int upgradeCount;
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
        this.backpackType = backpackType;

        this.xSize = type.xSize;
        this.ySize = type.ySize;

        this.upgradeCount = UpgradeMethods.getAlternateGuiUpgradesCount(upgrades);
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

        this.rowToClear = 1;
        this.idRow = this.hasRenamingUpgrade ? 2 : 1;
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

            this.buttonList.add(this.renameButton = new RenameButton(1, xStart + xSize - 57, yStart + 22, 25, 10, RenameButton.RENAME_BUTTON));
        }
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
    {
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F); //rests colors

        this.mc.getTextureManager().bindTexture(type.guiResourceList.location);
        int x = (width - xSize) / 2;
        int y = (height - ySize) / 2;
        drawTexturedModalRect(x+12, y, 0, 0, xSize, ySize);
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {
        ItemStack itemStack = IronBackpacksHelper.getBackpack(this.player);
        this.fontRendererObj.drawString(StatCollector.translateToLocal(itemStack.getDisplayName()), 20, 6, 4210752);
        this.fontRendererObj.drawString(StatCollector.translateToLocal("player.inventory"), 20, this.ySize - 96+4, 4210752);

        //draw the titles of all the upgrades in their correct positions and add the buttons (if valid)
        if (this.hasNoUpgrades){
            this.fontRendererObj.drawString(StatCollector.translateToLocal("noValidUpgradesFound"), 20, 22, 4210752);
        }
        int yStart = this.hasRenamingUpgrade ? 44 : 25;
        int yStartButton = ((height - ySize) / 2) + (this.hasRenamingUpgrade ? 40 : 21);
        int xStart = ((width - xSize) / 2) + xSize - 12 - 19;
        if (this.hasFilterBasicUpgrade) {
            this.fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterBasicUpgrade.name"), 20, yStart, 4210752);
            if (this.hasButtonUpgrade) {
                this.buttonList.add(this.row1 = new ButtonUpgrade(this.idRow, xStart, yStartButton, 11, 11, ButtonUpgrade.CLEAR_ROW));
                this.idRow++;
            }
            yStart += 36;
            yStartButton += 36;
        }
        if (this.hasFilterFuzzyUpgrade) {
            this.fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterFuzzyUpgrade.name"), 20, yStart, 4210752);
            if (this.hasButtonUpgrade) {
                this.buttonList.add(this.row1 = new ButtonUpgrade(this.idRow, xStart, yStartButton, 11, 11, ButtonUpgrade.CLEAR_ROW));
                this.idRow++;
            }
            yStart += 36;
            yStartButton += 36;
        }
        if (this.hasFilterOreDictUpgrade) {
            this.fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterOreDictUpgrade.name"), 20, yStart, 4210752);
            if (this.hasButtonUpgrade) {
                this.buttonList.add(this.row1 = new ButtonUpgrade(this.idRow, xStart, yStartButton, 11, 11, ButtonUpgrade.CLEAR_ROW));
                this.idRow++;
            }
            yStart += 36;
            yStartButton += 36;
        }
        if (this.hasFilterModSpecificUpgrade){
            this.fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterModSpecificUpgrade.name"),20, yStart, 4210752);
            if (this.hasButtonUpgrade){
                this.buttonList.add(this.row1 = new ButtonUpgrade(this.idRow, xStart, yStartButton, 11,11, ButtonUpgrade.CLEAR_ROW));
                this.idRow++;
            }
            yStart += 36;
            yStartButton += 36;
        }
        if (this.hasFilterAdvancedUpgrade){
            this.fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:filterAdvancedUpgrade.name"),20, yStart, 4210752);
            this.buttonList.add(this.moveLeft = new AdvancedFilterButtons(4, this.guiLeft + 15, yStartButton + 17, 4, 9, AdvancedFilterButtons.MOVE_LEFT));
            this.buttonList.add(this.moveRight = new AdvancedFilterButtons(5, xStart + 12, yStartButton + 17, 4, 9, AdvancedFilterButtons.MOVE_RIGHT));
            int start = 20;
            advFilters.clear();
            AdvancedFilterButtons temp;
            for (int i = 0; i < 9; i++){
                this.buttonList.add(temp = new AdvancedFilterButtons(6 + i, this.guiLeft + start, yStartButton + 31, 16, 5, AdvancedFilterButtons.EXACT));
                advFilters.add(temp);
                start += 18;
            }
            if (this.hasButtonUpgrade){
                this.buttonList.add(this.row1 = new ButtonUpgrade(this.idRow, xStart, yStartButton, 11,11, ButtonUpgrade.CLEAR_ROW));
                this.idRow++;
            }
            yStart += 36;
            yStartButton += 36;
        }
        if (this.hasHopperUpgrade){
            this.fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:hopperUpgrade.name"),20, yStart, 4210752);
            if (this.hasButtonUpgrade){
                this.buttonList.add(this.row2 = new ButtonUpgrade(this.idRow, xStart, yStartButton, 11,11, ButtonUpgrade.CLEAR_ROW));
                this.idRow++;
            }
            yStart += 36;
            yStartButton += 36;
        }
        if (this.hasCondenserUpgrade){
            this.fontRendererObj.drawString(StatCollector.translateToLocal("item.ironbackpacks:condenserUpgrade.name"),20, yStart, 4210752);
            if (this.hasButtonUpgrade){
                this.buttonList.add(this.row3 = new ButtonUpgrade(this.idRow, xStart, yStartButton, 11,11, ButtonUpgrade.CLEAR_ROW));
            }
        }
    }

    @Override
    protected void keyTyped(char char1, int int1)
    {
        if (this.hasRenamingUpgrade) {
            if (this.textField.textboxKeyTyped(char1, int1)){ //TODO-fix?
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
        if (this.hasRenamingUpgrade) {
            this.textField.mouseClicked(int1, int2, int3);
        }
    }

    @Override
    public void drawScreen(int int1, int int2, float float1)
    {
        super.drawScreen(int1, int2, float1);
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_BLEND);
        if (this.hasRenamingUpgrade) {
            this.textField.drawTextBox();
        }
    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == renameButton) {
            this.container.renameBackpack(this.textField.getText());
            NetworkingHandler.network.sendToServer(new RenameMessage(this.textField.getText()));
        }else if(button == moveLeft) {
            this.container.changeAdvFilterSlots("left");
            NetworkingHandler.network.sendToServer(new AdvancedFilterMessage(AdvancedFilterMessage.MOVE_LEFT));
        }else if(button == moveRight) {
            this.container.changeAdvFilterSlots("right");
            NetworkingHandler.network.sendToServer(new AdvancedFilterMessage(AdvancedFilterMessage.MOVE_RIGHT));
        }else if (button instanceof AdvancedFilterButtons && advFilters.contains(button)){
            System.out.println("ADV FILTER BUTTON PRESSED");
            System.out.println("SLOT: "+advFilters.indexOf(button));
            incrementFilter(this.buttonList, button);
            System.out.println("INCREMENTED");
        }else if (this.buttonList.size() > 1 && button == this.buttonList.get(1)){
            this.container.removeSlotsInRow(1);
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(1));
        }else if (this.buttonList.size() > 2 && button == this.buttonList.get(2)){
            this.container.removeSlotsInRow(2);
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(2));
        }else if (this.buttonList.size() > 3 && button == this.buttonList.get(3)){
            this.container.removeSlotsInRow(3);
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(3));
        }
    }

    private void incrementFilter(List buttonList, GuiButton button){
        int addIndex = this.buttonList.indexOf(button);
        AdvancedFilterButtons temp;
        this.buttonList.add(addIndex, temp = new AdvancedFilterButtons(button.id, button.xPosition, button.yPosition, button.width, button.height, incrementType(button)));
        advFilters.remove(button);
        advFilters.add(temp);
        this.buttonList.remove(button);
    }

    private int incrementType(GuiButton button){
        int type = ((AdvancedFilterButtons)button).buttonType;
        if (type < AdvancedFilterButtons.MOD_SPECIFIC){
            return ++type;
        }else{
            return AdvancedFilterButtons.EXACT;
        }
    }

}
