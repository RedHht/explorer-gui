package com.redhht.gui;

import com.Zrips.CMI.CMI;
import com.Zrips.CMI.Containers.CMIUser;
import com.Zrips.CMI.Locale.Language;
import com.Zrips.CMI.Modules.Homes.CmiHome;
import com.Zrips.CMI.Modules.tp.Teleportations;
import com.redhht.ExplorerGui;
import com.redhht.utils.Configuration;
import com.samjakob.spigui.buttons.SGButton;
import com.samjakob.spigui.menu.SGMenu;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class HomeGui {

    public final static CMI instance = CMI.getInstance();

    public final static Configuration fileConfiguration = ExplorerGui.getInstance().mainConfiguration;

    public static void showMenu(Player player) {

        SGMenu menu = ExplorerGui.spiGUI.create("§b§lExplorador: §7¿Donde vamos?", 6);

        CMIUser sender = instance.getPlayerManager().getUser(player);
        ArrayList<String> homes = sender.getHomesList();

        for (String home : homes) {
            SGButton boton = new SGButton(getHome(home))
                    .withListener((InventoryClickEvent event) -> itemUtility(home, sender));
            menu.addButton(boton);
        }

        player.openInventory(menu.getInventory());
    }

    /**
     * Teleports the player to his home.
     *
     * @param homeString The name of the home.
     * @param sender The owner of the home.
     *
     */
    public static void itemUtility(String homeString, CMIUser sender) {

        Teleportations tpManager = instance.getTeleportations();
        CmiHome home = sender.getHome(homeString);

        tpManager.teleport(sender.getPlayer(), home.getLoc().getBukkitLoc(), Teleportations.TeleportType.Tp);

    }

    public static ItemStack getHome(String homeString) {

        ItemStack bed = new ItemStack(Material.RED_BED, 1);
        ItemMeta meta = bed.getItemMeta();

        assert meta != null;
        meta.setDisplayName("§e" + homeString);
        meta.setLore();

        bed.setItemMeta(meta);

        return bed;
    }

}
