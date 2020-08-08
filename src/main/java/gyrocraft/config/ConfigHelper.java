package gyrocraft.config;

import net.minecraftforge.fml.config.ModConfig;

/**
 * This bakes the config values to normal fields
 *
 * @author Cadiboo
 * It can be merged into the main GyroCraftConfig class, but is separate because of personal preference and to keep the code organised
 */
public final class ConfigHelper {

	public static void bakeClient(final ModConfig config) {
		GyroCraftConfig.clientBoolean = ConfigHolder.CLIENT.clientBoolean.get();
		GyroCraftConfig.clientStringList = ConfigHolder.CLIENT.clientStringList.get();
		GyroCraftConfig.clientDyeColorEnum = ConfigHolder.CLIENT.clientDyeColorEnum.get();

		GyroCraftConfig.modelTranslucency = ConfigHolder.CLIENT.modelTranslucency.get();
		GyroCraftConfig.modelScale = ConfigHolder.CLIENT.modelScale.get().floatValue();
	}

	public static void bakeServer(final ModConfig config) {
		GyroCraftConfig.serverBoolean = ConfigHolder.SERVER.serverBoolean.get();
		GyroCraftConfig.serverStringList = ConfigHolder.SERVER.serverStringList.get();
		GyroCraftConfig.serverEnumDyeColor = ConfigHolder.SERVER.serverEnumDyeColor.get();

		GyroCraftConfig.electricFurnaceEnergySmeltCostPerTick = ConfigHolder.SERVER.electricFurnaceEnergySmeltCostPerTick.get();
		GyroCraftConfig.heatCollectorTransferAmountPerTick = ConfigHolder.SERVER.heatCollectorTransferAmountPerTick.get();
	}

}
