package gps;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

import gps.commands.getMagicalTool;

public class Main extends JavaPlugin{

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		FileConfiguration cfg = getConfig();
		if(cfg.getConfigurationSection("points").getKeys(false).size() < 2) {
			System.out.println("T'as fais de la mderde avec config.yml gros tas");
			
			//Regen config
		}
		
		getServer().getPluginManager().registerEvents(new getMagicalTool(this), this);
		
		getCommand("gpstool").setExecutor(new getMagicalTool(this));
		
		System.out.println("GogoleMaps started !");
		
		for(String point : getConfig().getConfigurationSection("points").getKeys(false)) {
			List<Double> coords = getConfig().getDoubleList("points."+point+".coords");
			Location loc = new Location(Bukkit.getWorlds().get(0), coords.get(0), coords.get(1), coords.get(2));
			loc.add(0.5, 0.5, 0.5);
			
			new BukkitRunnable() {
				
				@Override
				public void run() {
					loc.getWorld().spawnParticle(Particle.FLAME, loc, 0, 0, 0, -0.0005);
				}
			}.runTaskTimer(this, 0, 4);
		}
	}
	
	@Override
	public void onDisable() {

		
		System.out.println("GogoleMaps gave up !");
	}
}
