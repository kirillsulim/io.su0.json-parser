package io.su0.json.parser;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class MultithreadParsingTest {

    private static final int THREAD_COUNT = 100;
    public static final int COLLECTION_LENGTH = 1_000;


    private static class Container {
        private int value;

        public int getValue() {
            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    private static class ContainerParser extends AbstractJsonParserOfType<Container> {
        public ContainerParser() {
            super(Container::new);
            add("$.value", (container, jsonNode) -> container.setValue(jsonNode.intValue()));
        }
    }

    private static class TestParser extends CollectionParser<Container, ArrayList<Container>> {
        public TestParser() {
            super(ArrayList::new, new ContainerParser());
        }
    }

    private static final TestParser PARSER = new TestParser();

    @Test
    public void shouldParseInMultuthreadingEnvironment() throws Exception {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        stream.write("[".getBytes());
        for (int i = 0; i < COLLECTION_LENGTH; ++i) {
            stream.write("{\"value\":".getBytes());
            stream.write(String.valueOf(i).getBytes());
            stream.write("}".getBytes());
            if (i != COLLECTION_LENGTH - 1) {
                stream.write(",".getBytes());
            }
        }
        stream.write("]".getBytes());

        final byte[] bytes = stream.toByteArray();

        List<Thread> threads = new ArrayList<>(THREAD_COUNT);

        AtomicBoolean mismatch = new AtomicBoolean(false);

        for (int i = 0; i < THREAD_COUNT; ++i) {
            threads.add(new Thread(() -> {
                try {
                    ArrayList<Container> parsed = PARSER.parse(new ByteArrayInputStream(bytes));

                    assertEquals(COLLECTION_LENGTH, parsed.size());
                    for (int j = 0; j < COLLECTION_LENGTH; ++j) {
                        if (j != parsed.get(j).value) {
                            mismatch.set(true);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }));
        }
        for (Thread thread : threads) {
            thread.start();
        }
        for (Thread thread : threads) {
            thread.join();
        }
        assertFalse(mismatch.get(), "Value mismatch found");
    }
}
