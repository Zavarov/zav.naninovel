package zav.naninovel.ui;

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

import zav.naninovel.ui.viewer.SaveGameTreeNodeEditingSupport;

public class SaveGameTreeViewer extends SaveGameEditorPart {
	protected final TreeViewer viewer;

	public SaveGameTreeViewer(Composite parent, TreeNode root) {
		this(parent, TreeViewer.ALL_LEVELS, root);
	}

	public SaveGameTreeViewer(Composite parent, int level, TreeNode root) {
		super(parent);

		viewer = new TreeViewer(control, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

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
		IObservableValue<Rectangle> observable = WidgetProperties.bounds().observe(control);

		WidgetSideEffects.createFactory(control).create(() -> {
			TreeColumn treeColumn = viewerColumn.getColumn();
			Rectangle bounds = observable.getValue();

			treeColumn.pack();
			treeColumn.setWidth(Math.max(treeColumn.getWidth(), bounds.width));
		});

		postConstruct();
	}
}
