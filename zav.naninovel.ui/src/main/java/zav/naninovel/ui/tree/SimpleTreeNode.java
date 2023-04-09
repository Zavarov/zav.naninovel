package zav.naninovel.ui.tree;

import org.eclipse.core.runtime.Adapters;
import org.eclipse.jface.viewers.TreeNode;

import zav.naninovel.core.model.SaveGameValue;

public class SimpleTreeNode extends TreeNode {

	public SimpleTreeNode(TreeNode parent, String name, SaveGameValue value) {
		this(parent, name);
		this.setChildren(new TreeNode[] { new SaveGameTreeNode(this, value) });
	}

	public SimpleTreeNode(TreeNode parent, Object value) {
		super(value);
		setParent(parent);
	}

	@Override
	public String getValue() {
		return Adapters.adapt(value, String.class);
	}
}
