package com.predo.commands;

import com.predo.classes.ListaPontos;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;

public class CriarPonto implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        ListaPontos pontos = new ListaPontos();

        //carregar retorna endereço de memória da lista anterior carregada!
        pontos = pontos.carregar(); //REWORKED

        if(commandSender instanceof Player){
            Player p = (Player) commandSender;

            if(strings.length == 1){
                pontos.push(strings[0], p.getLocation());
                p.sendMessage(ChatColor.GREEN + "Ponto criado com sucesso!");
            }

            else if(strings.length > 1){
                p.sendMessage(ChatColor.GREEN + "Muitos argumentos no comando! (Modelo: /criarponto nome_do_ponto)");
            }

            else{
                p.sendMessage(ChatColor.GREEN + "Poucos argumentos no comando! (Modelo: /criarponto nome_do_ponto)");
            }

        }
        return true;
    }
}
