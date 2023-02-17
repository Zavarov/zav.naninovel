package zav.naninovel.ui.widgets;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;

import zav.naninovel.core.model.SaveGame;

public class Thumbnail {
	public Thumbnail(Composite parent, SaveGame model) {
		Composite composite = new GridComposite(parent, SWT.NONE);

		Label thumbnail = new Label(composite, SWT.NONE);
		thumbnail.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true, 2, 1));
		thumbnail.setImage(getImage(composite, model));
	}

	private static Image getImage(Composite parent, SaveGame model) {
		try (InputStream is = new ByteArrayInputStream(model.getThumbnail())) {
			Image image = new Image(Display.getCurrent(), is);

			parent.addDisposeListener(event -> image.dispose());

			return image;
		} catch (IOException e) {
			throw new IllegalArgumentException(e.getMessage(), e);
		}
	}
}
