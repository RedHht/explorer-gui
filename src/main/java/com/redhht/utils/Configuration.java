package com.redhht.utils;

import com.redhht.ExplorerGui;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

public class Configuration {

    private final static ExplorerGui javaPlugin = ExplorerGui.getInstance();

    public File file;
    public FileConfiguration config;

    public Configuration(String resourceFile) {
        this.file = new File(javaPlugin.getDataFolder(), resourceFile);

        if (!file.exists()) {
            file.getParentFile().mkdirs();
            javaPlugin.saveResource(resourceFile, false);
        }

        config = new YamlConfiguration();

        try {
            config.load(file);
        } catch (IOException | InvalidConfigurationException exception) {
            exception.printStackTrace();
        }
     }

    public FileConfiguration getRawConfig() {
        return config;
    }

    public List<String> replacePlaceholders(String path, String... placeholders) {
        List<String> originalStringList = config.getStringList(path);

        for (String line : originalStringList) {

        }
    }
}
