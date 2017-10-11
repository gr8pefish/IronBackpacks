package gr8pefish.ironbackpacks.achievements;
/*
import gr8pefish.ironbackpacks.api.Constants;
import gr8pefish.ironbackpacks.registry.ItemRegistry;
import gr8pefish.ironbackpacks.util.IronBackpacksConstants;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.common.MinecraftForge;

public final class IronBackpacksAchievements {

    public static AchievementPage ibAchievementsPage;
    public static int pageIndex;

    public static Achievement basicPackCrafted;
    public static Achievement ironPackCrafted;
    public static Achievement goldPackCrafted;
    public static Achievement diamondPackCrafted;

    public static Achievement storageEmphasisPackCrafted;
    public static Achievement upgradeEmphasisPackCrafted;

    public static Achievement upgradeCrafted;

    public static void init() {

        //Achievements themselves
        basicPackCrafted = new AchievementIronBackpacks(IronBackpacksConstants.Achievements.Names.BASIC_BACKPACK_CRAFTED, -2, 0, ItemRegistry.basicBackpack, null);
        ironPackCrafted = new AchievementIronBackpacks(IronBackpacksConstants.Achievements.Names.IRON_BACKPACK_CRAFTED, -0, 0, ItemRegistry.ironBackpackStorageEmphasis, basicPackCrafted);
        goldPackCrafted = new AchievementIronBackpacks(IronBackpacksConstants.Achievements.Names.GOLD_BACKPACK_CRAFTED, 2, 0, ItemRegistry.goldBackpackStorageEmphasis, ironPackCrafted);
        diamondPackCrafted = new AchievementIronBackpacks(IronBackpacksConstants.Achievements.Names.DIAMOND_BACKPACK_CRAFTED, 4, 0, ItemRegistry.diamondBackpackStorageEmphasis, goldPackCrafted);

        //Achievements Page
        pageIndex = AchievementPage.getAchievementPages().size();
        ibAchievementsPage = new AchievementPage(Constants.MOD_NAME, AchievementIronBackpacks.achievements.toArray(new Achievement[AchievementIronBackpacks.achievements.size()]));
        AchievementPage.registerAchievementPage(ibAchievementsPage);

        //Register trigger for the achievements
        MinecraftForge.EVENT_BUS.register(AchievementTriggerer.class);
    }

}
*/