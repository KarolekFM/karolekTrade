package net.karolek.trade.configuration.bukkit;

import lombok.Getter;
import lombok.Setter;
import net.karolek.trade.configuration.Configuration;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.logging.Level;

import static net.karolek.trade.utils.IoUtil.copy;


@Getter
@Setter
public abstract class BukkitConfiguration implements Configuration {

    protected final JavaPlugin plugin;
    protected final String fileName;
    protected final String prefix;

    private File configFile;
    private FileConfiguration fileConfiguration;

    public BukkitConfiguration(JavaPlugin plugin, String fileName, String prefix) {
        this.plugin = plugin;
        this.fileName = fileName;
        this.prefix = prefix;
        reloadConfig();
    }

    protected File getConfigFile() {
        if (configFile == null) {
            configFile = new File(getPlugin().getDataFolder(), fileName);
            if (!configFile.exists()) {
                configFile.getParentFile().mkdirs();
                InputStream resource = getPlugin().getResource(fileName);
                if (resource != null)
                    copy(resource, configFile);
            }
        }
        return configFile;
    }

    protected FileConfiguration getFileConfiguration() {
        if (fileConfiguration == null)
            fileConfiguration = YamlConfiguration.loadConfiguration(getConfigFile());
        return fileConfiguration;
    }

    @Override
    public void loadConfig() {
        getPlugin().getLogger().log(Level.INFO, "Loading '" + fileName + "'!");
        try {
            FileConfiguration f = getFileConfiguration();
            for (Field field : getClass().getFields()) {
                if (!Modifier.isStatic(field.getModifiers())) continue;
                if (!Modifier.isPublic(field.getModifiers())) continue;
                if (Modifier.isFinal(field.getModifiers())) continue;

                String path = prefix + field.getName().toLowerCase().replace("$", "-").replace("_", ".");

                Object o = f.get(path);

                if(o == null) continue;

                if ((field.getType() == String.class) && ((o instanceof String))) {
                    o = ChatColor.translateAlternateColorCodes('&', (String)o);
                }

                field.set(null, o);
            }
        } catch (Exception e) {
            getPlugin().getLogger().log(Level.WARNING, "An error occured while loading '" + fileName + "'!", e);
        }
    }

    @Override
    public void saveConfig() {
        getPlugin().getLogger().log(Level.INFO, "Saving '" + fileName + "'!");
        try {
            FileConfiguration f = getFileConfiguration();
            for (Field field : getClass().getFields()) {
                if (!Modifier.isStatic(field.getModifiers())) continue;
                if (!Modifier.isPublic(field.getModifiers())) continue;
                if (Modifier.isFinal(field.getModifiers())) continue;

                String path = prefix + field.getName().toLowerCase().replace("$", "-").replace("_", ".");
                f.set(path, field.get(null));
            }
            getFileConfiguration().save(getConfigFile());
        } catch (Exception e) {
            getPlugin().getLogger().log(Level.WARNING, "An error occured while saving '" + fileName + "'!", e);
        }
    }

    @Override
    public void reloadConfig() {
        configFile = null;
        fileConfiguration = null;
        loadConfig();
        saveConfig();
    }
}
