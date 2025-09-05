package builderb0y.fractallightning;

import org.joml.Matrix4f;

import net.minecraft.client.render.VertexConsumer;

public class LightningRendererImpl extends LightningRenderer {

	public static final float
		baseWidth = 0.015625F,
		eighthWidth = baseWidth * 0.125F;

	public final float age;

	public LightningRendererImpl(Matrix4f modelViewMatrix, VertexConsumer buffer, float age) {
		super(modelViewMatrix, buffer);
		this.age = age;
	}

	@Override
	public float adjustWidth(float width, float startFrac, float endFrac) {
		if (this.age >= 8.0F) {
			return width - (this.age * eighthWidth - baseWidth);
		}
		else if (startFrac >= this.age * 0.125F) {
			return 0.0F;
		}
		else {
			return width;
		}
	}

	public void generatePoints(long seed) {
		this.generatePoints(
			seed,
			RNG.nextUniformFloat (seed += RNG.PHI64) * 32.0F,
			RNG.nextPositiveFloat(seed += RNG.PHI64) * 64.0F + 128.0F,
			RNG.nextUniformFloat (seed += RNG.PHI64) * 32.0F,
			0.0F,
			0.0F,
			0.0F,
			baseWidth
		);
	}
}