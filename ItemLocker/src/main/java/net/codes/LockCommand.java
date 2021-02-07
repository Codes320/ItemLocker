package net.codes;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.UUID;

public class LockCommand implements CommandExecutor {

    private Main main;

    public LockCommand(Main main) {
        this.main = main;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (command.getName().equalsIgnoreCase("lockitem")) {

            Player player = (Player) sender;
            String hand = player.getInventory().getItemInMainHand().getType().toString();
            UUID playerUUID = player.getUniqueId();
            String lockMessage = main.getConfig().getString("LockMessage");
            String unlockMessage = main.getConfig().getString("UnlockMessage");


            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "You're not a player...");
            return true;
            }

            if (!player.hasPermission("lockitem.use")) {
                sender.sendMessage(ChatColor.RED + "You don't have permission...");
                return true;
            }

            if (player.getInventory().getItemInMainHand().getType().equals(Material.AIR)) {
                player.sendMessage(ChatColor.RED + "You must be holding an item to use this command!");
                return true;
            }

            if (!main.getCache().containsKey(playerUUID)) {
                main.getCache().put(player.getUniqueId(), new ArrayList<String>());
            }

            if (main.getCache().get(playerUUID).contains(hand)) {
                main.getCache().get(playerUUID).remove(hand);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', unlockMessage));
            } else {
                main.getCache().get(playerUUID).add(hand);
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', lockMessage));

            }

        }

        return false;
    }
}
