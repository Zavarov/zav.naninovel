package zav.naninovel.ui.adapter;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.core.runtime.IAdapterFactory;
import org.eclipse.jface.viewers.TreeNode;

public class TreeNodeAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		TreeNode source = (TreeNode) adaptableObject;
		Object value = source.getValue();

		if (value == null) {
			return adapterType.cast("");
		}

		T adaptee = Adapters.adapt(value, adapterType);

		if (adaptee == null) {
			return adapterType.cast("");
		}

		return adaptee;
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { String.class };
	}

}
