package builderb0y.fractallightning;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import org.joml.Matrix4f;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.state.LightningEntityRenderState;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LightningEntity;
import net.minecraft.util.Identifier;

#if MC_VERSION >= MC_1_21_2

@Environment(EnvType.CLIENT)
public class FractalLightningEntityRenderer extends EntityRenderer<LightningEntity, LightningEntityRenderState> {

	public FractalLightningEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public LightningEntityRenderState createRenderState() {
		return new LightningEntityRenderState();
	}

	@Override
	public void updateRenderState(LightningEntity entity, LightningEntityRenderState state, float tickDelta) {
		super.updateRenderState(entity, state, tickDelta);
		state.seed = entity.seed;
	}

	@Override
	public void render(LightningEntityRenderState state, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		VertexConsumer buffer = vertexConsumers.getBuffer(LightningRenderer.LIGHTNING_LAYER);
		Matrix4f matrix = matrices.peek().getPositionMatrix();
		new LightningRendererImpl(matrix, buffer, state.age).generatePoints(state.seed);
	}

	@Override
	public boolean canBeCulled(LightningEntity entity) {
		return false;
	}
}

#else

@Environment(EnvType.CLIENT)
public class FractalLightningEntityRenderer extends EntityRenderer<LightningEntity> {

	public FractalLightningEntityRenderer(EntityRendererFactory.Context context) {
		super(context);
	}

	@Override
	public void render(LightningEntity entity, float yaw, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
		VertexConsumer buffer = vertexConsumers.getBuffer(LightningRenderer.LIGHTNING_LAYER);
		Matrix4f matrix = matrices.peek().getPositionMatrix();
		new LightningRendererImpl(matrix, buffer, entity.age + tickDelta).generatePoints(entity.seed);
	}

	@Override
	public Identifier getTexture(LightningEntity entity) {
		return SpriteAtlasTexture.BLOCK_ATLAS_TEXTURE; //this is what vanilla lightning uses.
	}
}

#endif