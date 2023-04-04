package zav.naninovel.core.adapter;

import org.eclipse.core.runtime.IAdapterFactory;

import com.fasterxml.jackson.core.TreeNode;

/**
 * Utility class for transforming objects of type {@link String} into
 * {@link Boolean}, using the Eclipse adapters.
 */
public class BooleanAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		return adapterType.cast(Boolean.toString((Boolean) adaptableObject));
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { String.class, TreeNode.class };
	}
}
