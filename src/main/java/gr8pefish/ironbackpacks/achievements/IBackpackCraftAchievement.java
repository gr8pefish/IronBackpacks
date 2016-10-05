package gr8pefish.ironbackpacks.achievements;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

//cred to Vazkii for the base code
public interface IBackpackCraftAchievement {

    public Achievement getAchievementOnCraft(ItemStack stack, EntityPlayer player, IInventory matrix);
}
