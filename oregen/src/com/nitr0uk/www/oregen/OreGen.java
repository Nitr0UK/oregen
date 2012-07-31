package com.nitr0uk.www.oregen;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class OreGen extends JavaPlugin{

	private static Configuration configuration = null;

	@Override
	public void onEnable()
	{
		if (configuration != null)
		{
			//something went horribly, horribly wrong
			throw new InternalError();
		}
		configuration = getConfig();
		getCommand("oregen").setExecutor(this);
		
	}
	
	@Override
	public void onDisable()
	{
		configuration = null;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (sender instanceof Player)
		{
			Player player = (Player) sender;
			Location location = player.getLocation();
			Chunk chunk = location.getChunk();
			sender.sendMessage("Current location is: X: " + location.getBlockX() + " Y: "
					+ location.getBlockY() + " Z: " + location.getBlockZ() + " Current chunk is: " + chunk.toString());
			
			//arguments should be a list of blocks or block IDs
			if(args.length >= 1)
			{
				if(args[0].equals("generate"))
				{
					generateWorld(player.getWorld());
					sender.sendMessage("Generated ore in world successfully!");
				}
				else
				{
					sender.sendMessage(args.length + " arguments given! Parsing integers as BlockID/s");
					for (String argument : args)
					{
						Integer blockID = Integer.parseInt(argument);
						if (blockID != null)
						{
							int blockCount = WorldApplier.countBlocksInChunk(chunk, blockID);

							sender.sendMessage("Block count for block ID " + blockID + ", " + blockCount);
						}
						else
						{
							sender.sendMessage("Block ID " + argument + " not a valid block ID, skipping");
						}
					}
				}
			}
		}
		else
		{
			sender.sendMessage("This command cannot be run from the console");
		}
		//		System.out.println("Command Executed!");
		//		World world = getServer().getWorld("world");
		//		Chunk chunk = world.getChunkAt(0,0);
		//		System.out.println(chunk.getX());
		//		System.out.println(chunk.getZ());
		//		chunk = world.getChunkAt(0,1);
		//		System.out.println(chunk.getX());
		//		System.out.println(chunk.getZ());
		//		Block block = chunk.getBlock(22, 0, 0);
		//		System.out.println("Block ID: " + block.getTypeId());


		return true;
	}
	
	private void generateWorld(World world)
	{
		WorldApplier worldApplier = new WorldApplier(world, 10, -250, 250, -250, 250);
		Bukkit.getScheduler().scheduleSyncRepeatingTask(this, worldApplier, 0, 1);
	}
	
	public static Configuration getConfiguration() {
		return configuration;
	}
}

