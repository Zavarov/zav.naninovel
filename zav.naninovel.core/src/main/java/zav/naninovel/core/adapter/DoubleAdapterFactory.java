package zav.naninovel.core.adapter;

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * Utility class for transforming objects of type {@link String} into
 * {@link Double}, using the Eclipse adapters.
 */
public class DoubleAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		return adapterType.cast(Double.toString((Double) adaptableObject));
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { String.class };
	}
}
