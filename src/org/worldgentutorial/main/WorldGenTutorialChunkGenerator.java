package org.worldgentutorial.main;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.generator.BlockPopulator;
import org.bukkit.generator.ChunkGenerator;
import org.bukkit.generator.WorldInfo;
import org.bukkit.util.noise.SimplexOctaveGenerator;

public class WorldGenTutorialChunkGenerator extends ChunkGenerator {
	
	/**
	 * NOTE: This method is by no means optimized. Very often, you shouldn't instantiate 
	 * your noise generator all the time, as it runs math operations in the background.
	 * However, this is a world generation tutorial and I want to keep things simple.
	 * There are a lot of different steps that you as an individual developer need to 
	 * optimize based on what it is that you're generating. 
	 * 
	 * Most worldgen plugins don't even use SimplexOctaveGenerator.
	 * @param info
	 * @param rawX
	 * @param rawZ
	 * @return
	 */
	private int getHeightFor(WorldInfo info, int rawX, int rawZ) {
		//The shape of the world is created with "noise maps". There is an inbuilt noise map generator
		//within the bukkit API that the old tutorial also uses:
		SimplexOctaveGenerator generator = new SimplexOctaveGenerator(new Random(info.getSeed()), 8); //8 refers to the number of octaves. More octaves are slower, but look better (less smooth, and more rough like in real life)
        
		//I, for one, don't know what this number does, only that the bukkit tutorial set it way too low
		//and made the terrain excessively smooth.
		generator.setScale(0.03D);
		
		return (int) (generator.noise(rawX, rawZ, 
				0.002D,  //The higher frequency is, the more hills there are.
				1D)*5D //These 2 numbers affect amplitude, how high each 'hill' or 'pit' is
				+50D); //50 is the average height of the whole map
	}
	
	/**
	 * This is where we will set the shape of the world. 
	 */
	@Override
	public void generateNoise(WorldInfo info, Random random, int chunkX, int chunkZ, ChunkData chunkAccess)
	{
		//Iterate through the chunk and set the stone
        for(int x = 0; x < 16; x++)
        	for(int z = 0; z < 16; z++)
			{
        		int rawX = chunkX*16 + x;
        		int rawZ = chunkZ*16 + z;
				int currentHeight = getHeightFor(info, rawX, rawZ);
				for(int y = info.getMinHeight(); y <= currentHeight; y++) {
					//be very sure that x and z is in the 0-15 range. 
					//DO NOT pass rawX and rawZ here, it will cause a crash.
					chunkAccess.setBlock(x, y, z, Material.STONE);
				}
			}
	}
	
	/**
	 * This is where we put grass and dirt
	 */
	@Override
	public void generateSurface(WorldInfo info, Random random, int chunkX, int chunkZ, ChunkData chunkAccess)
	{
		//Iterate through the chunk and set grass blocks
        for(int x = 0; x < 16; x++)
        	for(int z = 0; z < 16; z++)
			{
        		int rawX = chunkX*16 + x;
        		int rawZ = chunkZ*16 + z;
				int currentHeight = getHeightFor(info, rawX, rawZ);
				chunkAccess.setBlock(x, currentHeight, z, Material.GRASS_BLOCK);
				
				for(int depth = 1; depth < random.nextInt(3)+1; depth++) {
					chunkAccess.setBlock(x, currentHeight-depth, z, Material.DIRT);
				}
			}
	}

	//Just set bedrock. Simple and direct.
	@Override
	public void generateBedrock(WorldInfo info, Random random, int chunkX, int chunkZ, ChunkData chunkAccess)
	{
        for(int x = 0; x < 16; x++)
        	for(int z = 0; z < 16; z++)
			{
				chunkAccess.setBlock(x, info.getMinHeight(), z, Material.BEDROCK);
				
				//Sometimes bedrock appears a little higher than minY.
				if(random.nextBoolean())
					chunkAccess.setBlock(x, info.getMinHeight()+1, z, Material.BEDROCK);
			}
	}

	@Override
	public void generateCaves(WorldInfo info, Random random, int chunkX, int chunkZ, ChunkData chunkAccess)
	{
		//There are many different cave algorithms. For this, almost any dev will
		//code their own thing. For now, I will leave this empty for simplicity's sake.
		
		//Additionally, note that vanilla's 1.18 caves are generated in generateNoise,
		//while 1.17 and below caves (stuff like ravines) are generated
		//here
	}
	
	//Register your populator
	@Override
    public List<BlockPopulator> getDefaultPopulators(World world) {
        return new ArrayList<BlockPopulator>() {{
        	add(new WorldGenTutorialBlockPopulator());
        }};
    }
	
	
	//For the purpose of this tutorial, we will suppress vanilla completely.
	//This may not always be what you want (i.e. my world generation plugin, TerraformGenerator,
	//will allow vanilla caves to generate, so I set shouldGenerateNoise and shouldGenerateCaves
	//to true.
	
	//On your own, you can toggle these to see what happens. Refer to the API as well.
	public boolean shouldGenerateNoise() { return false; }
	public boolean shouldGenerateSurface() { return false; }
	public boolean shouldGenerateBedrock() { return false; }
	public boolean shouldGenerateCaves() { return false; }
	public boolean shouldGenerateDecorations() { return false; }
	public boolean shouldGenerateMobs() { return false; }
	public boolean shouldGenerateStructures() { return false; }
	
}
