package zav.naninovel.ui.viewer;

import java.util.List;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.jface.databinding.swt.WidgetSideEffects;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;

import zav.naninovel.core.model.SaveGameValue;
import zav.naninovel.ui.SaveGameEditorPart;
import zav.naninovel.ui.internal.viewer.SaveGameColumnLabelProvider;
import zav.naninovel.ui.internal.viewer.SaveGameEditingSupport;

public class SaveGameListViewer extends SaveGameEditorPart {
	protected final TableViewer viewer;

	public SaveGameListViewer(Composite parent, List<SaveGameValue> content) {
		super(parent);

		viewer = new TableViewer(control, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		TableViewerColumn viewerColumn = new TableViewerColumn(viewer, SWT.NONE);
		viewerColumn.setLabelProvider(new SaveGameColumnLabelProvider());
		viewerColumn.setEditingSupport(new SaveGameEditingSupport(viewer));

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(content);
		viewer.getTable().setLayout(new GridLayout());
		viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));

		// Make sure that the table column takes up the entire viewer
		IObservableValue<Rectangle> observable = WidgetProperties.bounds().observe(control);

		WidgetSideEffects.createFactory(control).create(() -> {
			TableColumn tableColumn = viewerColumn.getColumn();
			Rectangle bounds = observable.getValue();

			tableColumn.pack();
			tableColumn.setWidth(Math.max(tableColumn.getWidth(), bounds.width));
		});

		postConstruct();
	}
}
