package zav.naninovel.ui.viewer;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.eclipse.jface.viewers.ArrayContentProvider;
import org.eclipse.jface.viewers.ColumnLabelProvider;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

import zav.naninovel.core.model.SaveGameValue;
import zav.naninovel.ui.internal.viewer.SaveGameColumnLabelProvider;
import zav.naninovel.ui.internal.viewer.SaveGameEditingSupport;

public class SaveGameMapViewer {
	protected final TableViewer viewer;

	public SaveGameMapViewer(Composite parent, List<String> keys, List<SaveGameValue> values) {
		List<Integer> range = IntStream.range(0, keys.size()).boxed().collect(Collectors.toList());

		viewer = new TableViewer(parent, SWT.SINGLE | SWT.H_SCROLL | SWT.V_SCROLL | SWT.BORDER);

		TableViewerColumn keyColumn = new TableViewerColumn(viewer, SWT.NONE);
		keyColumn.setLabelProvider(ColumnLabelProvider.createTextProvider(i -> keys.get((int) i)));

		TableViewerColumn valueColumn = new TableViewerColumn(viewer, SWT.NONE);
		valueColumn.setLabelProvider(new SaveGameColumnLabelProvider(i -> values.get((int) i)));
		valueColumn.setEditingSupport(new SaveGameEditingSupport(viewer, (i -> values.get((int) i))));

		viewer.setContentProvider(ArrayContentProvider.getInstance());
		viewer.setInput(range);

		viewer.getTable().setLayout(new GridLayout(2, true));
		viewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}
}
