package zav.naninovel.core.model;

public record GameState(String className, String runtime, String instanceId) {

	public static final String SEPARATOR = ", ";

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();

		builder.append(className);
		builder.append(SEPARATOR);
		builder.append(runtime);
		builder.append(SEPARATOR);
		builder.append("Version=0.0.0.0");
		builder.append(SEPARATOR);
		builder.append("Culture=neutral");
		builder.append(SEPARATOR);
		builder.append("PublicKeyToken=null");

		if (instanceId != null) {
			builder.append(SEPARATOR);
			builder.append(instanceId);
		}

		return builder.toString();
	}
}
