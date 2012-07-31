/** 
 * Copyright (c) SpaceToad, 2011
 * http://www.mod-buildcraft.com
 * 
 * BuildCraft is distributed under the terms of the Minecraft Mod Public 
 * License 1.0, or MMPL. Please check the contents of the license located in
 * http://www.mod-buildcraft.com/MMPL-1.0.txt
 */

package com.nitr0uk.www.oregen;

import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Biome;
import org.bukkit.block.Block;
import org.bukkit.Chunk;


public class GenOil {

	public final static int OIL_MOVING_ID = 162;
	public final static int OIL_STILL_ID = 163;
	
	public static void generate(Random random, World world, int x, int z) {


		Biome biome = world.getBiome(x, z);
		//BiomeGenBase biomegenbase = world.getWorldChunkManager().getBiomeGenAt(x, z);

		// Do not generate oil in the End
		if (biome.name() == Biome.SKY.name())
			return;

		if (biome.name() == Biome.DESERT.name() && random.nextFloat() > 0.97) {
			// Generate a small desert deposit

			int startX = random.nextInt(10) + 2;
			int startZ = random.nextInt(10) + 2;

			for (int j = 128; j > 65; --j) {
				int i = startX + x;
				int k = startZ + z;

				if (world.getBlockAt(i, j, k).getTypeId() != 0) {
					if (world.getBlockAt(i, j, k).getTypeId() == Material.SAND.getId()) {
						generateSurfaceDeposit(random, world, i, j, k, 3);
					}

					break;
				}
			}
		}

		boolean mediumDeposit = random.nextDouble() <= (0.15 / 100.0);
		boolean largeDeposit = random.nextDouble() <= (0.005 / 100.0);

		if (mediumDeposit || largeDeposit) {
			// Generate a large cave deposit

			int cx = x, cy = 20 + random.nextInt(10), cz = z;

			int r = 0;

			if (largeDeposit) {
				r = 8 + random.nextInt(9);
			} else if (mediumDeposit) {
				r = 4 + random.nextInt(4);
			}

			int r2 = r * r;

			for (int bx = -r; bx <= r; bx++) {
				for (int by = -r; by <= r; by++) {
					for (int bz = -r; bz <= r; bz++) {
						int d2 = bx * bx + by * by + bz * bz;

						if (d2 <= r2) {
							world.getBlockAt(bx + cx, by + cy, bz + cz).setTypeId(OIL_MOVING_ID);
						}
					}
				}
			}

			boolean started = false;

			for (int y = 128; y >= cy; --y) {
				if (!started && world.getBlockAt(cx, y, cz).getTypeId() != 0 && world.getBlockAt(cx, y, cz).getTypeId() != Material.LEAVES.getId()
						&& world.getBlockAt(cx, y, cz).getTypeId() != Material.LOG.getId()
						&& world.getBlockAt(cx, y, cz).getTypeId() != Material.GRASS.getId()) {

					started = true;

					if (largeDeposit) {
						generateSurfaceDeposit(random, world, cx, y, cz, 20 + random.nextInt(20));
					} else if (mediumDeposit) {
						generateSurfaceDeposit(random, world, cx, y, cz, 5 + random.nextInt(5));
					}

					int ymax = 0;

					if (largeDeposit) {
						ymax = (y + 30 < 128 ? y + 30 : 128);
					} else if (mediumDeposit) {
						ymax = (y + 4 < 128 ? y + 4 : 128);
					}

					for (int h = y + 1; h <= ymax; ++h) {
						world.getBlockAt(cx, h, cz).setTypeId(OIL_STILL_ID);
					}

				} else if (started) {
					world.getBlockAt(cx, y, cz).setTypeId(OIL_STILL_ID);
				}
			}

		}
	}

	public static void generateSurfaceDeposit(Random random, World world, int x, int y, int z, int radius) {
		setOilWithProba(random, world, 1, x, y, z, true);

		for (int w = 1; w <= radius; ++w) {
			float proba = (float) (radius - w + 4) / (float) (radius + 4);

			for (int d = -w; d <= w; ++d) {
				setOilWithProba(random, world, proba, x + d, y, z + w, false);
				setOilWithProba(random, world, proba, x + d, y, z - w, false);
				setOilWithProba(random, world, proba, x + w, y, z + d, false);
				setOilWithProba(random, world, proba, x - w, y, z + d, false);
			}
		}

		for (int dx = x - radius; dx <= x + radius; ++dx) {
			for (int dz = z - radius; dz <= z + radius; ++dz) {

				if (world.getBlockAt(dx, y - 1, dz).getTypeId() != OIL_STILL_ID) {
					if (isOil(world, dx + 1, y - 1, dz) && isOil(world, dx - 1, y - 1, dz) && isOil(world, dx, y - 1, dz + 1)
							&& isOil(world, dx, y - 1, dz - 1)) {
						setOilWithProba(random, world, 1.0F, dx, y, dz, true);
					}
				}
			}
		}
	}

	private static boolean isOil(World world, int x, int y, int z) {
		return (world.getBlockAt(x, y, z).getTypeId() == OIL_STILL_ID || world.getBlockAt(x, y, z).getTypeId() == OIL_MOVING_ID);
	}

	public static void setOilWithProba(Random random, World world, float proba, int x, int y, int z, boolean force) {
		if ((random.nextFloat() <= proba && world.getBlockAt(x, y - 2, z).getTypeId() != 0) || force) {
			boolean adjacentOil = false;

			for (int d = -1; d <= 1; ++d) {
				if (isOil(world, x + d, y - 1, z) || isOil(world, x - d, y - 1, z) || isOil(world, x, y - 1, z + d)
						|| isOil(world, x, y - 1, z - d)) {
					adjacentOil = true;
				}
			}

			if (adjacentOil || force) {
				if (world.getBlockAt(x, y, z).getTypeId() == Material.WATER.getId() 
						|| world.getBlockAt(x, y, z).getTypeId() == Material.STATIONARY_WATER.getId() || isOil(world, x, y, z)) {

					world.getBlockAt(x, y, z).setTypeId(OIL_STILL_ID);
					System.out.println("Generated Oil Block!!");
				} else {
					world.getBlockAt(x, y, z).setTypeId(0);
				}

				world.getBlockAt(x, y - 1, z).setTypeId(OIL_STILL_ID);
			}
		}
	}

}
