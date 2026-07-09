package com.app.cards.config;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import java.io.IOException;

public class CustomSerializer extends JsonSerializer<String> {

    @Override
    public void serialize(String value, JsonGenerator gen, SerializerProvider serializers) throws IOException {

        if (value == null) {
            gen.writeNull();
            return;
        }
        // Keep last 4 digits visible, mask everything else
        if (value.length() > 4) {
            String masked = "****-****-****-" + value.substring(value.length() - 4);
            gen.writeString(masked);
        } else {
            gen.writeString("****");
        }

    }
}
