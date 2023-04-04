package zav.naninovel.core.adapter;

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * Utility class for transforming objects of type {@link String} into
 * {@link Short}, using the Eclipse adapters.
 */
public class ShortAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		return adapterType.cast(Short.toString((Short) adaptableObject));
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { String.class };
	}
}
