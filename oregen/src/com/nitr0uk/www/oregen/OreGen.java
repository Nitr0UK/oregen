package com.nitr0uk.www.oregen;

import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.Configuration;
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
		System.out.println("Command Executed!");
		World world = getServer().getWorld("world");
		Chunk chunk = world.getChunkAt(0,0);
		System.out.println(chunk.getX());
		System.out.println(chunk.getZ());
		chunk = world.getChunkAt(0,1);
		System.out.println(chunk.getX());
		System.out.println(chunk.getZ());
		Block block = chunk.getBlock(22, 0, 0);
		System.out.println("Block ID: " + block.getTypeId());
		return true;
	}
	
	public static Configuration getConfiguration() {
		return configuration;
	}
}

