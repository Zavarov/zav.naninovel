package zav.naninovel.core.model.internal;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import zav.naninovel.core.model.SerializableMap;

public class SerializableMapSerializer extends StdSerializer<SerializableMap> {
	private static final long serialVersionUID = 1L;

	public SerializableMapSerializer() {
		super(SerializableMap.class, false);
	}

	@Override
	public void serialize(SerializableMap value, JsonGenerator gen, SerializerProvider provider) throws IOException {
		Map<String, Object> result = new LinkedHashMap<>();

		result.put("keys", value.getKeys());
		result.put("values", value.getValues());

		gen.writeStartObject();
		gen.writeObjectField("objectJsonMap", result);
		gen.writeEndObject();
	}

}
