package zav.naninovel.core.adapter;

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * Utility class for transforming objects of type {@link String} into
 * {@link Byte}, using the Eclipse adapters.
 */
public class ByteAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		return adapterType.cast(Byte.toString((Byte) adaptableObject));
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { String.class };
	}
}
