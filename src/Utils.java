public class Utils {
    private static float WORLD_TO_PIXEL_SCALE_FACTOR = 32;
    
    public static int toPixelCoords(float world) {
	return (int)(world * WORLD_TO_PIXEL_SCALE_FACTOR);
    }
}
