package zav.naninovel.core.model.internal;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import zav.naninovel.core.model.SerializableMap;

public class SerializableMapDeserializer extends StdDeserializer<SerializableMap> {
	private static final long serialVersionUID = 1L;

	public SerializableMapDeserializer() {
		super(SerializableMap.class);
	}

	@Override
	public SerializableMap deserialize(JsonParser p, DeserializationContext ctxt) throws IOException {
		TypeReference<Map<String, List<Object>>> type = new TypeReference<>() {
		};

		Map<String, List<Object>> source = p.readValueAs(type);

		assert source.size() == 2;
		assert source.containsKey("keys");
		assert source.containsKey("values");

		List<String> keys = source.get("keys").stream().map(String.class::cast).collect(Collectors.toList());
		List<Object> values = source.get("values");

		SerializableMap target = new SerializableMap();
		target.setKeys(keys);
		target.setValues(values);
		return target;
	}

}
