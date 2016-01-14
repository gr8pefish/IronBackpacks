package main.ironbackpacks.registry;

public class IronBackpacksRegistry {

    public static void preInitClient(){
        IBGuiButtonRegistry.initButtons();
    }

    public static void preInitServer(){
        IBGuiButtonRegistry.initButtons();
    }
}
