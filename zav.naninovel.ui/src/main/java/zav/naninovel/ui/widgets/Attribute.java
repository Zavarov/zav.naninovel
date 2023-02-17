package zav.naninovel.ui.widgets;

import org.eclipse.core.databinding.observable.value.IObservableValue;
import org.eclipse.core.runtime.Platform;
import org.eclipse.jface.databinding.swt.WidgetSideEffects;
import org.eclipse.jface.databinding.swt.typed.WidgetProperties;
import org.eclipse.jface.layout.GridDataFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

import zav.naninovel.core.model.SaveGameException;
import zav.naninovel.core.model.SaveGameValue;

public class Attribute {

	public Attribute(Composite parent, String title, SaveGameValue adapter) {
		GridData gd = GridDataFactory.swtDefaults().align(SWT.FILL, SWT.CENTER).grab(true, false).create();

		Composite composite = new Composite(parent, SWT.NONE);
		composite.setLayout(new GridLayout(2, true));
		composite.setLayoutData(GridDataFactory.copyData(gd));

		Label key = new Label(composite, SWT.NONE);
		key.setLayoutData(GridDataFactory.copyData(gd));
		key.setText(title);

		Text value = new Text(composite, SWT.BORDER);
		value.setEditable(true);
		value.setLayoutData(GridDataFactory.copyData(gd));
		value.setText(adapter.getValue());

		// Synchronize UI and data model
		IObservableValue<String> observable = WidgetProperties.text(SWT.Modify).observeDelayed(500, value);
		WidgetSideEffects.createFactory(composite).create(() -> {
			try {
				adapter.setValue(observable.getValue());
			} catch (SaveGameException e) {
				Platform.getLog(Attribute.class).error(e.getLocalizedMessage(), e);
				// Revert to original value...
				value.setText(adapter.getValue());
			}
		});
	}
}
