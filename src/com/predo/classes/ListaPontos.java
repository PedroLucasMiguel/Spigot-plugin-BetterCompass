package com.predo.classes;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.*;
import java.util.Objects;

public class ListaPontos implements Serializable {
    private String nome;
    private String world;
    private double x;
    private double y;
    private double z;
    private ListaPontos next;
    private ListaPontos prev;
    private static ListaPontos head;
    private static ListaPontos tail;
    private ListaPontos headS;
    private ListaPontos tailS;

    private static void changeHead(ListaPontos newHead){ //Define o novo valor da head
        head = newHead;
    }

    private static void changeTail(ListaPontos newTail){ //Define o valor da tail
        tail = newTail;
    }

    private static ListaPontos getHead(){
        return head;
    }

    private static ListaPontos getTail(){
        return tail;
    }

    public ListaPontos carregar(){
        ListaPontos pontos = new ListaPontos();
        try {
            FileInputStream fin = new FileInputStream("locations.bin");
            ObjectInputStream oinput = new ObjectInputStream(fin);
            pontos = (ListaPontos) oinput.readObject();
            oinput.close();
            fin.close();
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("[Better Compass] - Arquivo ainda inexistente!");
        }
        return pontos;
    }

    public void salvar(){
        try {
            this.headS = getHead();
            this.tailS = getTail();
            FileOutputStream fout = new FileOutputStream("locations.bin");
            ObjectOutputStream oout = new ObjectOutputStream(fout);
            oout.writeObject(this);
            oout.close();
            fout.close();
        } catch (IOException e) {
            System.out.println("[Better Compass] Error! Impossível criar arquivo");
        }
    }

    public void changePoint(String nome, Player p){
        ListaPontos aux = head;
        boolean found = false;
        while(!found && aux != null){
            if(nome.compareTo(aux.nome) == 0){
                Location newPoint = new Location(Bukkit.getWorld(aux.world) ,aux.x, aux.y, aux.z);
                p.setCompassTarget(newPoint);
                found = true;
            }
            aux = aux.next;
        }
        if(found){
            p.sendMessage(ChatColor.GREEN + "Bússola atualizada!");
        }
        else{
            p.sendMessage(ChatColor.GREEN + "Ponto não encontrado! (Veja os pontos em /mostrarpontos)");
        }
    }

    public void pop(String nome, Player p){

        if(head == null){
            p.sendMessage(ChatColor.GREEN + "Não existem pontos para serem removidos!");
        }

        else if(head == tail && nome.compareTo(head.nome) == 0){ //Remoção do ultimo elemento
            head = null;
            tail = null;
            System.gc();
            p.sendMessage(ChatColor.GREEN + "Ponto removido!");
        }

        else if(nome.compareTo(head.nome) == 0){ //Remoção da head
            head.next.prev = null;
            changeHead(head.next);
            System.gc();
            p.sendMessage(ChatColor.GREEN + "Ponto removido!");
        }

        else if(nome.compareTo(tail.nome) == 0){ //Remoção da tail
            tail.prev.next = null;
            changeTail(tail.prev);
            System.gc();
            p.sendMessage(ChatColor.GREEN + "Ponto removido!");
        }

        else{
            ListaPontos aux = head;
            boolean removed = false;
            while(!removed && aux != null){
                if(nome.compareTo(aux.nome) == 0){
                    aux.prev.next = aux.next;
                    aux.next.prev = aux.prev;
                    System.gc();
                    removed = true;
                }
                aux = aux.next;
            }
            if(removed){
                p.sendMessage(ChatColor.GREEN + "Ponto removido!");
            }
            else{
                p.sendMessage(ChatColor.GREEN + "Ponto não encontrado para remoção! (Veja os pontos em /mostrarpontos)");
            }
        }
        salvar();
    }

    public void push(String nome, Location local){
        //Primeira inserção
        if(head == null){
            this.nome = nome;
            this.x = local.getX();
            this.y = local.getY();
            this.z = local.getZ();
            this.world = Objects.requireNonNull(local.getWorld()).getName();
            this.next = null;
            this.prev = null;
            changeHead(this);
            changeTail(this);
        }

        //Segunda inserção
        else if(head.next == null){
            if(nome.compareTo(head.nome) >= 0){ //Caso for maior ou igual a head, colocar a direita de head
                head.next = new ListaPontos();
                head.next.nome = nome;
                head.next.x = local.getX();
                head.next.y = local.getY();
                head.next.z = local.getZ();
                head.next.world = Objects.requireNonNull(local.getWorld()).getName();
                head.next.next = null;
                head.next.prev = head;
                changeTail(head.next);
            }
            else{ //Caso contrario, colocar a esquerda e definir como novo head
                head.prev = new ListaPontos();
                head.prev.nome = nome;
                head.prev.x = local.getX();
                head.prev.y = local.getY();
                head.prev.z = local.getZ();
                head.prev.world = Objects.requireNonNull(local.getWorld()).getName();
                head.prev.prev = null;
                head.prev.next = head;
                changeTail(head);
                changeHead(head.prev);
            }
        }

        //Caso menor que a head
        else if(nome.compareTo(head.nome) < 0){
            head.prev = new ListaPontos();
            head.prev.nome = nome;
            head.prev.x = local.getX();
            head.prev.y = local.getY();
            head.prev.z = local.getZ();
            head.prev.world = Objects.requireNonNull(local.getWorld()).getName();
            head.prev.next = head;
            head.prev.prev = null;
            changeHead(head.prev);
        }

        //Caso maior que tail
        else if(nome.compareTo(tail.nome) >= 0){
            tail.next = new ListaPontos();
            tail.next.nome = nome;
            tail.next.x = local.getX();
            tail.next.y = local.getY();
            tail.next.z = local.getZ();
            tail.next.world = Objects.requireNonNull(local.getWorld()).getName();
            tail.next.next = null;
            tail.next.prev = tail;
            changeTail(tail.next);
        }

        //Demais inserções
        else{
            boolean inserido = false;
            ListaPontos aux = head;
            ListaPontos novoPonto = new ListaPontos();
            while(!inserido){
                if(nome.compareTo(aux.nome) >= 0 && nome.compareTo(aux.next.nome) <= 0 && aux != tail){
                    novoPonto.nome = nome;
                    novoPonto.x = local.getX();
                    novoPonto.y = local.getY();
                    novoPonto.z = local.getZ();
                    novoPonto.world = Objects.requireNonNull(local.getWorld()).getName();
                    novoPonto.next = aux.next;
                    novoPonto.prev = aux;
                    aux.next.prev = novoPonto;
                    aux.next = novoPonto;
                    inserido = true;
                }
                if(aux.next.next != null) {
                    aux = aux.next;
                }
            }
        }
        salvar(); //Sempre salva apos inserção
    }

    public void mostrarNomes(Player p){
        changeHead(this.headS);
        changeTail(this.tailS);
        ListaPontos aux = head;

        if(head == null){
            p.sendMessage(ChatColor.GREEN + "Ainda não existem pontos criados!");
        }
        else{
            p.sendMessage(ChatColor.GREEN + "\n###----Pontos----###\n");
            while (aux != null){
                p.sendMessage(ChatColor.GREEN + "Nome: " + aux.nome);
                aux = aux.next;
            }
            p.sendMessage(ChatColor.GREEN + "\n###-------------###");
        }
    }
}
