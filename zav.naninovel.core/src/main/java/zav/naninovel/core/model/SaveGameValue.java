package zav.naninovel.core.model;

import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.eclipse.core.databinding.observable.AbstractObservable;
import org.eclipse.core.databinding.observable.ObservableTracker;
import org.eclipse.core.databinding.observable.Realm;
import org.eclipse.core.runtime.Platform;

public class SaveGameValue extends AbstractObservable {
	private final Class<?> clazz;
	private final Supplier<Object> getter;
	private final Consumer<Object> setter;

	@SuppressWarnings("unchecked")
	public <T> SaveGameValue(Class<T> clazz, Supplier<T> getter, Consumer<T> setter) {
		super(Realm.getDefault());
		this.clazz = clazz;
		this.getter = (Supplier<Object>) getter;
		this.setter = (Consumer<Object>) setter;
	}

	public String getValue() {
		ObservableTracker.getterCalled(this);
		return Platform.getAdapterManager().getAdapter(getter.get(), String.class);
	}

	public void setValue(String value) throws SaveGameException {
		Object oldValue = getter.get();
		Object newValue = Platform.getAdapterManager().getAdapter(value, clazz);

		if (newValue == null) {
			throw new SaveGameException(value);
		}

		if (Objects.equals(oldValue, newValue)) {
			return;
		}

		setter.accept(newValue);

		fireChange();
	}

	@Override
	public boolean isStale() {
		return false;
	}
}
