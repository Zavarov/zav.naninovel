package zav.naninovel.ui.internal.viewer;

import java.util.function.Function;

import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.viewers.CellEditor;
import org.eclipse.jface.viewers.EditingSupport;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TextCellEditor;

import zav.naninovel.core.model.SaveGameException;
import zav.naninovel.core.model.SaveGameValue;
import zav.naninovel.ui.widgets.Attribute;

public class SaveGameEditingSupport extends EditingSupport {
	private final CellEditor editor;
	private final Function<Object, SaveGameValue> mapper;

	public SaveGameEditingSupport(TableViewer viewer) {
		this(viewer, adapter -> (SaveGameValue) adapter);
	}

	public SaveGameEditingSupport(TableViewer viewer, Function<Object, SaveGameValue> mapper) {
		super(viewer);
		this.editor = new TextCellEditor(viewer.getTable());
		this.mapper = mapper;
	}

	@Override
	protected CellEditor getCellEditor(Object element) {
		return editor;
	}

	@Override
	protected boolean canEdit(Object element) {
		return true;
	}

	@Override
	protected String getValue(Object element) {
		return mapper.apply(element).getValue();
	}

	@Override
	protected void setValue(Object element, Object value) {
		try {
			mapper.apply(element).setValue((String) value);
			// Update UI with new value
			getViewer().refresh(element);
		} catch (SaveGameException e) {
			Platform.getLog(Attribute.class).error(e.getLocalizedMessage(), e);
		}
	}
}
