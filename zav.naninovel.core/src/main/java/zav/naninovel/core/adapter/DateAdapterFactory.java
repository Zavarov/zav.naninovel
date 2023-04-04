package zav.naninovel.core.adapter;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * Utility class for transforming objects of type {@link String} into
 * {@link Date}, using the Eclipse adapters.
 */
public class DateAdapterFactory implements IAdapterFactory {
	/* package */ static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		return adapterType.cast(DATE_FORMAT.format((Date) adaptableObject));
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { String.class };
	}
}
