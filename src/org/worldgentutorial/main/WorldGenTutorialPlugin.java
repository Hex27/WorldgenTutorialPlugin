package org.worldgentutorial.main;

import org.bukkit.generator.ChunkGenerator;
import org.bukkit.plugin.java.JavaPlugin;

public class WorldGenTutorialPlugin extends JavaPlugin {

	@Override
	public ChunkGenerator getDefaultWorldGenerator(String worldName, String id) {
	    return new WorldGenTutorialChunkGenerator();
	}
	
}
