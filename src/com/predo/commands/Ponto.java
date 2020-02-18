package com.predo.commands;

import com.predo.classes.ListaPontos;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Ponto implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ListaPontos lista = new ListaPontos();

        if(commandSender instanceof Player) {
            Player p = (Player) commandSender;

            lista = lista.carregar();

            if(strings.length == 1){
                if(strings[0].compareTo("spawn") == 0){
                    p.setCompassTarget(p.getWorld().getSpawnLocation());
                    p.sendMessage(ChatColor.GREEN + "Bússola atualizada!");
                }
                else{
                    lista.changePoint(strings[0], p);
                }
            }
            else if(strings.length > 1){
                p.sendMessage(ChatColor.GREEN + "Muitos argumentos no comando! (Modelo: /ponto nome_do_ponto)");
            }
            else{
                p.sendMessage(ChatColor.GREEN + "Poucos argumentos no comando! (Modelo: /ponto nome_do_ponto)");
            }
        }

        return true;
    }

}
