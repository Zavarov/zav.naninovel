package zav.naninovel.ui.viewer;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetSideEffects;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TreeNode;
import org.eclipse.jface.viewers.TreeNodeContentProvider;
import org.eclipse.jface.viewers.TreeViewer;
import org.eclipse.jface.viewers.TreeViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TreeColumn;

import zav.naninovel.ui.internal.viewer.SaveGameTreeNodeEditingSupport;

public class SaveGameTreeViewer {
	protected final TreeViewer viewer;

	public SaveGameTreeViewer(Composite parent, TreeNode root) {
		this(parent, TreeViewer.ALL_LEVELS, root);
	}

	public SaveGameTreeViewer(Composite parent, int level, TreeNode root) {
		viewer = new TreeViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		TreeViewerColumn viewerColumn = new TreeViewerColumn(viewer, SWT.NONE);
		// TODO
		viewerColumn.setLabelProvider(
				ColumnLabelProvider.createTextProvider(node -> (String) ((TreeNode) node).getValue()));
		viewerColumn.setEditingSupport(new SaveGameTreeNodeEditingSupport(viewer));

		viewer.setAutoExpandLevel(level);
		viewer.setContentProvider(new TreeNodeContentProvider());
		viewer.setInput(root.getChildren());
		viewer.getTree().setLayout(new GridLayout());
		viewer.getTree().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Make sure that the tree column takes up the entire viewer
		IObservableValue<Rectangle> observable = WidgetProperties.bounds().observe(parent);

		WidgetSideEffects.createFactory(parent).create(() -> {
			TreeColumn treeColumn = viewerColumn.getColumn();
			Rectangle bounds = observable.getValue();

			treeColumn.pack();
			treeColumn.setWidth(Math.max(treeColumn.getWidth(), bounds.width));
		});
	}
}
