package zav.naninovel.core.adapter;

import org.eclipse.core.runtime.IAdapterFactory;

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
