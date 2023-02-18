package zav.naninovel.core.model;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.runtime.Platform;

public class SaveGameValue {
	private final Class<?> clazz;
	private final Supplier<Object> getter;
	private final Consumer<Object> setter;

	@SuppressWarnings("unchecked")
	public <T> SaveGameValue(Class<T> clazz, Supplier<T> getter, Consumer<T> setter) {
		this.clazz = clazz;
		this.getter = (Supplier<Object>) getter;
		this.setter = (Consumer<Object>) setter;
	}

	public String getValue() {
		return Platform.getAdapterManager().getAdapter(getter.get(), String.class);
	}

	public void setValue(String value) throws SaveGameException {
		Object realValue = Platform.getAdapterManager().getAdapter(value, clazz);

		if (realValue == null || Objects.equals(realValue, getter.get())) {
			throw new SaveGameException(value);
		}

		setter.accept(realValue);
	}
}