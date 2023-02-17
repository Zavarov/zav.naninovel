package zav.naninovel.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Layout;

public abstract class SaveGameEditorPart {
	protected final Composite control;

	public SaveGameEditorPart(Composite parent) {
		this(parent, new GridLayout());
	}

	public SaveGameEditorPart(Composite parent, GridLayout layout) {
		this(parent, layout, new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	public SaveGameEditorPart(Composite parent, Layout layout, Object layoutData) {
		this.control = new Composite(parent, SWT.NONE);
		this.control.setLayout(layout);
		this.control.setLayoutData(layoutData);
	}

	public final Composite getControl() {
		return control;
	}

	protected void postConstruct() {
		// May be overwritten by subclasses
	}
}
