package zav.naninovel.ui.viewer;

import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TextCellEditor;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeViewer;

import zav.naninovel.ui.tree.SaveGameTreeNode;

public class SaveGameTreeNodeEditingSupport extends EditingSupport {
	private final CellEditor editor;

	public SaveGameTreeNodeEditingSupport(TreeViewer viewer) {
		super(viewer);
		editor = new TextCellEditor(viewer.getTree());
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		// Only leafs can be edited...
		return !((TreeNode) element).hasChildren();
	}

	@Override
	protected Object getValue(Object element) {
		return ((TreeNode) element).getValue();
	}

	@Override
	protected void setValue(Object element, Object value) {
		((SaveGameTreeNode) element).setValue((String) value);
		// Update UI with new value
		getViewer().refresh(element);
	}
}
