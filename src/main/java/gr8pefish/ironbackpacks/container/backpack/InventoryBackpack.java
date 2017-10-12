package gr8pefish.ironbackpacks.container.backpack;

import java.util.UUID;

import gr8pefish.ironbackpacks.api.items.backpacks.interfaces.IBackpack;
import gr8pefish.ironbackpacks.capabilities.player.PlayerWearingBackpackCapabilities;
import gr8pefish.ironbackpacks.items.backpacks.ItemBackpack;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import gr8pefish.ironbackpacks.util.NBTUtils;
import gr8pefish.ironbackpacks.util.helpers.IronBackpacksHelper;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraftforge.common.util.Constants;

/**
 * The inventory used when opening the backpack normally.
 */
public class InventoryBackpack implements IInventory {

    private ItemStack backpackStack; //the itemstack instance of the backpack
    private EntityPlayer player; //the player
    private NonNullList<ItemStack> inventory; //the stored items
    private String sortType; //the sort option (id/alphabetical)

    //Instantiated from GuiHandler
    public InventoryBackpack(EntityPlayer player, ItemStack backpackStack){
        this.backpackStack = backpackStack;
        this.player = player;
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        this.sortType = "id";
        readFromNBT(backpackStack.getTagCompound());
    }

    //Instantiated for crafting result (no-player specific opening)
    public InventoryBackpack(ItemStack backpackStack, boolean noPlayer){
        this.backpackStack = backpackStack;
        this.player = null;
        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        this.sortType = "id";
        readFromNBT(backpackStack.getTagCompound(), noPlayer);
    }

    public ItemStack getBackpackStack(){
        return backpackStack;
    }

    public EntityPlayer getPlayer(){
        return player;
    }

    public String getSortType() {
        return sortType;
    }

    public void toggleSortType() {
        if (sortType.equals("id"))
            sortType = "alphabetical";
        else
            sortType = "id";
        save();
    }

    @Override
    public int getSizeInventory() {
        return ((ItemBackpack)backpackStack.getItem()).getSize(backpackStack);
    }

    @Override
    public ItemStack getStackInSlot(int slotIndex) {
        return slotIndex >= this.getSizeInventory() ? ItemStack.EMPTY : this.inventory.get(slotIndex);
    }

    @Override
    public ItemStack decrStackSize(int slotIndex, int amount) {
        if (!inventory.get(slotIndex).isEmpty()) {
            if (inventory.get(slotIndex).getCount() <= amount) {
                ItemStack itemstack = inventory.get(slotIndex);
                inventory.set(slotIndex, ItemStack.EMPTY);
                return itemstack;
            }
            ItemStack itemstack1 = inventory.get(slotIndex).splitStack(amount);
            if (inventory.get(slotIndex).getCount() == 0) {
                inventory.set(slotIndex, ItemStack.EMPTY);
            }
            return itemstack1;
        }
        else {
            return ItemStack.EMPTY;
        }
    }

    @Override
    public void setInventorySlotContents(int slotIndex, ItemStack itemStack) {
        inventory.set(slotIndex, itemStack);
        if (!itemStack.isEmpty() && itemStack.getCount() > getInventoryStackLimit()) {
            itemStack.setCount(getInventoryStackLimit());
        }
    }

    @Override
    public String getName() {
        return ((ItemBackpack)backpackStack.getItem()).getName(backpackStack);
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {return new TextComponentString(getName());
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        ItemStack stack = ItemStack.EMPTY;
        if (!inventory.get(index).isEmpty()){
            stack = inventory.get(index);
            inventory.set(index, ItemStack.EMPTY);
        }
        return stack;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        //unnecessary, as it saves when closing
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {
        //unused
    }

    @Override
    public void closeInventory(EntityPlayer player) {
        //unused
    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack itemStack) {
        return (IronBackpacksHelper.areItemStacksTheSame(itemStack, backpackStack));
//        return true; //handled by BackpackSlot //TODO: fix this?
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) {
        //nothing
    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        for (int i = 0; i < inventory.size(); i++){
            inventory.set(i, ItemStack.EMPTY);
        }
    }

    //Helper methods for Botania's API and IBlockProvider
    public int hasStackInInv(Block blockToCheck, int meta){
        int total = 0;
        for (int i = 0; i < inventory.size(); i++){
            if (!inventory.get(i).isEmpty()){
                Block backpackItemAsBlock = Block.getBlockFromItem(inventory.get(i).getItem());
                if (backpackItemAsBlock.equals(blockToCheck) && inventory.get(i).getItemDamage() == meta){
                    total += inventory.get(i).getCount();
                }
            }
        }
        return total;
    }

    public boolean removeOneItem(Block blockToCheck, int meta){
        for (int i = 0; i < inventory.size(); i++){
            if (!inventory.get(i).isEmpty()){
                Block backpackItemAsBlock = Block.getBlockFromItem(inventory.get(i).getItem());
                if (backpackItemAsBlock.equals(blockToCheck) && inventory.get(i).getItemDamage() == meta){
                    inventory.get(i).shrink(1);
                    if (inventory.get(i).isEmpty()) inventory.set(i, ItemStack.EMPTY);
                    save();
                    return true;
                }
            }
        }
        return false;
    }


    //=========================================================HELPER METHODS=========================================================================

    /**
     * Checks if the backpack contains any items
     * @return - true if it does, false otherwise
     */
    public boolean isEmpty(){
        for (ItemStack stack : inventory) {
            if (stack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    /**
     * Called from the container and saves the backpack.
     * @param entityPlayer - the player with the backpack
     */
    public void onGuiSaved(EntityPlayer entityPlayer){
        if (!backpackStack.isEmpty()){
            save();
        }
    }

    /**
     * Saves the backpack (on the server side only)
     * @param player - the player with the backpack
     */
    public void saveWithSideCheck(EntityPlayer player){
        if (!player.world.isRemote) {
            onGuiSaved(player);
        }
    }

    /**
     * Updates the NBT data of the itemstack to save it
     */
    public void save(){

        NBTTagCompound nbtTagCompound = backpackStack.getTagCompound();

        if (nbtTagCompound == null) {
            nbtTagCompound = new NBTTagCompound();
        }

        writeToNBT(nbtTagCompound);
        backpackStack.setTagCompound(nbtTagCompound);
    }

    /**
     * Writes the data of the backpack to NBT form.
     * @param nbtTagCompound - the tag compound
     */
    public void writeToNBT(NBTTagCompound nbtTagCompound){
        if (!player.world.isRemote) { //server side only
            ItemStack tempStack = findParentItemStack(player);
            ItemStack stackToUse = (tempStack.isEmpty()) ? backpackStack : tempStack;

            nbtTagCompound = stackToUse.getTagCompound();

            // Write the ItemStacks in the inventory to NBT
            NBTTagList tagList = new NBTTagList();
            for (int i = 0; i < inventory.size(); i++) {
                if (!inventory.get(i).isEmpty()) {
                    NBTTagCompound tagCompound = new NBTTagCompound();
                    tagCompound.setByte(IronBackpacksConstants.NBTKeys.SLOT, (byte) i);
                    inventory.get(i).writeToNBT(tagCompound);
                    tagList.appendTag(tagCompound);
                }
            }
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.ITEMS, tagList);
            nbtTagCompound.setTag(IronBackpacksConstants.NBTKeys.SORT_TYPE, new NBTTagString(sortType));
        }
    }

    /**
     * Loads in the data stored in the NBT of this stack and puts the items in their respective slots.
     * @param nbtTagCompound - the tag compound
     */
    //ToDo: Really need to remove this nonsense in next refactor
    public void readFromNBT(NBTTagCompound nbtTagCompound){
        if (!player.world.isRemote) { //server side only
            ItemStack tempStack = findParentItemStack(player);
            backpackStack = (tempStack.isEmpty()) ? backpackStack : tempStack;
            if (!backpackStack.isEmpty()) {
                nbtTagCompound = backpackStack.getTagCompound();

                if (nbtTagCompound != null) {
                    //load in sortType
                    if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.SORT_TYPE)) {
                        this.sortType = nbtTagCompound.getString(IronBackpacksConstants.NBTKeys.SORT_TYPE);
                    }
                    //load in items
                    if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ITEMS)) {
                        NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.ITEMS, Constants.NBT.TAG_COMPOUND);
                        this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

                        for (int i = 0; i < tagList.tagCount(); i++) {
                            NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                            int j = stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                            if (i >= 0 && i <= inventory.size()) { //ToDo: Remove 2nd equals (so just less than) as per a 1.7.10 PR; test it
                                this.inventory.set(j, new ItemStack(stackTag));
                            }
                        }
                    }
                }
            }
        } else { //client side: load in the sort-type for updating the sort button's tooltip
            if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.SORT_TYPE)) {
                this.sortType = nbtTagCompound.getString(IronBackpacksConstants.NBTKeys.SORT_TYPE);
            }
        }
    }

    public void readFromNBT(NBTTagCompound nbtTagCompound, boolean noPlayer){
        if (noPlayer && (!backpackStack.isEmpty())) {
            nbtTagCompound = backpackStack.getTagCompound();

            if (nbtTagCompound != null) {
                //load in sortType
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.SORT_TYPE)) {
                    this.sortType = nbtTagCompound.getString(IronBackpacksConstants.NBTKeys.SORT_TYPE);
                }
                //load in items
                if (nbtTagCompound.hasKey(IronBackpacksConstants.NBTKeys.ITEMS)) {
                    NBTTagList tagList = nbtTagCompound.getTagList(IronBackpacksConstants.NBTKeys.ITEMS, Constants.NBT.TAG_COMPOUND);
                    this.inventory = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);

                    for (int i = 0; i < tagList.tagCount(); i++) {
                        NBTTagCompound stackTag = tagList.getCompoundTagAt(i);
                        int j = stackTag.getByte(IronBackpacksConstants.NBTKeys.SLOT);
                        if (i >= 0 && i <= inventory.size()) { //ToDo: Remove 2nd equals (so just less than) as per a 1.7.10 PR; test it
                            this.inventory.set(j, new ItemStack(stackTag));
                        }
                    }
                }
            }
        }
    }

    /**
     * Helper method to get the stack, and make sure it is unique.
     * @param entityPlayer - the player to check
     * @return - the itemstack if it is found, ItemStack.EMPTY otherwise
     */
    private ItemStack findParentItemStack(EntityPlayer entityPlayer){
        if (NBTUtils.hasUUID(backpackStack)){
            UUID parentUUID = new UUID(backpackStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID), backpackStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID));
            for (int i = 0; i < entityPlayer.inventory.getSizeInventory(); i++){
                ItemStack itemStack = entityPlayer.inventory.getStackInSlot(i);

                if (!itemStack.isEmpty() && itemStack.getItem() instanceof IBackpack && NBTUtils.hasUUID(itemStack)){
                    if (itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID) == parentUUID.getMostSignificantBits() && itemStack.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID) == parentUUID.getLeastSignificantBits()){
                        return itemStack;
                    }
                }
            }

            ItemStack equipped = PlayerWearingBackpackCapabilities.getEquippedBackpack(entityPlayer);
            if (!equipped.isEmpty() && equipped.getItem() instanceof IBackpack && NBTUtils.hasUUID(equipped)) {
                if (equipped.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.MOST_SIG_UUID) == parentUUID.getMostSignificantBits() && equipped.getTagCompound().getLong(IronBackpacksConstants.Miscellaneous.LEAST_SIG_UUID) == parentUUID.getLeastSignificantBits()) {
                    return equipped;
                }
            }
        }
        return ItemStack.EMPTY;
    }
}
