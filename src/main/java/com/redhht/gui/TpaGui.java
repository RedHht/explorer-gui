package com.redhht.gui;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.Locale.Language;
import com.Zrips.CMI.Modules.Economy.CMIEconomyAcount;
import com.Zrips.CMI.Modules.tp.TpManager;
import com.redhht.ExplorerGui;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;

public class TpaGui implements Listener {

    public final static CMI instance = CMI.getInstance();

    public final static TpManager tpManager = instance.getTpManager();
    public final static Language lm = instance.getLM();

    public final static FileConfiguration fileConfiguration = ExplorerGui.getInstance().mainConfiguration.getRawConfig();

    public static void showMenu(Player player) {

        SGMenu menu = ExplorerGui.spiGUI.create("§b§lExplorador: §7¿Donde vamos?", 6);

        for (Player receiver:
                Bukkit.getOnlinePlayers()) {
            if (!receiver.equals(player)) {
                SGButton boton = new SGButton(getPlayerHead(receiver))
                        .withListener((InventoryClickEvent event) -> itemUtility(player, receiver, menu));
                menu.addButton(boton);
            }
        }

        player.openInventory(menu.getInventory());
    }


    public static void itemUtility(Player sender, Player receiver, SGMenu menu) {
        if (takeMoney(sender)) {
            tpManager.addTpRequest(sender, receiver, TpManager.TpAction.tpa);

            sender.sendMessage("§a§lExplorador§7: §eTu peticion a §c" + receiver.getDisplayName() + "§e fue enviada.");

            receiver.sendMessage("§a§lExplorador§7: §eHey, §c" + sender.getDisplayName() + " §equiere ir contigo");
            receiver.sendMessage("§eAcepta la solicitud con §a/tpaccept §eo deniegala con §e/tpdeny");
        } else {
            sender.sendMessage("§cNo tienes suficientes fondos.");

            sender.closeInventory();
        }
    }

    /**
     * Take a defined amount of money from a player's account.
     *
     * @param player The player we are going take money from.
     * @return true if the operation was completed successfully and false if it wasn't
     */
    public static boolean takeMoney(Player player) {
        CMIUser user = instance.getPlayerManager().getUser(player);

        CMIEconomyAcount economyAcount = user.getEconomyAccount();

        double cost = fileConfiguration.getDouble("tpacost");

        if (economyAcount.getBalance() >= cost) {
            economyAcount.withdraw(cost);
            player.sendMessage("§eSe retiraron §a$" + cost + " §ede tu cuenta.");
            return true;
        } else {
            return false;
        }
    }

    public static ItemStack getPlayerHead(Player player) {
        double cost = fileConfiguration.getDouble("tpacost");

        String[] lore = {
                "§7",
                "§eHaz clic para solicitar",
                "§eteletransportarte.",
                "§7",
                "§7Costo: §6$§e" + cost
        };
        ItemStack head = new ItemStack(Material.PLAYER_HEAD, 1);
        SkullMeta skullmeta = (SkullMeta) head.getItemMeta();

        assert skullmeta != null;
        skullmeta.setDisplayName(player.getDisplayName());
        skullmeta.setLore(Arrays.asList(lore));
        skullmeta.setOwningPlayer((OfflinePlayer) player);

        head.setItemMeta(skullmeta);

        return head;
    }
}
