package com.nitr0uk.www.oregen;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;

public class WorldApplier implements Runnable{

	private final int maxChunksPerTick;
	private int startX;
	private int startZ;
	private int currentX;
	private int currentZ;
	private int endX;
	private int endZ;
	private World world;
	
	public WorldApplier(World world, int maxChunksPerTick, int startX, int endX, int startZ, int endZ)
	{
		this.world = world;
		this.maxChunksPerTick = maxChunksPerTick;
		currentX = startX;
		currentZ = startZ;
		this.startX = startX;
		this.startZ = startZ;
		this.endX = endX;
		this.endZ = endZ;
		
	}

	@Override
	public synchronized void run() 
	{
		System.gc();
		int chunksThisTick = 0;
		for(; currentX <= endX; ++currentX)
		{
			for(; currentZ <= endZ; ++currentZ)
			{
				if (chunksThisTick >= maxChunksPerTick)
				{
					return;
				}
				System.out.println("Processing Chunk X:" + currentX + " Z: " + currentZ);
				Chunk chunk = world.getChunkAt(currentX, currentZ);
				
				if (!chunk.isLoaded() && !chunk.load(false)) 
				{
					// chunk does not exist, skip this chunk
				} 
				else 
				{
					if (countBlocksInChunk(chunk, BlockGeneration.COPPER_ORE_ID) == 0) 
					{
						BlockGeneration.generateOre(chunk);
					}
					chunk.unload(true, true);
					++chunksThisTick;
				}
			}
			currentZ = startZ;
		}
		 Bukkit.getScheduler().cancelAllTasks();
	}
		
		
		public static int countBlocksInChunk(Chunk chunk, int blockID)
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
	
	
	

}
