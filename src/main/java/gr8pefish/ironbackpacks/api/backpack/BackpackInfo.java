package gr8pefish.ironbackpacks.api.backpack;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.backpack.inventory.IBackpackInventoryProvider;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackType;
import gr8pefish.ironbackpacks.api.backpack.variant.BackpackVariant;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.UUID;

/**
 * The main class to hold info about a backpack.
 *
 * Contains {@link BackpackVariant}, a {@code List<{@link BackpackUpgrade}>},
 * an {@link IBackpackInventoryProvider} and an {@link UUID} of the owner.
 *
 * Also contains simple getters/setters for the above.
 *
 * Finally, has some additional methods, such as NBT serialization and deserialization.
 */
public class BackpackInfo implements INBTSerializable<NBTTagCompound> {


    // Constants

    public static final int NO_COLOR = -1;


    // Fields

    @Nonnull
    private final List<BackpackUpgrade> upgrades;
    @Nonnull
    private BackpackVariant backpackVariant;
    private IItemHandlerModifiable inventory;
    @Nullable
    private UUID owner;
    private int rgbColor;


    // Constructors

    private BackpackInfo(@Nonnull BackpackVariant backpackVariant, @Nonnull List<BackpackUpgrade> upgrades) {
        Preconditions.checkNotNull(backpackVariant, "Backpack variant cannot be null");
        Preconditions.checkNotNull(upgrades, "Upgrade list cannot be null");

        this.backpackVariant = backpackVariant;
        this.upgrades = upgrades;

        this.rgbColor = NO_COLOR; //uncolored by default
    }

    public BackpackInfo(@Nonnull BackpackVariant backpackVariant) {
        this(backpackVariant, Lists.newArrayList());
    }

    private BackpackInfo() {
        //noinspection ConstantConditions - null/null is automatically registered, so we know it's always there.
        this(new BackpackVariant(IronBackpacksAPI.getBackpackType(IronBackpacksAPI.NULL), BackpackSpecialty.NONE), Lists.newArrayList());
    }


    // Getters/Setters

    /**
     * Gets the variant of the backpack.
     *
     * @return - {@link BackpackVariant}
     */
    @Nonnull
    public BackpackVariant getVariant() {
        return backpackVariant;
    }

    /**
     * Sets the {@link BackpackVariant} of the backpack.
     *
     * @param variant - The backpack variant to set it too
     * @return - The updated backpack info
     */
    @Nonnull
    public BackpackInfo setVariant(@Nonnull BackpackVariant variant) {
        Preconditions.checkNotNull(variant, "Backpack variant cannot be null");
        backpackVariant = variant;
        return this;
    }

    /**
     * Gets an immutable list of the {@link BackpackUpgrade}s on the backpack.
     *
     * @return - An immutable list of the backpack's upgrades
     */
    @Nonnull
    public List<BackpackUpgrade> getUpgrades() {
        return ImmutableList.copyOf(upgrades);
    }

    /**
     * Adds a {@link BackpackUpgrade} to the backpack's upgrade list.
     * IMPORTANT: DOES NO CHECKING (other than nonnull)
     *
     * @param upgrade - The upgrade to add
     * @return - The updated backpack info
     */
    @Nonnull
    public BackpackInfo addUpgrade(@Nonnull BackpackUpgrade upgrade) {
        Preconditions.checkNotNull(upgrade, "Upgrade cannot be null");
        upgrades.add(upgrade);
        return this;
    }

    /**
     * Removes a {@link BackpackUpgrade} from the backpack's upgrade list.
     * IMPORTANT: DOES NO CHECKING (other than nonnull)
     *
     * @param upgrade - The upgrade to remove
     * @return - The updated backpack info
     */
    @Nonnull
    public BackpackInfo removeUpgrade(@Nonnull BackpackUpgrade upgrade) {
        Preconditions.checkNotNull(upgrade, "Upgrade cannot be null");
        upgrades.remove(upgrade);
        return this;
    }

    public IItemHandlerModifiable getInventory() {
        return inventory;
    }

    public BackpackInfo setInventory(@Nonnull IItemHandlerModifiable inventory) {
        this.inventory = inventory;
        return this;
    }

    /**
     * Returns the {@link UUID} of the owner.
     * WARNING: May be NULL if no owner is assigned.
     *
     * @return - The UUID of the owner
     */
    @Nullable
    public UUID getOwner() {
        return owner;
    }

    /**
     * Sets the {@link UUID} of the owner to this backpack.
     * WARNING: May be NULL for no owner.
     *
     * @param owner - The UUID to set
     * @return - The updated backpack info
     */
    @Nonnull
    public BackpackInfo setOwner(@Nullable UUID owner) {
        this.owner = owner;
        return this;
    }

    /**
     * Gets if the backpack is dyed/colored or not.
     *
     * @return - {@link boolean}
     */
    public boolean getIsColored() {
        return rgbColor != NO_COLOR;
    }

    /**
     * Gets the dyed color of the backpack, as the {@link int} representation of a RGB color value.
     *
     * @return - The RGB color
     */
    public int getRGBColor() {
        return rgbColor;
    }

    /**
     * Sets the dyed color of the backpack, using a {@link int} representation of a RGB color value.
     * Set to NO_COLOR (-1) to have no additional color.
     *
     * @param rgbColor - the integer representation of a RGB colo value
     * @return - the updated {@link BackpackInfo}
     */
    public BackpackInfo setRGBColor(int rgbColor) {
        this.rgbColor = rgbColor;
        return this;
    }

    // INBTSerializable

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();

        // Serialize backpack info //TODO: Serialize variant directly
        tag.setString("type", backpackVariant.getBackpackType().getIdentifier().toString());
        tag.setString("spec", backpackVariant.getBackpackSpecialty().name());
        if (owner != null)
            tag.setString("own", owner.toString());
        if (getIsColored())
            tag.setInteger("color", rgbColor);

        // Serialize upgrade
        NBTTagList installedUpgrades = new NBTTagList();
        for (BackpackUpgrade backpackUpgrade : upgrades)
            installedUpgrades.appendTag(new NBTTagString(backpackUpgrade.getIdentifier().toString()));
        tag.setTag("upgrade", installedUpgrades);

        return tag;
    }

    @Override
    public void deserializeNBT(NBTTagCompound nbt) {
        // Deserialize backpack info
        //TODO: Deserialize from variant directly
        backpackVariant = new BackpackVariant(IronBackpacksAPI.getBackpackType(new ResourceLocation(nbt.getString("type"))), BackpackSpecialty.getBackpackSpecialty(nbt.getString("spec")));
        if (nbt.hasKey("own"))
            owner = UUID.fromString(nbt.getString("own"));
        if (nbt.hasKey("color")) {
            rgbColor = nbt.getInteger("color");
        }

        // Deserialize upgrade
        NBTTagList installedUpgrades = nbt.getTagList("upgrade", 8);
        for (int i = 0; i < installedUpgrades.tagCount(); i++) {
            ResourceLocation identifier = new ResourceLocation(installedUpgrades.getStringTagAt(i));
            BackpackUpgrade backpackUpgrade = IronBackpacksAPI.getUpgrade(identifier);
            if (!backpackUpgrade.isNull())
                upgrades.add(backpackUpgrade);
        }
    }

    public boolean conflicts(@Nullable BackpackUpgrade upgrade) {
        if (upgrade == null)
            return false;

        for (BackpackUpgrade installed : upgrades)
            if (upgrade.isConflicting(installed))
                return true;

        return false;
    }

    public boolean hasUpgrade(@Nullable BackpackUpgrade backpackUpgrade) {
        return upgrades.contains(backpackUpgrade);
    }

    public int getPointsUsed() {
        int used = 0;
        for (BackpackUpgrade backpackUpgrade : upgrades)
            used += backpackUpgrade.getApplicationCost();

        return used;
    }

    public int getMaxPoints() {
        return backpackVariant.getBackpackType().getBaseMaxUpgradePoints() + (backpackVariant.getBackpackSpecialty() == BackpackSpecialty.UPGRADE ? 5 : 0);
    }

    // Helper methods

    @Nonnull
    public static BackpackInfo fromStack(@Nonnull ItemStack stack) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");

        if (stack.isEmpty() || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("packInfo"))
            return new BackpackInfo();

        BackpackInfo tagged = fromTag(stack.getTagCompound().getCompoundTag("packInfo"));

        ItemStackHandler stackHandler = new ItemStackHandler(tagged.backpackVariant.getBackpackSize().getTotalSize());
        NBTTagList tagList = stack.getTagCompound().getTagList("packInv", 10);
        for (int i = 0; i < tagList.tagCount(); i++)
            stackHandler.setStackInSlot(i, new ItemStack(tagList.getCompoundTagAt(i)));

        return tagged.setInventory(stackHandler);
    }

    @Nonnull
    public static BackpackInfo fromTag(@Nullable NBTTagCompound tag) {
        BackpackInfo backpackInfo = new BackpackInfo();
        if (tag == null || tag.hasNoTags())
            return backpackInfo;

        backpackInfo.deserializeNBT(tag);
        return backpackInfo;
    }

    @Nonnull
    public static BackpackInfo upgradeTo(@Nonnull BackpackInfo toUpgrade, @Nonnull BackpackType newType, @Nonnull BackpackSpecialty newSpecialty) {
        return new BackpackInfo(new BackpackVariant(newType, newSpecialty), toUpgrade.upgrades)
                .setOwner(toUpgrade.getOwner())
                .setInventory(toUpgrade.inventory);
    }

    /**
     * Gets the color from the backpack's tag data directly
     * (i.e. inexpensive lookup for rendering purposes)
     *
     * @param stack - The backpack item stack to check
     * @return - The color in RGB, -1 if none
     */
    public static int getColor(@Nonnull ItemStack stack) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");

        if (stack.hasTagCompound()) {
            NBTTagCompound compound = stack.getTagCompound();
            if (compound.hasKey("packInfo")) {
                NBTTagCompound packInfo = compound.getCompoundTag("packInfo");
                if (packInfo.hasKey("color")) {
                    return packInfo.getInteger("color");
                }
            }
        }

        return -1; // no color
    }
}
