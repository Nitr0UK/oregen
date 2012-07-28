package com.nitr0uk.www.oregen;

import java.util.Random;

import org.bukkit.Chunk;

public class IndustrialCraftOreGeneration {

	public final int COPPER_ORE_ID = 249;
	public final int TIN_ORE_ID = 248;
	public final int URANIUM_ORE_ID = 247;

	public void generateOre(Chunk chunk) 
	{
		int chunkX = (chunk.getX() * 16);
		int chunkZ = (chunk.getZ() * 16);
		int baseScale = chunk.getWorld().getSeaLevel() + 1;
		Random random = new Random(System.nanoTime());
		int baseCount = 15 * baseScale / 64;
		int count = (int) Math.round(random.nextGaussian() * Math.sqrt(baseCount) + baseCount);

		for (int n = 0; n < count; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(40 * baseScale / 64) + random.nextInt(20 * baseScale / 64) + 10
					* baseScale / 64;
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), COPPER_ORE_ID, 10).generate(random, x-8, y, z-8);
		}

		baseCount = 25 * baseScale / 64;
		count = (int) Math.round(random.nextGaussian() * Math.sqrt(baseCount) + baseCount);

		for (int n = 0; n < count; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(40 * baseScale / 64);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), TIN_ORE_ID, 6).generate(random, x-8, y, z-8);
		}

		baseCount = 20 * baseScale / 64;
		count = (int) Math.round(random.nextGaussian() * Math.sqrt(baseCount) + baseCount);

		for (int n = 0; n < count; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(64 * baseScale / 64);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), URANIUM_ORE_ID, 3).generate(random, x-8, y, z-8);
		}
	}
}



