package gr8pefish.ironbackpacks.achievements;

import net.minecraft.stats.Achievement;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

//Credit to Vazkii for the code
public final class AchievementTriggerer {

    private AchievementTriggerer() {}

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        if(event.crafting != null && event.crafting.getItem() instanceof IBackpackCraftAchievement) {
            Achievement achievement = ((IBackpackCraftAchievement) event.crafting.getItem()).getAchievementOnCraft(event.crafting, event.player, event.craftMatrix);
            if(achievement != null)
                event.player.addStat(achievement, 1);
        }
    }

}
