package com.predo.commands;

import com.predo.classes.ListaPontos;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RemoverPonto implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ListaPontos lista = new ListaPontos();

        lista = lista.carregar();

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;
            if(strings.length == 1){
                lista.pop(strings[0], p);
            }
            else if(strings.length > 1){
                p.sendMessage(ChatColor.GREEN + "Muitos argumentos no comando! (Modelo: /removerponto nome_do_ponto)");
            }

        }
        return true;
    }
}
