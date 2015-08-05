import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
    private static float WORLD_TO_PIXEL_SCALE_FACTOR = 32;
    
    public static int toPixelCoords(float world) {
	return (int)(world * WORLD_TO_PIXEL_SCALE_FACTOR);
    }

    // caller is responsible for closing afterwards
    public static BufferedReader getFileInputStream(String name) throws IOException {
	return new BufferedReader(new FileReader(name));
    }
}
