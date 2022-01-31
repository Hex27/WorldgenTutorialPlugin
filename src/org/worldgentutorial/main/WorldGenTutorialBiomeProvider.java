package org.worldgentutorial.main;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.block.Biome;
import org.bukkit.generator.BiomeProvider;
import org.bukkit.generator.WorldInfo;

public class WorldGenTutorialBiomeProvider extends BiomeProvider {

	//For this tutorial, we will just force every biome in the world to be plains
	//For a more complex plugin, you'd return biome calculations here.
	//If you don't want to handle biomes, you can actually let vanilla do the
	//biomes by just not making a biome provider
	@Override
	public Biome getBiome(WorldInfo info, int x, int y, int z) {
		return Biome.PLAINS;
	}
	
	//This just needs to return every biome that we want to use in the provider.
	//We're just using plains.
	private static final ArrayList<Biome> biomes = new ArrayList<Biome>(){{ 
		add(Biome.PLAINS); 
	}};

	@Override
	public List<Biome> getBiomes(WorldInfo info) {
		return biomes;
	}

}
