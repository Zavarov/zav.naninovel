package zav.naninovel.ui.internal.viewer;

import java.util.function.Function;

import org.eclipse.jface.viewers.ColumnLabelProvider;

import zav.naninovel.core.model.SaveGameValue;

public class SaveGameColumnLabelProvider extends ColumnLabelProvider {
	private final Function<Object, SaveGameValue> mapper;

	public SaveGameColumnLabelProvider() {
		this(adapter -> (SaveGameValue) adapter);
	}

	public SaveGameColumnLabelProvider(Function<Object, SaveGameValue> mapper) {
		this.mapper = mapper;
	}

	@Override
	public String getText(Object element) {
		return mapper.apply(element).getValue();
	}
}
