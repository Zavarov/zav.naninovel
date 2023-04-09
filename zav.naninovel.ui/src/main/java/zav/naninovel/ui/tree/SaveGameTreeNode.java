package zav.naninovel.ui.tree;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.jface.viewers.TreeNode;

import zav.naninovel.core.model.SaveGameException;
import zav.naninovel.core.model.SaveGameValue;

public class SaveGameTreeNode extends SimpleTreeNode {
	private static final Logger LOGGER = Logger.getLogger(SaveGameTreeNode.class.getName());

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
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
		}
	}
}
