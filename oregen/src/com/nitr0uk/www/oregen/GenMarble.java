package com.nitr0uk.www.oregen;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.block.Block;

public class GenMarble
{
	LinkedList fillStack;
	HashSet fillStackTest;
	private int blockID;
	private byte blockMeta;
	private int size;
	private World world;

	public GenMarble(World world, int blockID, byte blockMeta, int size)
	{
		this.world = world;
		this.blockID = blockID;
		this.blockMeta = blockMeta;
		this.size = size;
		
		fillStack = new LinkedList();
		fillStackTest = new HashSet();
	}

	private void addBlock(int x, int y, int z, int paramInt4)
	{
		List localList = Arrays.asList(new Integer[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z) });

		if (this.fillStackTest.contains(localList))
		{
			return;
		}

		this.fillStack.addLast(Arrays.asList(new Integer[] { Integer.valueOf(x), Integer.valueOf(y), Integer.valueOf(z), Integer.valueOf(paramInt4) }));

		this.fillStackTest.add(localList);
	}

	private void searchBlock(World world, int x, int y, int z, int paramInt4)
	{
		if ((world.getBlockTypeIdAt(x - 1, y, z) == 0) || (world.getBlockTypeIdAt(x + 1, y, z) == 0) || (world.getBlockTypeIdAt(x, y - 1, z) == 0) || (world.getBlockTypeIdAt(x, y + 1, z) == 0) || (world.getBlockTypeIdAt(x, y, z - 1) == 0) || (world.getBlockTypeIdAt(x, y, z + 1) == 0))
		{
			paramInt4 = 6;
		}

		addBlock(x - 1, y, z, paramInt4);
		addBlock(x + 1, y, z, paramInt4);
		addBlock(x, y - 1, z, paramInt4);
		addBlock(x, y + 1, z, paramInt4);
		addBlock(x, y, z - 1, paramInt4);
		addBlock(x, y, z + 1, paramInt4);
	}

	public boolean generate(Random random, int x, int y, int z)
	{
		if (world.getBlockTypeIdAt(x, y, z) != 0)
		{
			return false;
		}

		int i = y;
		for (; world.getBlockTypeIdAt(x, i, z) != OreGeneratorAlgorithm.STONE_ID; i++)
		{
			if (i > 96)
			{
				return false;
			}
			
		}
		addBlock(x, i, z, 6);
		while ((this.fillStack.size() > 0) && (size > 0))
		{
			List localList = (List)this.fillStack.removeFirst();
			Integer[] arrayOfInteger = (Integer[])(Integer[])localList.toArray();
			if (world.getBlockTypeIdAt(arrayOfInteger[0].intValue(), arrayOfInteger[1].intValue(), arrayOfInteger[2].intValue()) == OreGeneratorAlgorithm.STONE_ID)
			{
				Block block = world.getBlockAt(arrayOfInteger[0].intValue(), arrayOfInteger[1].intValue(), arrayOfInteger[2].intValue());
				block.setData(blockMeta);
				block.setTypeId(blockID);

				if (arrayOfInteger[3].intValue() > 0)
				{
					searchBlock(world, arrayOfInteger[0].intValue(), arrayOfInteger[1].intValue(), arrayOfInteger[2].intValue(), arrayOfInteger[3].intValue() - 1);
				}

				size -= 1;
			}

		}

		return true;
	}
}