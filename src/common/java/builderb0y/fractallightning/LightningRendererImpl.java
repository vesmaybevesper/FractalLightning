package builderb0y.fractallightning;

import org.joml.Matrix4f;

import net.minecraft.client.render.VertexConsumer;
import net.minecraft.util.math.ColorHelper;

import builderb0y.fractallightning.config.FractalLightningConfig;

public class LightningRendererImpl extends LightningRenderer {

	public static final float
		baseWidth = 0.015625F,
		eighthWidth = baseWidth * 0.125F;

	public final float age;
	public final boolean rainbow;

	public LightningRendererImpl(Matrix4f modelViewMatrix, VertexConsumer buffer, float age) {
		super(modelViewMatrix, buffer);
		this.age = age;
		this.rainbow = FractalLightningConfig.instance().rainbow_mode.active;
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

	@Override
	public void addQuads(
		float transformedStartX,
		float transformedStartY,
		float transformedStartZ,
		float startFrac,
		float transformedEndX,
		float transformedEndY,
		float transformedEndZ,
		float endFrac,
		float cameraDistanceSquared,
		float width
	) {
		int innerArgb, outerArgb;
		if (this.rainbow) {
			double red   = square(fastCos01(startFrac              ) * 0.5D + 0.5D);
			double green = square(fastCos01(startFrac - 1.0D / 3.0D) * 0.5D + 0.5D);
			double blue  = square(fastCos01(startFrac - 2.0D / 3.0D) * 0.5D + 0.5D);

			double rcpMagnitude = 1.0D / Math.sqrt(red * red + green * green + blue * blue);
			red   = Math.sqrt(red   * rcpMagnitude);
			green = Math.sqrt(green * rcpMagnitude);
			blue  = Math.sqrt(blue  * rcpMagnitude);

			innerArgb = ColorHelper #if MC_VERSION < MC_1_21_2 .Argb #endif .getArgb(127, Math.min((int)(red *  64.0D + 192.0D), 255), Math.min((int)(green *  64.0D + 192.0D), 255), Math.min((int)(blue *  64.0D + 192.0D), 255));
			outerArgb = ColorHelper #if MC_VERSION < MC_1_21_2 .Argb #endif .getArgb(0,   Math.min((int)(red * 256.0D         ), 255), Math.min((int)(green * 256.0D         ), 255), Math.min((int)(blue * 256.0D         ), 255));
		}
		else {
			innerArgb = 0x7FFFFFFF;
			outerArgb = 0x00003F7F;
		}
		this.addColoredQuads(
			transformedStartX,
			transformedStartY,
			transformedStartZ,
			transformedEndX,
			transformedEndY,
			transformedEndZ,
			cameraDistanceSquared,
			width,
			innerArgb,
			outerArgb
		);
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