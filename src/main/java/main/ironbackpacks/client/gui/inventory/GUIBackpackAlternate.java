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
import net.minecraft.client.Minecraft;
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
        public static GUIBackpackAlternate buildGUIAlternate(ItemStack backpack, EntityPlayer player, InventoryAlternateGui inv, int[] upgrades, IronBackpackType backpackType) {
            GUI gui = UpgradeMethods.hasRenamingUpgrade(upgrades) ? values()[UpgradeMethods.getAlternateGuiUpgradesCount(upgrades) + 3] : values()[UpgradeMethods.getAlternateGuiUpgradesCount(upgrades)]; //shifts to correct index if renaming
            return new GUIBackpackAlternate(backpack, gui, player, inv, upgrades, backpackType);
        }
    }

    private GUI type;
    private ContainerAlternateGui container;
    private IronBackpackType backpackType;
    private EntityPlayer player;
    private ItemStack backpackStack;
    private InventoryAlternateGui inventoryAlternateGui;

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
    private int advFilterStartPoint;

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

    private GUIBackpackAlternate(ItemStack backpackStack, GUI type, EntityPlayer player, InventoryAlternateGui inv, int[] upgrades, IronBackpackType backpackType) {
        super(type.makeContainer(player, inv));
        this.player = player;
        this.container = (ContainerAlternateGui) type.makeContainer(player, inv);
        this.type = type;
        this.backpackType = backpackType;
        this.backpackStack = backpackStack;
//        this.inventoryAlternateGui = inv;

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
        this.advFilterStartPoint = 0;

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

        //draw all the buttons if you have the correct upgrade(s)
        if (hasRenamingUpgrade){
            int xStart = ((width - xSize) / 2);
            int yStart = ((height - ySize) / 2);
            buttonList.add(renameButton = new RenameButton(1, xStart + xSize - 57, yStart + 22, 25, 10, RenameButton.RENAME_BUTTON));
        }

        int yStartButton = ((height - ySize) / 2) + (hasRenamingUpgrade ? 40 : 21);
        int xStart = ((width - xSize) / 2) + xSize - 12 - 19;
        if (hasFilterBasicUpgrade) {
            if (hasButtonUpgrade) {
                buttonList.add(row1 = new ButtonUpgrade(idRow, xStart, yStartButton, 11, 11, ButtonUpgrade.CLEAR_ROW));
                idRow++;
            }
            yStartButton += 36;
        }
        if (hasFilterFuzzyUpgrade) {
            if (hasButtonUpgrade) {
                buttonList.add(row1 = new ButtonUpgrade(idRow, xStart, yStartButton, 11, 11, ButtonUpgrade.CLEAR_ROW));
                idRow++;
            }
            yStartButton += 36;
        }
        if (hasFilterOreDictUpgrade) {
            if (hasButtonUpgrade) {
                buttonList.add(row1 = new ButtonUpgrade(idRow, xStart, yStartButton, 11, 11, ButtonUpgrade.CLEAR_ROW));
                idRow++;
            }
            yStartButton += 36;
        }
        if (hasFilterModSpecificUpgrade){
            if (hasButtonUpgrade){
                buttonList.add(row1 = new ButtonUpgrade(idRow, xStart, yStartButton, 11,11, ButtonUpgrade.CLEAR_ROW));
                idRow++;
            }
            yStartButton += 36;
        }
        if (hasFilterAdvancedUpgrade){
            buttonList.add(moveLeft = new AdvancedFilterButtons(4, guiLeft + 15, yStartButton + 17, 4, 9, AdvancedFilterButtons.MOVE_LEFT));
            buttonList.add(moveRight = new AdvancedFilterButtons(5, xStart + 12, yStartButton + 17, 4, 9, AdvancedFilterButtons.MOVE_RIGHT));
            int positionStart = 20;
            advFilters.clear();
            AdvancedFilterButtons temp;
            if (container.inventory.advFilterButtonStartPoint + 9 > 18) {
                int overlap = 9 - (18 - container.inventory.advFilterButtonStartPoint);
                for (int i = container.inventory.advFilterButtonStartPoint; i < 18; i++) {
                    buttonList.add(temp = new AdvancedFilterButtons(6 + i, guiLeft + positionStart, yStartButton + 31, 16, 5, container.inventory.advFilterButtonStates[i]));//IronBackpacksHelper.getAdvFilterTypeFromNBT(advFilterStartPoint + i, backpackStack)));
                    advFilters.add(temp);
                    positionStart += 18;
                }
                for (int i = 0; i < overlap; i++) {
                    buttonList.add(temp = new AdvancedFilterButtons(6 + i, guiLeft + positionStart, yStartButton + 31, 16, 5, container.inventory.advFilterButtonStates[i]));//IronBackpacksHelper.getAdvFilterTypeFromNBT(advFilterStartPoint + i, backpackStack)));
                    advFilters.add(temp);
                    positionStart += 18;
                }
            }else {
                for (int i = container.inventory.advFilterButtonStartPoint; i < container.inventory.advFilterButtonStartPoint + 9; i++) {
                    buttonList.add(temp = new AdvancedFilterButtons(6 + i, guiLeft + positionStart, yStartButton + 31, 16, 5, container.inventory.advFilterButtonStates[i]));//IronBackpacksHelper.getAdvFilterTypeFromNBT(advFilterStartPoint + i, backpackStack)));
                    advFilters.add(temp);
                    positionStart += 18;
                }
            }
            if (hasButtonUpgrade){
                buttonList.add(row1 = new ButtonUpgrade(idRow, xStart, yStartButton, 11,11, ButtonUpgrade.CLEAR_ROW));
                idRow++;
            }
            yStartButton += 36;
        }
        if (hasHopperUpgrade){
            if (hasButtonUpgrade){
                buttonList.add(row2 = new ButtonUpgrade(idRow, xStart, yStartButton, 11,11, ButtonUpgrade.CLEAR_ROW));
                idRow++;
            }
            yStartButton += 36;
        }
        if (hasCondenserUpgrade){
            if (hasButtonUpgrade){
                buttonList.add(row3 = new ButtonUpgrade(idRow, xStart, yStartButton, 11,11, ButtonUpgrade.CLEAR_ROW));
            }
            yStartButton += 36;
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int par1, int par2) {

        ItemStack itemStack = IronBackpacksHelper.getBackpack(player);
        fontRendererObj.drawString(StatCollector.translateToLocal(itemStack.getDisplayName()), 20, 6, 4210752);
        fontRendererObj.drawString(StatCollector.translateToLocal("player.inventory"), 20, ySize - 96 + 4, 4210752);

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
            yStart += 36;
        }

//        drawButtons();
//        initGui();

    }

//    @Override
//    private void updateFilterButtons(){
//
//    }

    @Override
    protected void keyTyped(char char1, int int1)
    {
        if (hasRenamingUpgrade) {
            if (textField.textboxKeyTyped(char1, int1)){ //TODO-fix?
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


//    @Override
//    public void onGuiClosed() //already saves in onContainerClosed
//    {
//        if (mc.thePlayer != null)
//        {
//            container.inventory.onGuiSaved(mc.thePlayer);
//            inventorySlots.onContainerClosed(mc.thePlayer);
//        }
//    }

    @Override
    protected void actionPerformed(GuiButton button) {
        if (button == renameButton) {
            container.renameBackpack(textField.getText());
            NetworkingHandler.network.sendToServer(new RenameMessage(textField.getText()));
        }else if(button == moveLeft) {
            container.changeAdvFilterSlots("left");
            NetworkingHandler.network.sendToServer(new AdvancedFilterMessage(AdvancedFilterMessage.MOVE_LEFT));
            initGui();
        }else if(button == moveRight) {
            container.changeAdvFilterSlots("right");
            NetworkingHandler.network.sendToServer(new AdvancedFilterMessage(AdvancedFilterMessage.MOVE_RIGHT));
            initGui();
        }else if (button instanceof AdvancedFilterButtons && advFilters.contains(button)){
            System.out.println("ADV FILTER BUTTON PRESSED");
            System.out.println("SLOT: "+advFilters.indexOf(button));
//            IronBackpacksHelper.setAdvFilterTypeToNBT(advFilters.indexOf(button), backpackStack, incrementType(button)); //client side changes
            container.setAdvFilterButtonType(advFilters.indexOf(button)+container.inventory.advFilterButtonStartPoint, AdvancedFilterButtons.incrementType(button)); //change inventory on client side, should be instant
//            Minecraft.getMinecraft().playerController.sendEnchantPacket(1, IronBackpacksHelper.setOneNumberFromTwo(advFilters.indexOf(button) + 1, AdvancedFilterButtons.incrementType(button))); //server for persistence when save/quit
            NetworkingHandler.network.sendToServer(new AdvancedFilterMessage(IronBackpacksHelper.setOneNumberFromTwo(advFilters.indexOf(button) + 1 + container.inventory.advFilterButtonStartPoint, AdvancedFilterButtons.incrementType(button))));
            initGui();
        }else if (buttonList.size() > 1 && button == buttonList.get(1)){
            container.removeSlotsInRow(1);
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(1));
        }else if (buttonList.size() > 2 && button == buttonList.get(2)){
            container.removeSlotsInRow(2);
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(2));
        }else if (buttonList.size() > 3 && button == buttonList.get(3)){
            container.removeSlotsInRow(3);
            NetworkingHandler.network.sendToServer(new ButtonUpgradeMessage(3));
        }
        System.out.println("First button state client side:"+container.inventory.advFilterButtonStates[0]);
    }

}
