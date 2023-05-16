package net.swedz.draconic_industrialization.recipes;

import aztech.modern_industrialization.machines.init.MIMachineRecipeTypes;
import aztech.modern_industrialization.recipe.json.MIRecipeJson;
import net.minecraft.world.item.Items;
import net.swedz.draconic_industrialization.items.DIItems;

public final class RecipeDatagen
{
	public static void create(RecipeMapWrapper map)
	{
		map.addMI("draconic_industrialization:materials/ebf/draconium", () -> MIRecipeJson.create(MIMachineRecipeTypes.BLAST_FURNACE, 32, 20 * 20)
				.addItemInput(DIItems.DRACONIUM_DUST, 1)
				.addItemOutput(DIItems.DRACONIUM_HOT_INGOT, 1));
		
		map.addMI("draconic_industrialization:materials/mixer/adamantine", () -> MIRecipeJson.create(MIMachineRecipeTypes.MIXER, 2, 5 * 20)
				.addItemInput(Items.NETHERITE_SCRAP, 1)
				.addItemInput(DIItems.DRACONIUM_DUST, 3)
				.addItemOutput(DIItems.ADAMANTINE_DUST, 1));
		
		map.addMI("draconic_industrialization:materials/ebf/adamantine", () -> MIRecipeJson.create(MIMachineRecipeTypes.BLAST_FURNACE, 128, 20 * 20)
				.addItemInput(DIItems.ADAMANTINE_DUST, 1)
				.addItemOutput(DIItems.ADAMANTINE_HOT_INGOT, 1));
		
		map.addMI("draconic_industrialization:materials/ebf/awakened_draconium", () -> MIRecipeJson.create(MIMachineRecipeTypes.BLAST_FURNACE, 192, 20 * 20)
				.addItemInput(DIItems.AWAKENED_DRACONIUM_DUST, 1)
				.addItemOutput(DIItems.AWAKENED_DRACONIUM_HOT_INGOT, 1));
	}
}
