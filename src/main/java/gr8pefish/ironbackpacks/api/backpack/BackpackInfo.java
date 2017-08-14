package gr8pefish.ironbackpacks.api.backpack;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import gr8pefish.ironbackpacks.api.IronBackpacksAPI;
import gr8pefish.ironbackpacks.api.inventory.IBackpackInventoryProvider;
import gr8pefish.ironbackpacks.api.upgrade.BackpackUpgrade;
import gr8pefish.ironbackpacks.api.variant.BackpackSpecialty;
import gr8pefish.ironbackpacks.api.variant.BackpackVariant;
import gr8pefish.ironbackpacks.api.variant.BackpackVariantEnum;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.items.IItemHandler;

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

    @Nonnull
    private BackpackVariant backpackVariant;
    @Nonnull
    private final List<BackpackUpgrade> upgrades;
    @Nonnull
    private IBackpackInventoryProvider inventoryProvider;
    @Nullable
    private UUID owner;

    //Constructors

    private BackpackInfo(@Nonnull BackpackVariant backpackVariant, @Nonnull List<BackpackUpgrade> upgrades) {
        Preconditions.checkNotNull(backpackVariant, "Backpack variant cannot be null");
        Preconditions.checkNotNull(upgrades, "Upgrade list cannot be null");

        this.backpackVariant = backpackVariant;
        this.upgrades = upgrades;
    }

    public BackpackInfo(@Nonnull BackpackVariant backpackVariant) {
        this(backpackVariant, Lists.newArrayList());
    }

    private BackpackInfo() {
        //noinspection ConstantConditions - null/null is automatically registered, so we know it's always there.
        this(new BackpackVariant(IronBackpacksAPI.getBackpackType(IronBackpacksAPI.NULL), BackpackSpecialty.NONE), Lists.newArrayList()); //TODO: shouldn't do "new", get from registry?
    }


    //Getters/Setters

    /**
     * Gets the variant of the backpack.
     *
     * @return - {@link BackpackVariant}
     */
    @Nonnull
    public BackpackVariant getVariant() {
        return backpackVariant;
    }

    public BackpackVariantEnum getVariantEnum() {
        return BackpackVariantEnum.getVariant(backpackVariant);
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

    /**
     * Gets the {@link IBackpackInventoryProvider} for the backpack.
     *
     * @return - The inventory provider
     */
    @Nonnull
    public IBackpackInventoryProvider getInventoryProvider() {
        return inventoryProvider;
    }

    /**
     * Gets the {@link IItemHandler} for the backpack.
     * Helper method, as the IItemHandler is obtained via the {@link IBackpackInventoryProvider}
     *
     * @return - The item handler
     */
    @Nonnull
    public IItemHandler getStackHandler() {
        return inventoryProvider.getInventory(BackpackVariantEnum.getVariant(backpackVariant));
    }

    /**
     * Sets the {@link IBackpackInventoryProvider} for the backpack.
     *
     * @param inventoryProvider - The inventory provider to set
     * @return - The updated backpack info
     */
    @Nonnull
    public BackpackInfo setInventoryProvider(@Nonnull IBackpackInventoryProvider inventoryProvider) {
        this.inventoryProvider = inventoryProvider;
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


    //TODO: Move/Edit/Cleanup all below here

    //Helper methods (move to another class)

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
        return backpackVariant.getType().getBaseMaxUpgradePoints() + (backpackVariant.getSpecialty() == BackpackSpecialty.UPGRADE ? 5 : 0);
    }

    // INBTSerializable

    @Override
    public NBTTagCompound serializeNBT() {
        NBTTagCompound tag = new NBTTagCompound();

        // Serialize backpack info //TODO: Serialize variant directly
        tag.setString("type", backpackVariant.getType().getIdentifier().toString());
        tag.setString("spec", backpackVariant.getSpecialty().name());
        if (owner != null)
            tag.setString("own", owner.toString());

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
        backpackVariant = new BackpackVariant(IronBackpacksAPI.getBackpackType(new ResourceLocation(nbt.getString("type"))), BackpackSpecialty.getSpecialty(nbt.getString("spec")));
        if (nbt.hasKey("own"))
            owner = UUID.fromString(nbt.getString("own"));

        // Deserialize upgrade
        NBTTagList installedUpgrades = nbt.getTagList("upgrade", 8);
        for (int i = 0; i < installedUpgrades.tagCount(); i++) {
            ResourceLocation identifier = new ResourceLocation(installedUpgrades.getStringTagAt(i));
            BackpackUpgrade backpackUpgrade = IronBackpacksAPI.getUpgrade(identifier);
            if (!backpackUpgrade.isNull())
                upgrades.add(backpackUpgrade);
        }
    }

    @Nonnull
    public static BackpackInfo fromStack(@Nonnull ItemStack stack) {
        Preconditions.checkNotNull(stack, "ItemStack cannot be null");

        if (stack.isEmpty() || !stack.hasTagCompound() || !stack.getTagCompound().hasKey("packInfo"))
            return new BackpackInfo();

        BackpackInfo tagged = fromTag(stack.getTagCompound().getCompoundTag("packInfo"));
        return tagged.setInventoryProvider(stack.getCapability(IronBackpacksAPI.BACKPACK_INV_CAPABILITY, null));
    }

    @Nonnull
    public static BackpackInfo fromTag(@Nullable NBTTagCompound tag) {
        BackpackInfo backpackInfo = new BackpackInfo();
        if (tag == null || tag.hasNoTags())
            return backpackInfo;

        backpackInfo.deserializeNBT(tag);
        return backpackInfo;
    }

    @Override
    public String toString() {
        String strType = backpackVariant.getType().isNull() ? "NONE" : backpackVariant.getType().toString();
        String specType = backpackVariant.getSpecialty() == null ? "NONE" : backpackVariant.getSpecialty().toString();
        String stackType = inventoryProvider == null ? "NONE" : inventoryProvider.toString();
        return "TYPE: " + strType + " --- SPECIALTY: " + specType + " --- STACK HANDLER: " + stackType;
    }

}