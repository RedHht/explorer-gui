package com.redhht.commands;

import com.redhht.gui.TpaGui;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpaMenuCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Player operador = (Player) commandSender;

        if (operador.isOp() && command.getName().equals("tpamenu")) {
            TpaGui.showMenu(Bukkit.getPlayer(strings[0]));
        }

        return true;
    }
}
