package zav.naninovel.core.adapter;

import org.eclipse.core.runtime.IAdapterFactory;

public class IntegerAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		return adapterType.cast(Integer.toString((Integer) adaptableObject));
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { String.class };
	}
}
