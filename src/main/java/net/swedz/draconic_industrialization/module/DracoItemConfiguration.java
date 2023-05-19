package net.swedz.draconic_industrialization.module;

import net.minecraft.world.item.ItemStack;
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
	
	public <M extends DracoModule> Optional<M> getModule(DracoModules.ClassReference<M> module)
	{
		return modules.stream()
				.filter((m) -> module.reference().isAssignableFrom(m.getClass()))
				.map((m) -> (M) m)
				.findAny();
	}
	
	public <M extends DracoModule> M getModuleOrCreate(DracoModules.ClassReference<M> module)
	{
		return this.getModule(module).orElseGet(() -> DracoModules.create(module, item, new NBTTagWrapper()));
	}
	
	@Override
	public void read(NBTTagWrapper tag)
	{
		modules = tag.getList("Modules").stream()
				.map((nbt) -> DracoModules.create(nbt.getString("Key"), item, nbt))
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