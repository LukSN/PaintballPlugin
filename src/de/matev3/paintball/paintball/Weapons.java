package de.matev3.paintball.paintball;

import de.matev3.paintball.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;

public class Weapons {

	
	//Rifle
	
	static class Gun extends Weapon {

		public Gun(Main plugin, Material materiel, long reloadtime, double damage) {
			super(plugin, materiel, reloadtime, damage, "Rifle",0, 0);
		}

		@Override
		public void shooteffects(Player player) {
			player.launchProjectile(Snowball.class);
			player.playSound(player.getLocation(), Sound.ENTITY_ARROW_HIT, 10, 0);
		}

	}
	
	
	
	
	//Shotgun
	
	static class Shotgun extends Weapon {

		public Shotgun(Main plugin, Material materiel, long reloadtime, double damage) {
			super(plugin, materiel, reloadtime, damage, "Shotgun",50, 1);
		}

		@Override
		public void shooteffects(Player player) {
			player.launchProjectile(Snowball.class).getVelocity().multiply(15);
			player.launchProjectile(Snowball.class).getVelocity().multiply(50);
			player.launchProjectile(Snowball.class).getVelocity().multiply(10);
			player.launchProjectile(Snowball.class).getVelocity().multiply(20);
			player.launchProjectile(Snowball.class).getVelocity().multiply(70);
			
			
			player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0);
		}

	}
	
	
	
	
	//Minigun
	
	static class Minigun extends Weapon {

		public Minigun(Main plugin, Material materiel, long reloadtime, double damage) {
			super(plugin, materiel, reloadtime, damage, "Minigun", 50, 7);
		}

		@Override
		public void shooteffects(Player player) {
			player.launchProjectile(Snowball.class);
			player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0);
			
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						
					@Override
					public void run() {
						player.launchProjectile(Snowball.class);
						player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0);
						
						Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
							@Override
							public void run() {
								player.launchProjectile(Snowball.class);
								player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0);
								}
						}, 2);
					}
			}, 2);
		}

	}
	
	
	
	
	//RocketLauncher
	
	static class RocketLauncher extends Weapon {

		public RocketLauncher(Main plugin, Material materiel, long reloadtime, double damage) {
			super(plugin, materiel, reloadtime, damage, "Rocket Launcher", 70, 10);
		}

		@Override
		public void shooteffects(Player player) {
			player.launchProjectile(Fireball.class);
			player.playSound(player.getLocation(), Sound.ENTITY_BLAZE_DEATH, 10, 0);
		}

	}


	
	
	//Sturmgewehr
	
	static class Sturmgewehr extends Weapon {

		public Sturmgewehr(Main plugin, Material materiel, long reloadtime, double damage) {
			super(plugin, materiel, reloadtime, damage, "Sturmgewehr", 60, 10);
		}
		
		@Override
		public void shooteffects(Player player) {
			Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
				
				@Override
				public void run() {
					player.launchProjectile(Snowball.class);
					player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0);
					
					Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
						
						@Override
						public void run() {
							player.launchProjectile(Snowball.class);
							player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0);
							
							Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
								
								@Override
								public void run() {
									player.launchProjectile(Snowball.class);
									player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0);
									
									Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(), new Runnable() {
										
										@Override
										public void run() {
											player.launchProjectile(Snowball.class);
											player.playSound(player.getLocation(), Sound.ENTITY_FIREWORK_BLAST, 5, 0);
										}
									}, 2);
								}
							}, 2);
						}
					}, 2);
				}
			}, 2);
		}
	}	
}
