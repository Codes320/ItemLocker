package net.codes;


import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public final class Main extends JavaPlugin {

    private HashMap<UUID, List<String>> cache = new HashMap<>();
    private StorageAPI storage;

    public void onEnable() {
        getCommand("lockitem").setExecutor(new LockCommand(this));
        getServer().getPluginManager().registerEvents(new DropListener(this), this);
        storage = new StorageAPI(this);

        this.getConfig().options().copyDefaults();
        this.saveDefaultConfig();
        getStorageAPI().initiateFiles();

        if (getStorageAPI().getModifyPlayerData() == null){
            getStorageAPI().initiateFiles();
        } else {
            if (getStorageAPI().getModifyPlayerData().getConfigurationSection("data") != null) {
                if (!getStorageAPI().getModifyPlayerData().getConfigurationSection("data").getKeys(false).isEmpty()) {
                    getStorageAPI().loadItems();
                }
            }
        }
    }

    public void onDisable() {
        if (!cache.isEmpty()) { getStorageAPI().saveItems(); }
    }

    public HashMap<UUID, List<String>> getCache() { return this.cache; }
    public StorageAPI getStorageAPI() { return this.storage; }
}


