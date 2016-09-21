//package gr8pefish.ironbackpacks.entity.extendedProperties;
//
//import gr8pefish.ironbackpacks.api.Constants;
//import net.minecraft.entity.Entity;
//import net.minecraft.entity.EntityLivingBase;
//import net.minecraft.item.ItemStack;
//import net.minecraft.nbt.NBTTagCompound;
//import net.minecraft.nbt.NBTTagList;
//import net.minecraft.world.World;
//import net.minecraftforge.common.IExtendedEntityProperties;
//
//public class PlayerBackpackProperties implements IExtendedEntityProperties {
//
//    public static final String PROP_PACK_ID = Constants.MODID;
//    private ItemStack equippedBackpack;
//    private ItemStack currentBackpack;
//
//    @Override
//    public void saveNBTData(NBTTagCompound tag) {
//
//        //make new list
//        NBTTagList tagList = new NBTTagList();
//
//        //make new compound for the equipped pack
//        NBTTagCompound equipped = new NBTTagCompound();
//        if (equippedBackpack != null) {
//            equippedBackpack.writeToNBT(equipped);
//        }else{
//            equipped.setBoolean("noEquipped", false);
//        }
//        tagList.appendTag(equipped);
//
//        //make another for the saved one
//        NBTTagCompound current = new NBTTagCompound();
//        if (currentBackpack != null) {
//            currentBackpack.writeToNBT(current);
//        }else{
//            current.setBoolean("noCurrent", false);
//        }
//        tagList.appendTag(current);
//
//        //save all to the tag
//        tag.setTag(PROP_PACK_ID, tagList);
//    }
//
//    @Override
//    public void loadNBTData(NBTTagCompound tag) {
//        NBTTagList tagList = tag.getTagList(PROP_PACK_ID, net.minecraftforge.common.util.Constants.NBT.TAG_COMPOUND);
//
//        //get the equipped backpack without crashing
//        if (!tagList.getCompoundTagAt(0).hasKey("noEquipped")){ //if the key doesn't exist
//            try {
//                equippedBackpack = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(0));
//            } catch (NullPointerException e) { //might as well keep this catch statement
//                equippedBackpack = null;
//            }
//        } else {
//            equippedBackpack = null;
//        }
//
//        //get the current backpack without crashing
//        if (!tagList.getCompoundTagAt(1).hasKey("noCurrent")) {
//            try {
//                currentBackpack = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(1));
//            } catch (NullPointerException e) {
//                currentBackpack = null;
//            }
//        } else {
//            currentBackpack = null;
//        }
//    }
//
//    @Override
//    public void init(Entity entity, World world) {
//
//    }
//
//    //Getters and setters
//
//    public ItemStack getEquippedBackpack() {
//        return equippedBackpack;
//    }
//
//    public PlayerBackpackProperties setEquippedBackpack(ItemStack stack) {
//        this.equippedBackpack = stack;
//        return this;
//    }
//
//    public ItemStack getCurrentBackpack() {
//        return currentBackpack;
//    }
//
//    public PlayerBackpackProperties setCurrentBackpack(ItemStack stack) {
//        this.currentBackpack = stack;
//        return this;
//    }
//
//    //Other helper methods
//
//    public static void create(EntityLivingBase livingBase) {
//        livingBase.registerExtendedProperties(PROP_PACK_ID, new PlayerBackpackProperties());
//    }
//
//    public static PlayerBackpackProperties get(EntityLivingBase livingBase) {
//        return (PlayerBackpackProperties) livingBase.getExtendedProperties(PROP_PACK_ID);
//    }
//
//    //Static methods
//
//    public static ItemStack getEquippedBackpack(EntityLivingBase livingBase) {
//        return get(livingBase).getEquippedBackpack();
//    }
//
//    public static void setEquippedBackpack(EntityLivingBase livingBase, ItemStack stack) {
//        get(livingBase).setEquippedBackpack(stack);
//    }
//
//    public static ItemStack getCurrentBackpack(EntityLivingBase livingBase) {
//        return get(livingBase).getCurrentBackpack();
//    }
//
//    public static void setCurrentBackpack(EntityLivingBase livingBase, ItemStack stack) {
//        get(livingBase).setCurrentBackpack(stack);
//    }
//
//    public static void reset(EntityLivingBase livingBase) {
//        get(livingBase).setEquippedBackpack(null);
//        get(livingBase).setCurrentBackpack(null);
//    }
//}