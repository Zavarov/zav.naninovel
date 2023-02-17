package zav.naninovel.ui.tree;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.TreeNode;

import zav.naninovel.core.model.SaveGameException;
import zav.naninovel.core.model.SaveGameValue;
import zav.naninovel.ui.widgets.Attribute;

public class SaveGameTreeNode extends SimpleTreeNode {

	public SaveGameTreeNode(TreeNode parent, SaveGameValue adapter) {
		super(parent, adapter);
	}

	public SaveGameValue getAdapter() {
		return (SaveGameValue) value;
	}

	@Override
	public String getValue() {
		return getAdapter().getValue();
	}

	public void setValue(String value) {
		try {
			getAdapter().setValue((String) value);
		} catch (SaveGameException e) {
			Platform.getLog(Attribute.class).error(e.getLocalizedMessage(), e);
		}
	}
}
