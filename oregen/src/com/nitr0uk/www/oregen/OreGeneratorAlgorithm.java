package com.nitr0uk.www.oregen;

import java.util.Random;

public class OreGeneratorAlgorithm {

	private int blockId = 0;
	        private int blockData = 0;
	        private int size = 0;

	        public OreGeneratorAlgorithm (int blockId, int size)
	        {
	            this.blockId = blockId;
	            this.size = size;
	        }
	        
	        public OreGeneratorAlgorithm  (int blockId, int blockData, int size)
	        {
	           this.blockId = blockId;
	            this.blockData = blockData;
	            this.size = size;
	        }

	        public boolean Generate (int blockID, Random rand, int x, int y, int z)
	        {
	            float rpi = (float)(rand.nextDouble() * Math.PI);

	            double x1 = x + 8 + Math.sin(rpi) * size / 8.0F;
	            double x2 = x + 8 - Math.sin(rpi) * size / 8.0F;
	            double z1 = z + 8 + Math.cos(rpi) * size / 8.0F;
	            double z2 = z + 8 - Math.cos(rpi) * size / 8.0F;

	            double y1 = y + rand.nextInt(3) + 2;
	            double y2 = y + rand.nextInt(3) + 2;

	            for (int i = 0; i <= size; i++) {
	                double xPos = x1 + (x2 - x1) * i / size;
	                double yPos = y1 + (y2 - y1) * i / size;
	                double zPos = z1 + (z2 - z1) * i / size;

	                double fuzz = rand.nextDouble() * size / 16.0D;
	                double fuzzXZ = (Math.sin((float)(i * Math.PI / size)) + 1.0F) * fuzz + 1.0D;
	                double fuzzY = (Math.sin((float)(i * Math.PI / size)) + 1.0F) * fuzz + 1.0D;

	                int xStart, yStart, zStart, xEnd, yEnd, zEnd;

	                    xStart = (int)(xPos - fuzzXZ / 2.0D);
	                    yStart = (int)(yPos - fuzzY / 2.0D);
	                    zStart = (int)(zPos - fuzzXZ / 2.0D);

	                    xEnd = (int)(xPos + fuzzXZ / 2.0D);
	                    yEnd = (int)(yPos + fuzzY / 2.0D);
	                    zEnd = (int)(zPos + fuzzXZ / 2.0D);
	                

	                for (int ix = xStart; ix <= xEnd; ix++) {
	                    double xThresh = (ix + 0.5D - xPos) / (fuzzXZ / 2.0D);
	                    if (xThresh * xThresh < 1.0D) {
	                        for (int iy = yStart; iy <= yEnd; iy++) {
	                            double yThresh = (iy + 0.5D - yPos) / (fuzzY / 2.0D);
	                            if (xThresh * xThresh + yThresh * yThresh < 1.0D) {
	                                for (int iz = zStart; iz <= zEnd; iz++) {
	                                    double zThresh = (iz + 0.5D - zPos) / (fuzzXZ / 2.0D);
	                                    if (xThresh * xThresh + yThresh * yThresh + zThresh * zThresh < 1.0D) {
	                                        BlockRef block = blockMan.GetBlockRef(ix, iy, iz);
	                                        if (block != null) {
	                                            block.ID = _blockId;
	                                            block.Data = _blockData;
	                                        }
	                                    }
	                                }
	                            }
	                        }
	                    }
	                }
	            }

	            return true;
	        }
	    }
}
