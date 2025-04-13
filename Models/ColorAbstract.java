package Models;

public abstract class ColorAbstract {
	/**
	 * the red, green, and blue component values stored
	 * in range [0.0 .. 1.0]
	 */
	protected double r, g, b;

	/**
	 * Get access to red component
	 * @return red value
	 */
	public double getR() {
		return r;
	}

	/**
	 * Set access to red component
	 * @param r the new value of red
	 * @throws IllegalArgumentException if parameter < 0.0 or parameter > 1.0
	 */
	public void setR(double r) throws IllegalArgumentException {
		if (r < 0.0 || r > 1.0) {
			throw new IllegalArgumentException("value out of range");
		}
		this.r = r;
	}

	/**
	 * Get access to green component
	 * @return green value
	 */
	public double getG() {
		return g;
	}

	/**
	 * Set access to green component
	 * @param r the new value of green
	 * @throws IllegalArgumentException if parameter < 0.0 or parameter > 1.0
	 */
	public void setG(double g) throws IllegalArgumentException {
		if (g < 0.0 || g > 1.0) {
			throw new IllegalArgumentException("value out of range");
		}
		this.g = g;
	}


	/**
	 * Get access to blue component
	 * @return red value
	 */
	public double getB() {
		return b;
	}

	/**
	 * Set access to blue component
	 * @param b the new value of blue
	 * @throws IllegalArgumentException if parameter < 0.0 or parameter > 1.0
	 */
	public void setB(double b) throws IllegalArgumentException {
		if (b < 0.0 || b > 1.0) {
			throw new IllegalArgumentException("value out of range");
		}
		this.b = b;
	}

	@Override
	public String toString() {
		String s = "[" + r + ", " + g + ", " + b + "]";
		return s;
	}
	
	/**
	 * Scale to integer values
	 * @param s the integer scale factor
	 * @return array of scaled values {R, G, B}
	 */
	public abstract int[] scale(int s);
}
