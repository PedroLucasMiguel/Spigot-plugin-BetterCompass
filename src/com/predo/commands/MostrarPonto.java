package com.predo.commands;

import com.predo.classes.ListaPontos;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MostrarPonto implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ListaPontos lista = new ListaPontos();
        if(commandSender instanceof Player){
            Player p = (Player) commandSender;

            if(strings.length >= 1){
                p.sendMessage(ChatColor.GREEN + "Muitos argumentos no comando! (Modelo: /mostrarponto)");
            }
            else{
                //Carrega informações da serialização
                lista = lista.carregar();

                lista.mostrarNomes(p);
            }
        }
        return true;
    }
}
