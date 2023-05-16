package net.swedz.draconic_industrialization;

import com.llamalad7.mixinextras.MixinExtrasBootstrap;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;

public final class DraconicIndustrializationPreLaunch implements PreLaunchEntrypoint
{
	@Override
	public void onPreLaunch()
	{
		MixinExtrasBootstrap.init();
	}
}
