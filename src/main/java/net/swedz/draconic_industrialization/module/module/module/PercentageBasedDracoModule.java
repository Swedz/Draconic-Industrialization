package net.swedz.draconic_industrialization.module.module.module;

import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModuleReference;

public abstract class PercentageBasedDracoModule extends DracoModule
{
	protected final double fraction, fractionMultiple;
	
	protected final int maxValue;
	
	protected int value;
	
	public PercentageBasedDracoModule(DracoModuleReference reference, double fractionMultiple, double maxPercentage)
	{
		super(reference);
		this.fractionMultiple = fractionMultiple;
		this.fraction = 100 / fractionMultiple;
		if(maxPercentage % fractionMultiple != 0)
		{
			throw new IllegalArgumentException("Speed percentage must be multiple of %f, was given %f instead".formatted(fractionMultiple, maxPercentage));
		}
		this.maxValue = (int) Math.ceil(maxPercentage / 100D * fraction);
	}
	
	protected double bonus()
	{
		return value / fraction;
	}
	
	protected int bonusPercentage()
	{
		return (int) (this.bonus() * 100);
	}
	
	protected String bonusString(double value)
	{
		return "+%d%%".formatted((int) (value / fraction * 100));
	}
}
