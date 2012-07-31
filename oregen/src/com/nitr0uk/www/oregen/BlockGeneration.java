package com.nitr0uk.www.oregen;

import java.util.Random;
import org.bukkit.Chunk;

public class BlockGeneration {

	public final static int COPPER_ORE_ID = 249;
	public final static int TIN_ORE_ID = 248;
	public final static int URANIUM_ORE_ID = 247;
	public final static int MARBLE_ID = 142;
	public final static byte DEFAULT_METADATA = 0;
	public final static int REDPOWER_ORE_ID = 140;
	public final static byte RUBY_ORE_METADATA = 0;
	public final static byte EMERALD_ORE_METADATA = 1;
	public final static byte SAPPHIRE_ORE_METADATA = 2;
	public final static byte SILVER_ORE_METADATA = 3;
	public final static byte TUNGSTEN_ORE_METADATA = 6;
	public final static byte NIKOLITE_ORE_METADATA = 7;
	
	public static void generateOre(Chunk chunk) 
	{
		int chunkX = (chunk.getX() * 16);
		int chunkZ = (chunk.getZ() * 16);
		int baseScale = chunk.getWorld().getSeaLevel() + 1;
		Random random = new Random(System.nanoTime());
		
		//copper
		int baseCount = 15 * baseScale / 64;
		int count = (int) Math.round(random.nextGaussian() * Math.sqrt(baseCount) + baseCount);
		for (int n = 0; n < count; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(40 * baseScale / 64) + random.nextInt(20 * baseScale / 64) + 10
					* baseScale / 64;
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), COPPER_ORE_ID, DEFAULT_METADATA, 10).generate(random, x-8, y, z-8);
		}

		//tin
		baseCount = 25 * baseScale / 64;
		count = (int) Math.round(random.nextGaussian() * Math.sqrt(baseCount) + baseCount);
		for (int n = 0; n < count; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(40 * baseScale / 64);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), TIN_ORE_ID, DEFAULT_METADATA, 6).generate(random, x-8, y, z-8);
		}

		//uranium
		baseCount = 20 * baseScale / 64;
		count = (int) Math.round(random.nextGaussian() * Math.sqrt(baseCount) + baseCount);
		for (int n = 0; n < count; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(64 * baseScale / 64);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), URANIUM_ORE_ID, DEFAULT_METADATA, 3).generate(random, x-8, y, z-8);
		}
		
		//ruby
		for (int n = 0; n < 2; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(48);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), REDPOWER_ORE_ID, RUBY_ORE_METADATA, 7).generate(random, x-8, y, z-8);
		}
		
		//emerald
		for (int n = 0; n < 2; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(48);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), REDPOWER_ORE_ID, EMERALD_ORE_METADATA, 7).generate(random, x-8, y, z-8);
		}
		
		//sapphire
		for (int n = 0; n < 2; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(48);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), REDPOWER_ORE_ID, SAPPHIRE_ORE_METADATA, 7).generate(random, x-8, y, z-8);
		}
		
		//silver
		for (int n = 0; n < 4; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(32);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), REDPOWER_ORE_ID, SILVER_ORE_METADATA, 8).generate(random, x-8, y, z-8);
		}
		
		//tungsten
		for (int n = 0; n < 1; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(16);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), REDPOWER_ORE_ID, TUNGSTEN_ORE_METADATA, 4).generate(random, x-8, y, z-8);
		}
		
		//nikolite
		for (int n = 0; n < 4; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = random.nextInt(16);
			int z = chunkZ + random.nextInt(16);
			new OreGeneratorAlgorithm(chunk.getWorld(), REDPOWER_ORE_ID, NIKOLITE_ORE_METADATA, 10).generate(random, x-8, y, z-8);
		}
		
		//marble
		for (int n = 0; n < 4; n++) 
		{
			int x = chunkX + random.nextInt(16);
			int y = 32 + random.nextInt(32);
			int z = chunkZ + random.nextInt(16);
			new GenMarble(chunk.getWorld(), MARBLE_ID, DEFAULT_METADATA, random.nextInt(4096)).generate(random, x, y, z);
		}
		
		//oil
		GenOil.generate(random, chunk.getWorld(), chunkX, chunkZ);

	}
}



