package io.su0.json.parser.benchmark;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class BenchmarkUtil {

    private static final int BUFFER_SIZE = 64 * 1024; // 64 Kb

    public static byte[] getResource(String name) {
        try (InputStream stream = resourceStream(name)) {
            return toBytes(stream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static InputStream resourceStream(String name) {
        return BenchmarkUtil.class.getClassLoader().getResourceAsStream(name);
    }

    private static byte[] toBytes(InputStream stream) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        int line = 0;
        while ((line = stream.read(buffer)) != -1) {
            os.write(buffer, 0, line);
        }
        os.flush();
        os.close();
        return os.toByteArray();
    }

    private BenchmarkUtil() {
    }
}
