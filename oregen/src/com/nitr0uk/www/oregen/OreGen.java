package com.nitr0uk.www.oregen;

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
					
					new BlockGeneration().generateOre(chunk);
					sender.sendMessage("Generated ore in this chunk successfully!");
				}
				else
				{
					sender.sendMessage(args.length + " arguments given! Parsing integers as BlockID/s");
					for (String argument : args)
					{
						Integer blockID = Integer.parseInt(argument);
						if (blockID != null)
						{
							int blockCount = countBlocksInChunk(chunk, blockID);

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
		//generate the whole world with ore!
		new BlockGeneration().generateOre(world.getChunkAt(0, 0));
		
	}
	/**
	 * Count a specific number of chunks starting from the given location
	 * Chunks are counted in a clockwise spiral pattern starting from the
	 * center
	 * @param location
	 * @param numberOfChunks
	 * @return
	 */
	private int countChunks(Location location, int numberOfChunks)
	{
		return 0;
	}
	private int countBlocksInChunk(Chunk chunk, int blockID)
	{
		int blockCount = 0;
		for(int x = 0; x<16; ++x)
		{
			for(int z = 0; z<16; ++z)
			{
				for(int y = 0; y<256; ++y)
				{
					if (chunk.getBlock(x, y, z).getTypeId() == blockID)
					{
						++blockCount;
					}
				}
			}
		}
		
		return blockCount;
	}
	public static Configuration getConfiguration() {
		return configuration;
	}
}

