package zav.naninovel.core.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.zip.Deflater;
import java.util.zip.DeflaterInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import zav.naninovel.core.model.bluepharmac.InventoryUI;
import zav.naninovel.core.model.internal.SerializableMapDeserializer;
import zav.naninovel.core.model.internal.SerializableMapSerializer;
import zav.naninovel.core.model.ui.BacklogPanel;
import zav.naninovel.core.model.ui.ChoiceHandlerPanel;
import zav.naninovel.core.model.ui.VariableInputPanel;

public class SaveGame {
	// @formatter:off
	protected static final GameState CUSTOM_VARIABLE_MANAGER = new GameState("Naninovel.CustomVariableManager+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState INPUT_MANAGER = new GameState("Naninovel.InputManager+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState CAMERA_MANAGER = new GameState("Naninovel.CameraManager+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState BACKGROUND_ACTOR = new GameState("Naninovel.ActorManager`4+GameState[[Naninovel.IBackgroundActor, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.BackgroundState, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.BackgroundMetadata, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.BackgroundsConfiguration, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null]]", "Elringus.Naninovel.Runtime", null);
	protected static final GameState SCRIPT_PLAYER = new GameState("Naninovel.ScriptPlayer+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState TEXT_PRINTER_ACTOR = new GameState("Naninovel.ActorManager`4+GameState[[Naninovel.ITextPrinterActor, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.TextPrinterState, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.TextPrinterMetadata, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.TextPrintersConfiguration, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null]]", "Elringus.Naninovel.Runtime", null);
	protected static final GameState TEXT_PRINTER_MANAGER = new GameState("Naninovel.TextPrinterManager+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState CHARACTER_ACTOR = new GameState("Naninovel.ActorManager`4+GameState[[Naninovel.ICharacterActor, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.CharacterState, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.CharacterMetadata, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.CharactersConfiguration, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null]]", "Elringus.Naninovel.Runtime", null);
	protected static final GameState CHARACTER_MANAGER = new GameState("Naninovel.CharacterManager+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState CHOICE_HANDLER_ACTOR = new GameState("Naninovel.ActorManager`4+GameState[[Naninovel.IChoiceHandlerActor, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.ChoiceHandlerState, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.ChoiceHandlerMetadata, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null],[Naninovel.ChoiceHandlersConfiguration, Elringus.Naninovel.Runtime, Version=0.0.0.0, Culture=neutral, PublicKeyToken=null]]", "Elringus.Naninovel.Runtime", null);
	protected static final GameState AUDIO_MANAGER = new GameState("Naninovel.AudioManager+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState SPAWN_MANAGER = new GameState("Naninovel.SpawnManager+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState VARIABLE_INPUT_PANEL = new GameState("Naninovel.UI.VariableInputPanel+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState BACKLOG_PANEL = new GameState("Naninovel.UI.BacklogPanel+GameState", "Elringus.Naninovel.Runtime", null);
	protected static final GameState INVENTORY_UI = new GameState("NaninovelInventory.InventoryUI+GameState", "Elringus.NaninovelInventory.Runtime", null);
	protected static final GameState CHOICE_HANDLER_PANEL = new GameState("Naninovel.UI.ChoiceHandlerPanel+GameState", "Elringus.Naninovel.Runtime", "InstanceID=ButtonList");
	// @formatter:on
	private final ObjectMapper om;
	private final GameStateMap gameStateMap;
	private final File source;

	// Convert each object only once...
	private final Map<GameState, Object> cache = new HashMap<>();

	public SaveGame(File source) throws IOException {
		this.om = new ObjectMapper();

		SimpleModule module = new SimpleModule();
		module.addDeserializer(SerializableMap.class, new SerializableMapDeserializer());
		module.addSerializer(SerializableMap.class, new SerializableMapSerializer());

		this.om.registerModule(module);

		this.gameStateMap = om.readValue(decompress(source, 3), GameStateMap.class);

		this.source = source;
	}

	public String getName() {
		return source.getName();
	}

	public byte[] getThumbnail() {
		return Base64.getDecoder().decode(gameStateMap.getThumbnailBase64());
	}

	public Date getSaveDateTime() {
		return gameStateMap.getSaveDateTime();
	}

	public void setSaveDateTime(Date saveDateTime) {
		gameStateMap.setSaveDateTime(saveDateTime);
	}

	public ActorManager getBackgroundActor() {
		return find(BACKGROUND_ACTOR, ActorManager.class);
	}

	public ActorManager getTextPrinterActor() {
		return find(TEXT_PRINTER_ACTOR, ActorManager.class);
	}

	public ActorManager getCharacterActor() {
		return find(CHARACTER_ACTOR, ActorManager.class);
	}

	public ActorManager getChoiceHandlerActor() {
		return find(BACKGROUND_ACTOR, ActorManager.class);
	}

	public CustomVariableManager getCustomVariableManager() {
		return find(CUSTOM_VARIABLE_MANAGER, CustomVariableManager.class);
	}

	public InventoryUI getInventoryUI() {
		return find(INVENTORY_UI, InventoryUI.class);
	}

	public InputManager getInputManager() {
		return find(INPUT_MANAGER, InputManager.class);
	}

	public CharacterManager getCharacterManager() {
		return find(CHARACTER_MANAGER, CharacterManager.class);
	}

	public TextPrinterManager getTextPrinterManager() {
		return find(TEXT_PRINTER_MANAGER, TextPrinterManager.class);
	}

	public ScriptPlayer getScriptPlayer() {
		return find(SCRIPT_PLAYER, ScriptPlayer.class);
	}

	public BacklogPanel getBacklogPanel() {
		return find(BACKLOG_PANEL, BacklogPanel.class);
	}

	public VariableInputPanel getVariableInputPanel() {
		return find(VARIABLE_INPUT_PANEL, VariableInputPanel.class);
	}

	public SpawnManager getSpawnManager() {
		return find(SPAWN_MANAGER, SpawnManager.class);
	}

	public ChoiceHandlerPanel getChoiceHandlerPanel() {
		return find(CHOICE_HANDLER_PANEL, ChoiceHandlerPanel.class);
	}

	public CameraManager getCameraManager() {
		return find(CAMERA_MANAGER, CameraManager.class);
	}

	public AudioManager getAudioManager() {
		return find(AUDIO_MANAGER, AudioManager.class);
	}

	protected <T> T find(GameState gameState, Class<T> clazz) {
		Object value = cache.get(gameState);

		if (value != null) {
			return clazz.cast(value);
		}

		List<String> keys = gameStateMap.getObjectJsonMap().getKeys();
		List<Object> values = gameStateMap.getObjectJsonMap().getValues();

		for (int i = 0; i < keys.size(); ++i) {
			if (gameState.toString().equals(keys.get(i))) {
				T result = readValue((String) values.get(i), clazz);
				cache.put(gameState, result);
				return result;
			}
		}

		throw new NoSuchElementException(gameState.toString());
	}

	private <T> T readValue(String content, Class<T> target) {
		try {
			return om.readValue(content, target);
		} catch (IOException e) {
			throw new IllegalArgumentException(e);
		}
	}

	public static void compress(File file, byte[] source) throws IOException {
		try (ByteArrayInputStream bais = new ByteArrayInputStream(source)) {
			// Deflater(x, true) -> no zlib header
			try (InputStream is = new DeflaterInputStream(bais, new Deflater(Deflater.DEFAULT_COMPRESSION, true))) {
				try (FileOutputStream os = new FileOutputStream(file)) {
					is.transferTo(os);
				}
			}
		}
	}

	public static byte[] decompress(File file, int offset) throws IOException {
		try (FileInputStream fis = new FileInputStream(file)) {
			// Inflater(true) -> no zlib header
			try (InputStream is = new InflaterInputStream(fis, new Inflater(true))) {
				try (ByteArrayOutputStream os = new ByteArrayOutputStream()) {
					is.readNBytes(offset);
					os.write(is.readAllBytes());
					return os.toByteArray();
				}
			}
		}
	}
}
