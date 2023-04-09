package zav.naninovel.core.adapter;

import java.text.ParseException;
import java.util.Date;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.eclipse.core.runtime.IAdapterFactory;

/**
 * Utility class for transforming objects of type {@link String} into any other
 * serializable datatype, using the Eclipse adapters.
 */
public class StringAdapterFactory implements IAdapterFactory {
	private static final Logger LOGGER = Logger.getLogger(StringAdapterFactory.class.getName());

	@Override
	public <T> T getAdapter(Object adaptableObject, Class<T> adapterType) {
		String source = (String) adaptableObject;

		if (adapterType == Byte.class) {
			return adaptNumber(adapterType, () -> Byte.valueOf(source));
		} else if (adapterType == Short.class) {
			return adaptNumber(adapterType, () -> Short.valueOf(source));
		} else if (adapterType == Integer.class) {
			return adaptNumber(adapterType, () -> Integer.valueOf(source));
		} else if (adapterType == Long.class) {
			return adaptNumber(adapterType, () -> Long.valueOf(source));
		} else if (adapterType == Float.class) {
			return adaptNumber(adapterType, () -> Float.valueOf(source));
		} else if (adapterType == Double.class) {
			return adaptNumber(adapterType, () -> Double.valueOf(source));
		} else if (adapterType == Boolean.class) {
			return adaptBoolean(adapterType, source);
		} else if (adapterType == Date.class) {
			return adaptDate(adapterType, source);
		} else if (adapterType == String.class) {
			return adapterType.cast(source);
		} else {
			LOGGER.log(Level.SEVERE, "Unknown type " + adapterType, new IllegalArgumentException());
			return null;
		}
	}

	@Override
	public Class<?>[] getAdapterList() {
		return new Class<?>[] { Byte.class, Short.class, Integer.class, Long.class, Float.class, Double.class,
				Boolean.class, Date.class };
	}

	private static <T, U extends Number> T adaptNumber(Class<T> clazz, Supplier<U> adapter) {
		try {
			return clazz.cast(adapter.get());
		} catch (NumberFormatException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}

	private static <T> T adaptBoolean(Class<T> clazz, String source) {
		return clazz.cast(Boolean.valueOf(source));
	}

	private static <T> T adaptDate(Class<T> clazz, String source) {
		try {
			return clazz.cast(DateAdapterFactory.DATE_FORMAT.parse(source));
		} catch (ParseException e) {
			LOGGER.log(Level.SEVERE, e.getMessage(), e);
			return null;
		}
	}
}
