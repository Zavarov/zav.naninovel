package zav.naninovel.ui;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.ui.part.MultiPageEditorPart;

public abstract class SaveGameEditor extends MultiPageEditorPart {

	@Override
	public void doSave(IProgressMonitor monitor) {
		getEditor(0).doSave(monitor);
	}

	@Override
	public void doSaveAs() {
		getEditor(0).doSaveAs();
	}

	@Override
	public boolean isSaveAsAllowed() {
		return true;
	}
}
