package gps.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import gps.Main;
import gps.utils.Coordinate;
import gps.utils.Node;

public class getMagicalTool implements CommandExecutor, Listener{
	
	static Location coord = null;
	
	static Main main;
	static FileConfiguration cfg;
	public getMagicalTool(Main main) {
		getMagicalTool.main = main;
		cfg = main.getConfig();
	}

	@Override
	public boolean onCommand(CommandSender s, Command cmd, String label, String[] args) {
		
		if(!(s instanceof Player)) {
			s.sendMessage("Only playerscan execute this command");
			
			Node begin = new Node("begin", new Coordinate(0, 0, 0));
			Node A = new Node("A", new Coordinate(3, 0, 0));
			Node B = new Node("B", new Coordinate(5, 0, 0));
			Node C = new Node("C", new Coordinate(10, 0, 0));
			Node D = new Node("D", new Coordinate(10, 10, 10));

			begin.addLink(A, begin.distanceOf(A));
			begin.addLink(B, begin.distanceOf(B));
			A.addLink(begin, A.distanceOf(begin));
			A.addLink(C, A.distanceOf(C));
			B.addLink(begin, B.distanceOf(begin));
			B.addLink(C, B.distanceOf(C));
			C.addLink(D, C.distanceOf(D));
			
			begin.getPath(D);
			
			return true;
		}
		Player p = (Player) s;
		if(args.length == 0) {
			
			p.getInventory().addItem(getTool());
			
			p.sendMessage("§aYou got a magical poutre");
			
			return true;
		} else {
			/*if(coord == null) {
				p.sendMessage("§cVous n'avez pas de point selectionné >:(");
				return true;
			}*/
		}
		String point;
		List<Double> coords = new ArrayList<>();
		List<String> linked = new ArrayList<>();
		if(args.length == 1) {
			switch(args[0]) {
			case "register":
				p.sendMessage("§c/gpstool register <name>");
				return true;
			case "addlink":
				p.sendMessage("§c/gpstool addlink <name> <point1> <point2> <etc>");
				return true;
			}
		}
		
		if(args.length == 2) {
			point = args[1];
			coords.add(coord.getX());
			coords.add(coord.getY()+1); //+1 pr que le point soit pas dans le sol
			coords.add(coord.getZ());
			switch(args[0]) {
			case "register":
				if(cfg.getConfigurationSection("points." + point) != null) {
					p.sendMessage("§cThis point already exist (try another name)");
					return true;
				}
				if(args[1] == null) {
					p.sendMessage("§cYou must name your point !");
					return true;
				}
				
				cfg.getConfigurationSection("points").createSection(point);
				cfg.set("points."+point+".coords", coords);
				cfg.set("points."+point+".link", linked); //Empty
				main.saveConfig();
				p.sendMessage("§aLe point a bien été register");
				return true;
			default:
				return false;
			}
		}
		if(args.length > 2) {
			point = args[1];
			if(cfg.getConfigurationSection("points." + point) == null) {
				p.sendMessage("§cThis point doesnt exist !");
				return true;
			}
			
			switch(args[0]) {
			case "addlink":
				for(int i = 2; i<args.length;i++) {
					linked.add(args[i]);
				}
				cfg.set("points."+point+".link", linked);
				main.saveConfig();
				p.sendMessage("§aLe point " + point + " a bien été lié !");
				return true;
			default:
				return false;
			}
		}
		
		return false;
	}
	
	public static ItemStack getTool() {
		ItemStack item = new ItemStack(Material.IRON_INGOT);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName("§6Poutre Magique du GPS");
		meta.setLore(Arrays.asList("§fCette poutre sert à créer des points","§f pour le GEUPEUCEU (ou GPS)"));
		meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
		meta.addEnchant(Enchantment.PROTECTION_ENVIRONMENTAL, 1, false);
		item.setItemMeta(meta);
		return item;
	}
	
	@EventHandler
	public static void onInteract(PlayerInteractEvent e) {
		ItemStack item = e.getItem();
		if(item == null) return;
		if(!item.isSimilar(getTool())) return;
		e.setCancelled(true);
		
		coord = e.getClickedBlock().getLocation();
		e.getPlayer().sendMessage("Point selected !");
		e.getPlayer().sendMessage("(" + coord.getBlockX() +", " + coord.getBlockY()+"+1, "+coord.getBlockZ()+")");
		e.getPlayer().sendMessage("§aVous pouvez §6register §aou §addlink §a(/gpstool register <name>)");
		
		int delay = main.getConfig().getInt("forgetting_time")*20;
		
		new BukkitRunnable() {

			@Override
			public void run() {
				coord = null;
				e.getPlayer().sendMessage("Your point has been deselected");
				cancel();
				return;
			}
			
		}.runTaskLater(main, delay);
	}
	
	
}
