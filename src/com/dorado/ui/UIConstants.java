package com.dorado.ui;

import java.awt.Color;
import java.awt.Font;

/**
 * Constants for visual configuration.
 */
public final class UIConstants {
	/* Name to show for the application frame. */
	public static final String APP_TITLE = "Dorado";
	/* Path for application icon */
	public static final String ICON_PATH = "resources/icons/app.iconset/icon_32x32@2x.png";
	
	/* Fonts the entire application is to use. */
	public static final Font FONT_NORMAL = new Font("sans-serif", Font.PLAIN, 11);
	public static final Font FONT_SMALL = new Font("sans-serif", Font.PLAIN, 9);
	
	/* Color for the frames that contain the user controls. */
	public static final Color PANEL_COLOR = new Color(0xCCCCCC);
	/* Color for the empty areas where there are no controls. */
	public static final Color EMPTY_COLOR = new Color(0x666666);
	/* Color for text */
	public static final Color TEXT_COLOR = new Color(0x111111);
}