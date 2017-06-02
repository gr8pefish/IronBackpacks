package gr8pefish.ironbackpacks.integration.jei;

import gr8pefish.ironbackpacks.core.ModObjects;
import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.JEIPlugin;

@JEIPlugin
public class IronBackpacksJEIPlugin extends BlankModPlugin {

    @Override
    public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
        subtypeRegistry.useNbtForSubtypes(
                ModObjects.BACKPACK,
                ModObjects.UPGRADE
        );
    }
}
