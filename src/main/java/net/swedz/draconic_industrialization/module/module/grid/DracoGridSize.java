package net.swedz.draconic_industrialization.module.module.grid;

public record DracoGridSize(int width, int height)
{
	public static DracoGridSize of(int width, int height)
	{
		return new DracoGridSize(width, height);
	}
}
