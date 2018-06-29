package io.su0.json.parser;

import java.io.IOException;
import java.io.InputStream;

public interface Parser<Type> {

    Type parse(InputStream inputStream) throws IOException;
}
