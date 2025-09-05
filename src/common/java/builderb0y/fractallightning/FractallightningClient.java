package builderb0y.fractallightning;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.minecraft.client.render.entity.EntityRenderers;
import net.minecraft.entity.EntityType;

public class FractallightningClient implements ClientModInitializer {

	public static final String
		MODID   = "fractallightning",
		MODNAME = "Fractal Lightning";
	public static final Logger
		LOGGER = LoggerFactory.getLogger(MODNAME);

	@Override
	public void onInitializeClient() {
		EntityRenderers.register(EntityType.LIGHTNING_BOLT, FractalLightningEntityRenderer::new);
	}
}