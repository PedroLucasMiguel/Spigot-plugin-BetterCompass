package com.predo.listeners;
import com.predo.commands.CriarPonto;
import com.predo.commands.MostrarPonto;
import com.predo.commands.Ponto;
import com.predo.commands.RemoverPonto;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
        this.getCommand("ponto").setExecutor(new Ponto());
        this.getCommand("removerponto").setExecutor(new RemoverPonto());
        this.getCommand("criarponto").setExecutor(new CriarPonto());
        this.getCommand("mostrarpontos").setExecutor(new MostrarPonto());
        System.out.println("[Better Compass] Inicializado com sucesso!");
    }

    @Override
    public void onDisable() {
        System.out.println("[Better Compass] Desativando...");
    }
}
