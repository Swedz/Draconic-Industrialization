package net.swedz.draconic_industrialization.module;

import net.minecraft.world.item.ItemStack;
import net.swedz.draconic_industrialization.DraconicIndustrialization;
import net.swedz.draconic_industrialization.api.NBTSerializer;
import net.swedz.draconic_industrialization.api.NBTTagWrapper;
import net.swedz.draconic_industrialization.api.tier.DracoTier;
import net.swedz.draconic_industrialization.module.module.DracoModule;
import net.swedz.draconic_industrialization.module.module.DracoModules;

import java.util.List;
import java.util.Optional;

public class DracoItemConfiguration implements NBTSerializer<DracoItemConfiguration>
{
	static final String PARENT_KEY = "DracoItemConfiguration";
	
	private final DracoItem item;
	private final ItemStack itemStack;
	
	private final DracoTier tier;
	
	private List<DracoModule> modules;
	
	DracoItemConfiguration(DracoItem item, ItemStack itemStack)
	{
		this.item = item;
		this.itemStack = itemStack;
		this.tier = item.tier();
	}
	
	public DracoItem item()
	{
		return item;
	}
	
	public ItemStack itemStack()
	{
		return itemStack;
	}
	
	public DracoTier tier()
	{
		return tier;
	}
	
	public <M extends DracoModule> Optional<M> getModule(Class<M> moduleClass)
	{
		return modules.stream()
				.filter((m) -> moduleClass.isAssignableFrom(m.getClass()))
				.map((m) -> (M) m)
				.findAny();
	}
	
	public <M extends DracoModule> M getModuleOrCreate(Class<M> moduleClass)
	{
		return this.getModule(moduleClass).orElseGet(() ->
		{
			DraconicIndustrialization.LOGGER.info("Creating default module instance for item ({})", moduleClass.getName());
			M module = DracoModules.create(moduleClass, item);
			module.deserialize(new NBTTagWrapper());
			return module;
		});
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		modules = tag.getList("Modules").stream()
				.map((nbt) -> DracoModules.create(nbt.getString("Key"), item))
				.toList();
	}
	
	@Override
	public void write(NBTTagWrapper tag)
	{
		tag.setList("Modules", modules.stream()
				.map(NBTSerializer::serialize)
				.map(NBTTagWrapper::new)
				.toList());
	}
}
