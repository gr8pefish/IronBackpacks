package main.ironbackpacks.client.gui;

import cpw.mods.fml.client.IModGuiFactory;
import cpw.mods.fml.client.config.GuiConfig;
import cpw.mods.fml.client.config.IConfigElement;
import main.ironbackpacks.IronBackpacks;
import main.ironbackpacks.ModInformation;
import main.ironbackpacks.util.ConfigHandler;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class ConfigGuiFactory implements IModGuiFactory {

    @Override
    public void initialize(Minecraft minecraftInstance) {}

    @Override
    public Class<? extends GuiScreen> mainConfigGuiClass() {
        return ConfigGui.class;
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }

    @Override
    public RuntimeOptionGuiHandler getHandlerFor(RuntimeOptionCategoryElement element) {
        return null;
    }

    @SuppressWarnings({ "rawtypes", "unchecked" })
    public static class ConfigGui extends GuiConfig {

        public ConfigGui(GuiScreen parent) {
            super(parent, null, ModInformation.ID, false, false, GuiConfig.getAbridgedConfigPath(IronBackpacks.instance.toString()));
        }          //null -> getConfigElements()

//        /** Compiles a list of config elements */
//        private static List<IConfigElement> getConfigElements() {
//            List<IConfigElement> list = new ArrayList<IConfigElement>();
//
//            //Add categories to config GUI
//            list.add(categoryElement(ConfigHandler.CATEGORY_GENERAL, "General", "mymod.configgui.ctgy.general"));
//            list.add(categoryElement(Config.CATEGORY_FOO, "Foo", "mymod.configgui.ctgy.foo"));
//            list.add(categoryElement(Config.CATEGORY_BAR, "Bar", "mymod.configgui.ctgy.bar"));
//
//            return list;
//        }
//
//        /** Creates a button linking to another screen where all options of the category are available */
//        private static IConfigElement categoryElement(String category, String name, String tooltip_key) {
//            return new DummyConfigElement.DummyCategoryElement(name, tooltip_key,
//                    new ConfigElement(Config.conf.getCategory(category)).getChildElements());
//        }
    }
}