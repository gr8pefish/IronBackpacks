package main.ironbackpacks.integration.deprecated;

import net.minecraft.entity.player.EntityPlayer;

public class OpenBlocksGliderSupport extends OpenBlocksNoSupport {

    @Override
    public boolean isGliding(EntityPlayer player){
//        return !EntityHangGlider.isGliderDeployed(player);
    }
}
