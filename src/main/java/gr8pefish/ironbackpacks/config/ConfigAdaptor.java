package gr8pefish.ironbackpacks.config;

import gr8pefish.ironbackpacks.IronBackpacks;
import gr8pefish.ironbackpacks.util.Logger;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.config.Property;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//Thanks vazkii!

/**
 * A class to manage changed config with versions
 */
public class ConfigAdaptor {

    private boolean enabled;
    private int lastBuild;
    private int currentBuild;

    private Map<String, List<AdaptableValue>> adaptableValues = new HashMap();
    private List<String> changes = new ArrayList();

    public ConfigAdaptor(boolean enabled) {
        this.enabled = enabled;

        String lastVersion = IronBackpacks.proxy.getModVersion();

        if (lastVersion != null){ //client
            Logger.info("Version: "+lastVersion);
            try {
                String last = lastVersion.substring(lastVersion.lastIndexOf('-') + 1);
                Logger.info("Version2: "+last);
                lastBuild = Integer.parseInt(last);
            } catch(NumberFormatException e) {
                this.enabled = false;
            }
        }
    }

    public <T> void adaptProperty(Property prop, T val) {
        if(!enabled)
            return;

        String name = prop.getName();

        if(!adaptableValues.containsKey(name))
            return;

        AdaptableValue<T> bestValue = null;
        for(AdaptableValue<T> value : adaptableValues.get(name)) {
            if(value.version >= lastBuild) // If 1.7.10 is newer than what we last used we don't care about it
                continue;

            if(bestValue == null || value.version > bestValue.version)
                bestValue = value;
        }

        if(bestValue != null) {
            T expected = bestValue.value;
            T def = (T) prop.getDefault();

            if(areEqualNumbers(val, expected) && !areEqualNumbers(val, def)) {
                prop.setValue(def.toString());
                changes.add(" " + prop.getName() + ": " + val + " -> " + def);
            }
        }
    }

    public <T> void addMapping(int version, String key, T val) {
        if(!enabled)
            return;

        AdaptableValue<T> adapt = new AdaptableValue<T>(version, val);
        if(!adaptableValues.containsKey(key)) {
            ArrayList list = new ArrayList();
            adaptableValues.put(key, list);
        }

        List<AdaptableValue> list = adaptableValues.get(key);
        list.add(adapt);
    }

    public boolean areEqualNumbers(Object v1, Object v2) {
        double epsilon = 1.0E-6;
        float v1f = ((Number) v1).floatValue();
        float v2f;

        if(v2 instanceof String)
            v2f = Float.parseFloat((String) v2);
        else v2f = ((Number) v2).floatValue();

        return Math.abs(v1f - v2f) < epsilon;
    }

    public void tellChanges(EntityPlayer player) {
        if(changes.size() == 0)
            return;

        player.addChatComponentMessage(new TextComponentTranslation("botaniamisc.adaptativeConfigChanges").setStyle(new Style().setColor(TextFormatting.GOLD)));
        for(String change : changes)
            player.addChatMessage(new TextComponentString(change).setStyle(new Style().setColor(TextFormatting.LIGHT_PURPLE)));
    }

    public void addMappingInt(int version, String key, int val) {
        this.<Integer>addMapping(version, key, val);
    }

    public void addMappingDouble(int version, String key, double val) {
        this.<Double>addMapping(version, key, val);
    }

    public void addMappingBool(int version, String key, boolean val) {
        this.<Boolean>addMapping(version, key, val);
    }

    public void adaptPropertyInt(Property prop, int val) {
        this.<Integer>adaptProperty(prop, val);
    }

    public void adaptPropertyDouble(Property prop, double val) {
        this.<Double>adaptProperty(prop, val);
    }

    public void adaptPropertyBool(Property prop, boolean val) {
        this.<Boolean>adaptProperty(prop, val);
    }

    public static class AdaptableValue<T> {

        public final int version;
        public final T value;
        public final Class<? extends T> valueType;

        public AdaptableValue(int version, T value) {
            this.version = version;
            this.value = value;
            valueType = (Class<? extends T>) value.getClass();
        }

    }

    public static String getLatestFilenameFromCurse(String urlString) {
        try
        {
            while (urlString != null && !urlString.isEmpty())
            {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(false);
                urlString = connection.getHeaderField("Location");

                if (urlString != null && (urlString.endsWith(".jar") || urlString.endsWith(".zip")))
                {
                    return urlString.substring(urlString.lastIndexOf("/") + 1);
                }
            }
        } catch (MalformedURLException e)
        {
            Logger.error("Malformed URL was given when searching in Curse database!");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    public static String getLatestFilenameFromGithub(String urlString) {
        try
        {
            while (urlString != null && !urlString.isEmpty())
            {
                URL url = new URL(urlString);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setInstanceFollowRedirects(false);
                urlString = connection.getHeaderField("Location");

                if (urlString != null && (urlString.endsWith(".jar") || urlString.endsWith(".zip")))
                {
                    return urlString.substring(urlString.lastIndexOf("/") + 1);
                }
            }
        } catch (MalformedURLException e)
        {
            Logger.error("Malformed URL was given when searching in Curse database!");
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

}

