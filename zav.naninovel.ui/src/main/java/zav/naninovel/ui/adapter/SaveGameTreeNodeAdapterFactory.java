package zav.naninovel.ui.adapter;

import org.eclipse.core.runtime.IAdapterFactory;

import zav.naninovel.ui.tree.SaveGameTreeNode;

public class SaveGameTreeNodeAdapterFactory implements IAdapterFactory {

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		return adapterType.cast(((SaveGameTreeNode) adaptableObject).getValue());
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { String.class };
	}

}
