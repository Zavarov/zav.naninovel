package zav.naninovel.ui.widgets;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;

public class GridComposite extends Composite {
	public GridComposite(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout());
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

	public GridComposite(Composite parent, int style, int numColumns, boolean makeColumnsEqualWidth) {
		super(parent, style);
		setLayout(new GridLayout(numColumns, makeColumnsEqualWidth));
		setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
	}

}
