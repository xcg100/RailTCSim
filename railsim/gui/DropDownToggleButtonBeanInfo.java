/*
 * $Revision: 20 $
 * Copyright 2008 js-home.org
 * $Name: not supported by cvs2svn $
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.railsim.gui;

import java.beans.*;

/**
 * @author js
 */
public class DropDownToggleButtonBeanInfo extends SimpleBeanInfo {

    // Bean descriptor//GEN-FIRST:BeanDescriptor
    /*lazy BeanDescriptor*/
    private static BeanDescriptor getBdescriptor(){
        BeanDescriptor beanDescriptor = new BeanDescriptor  ( org.railsim.gui.DropDownToggleButton.class , null ); // NOI18N//GEN-HEADEREND:BeanDescriptor

		// Here you can add code for customizing the BeanDescriptor.

        return beanDescriptor;     }//GEN-LAST:BeanDescriptor
    // Property identifiers//GEN-FIRST:Properties
    private static final int PROPERTY_accessibleContext = 0;
    private static final int PROPERTY_action = 1;
    private static final int PROPERTY_actionCommand = 2;
    private static final int PROPERTY_actionListeners = 3;
    private static final int PROPERTY_actionMap = 4;
    private static final int PROPERTY_alignmentX = 5;
    private static final int PROPERTY_alignmentY = 6;
    private static final int PROPERTY_ancestorListeners = 7;
    private static final int PROPERTY_autoscrolls = 8;
    private static final int PROPERTY_background = 9;
    private static final int PROPERTY_backgroundSet = 10;
    private static final int PROPERTY_baselineResizeBehavior = 11;
    private static final int PROPERTY_border = 12;
    private static final int PROPERTY_borderPainted = 13;
    private static final int PROPERTY_bounds = 14;
    private static final int PROPERTY_changeListeners = 15;
    private static final int PROPERTY_colorModel = 16;
    private static final int PROPERTY_component = 17;
    private static final int PROPERTY_componentCount = 18;
    private static final int PROPERTY_componentListeners = 19;
    private static final int PROPERTY_componentOrientation = 20;
    private static final int PROPERTY_componentPopupMenu = 21;
    private static final int PROPERTY_components = 22;
    private static final int PROPERTY_containerListeners = 23;
    private static final int PROPERTY_contentAreaFilled = 24;
    private static final int PROPERTY_cursor = 25;
    private static final int PROPERTY_cursorSet = 26;
    private static final int PROPERTY_debugGraphicsOptions = 27;
    private static final int PROPERTY_disabledIcon = 28;
    private static final int PROPERTY_disabledSelectedIcon = 29;
    private static final int PROPERTY_displayable = 30;
    private static final int PROPERTY_displayedMnemonicIndex = 31;
    private static final int PROPERTY_doubleBuffered = 32;
    private static final int PROPERTY_dropTarget = 33;
    private static final int PROPERTY_enabled = 34;
    private static final int PROPERTY_focusable = 35;
    private static final int PROPERTY_focusCycleRoot = 36;
    private static final int PROPERTY_focusCycleRootAncestor = 37;
    private static final int PROPERTY_focusListeners = 38;
    private static final int PROPERTY_focusOwner = 39;
    private static final int PROPERTY_focusPainted = 40;
    private static final int PROPERTY_focusTraversable = 41;
    private static final int PROPERTY_focusTraversalKeys = 42;
    private static final int PROPERTY_focusTraversalKeysEnabled = 43;
    private static final int PROPERTY_focusTraversalPolicy = 44;
    private static final int PROPERTY_focusTraversalPolicyProvider = 45;
    private static final int PROPERTY_focusTraversalPolicySet = 46;
    private static final int PROPERTY_font = 47;
    private static final int PROPERTY_fontSet = 48;
    private static final int PROPERTY_foreground = 49;
    private static final int PROPERTY_foregroundSet = 50;
    private static final int PROPERTY_graphics = 51;
    private static final int PROPERTY_graphicsConfiguration = 52;
    private static final int PROPERTY_height = 53;
    private static final int PROPERTY_hideActionText = 54;
    private static final int PROPERTY_hierarchyBoundsListeners = 55;
    private static final int PROPERTY_hierarchyListeners = 56;
    private static final int PROPERTY_horizontalAlignment = 57;
    private static final int PROPERTY_horizontalTextPosition = 58;
    private static final int PROPERTY_icon = 59;
    private static final int PROPERTY_iconTextGap = 60;
    private static final int PROPERTY_ignoreRepaint = 61;
    private static final int PROPERTY_inheritsPopupMenu = 62;
    private static final int PROPERTY_inputContext = 63;
    private static final int PROPERTY_inputMap = 64;
    private static final int PROPERTY_inputMethodListeners = 65;
    private static final int PROPERTY_inputMethodRequests = 66;
    private static final int PROPERTY_inputVerifier = 67;
    private static final int PROPERTY_insets = 68;
    private static final int PROPERTY_itemListeners = 69;
    private static final int PROPERTY_items = 70;
    private static final int PROPERTY_keyListeners = 71;
    private static final int PROPERTY_label = 72;
    private static final int PROPERTY_layout = 73;
    private static final int PROPERTY_lightweight = 74;
    private static final int PROPERTY_locale = 75;
    private static final int PROPERTY_location = 76;
    private static final int PROPERTY_locationOnScreen = 77;
    private static final int PROPERTY_managingFocus = 78;
    private static final int PROPERTY_margin = 79;
    private static final int PROPERTY_maximumSize = 80;
    private static final int PROPERTY_maximumSizeSet = 81;
    private static final int PROPERTY_minimumSize = 82;
    private static final int PROPERTY_minimumSizeSet = 83;
    private static final int PROPERTY_mnemonic = 84;
    private static final int PROPERTY_model = 85;
    private static final int PROPERTY_mouseListeners = 86;
    private static final int PROPERTY_mouseMotionListeners = 87;
    private static final int PROPERTY_mousePosition = 88;
    private static final int PROPERTY_mouseWheelListeners = 89;
    private static final int PROPERTY_multiClickThreshhold = 90;
    private static final int PROPERTY_name = 91;
    private static final int PROPERTY_nextFocusableComponent = 92;
    private static final int PROPERTY_opaque = 93;
    private static final int PROPERTY_optimizedDrawingEnabled = 94;
    private static final int PROPERTY_paintingForPrint = 95;
    private static final int PROPERTY_paintingTile = 96;
    private static final int PROPERTY_parent = 97;
    private static final int PROPERTY_peer = 98;
    private static final int PROPERTY_preferredSize = 99;
    private static final int PROPERTY_preferredSizeSet = 100;
    private static final int PROPERTY_pressedIcon = 101;
    private static final int PROPERTY_propertyChangeListeners = 102;
    private static final int PROPERTY_registeredKeyStrokes = 103;
    private static final int PROPERTY_requestFocusEnabled = 104;
    private static final int PROPERTY_rolloverEnabled = 105;
    private static final int PROPERTY_rolloverIcon = 106;
    private static final int PROPERTY_rolloverSelectedIcon = 107;
    private static final int PROPERTY_rootPane = 108;
    private static final int PROPERTY_selected = 109;
    private static final int PROPERTY_selectedIcon = 110;
    private static final int PROPERTY_selectedObjects = 111;
    private static final int PROPERTY_showing = 112;
    private static final int PROPERTY_size = 113;
    private static final int PROPERTY_text = 114;
    private static final int PROPERTY_toolkit = 115;
    private static final int PROPERTY_toolTipText = 116;
    private static final int PROPERTY_topLevelAncestor = 117;
    private static final int PROPERTY_transferHandler = 118;
    private static final int PROPERTY_treeLock = 119;
    private static final int PROPERTY_UI = 120;
    private static final int PROPERTY_UIClassID = 121;
    private static final int PROPERTY_valid = 122;
    private static final int PROPERTY_validateRoot = 123;
    private static final int PROPERTY_verifyInputWhenFocusTarget = 124;
    private static final int PROPERTY_verticalAlignment = 125;
    private static final int PROPERTY_verticalTextPosition = 126;
    private static final int PROPERTY_vetoableChangeListeners = 127;
    private static final int PROPERTY_visible = 128;
    private static final int PROPERTY_visibleRect = 129;
    private static final int PROPERTY_width = 130;
    private static final int PROPERTY_x = 131;
    private static final int PROPERTY_y = 132;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[133];
    
        try {
            properties[PROPERTY_accessibleContext] = new PropertyDescriptor ( "accessibleContext", org.railsim.gui.DropDownToggleButton.class, "getAccessibleContext", null ); // NOI18N
            properties[PROPERTY_action] = new PropertyDescriptor ( "action", org.railsim.gui.DropDownToggleButton.class, "getAction", "setAction" ); // NOI18N
            properties[PROPERTY_actionCommand] = new PropertyDescriptor ( "actionCommand", org.railsim.gui.DropDownToggleButton.class, "getActionCommand", "setActionCommand" ); // NOI18N
            properties[PROPERTY_actionListeners] = new PropertyDescriptor ( "actionListeners", org.railsim.gui.DropDownToggleButton.class, "getActionListeners", null ); // NOI18N
            properties[PROPERTY_actionMap] = new PropertyDescriptor ( "actionMap", org.railsim.gui.DropDownToggleButton.class, "getActionMap", "setActionMap" ); // NOI18N
            properties[PROPERTY_alignmentX] = new PropertyDescriptor ( "alignmentX", org.railsim.gui.DropDownToggleButton.class, "getAlignmentX", "setAlignmentX" ); // NOI18N
            properties[PROPERTY_alignmentY] = new PropertyDescriptor ( "alignmentY", org.railsim.gui.DropDownToggleButton.class, "getAlignmentY", "setAlignmentY" ); // NOI18N
            properties[PROPERTY_ancestorListeners] = new PropertyDescriptor ( "ancestorListeners", org.railsim.gui.DropDownToggleButton.class, "getAncestorListeners", null ); // NOI18N
            properties[PROPERTY_autoscrolls] = new PropertyDescriptor ( "autoscrolls", org.railsim.gui.DropDownToggleButton.class, "getAutoscrolls", "setAutoscrolls" ); // NOI18N
            properties[PROPERTY_background] = new PropertyDescriptor ( "background", org.railsim.gui.DropDownToggleButton.class, "getBackground", "setBackground" ); // NOI18N
            properties[PROPERTY_backgroundSet] = new PropertyDescriptor ( "backgroundSet", org.railsim.gui.DropDownToggleButton.class, "isBackgroundSet", null ); // NOI18N
            properties[PROPERTY_baselineResizeBehavior] = new PropertyDescriptor ( "baselineResizeBehavior", org.railsim.gui.DropDownToggleButton.class, "getBaselineResizeBehavior", null ); // NOI18N
            properties[PROPERTY_border] = new PropertyDescriptor ( "border", org.railsim.gui.DropDownToggleButton.class, "getBorder", "setBorder" ); // NOI18N
            properties[PROPERTY_borderPainted] = new PropertyDescriptor ( "borderPainted", org.railsim.gui.DropDownToggleButton.class, "isBorderPainted", "setBorderPainted" ); // NOI18N
            properties[PROPERTY_bounds] = new PropertyDescriptor ( "bounds", org.railsim.gui.DropDownToggleButton.class, "getBounds", "setBounds" ); // NOI18N
            properties[PROPERTY_changeListeners] = new PropertyDescriptor ( "changeListeners", org.railsim.gui.DropDownToggleButton.class, "getChangeListeners", null ); // NOI18N
            properties[PROPERTY_colorModel] = new PropertyDescriptor ( "colorModel", org.railsim.gui.DropDownToggleButton.class, "getColorModel", null ); // NOI18N
            properties[PROPERTY_component] = new IndexedPropertyDescriptor ( "component", org.railsim.gui.DropDownToggleButton.class, null, null, "getComponent", null ); // NOI18N
            properties[PROPERTY_componentCount] = new PropertyDescriptor ( "componentCount", org.railsim.gui.DropDownToggleButton.class, "getComponentCount", null ); // NOI18N
            properties[PROPERTY_componentListeners] = new PropertyDescriptor ( "componentListeners", org.railsim.gui.DropDownToggleButton.class, "getComponentListeners", null ); // NOI18N
            properties[PROPERTY_componentOrientation] = new PropertyDescriptor ( "componentOrientation", org.railsim.gui.DropDownToggleButton.class, "getComponentOrientation", "setComponentOrientation" ); // NOI18N
            properties[PROPERTY_componentPopupMenu] = new PropertyDescriptor ( "componentPopupMenu", org.railsim.gui.DropDownToggleButton.class, "getComponentPopupMenu", "setComponentPopupMenu" ); // NOI18N
            properties[PROPERTY_components] = new PropertyDescriptor ( "components", org.railsim.gui.DropDownToggleButton.class, "getComponents", null ); // NOI18N
            properties[PROPERTY_containerListeners] = new PropertyDescriptor ( "containerListeners", org.railsim.gui.DropDownToggleButton.class, "getContainerListeners", null ); // NOI18N
            properties[PROPERTY_contentAreaFilled] = new PropertyDescriptor ( "contentAreaFilled", org.railsim.gui.DropDownToggleButton.class, "isContentAreaFilled", "setContentAreaFilled" ); // NOI18N
            properties[PROPERTY_cursor] = new PropertyDescriptor ( "cursor", org.railsim.gui.DropDownToggleButton.class, "getCursor", "setCursor" ); // NOI18N
            properties[PROPERTY_cursorSet] = new PropertyDescriptor ( "cursorSet", org.railsim.gui.DropDownToggleButton.class, "isCursorSet", null ); // NOI18N
            properties[PROPERTY_debugGraphicsOptions] = new PropertyDescriptor ( "debugGraphicsOptions", org.railsim.gui.DropDownToggleButton.class, "getDebugGraphicsOptions", "setDebugGraphicsOptions" ); // NOI18N
            properties[PROPERTY_disabledIcon] = new PropertyDescriptor ( "disabledIcon", org.railsim.gui.DropDownToggleButton.class, "getDisabledIcon", "setDisabledIcon" ); // NOI18N
            properties[PROPERTY_disabledSelectedIcon] = new PropertyDescriptor ( "disabledSelectedIcon", org.railsim.gui.DropDownToggleButton.class, "getDisabledSelectedIcon", "setDisabledSelectedIcon" ); // NOI18N
            properties[PROPERTY_displayable] = new PropertyDescriptor ( "displayable", org.railsim.gui.DropDownToggleButton.class, "isDisplayable", null ); // NOI18N
            properties[PROPERTY_displayedMnemonicIndex] = new PropertyDescriptor ( "displayedMnemonicIndex", org.railsim.gui.DropDownToggleButton.class, "getDisplayedMnemonicIndex", "setDisplayedMnemonicIndex" ); // NOI18N
            properties[PROPERTY_doubleBuffered] = new PropertyDescriptor ( "doubleBuffered", org.railsim.gui.DropDownToggleButton.class, "isDoubleBuffered", "setDoubleBuffered" ); // NOI18N
            properties[PROPERTY_dropTarget] = new PropertyDescriptor ( "dropTarget", org.railsim.gui.DropDownToggleButton.class, "getDropTarget", "setDropTarget" ); // NOI18N
            properties[PROPERTY_enabled] = new PropertyDescriptor ( "enabled", org.railsim.gui.DropDownToggleButton.class, "isEnabled", "setEnabled" ); // NOI18N
            properties[PROPERTY_focusable] = new PropertyDescriptor ( "focusable", org.railsim.gui.DropDownToggleButton.class, "isFocusable", "setFocusable" ); // NOI18N
            properties[PROPERTY_focusCycleRoot] = new PropertyDescriptor ( "focusCycleRoot", org.railsim.gui.DropDownToggleButton.class, "isFocusCycleRoot", "setFocusCycleRoot" ); // NOI18N
            properties[PROPERTY_focusCycleRootAncestor] = new PropertyDescriptor ( "focusCycleRootAncestor", org.railsim.gui.DropDownToggleButton.class, "getFocusCycleRootAncestor", null ); // NOI18N
            properties[PROPERTY_focusListeners] = new PropertyDescriptor ( "focusListeners", org.railsim.gui.DropDownToggleButton.class, "getFocusListeners", null ); // NOI18N
            properties[PROPERTY_focusOwner] = new PropertyDescriptor ( "focusOwner", org.railsim.gui.DropDownToggleButton.class, "isFocusOwner", null ); // NOI18N
            properties[PROPERTY_focusPainted] = new PropertyDescriptor ( "focusPainted", org.railsim.gui.DropDownToggleButton.class, "isFocusPainted", "setFocusPainted" ); // NOI18N
            properties[PROPERTY_focusTraversable] = new PropertyDescriptor ( "focusTraversable", org.railsim.gui.DropDownToggleButton.class, "isFocusTraversable", null ); // NOI18N
            properties[PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor ( "focusTraversalKeys", org.railsim.gui.DropDownToggleButton.class, null, null, "getFocusTraversalKeys", "setFocusTraversalKeys" ); // NOI18N
            properties[PROPERTY_focusTraversalKeysEnabled] = new PropertyDescriptor ( "focusTraversalKeysEnabled", org.railsim.gui.DropDownToggleButton.class, "getFocusTraversalKeysEnabled", "setFocusTraversalKeysEnabled" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicy] = new PropertyDescriptor ( "focusTraversalPolicy", org.railsim.gui.DropDownToggleButton.class, "getFocusTraversalPolicy", "setFocusTraversalPolicy" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicyProvider] = new PropertyDescriptor ( "focusTraversalPolicyProvider", org.railsim.gui.DropDownToggleButton.class, "isFocusTraversalPolicyProvider", "setFocusTraversalPolicyProvider" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicySet] = new PropertyDescriptor ( "focusTraversalPolicySet", org.railsim.gui.DropDownToggleButton.class, "isFocusTraversalPolicySet", null ); // NOI18N
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", org.railsim.gui.DropDownToggleButton.class, "getFont", "setFont" ); // NOI18N
            properties[PROPERTY_fontSet] = new PropertyDescriptor ( "fontSet", org.railsim.gui.DropDownToggleButton.class, "isFontSet", null ); // NOI18N
            properties[PROPERTY_foreground] = new PropertyDescriptor ( "foreground", org.railsim.gui.DropDownToggleButton.class, "getForeground", "setForeground" ); // NOI18N
            properties[PROPERTY_foregroundSet] = new PropertyDescriptor ( "foregroundSet", org.railsim.gui.DropDownToggleButton.class, "isForegroundSet", null ); // NOI18N
            properties[PROPERTY_graphics] = new PropertyDescriptor ( "graphics", org.railsim.gui.DropDownToggleButton.class, "getGraphics", null ); // NOI18N
            properties[PROPERTY_graphicsConfiguration] = new PropertyDescriptor ( "graphicsConfiguration", org.railsim.gui.DropDownToggleButton.class, "getGraphicsConfiguration", null ); // NOI18N
            properties[PROPERTY_height] = new PropertyDescriptor ( "height", org.railsim.gui.DropDownToggleButton.class, "getHeight", null ); // NOI18N
            properties[PROPERTY_hideActionText] = new PropertyDescriptor ( "hideActionText", org.railsim.gui.DropDownToggleButton.class, "getHideActionText", "setHideActionText" ); // NOI18N
            properties[PROPERTY_hierarchyBoundsListeners] = new PropertyDescriptor ( "hierarchyBoundsListeners", org.railsim.gui.DropDownToggleButton.class, "getHierarchyBoundsListeners", null ); // NOI18N
            properties[PROPERTY_hierarchyListeners] = new PropertyDescriptor ( "hierarchyListeners", org.railsim.gui.DropDownToggleButton.class, "getHierarchyListeners", null ); // NOI18N
            properties[PROPERTY_horizontalAlignment] = new PropertyDescriptor ( "horizontalAlignment", org.railsim.gui.DropDownToggleButton.class, "getHorizontalAlignment", "setHorizontalAlignment" ); // NOI18N
            properties[PROPERTY_horizontalTextPosition] = new PropertyDescriptor ( "horizontalTextPosition", org.railsim.gui.DropDownToggleButton.class, "getHorizontalTextPosition", "setHorizontalTextPosition" ); // NOI18N
            properties[PROPERTY_icon] = new PropertyDescriptor ( "icon", org.railsim.gui.DropDownToggleButton.class, "getIcon", "setIcon" ); // NOI18N
            properties[PROPERTY_iconTextGap] = new PropertyDescriptor ( "iconTextGap", org.railsim.gui.DropDownToggleButton.class, "getIconTextGap", "setIconTextGap" ); // NOI18N
            properties[PROPERTY_ignoreRepaint] = new PropertyDescriptor ( "ignoreRepaint", org.railsim.gui.DropDownToggleButton.class, "getIgnoreRepaint", "setIgnoreRepaint" ); // NOI18N
            properties[PROPERTY_inheritsPopupMenu] = new PropertyDescriptor ( "inheritsPopupMenu", org.railsim.gui.DropDownToggleButton.class, "getInheritsPopupMenu", "setInheritsPopupMenu" ); // NOI18N
            properties[PROPERTY_inputContext] = new PropertyDescriptor ( "inputContext", org.railsim.gui.DropDownToggleButton.class, "getInputContext", null ); // NOI18N
            properties[PROPERTY_inputMap] = new PropertyDescriptor ( "inputMap", org.railsim.gui.DropDownToggleButton.class, "getInputMap", null ); // NOI18N
            properties[PROPERTY_inputMethodListeners] = new PropertyDescriptor ( "inputMethodListeners", org.railsim.gui.DropDownToggleButton.class, "getInputMethodListeners", null ); // NOI18N
            properties[PROPERTY_inputMethodRequests] = new PropertyDescriptor ( "inputMethodRequests", org.railsim.gui.DropDownToggleButton.class, "getInputMethodRequests", null ); // NOI18N
            properties[PROPERTY_inputVerifier] = new PropertyDescriptor ( "inputVerifier", org.railsim.gui.DropDownToggleButton.class, "getInputVerifier", "setInputVerifier" ); // NOI18N
            properties[PROPERTY_insets] = new PropertyDescriptor ( "insets", org.railsim.gui.DropDownToggleButton.class, "getInsets", null ); // NOI18N
            properties[PROPERTY_itemListeners] = new PropertyDescriptor ( "itemListeners", org.railsim.gui.DropDownToggleButton.class, "getItemListeners", null ); // NOI18N
            properties[PROPERTY_items] = new PropertyDescriptor ( "items", org.railsim.gui.DropDownToggleButton.class, null, "setItems" ); // NOI18N
            properties[PROPERTY_items].setPreferred ( true );
            properties[PROPERTY_items].setDisplayName ( "Items" );
            properties[PROPERTY_items].setShortDescription ( "Text:ActionCommand" );
            properties[PROPERTY_keyListeners] = new PropertyDescriptor ( "keyListeners", org.railsim.gui.DropDownToggleButton.class, "getKeyListeners", null ); // NOI18N
            properties[PROPERTY_label] = new PropertyDescriptor ( "label", org.railsim.gui.DropDownToggleButton.class, "getLabel", "setLabel" ); // NOI18N
            properties[PROPERTY_layout] = new PropertyDescriptor ( "layout", org.railsim.gui.DropDownToggleButton.class, "getLayout", "setLayout" ); // NOI18N
            properties[PROPERTY_lightweight] = new PropertyDescriptor ( "lightweight", org.railsim.gui.DropDownToggleButton.class, "isLightweight", null ); // NOI18N
            properties[PROPERTY_locale] = new PropertyDescriptor ( "locale", org.railsim.gui.DropDownToggleButton.class, "getLocale", "setLocale" ); // NOI18N
            properties[PROPERTY_location] = new PropertyDescriptor ( "location", org.railsim.gui.DropDownToggleButton.class, "getLocation", "setLocation" ); // NOI18N
            properties[PROPERTY_locationOnScreen] = new PropertyDescriptor ( "locationOnScreen", org.railsim.gui.DropDownToggleButton.class, "getLocationOnScreen", null ); // NOI18N
            properties[PROPERTY_managingFocus] = new PropertyDescriptor ( "managingFocus", org.railsim.gui.DropDownToggleButton.class, "isManagingFocus", null ); // NOI18N
            properties[PROPERTY_margin] = new PropertyDescriptor ( "margin", org.railsim.gui.DropDownToggleButton.class, "getMargin", "setMargin" ); // NOI18N
            properties[PROPERTY_maximumSize] = new PropertyDescriptor ( "maximumSize", org.railsim.gui.DropDownToggleButton.class, "getMaximumSize", "setMaximumSize" ); // NOI18N
            properties[PROPERTY_maximumSizeSet] = new PropertyDescriptor ( "maximumSizeSet", org.railsim.gui.DropDownToggleButton.class, "isMaximumSizeSet", null ); // NOI18N
            properties[PROPERTY_minimumSize] = new PropertyDescriptor ( "minimumSize", org.railsim.gui.DropDownToggleButton.class, "getMinimumSize", "setMinimumSize" ); // NOI18N
            properties[PROPERTY_minimumSizeSet] = new PropertyDescriptor ( "minimumSizeSet", org.railsim.gui.DropDownToggleButton.class, "isMinimumSizeSet", null ); // NOI18N
            properties[PROPERTY_mnemonic] = new PropertyDescriptor ( "mnemonic", org.railsim.gui.DropDownToggleButton.class, null, "setMnemonic" ); // NOI18N
            properties[PROPERTY_model] = new PropertyDescriptor ( "model", org.railsim.gui.DropDownToggleButton.class, "getModel", "setModel" ); // NOI18N
            properties[PROPERTY_mouseListeners] = new PropertyDescriptor ( "mouseListeners", org.railsim.gui.DropDownToggleButton.class, "getMouseListeners", null ); // NOI18N
            properties[PROPERTY_mouseMotionListeners] = new PropertyDescriptor ( "mouseMotionListeners", org.railsim.gui.DropDownToggleButton.class, "getMouseMotionListeners", null ); // NOI18N
            properties[PROPERTY_mousePosition] = new PropertyDescriptor ( "mousePosition", org.railsim.gui.DropDownToggleButton.class, "getMousePosition", null ); // NOI18N
            properties[PROPERTY_mouseWheelListeners] = new PropertyDescriptor ( "mouseWheelListeners", org.railsim.gui.DropDownToggleButton.class, "getMouseWheelListeners", null ); // NOI18N
            properties[PROPERTY_multiClickThreshhold] = new PropertyDescriptor ( "multiClickThreshhold", org.railsim.gui.DropDownToggleButton.class, "getMultiClickThreshhold", "setMultiClickThreshhold" ); // NOI18N
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", org.railsim.gui.DropDownToggleButton.class, "getName", "setName" ); // NOI18N
            properties[PROPERTY_nextFocusableComponent] = new PropertyDescriptor ( "nextFocusableComponent", org.railsim.gui.DropDownToggleButton.class, "getNextFocusableComponent", "setNextFocusableComponent" ); // NOI18N
            properties[PROPERTY_opaque] = new PropertyDescriptor ( "opaque", org.railsim.gui.DropDownToggleButton.class, "isOpaque", "setOpaque" ); // NOI18N
            properties[PROPERTY_optimizedDrawingEnabled] = new PropertyDescriptor ( "optimizedDrawingEnabled", org.railsim.gui.DropDownToggleButton.class, "isOptimizedDrawingEnabled", null ); // NOI18N
            properties[PROPERTY_paintingForPrint] = new PropertyDescriptor ( "paintingForPrint", org.railsim.gui.DropDownToggleButton.class, "isPaintingForPrint", null ); // NOI18N
            properties[PROPERTY_paintingTile] = new PropertyDescriptor ( "paintingTile", org.railsim.gui.DropDownToggleButton.class, "isPaintingTile", null ); // NOI18N
            properties[PROPERTY_parent] = new PropertyDescriptor ( "parent", org.railsim.gui.DropDownToggleButton.class, "getParent", null ); // NOI18N
            properties[PROPERTY_peer] = new PropertyDescriptor ( "peer", org.railsim.gui.DropDownToggleButton.class, "getPeer", null ); // NOI18N
            properties[PROPERTY_preferredSize] = new PropertyDescriptor ( "preferredSize", org.railsim.gui.DropDownToggleButton.class, "getPreferredSize", "setPreferredSize" ); // NOI18N
            properties[PROPERTY_preferredSizeSet] = new PropertyDescriptor ( "preferredSizeSet", org.railsim.gui.DropDownToggleButton.class, "isPreferredSizeSet", null ); // NOI18N
            properties[PROPERTY_pressedIcon] = new PropertyDescriptor ( "pressedIcon", org.railsim.gui.DropDownToggleButton.class, "getPressedIcon", "setPressedIcon" ); // NOI18N
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor ( "propertyChangeListeners", org.railsim.gui.DropDownToggleButton.class, "getPropertyChangeListeners", null ); // NOI18N
            properties[PROPERTY_registeredKeyStrokes] = new PropertyDescriptor ( "registeredKeyStrokes", org.railsim.gui.DropDownToggleButton.class, "getRegisteredKeyStrokes", null ); // NOI18N
            properties[PROPERTY_requestFocusEnabled] = new PropertyDescriptor ( "requestFocusEnabled", org.railsim.gui.DropDownToggleButton.class, "isRequestFocusEnabled", "setRequestFocusEnabled" ); // NOI18N
            properties[PROPERTY_rolloverEnabled] = new PropertyDescriptor ( "rolloverEnabled", org.railsim.gui.DropDownToggleButton.class, "isRolloverEnabled", "setRolloverEnabled" ); // NOI18N
            properties[PROPERTY_rolloverIcon] = new PropertyDescriptor ( "rolloverIcon", org.railsim.gui.DropDownToggleButton.class, "getRolloverIcon", "setRolloverIcon" ); // NOI18N
            properties[PROPERTY_rolloverSelectedIcon] = new PropertyDescriptor ( "rolloverSelectedIcon", org.railsim.gui.DropDownToggleButton.class, "getRolloverSelectedIcon", "setRolloverSelectedIcon" ); // NOI18N
            properties[PROPERTY_rootPane] = new PropertyDescriptor ( "rootPane", org.railsim.gui.DropDownToggleButton.class, "getRootPane", null ); // NOI18N
            properties[PROPERTY_selected] = new PropertyDescriptor ( "selected", org.railsim.gui.DropDownToggleButton.class, "isSelected", "setSelected" ); // NOI18N
            properties[PROPERTY_selectedIcon] = new PropertyDescriptor ( "selectedIcon", org.railsim.gui.DropDownToggleButton.class, "getSelectedIcon", "setSelectedIcon" ); // NOI18N
            properties[PROPERTY_selectedObjects] = new PropertyDescriptor ( "selectedObjects", org.railsim.gui.DropDownToggleButton.class, "getSelectedObjects", null ); // NOI18N
            properties[PROPERTY_showing] = new PropertyDescriptor ( "showing", org.railsim.gui.DropDownToggleButton.class, "isShowing", null ); // NOI18N
            properties[PROPERTY_size] = new PropertyDescriptor ( "size", org.railsim.gui.DropDownToggleButton.class, "getSize", "setSize" ); // NOI18N
            properties[PROPERTY_text] = new PropertyDescriptor ( "text", org.railsim.gui.DropDownToggleButton.class, "getText", "setText" ); // NOI18N
            properties[PROPERTY_toolkit] = new PropertyDescriptor ( "toolkit", org.railsim.gui.DropDownToggleButton.class, "getToolkit", null ); // NOI18N
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", org.railsim.gui.DropDownToggleButton.class, "getToolTipText", "setToolTipText" ); // NOI18N
            properties[PROPERTY_topLevelAncestor] = new PropertyDescriptor ( "topLevelAncestor", org.railsim.gui.DropDownToggleButton.class, "getTopLevelAncestor", null ); // NOI18N
            properties[PROPERTY_transferHandler] = new PropertyDescriptor ( "transferHandler", org.railsim.gui.DropDownToggleButton.class, "getTransferHandler", "setTransferHandler" ); // NOI18N
            properties[PROPERTY_treeLock] = new PropertyDescriptor ( "treeLock", org.railsim.gui.DropDownToggleButton.class, "getTreeLock", null ); // NOI18N
            properties[PROPERTY_UI] = new PropertyDescriptor ( "UI", org.railsim.gui.DropDownToggleButton.class, "getUI", "setUI" ); // NOI18N
            properties[PROPERTY_UIClassID] = new PropertyDescriptor ( "UIClassID", org.railsim.gui.DropDownToggleButton.class, "getUIClassID", null ); // NOI18N
            properties[PROPERTY_valid] = new PropertyDescriptor ( "valid", org.railsim.gui.DropDownToggleButton.class, "isValid", null ); // NOI18N
            properties[PROPERTY_validateRoot] = new PropertyDescriptor ( "validateRoot", org.railsim.gui.DropDownToggleButton.class, "isValidateRoot", null ); // NOI18N
            properties[PROPERTY_verifyInputWhenFocusTarget] = new PropertyDescriptor ( "verifyInputWhenFocusTarget", org.railsim.gui.DropDownToggleButton.class, "getVerifyInputWhenFocusTarget", "setVerifyInputWhenFocusTarget" ); // NOI18N
            properties[PROPERTY_verticalAlignment] = new PropertyDescriptor ( "verticalAlignment", org.railsim.gui.DropDownToggleButton.class, "getVerticalAlignment", "setVerticalAlignment" ); // NOI18N
            properties[PROPERTY_verticalTextPosition] = new PropertyDescriptor ( "verticalTextPosition", org.railsim.gui.DropDownToggleButton.class, "getVerticalTextPosition", "setVerticalTextPosition" ); // NOI18N
            properties[PROPERTY_vetoableChangeListeners] = new PropertyDescriptor ( "vetoableChangeListeners", org.railsim.gui.DropDownToggleButton.class, "getVetoableChangeListeners", null ); // NOI18N
            properties[PROPERTY_visible] = new PropertyDescriptor ( "visible", org.railsim.gui.DropDownToggleButton.class, "isVisible", "setVisible" ); // NOI18N
            properties[PROPERTY_visibleRect] = new PropertyDescriptor ( "visibleRect", org.railsim.gui.DropDownToggleButton.class, "getVisibleRect", null ); // NOI18N
            properties[PROPERTY_width] = new PropertyDescriptor ( "width", org.railsim.gui.DropDownToggleButton.class, "getWidth", null ); // NOI18N
            properties[PROPERTY_x] = new PropertyDescriptor ( "x", org.railsim.gui.DropDownToggleButton.class, "getX", null ); // NOI18N
            properties[PROPERTY_y] = new PropertyDescriptor ( "y", org.railsim.gui.DropDownToggleButton.class, "getY", null ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Properties

		// Here you can add code for customizing the properties array.

        return properties;     }//GEN-LAST:Properties
    // EventSet identifiers//GEN-FIRST:Events
    private static final int EVENT_actionListener = 0;
    private static final int EVENT_ancestorListener = 1;
    private static final int EVENT_changeListener = 2;
    private static final int EVENT_componentListener = 3;
    private static final int EVENT_containerListener = 4;
    private static final int EVENT_focusListener = 5;
    private static final int EVENT_hierarchyBoundsListener = 6;
    private static final int EVENT_hierarchyListener = 7;
    private static final int EVENT_inputMethodListener = 8;
    private static final int EVENT_itemListener = 9;
    private static final int EVENT_keyListener = 10;
    private static final int EVENT_mouseListener = 11;
    private static final int EVENT_mouseMotionListener = 12;
    private static final int EVENT_mouseWheelListener = 13;
    private static final int EVENT_propertyChangeListener = 14;
    private static final int EVENT_vetoableChangeListener = 15;

    // EventSet array
    /*lazy EventSetDescriptor*/
    private static EventSetDescriptor[] getEdescriptor(){
        EventSetDescriptor[] eventSets = new EventSetDescriptor[16];
    
        try {
            eventSets[EVENT_actionListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "actionListener", java.awt.event.ActionListener.class, new String[] {"actionPerformed"}, "addActionListener", "removeActionListener" ); // NOI18N
            eventSets[EVENT_ancestorListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "ancestorListener", javax.swing.event.AncestorListener.class, new String[] {"ancestorAdded", "ancestorMoved", "ancestorRemoved"}, "addAncestorListener", "removeAncestorListener" ); // NOI18N
            eventSets[EVENT_changeListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "changeListener", javax.swing.event.ChangeListener.class, new String[] {"stateChanged"}, "addChangeListener", "removeChangeListener" ); // NOI18N
            eventSets[EVENT_componentListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "componentListener", java.awt.event.ComponentListener.class, new String[] {"componentHidden", "componentMoved", "componentResized", "componentShown"}, "addComponentListener", "removeComponentListener" ); // NOI18N
            eventSets[EVENT_containerListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "containerListener", java.awt.event.ContainerListener.class, new String[] {"componentAdded", "componentRemoved"}, "addContainerListener", "removeContainerListener" ); // NOI18N
            eventSets[EVENT_focusListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "focusListener", java.awt.event.FocusListener.class, new String[] {"focusGained", "focusLost"}, "addFocusListener", "removeFocusListener" ); // NOI18N
            eventSets[EVENT_hierarchyBoundsListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "hierarchyBoundsListener", java.awt.event.HierarchyBoundsListener.class, new String[] {"ancestorMoved", "ancestorResized"}, "addHierarchyBoundsListener", "removeHierarchyBoundsListener" ); // NOI18N
            eventSets[EVENT_hierarchyListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "hierarchyListener", java.awt.event.HierarchyListener.class, new String[] {"hierarchyChanged"}, "addHierarchyListener", "removeHierarchyListener" ); // NOI18N
            eventSets[EVENT_inputMethodListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "inputMethodListener", java.awt.event.InputMethodListener.class, new String[] {"caretPositionChanged", "inputMethodTextChanged"}, "addInputMethodListener", "removeInputMethodListener" ); // NOI18N
            eventSets[EVENT_itemListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "itemListener", java.awt.event.ItemListener.class, new String[] {"itemStateChanged"}, "addItemListener", "removeItemListener" ); // NOI18N
            eventSets[EVENT_keyListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "keyListener", java.awt.event.KeyListener.class, new String[] {"keyPressed", "keyReleased", "keyTyped"}, "addKeyListener", "removeKeyListener" ); // NOI18N
            eventSets[EVENT_mouseListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "mouseListener", java.awt.event.MouseListener.class, new String[] {"mouseClicked", "mouseEntered", "mouseExited", "mousePressed", "mouseReleased"}, "addMouseListener", "removeMouseListener" ); // NOI18N
            eventSets[EVENT_mouseMotionListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "mouseMotionListener", java.awt.event.MouseMotionListener.class, new String[] {"mouseDragged", "mouseMoved"}, "addMouseMotionListener", "removeMouseMotionListener" ); // NOI18N
            eventSets[EVENT_mouseWheelListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "mouseWheelListener", java.awt.event.MouseWheelListener.class, new String[] {"mouseWheelMoved"}, "addMouseWheelListener", "removeMouseWheelListener" ); // NOI18N
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" ); // NOI18N
            eventSets[EVENT_vetoableChangeListener] = new EventSetDescriptor ( org.railsim.gui.DropDownToggleButton.class, "vetoableChangeListener", java.beans.VetoableChangeListener.class, new String[] {"vetoableChange"}, "addVetoableChangeListener", "removeVetoableChangeListener" ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Events

		// Here you can add code for customizing the event sets array.

        return eventSets;     }//GEN-LAST:Events
    // Method identifiers//GEN-FIRST:Methods
    private static final int METHOD_action0 = 0;
    private static final int METHOD_add1 = 1;
    private static final int METHOD_addNotify2 = 2;
    private static final int METHOD_addPropertyChangeListener3 = 3;
    private static final int METHOD_applyComponentOrientation4 = 4;
    private static final int METHOD_areFocusTraversalKeysSet5 = 5;
    private static final int METHOD_bounds6 = 6;
    private static final int METHOD_checkImage7 = 7;
    private static final int METHOD_computeVisibleRect8 = 8;
    private static final int METHOD_contains9 = 9;
    private static final int METHOD_countComponents10 = 10;
    private static final int METHOD_createImage11 = 11;
    private static final int METHOD_createToolTip12 = 12;
    private static final int METHOD_createVolatileImage13 = 13;
    private static final int METHOD_deliverEvent14 = 14;
    private static final int METHOD_disable15 = 15;
    private static final int METHOD_dispatchEvent16 = 16;
    private static final int METHOD_doClick17 = 17;
    private static final int METHOD_doLayout18 = 18;
    private static final int METHOD_enable19 = 19;
    private static final int METHOD_enableInputMethods20 = 20;
    private static final int METHOD_findComponentAt21 = 21;
    private static final int METHOD_firePropertyChange22 = 22;
    private static final int METHOD_getActionForKeyStroke23 = 23;
    private static final int METHOD_getBaseline24 = 24;
    private static final int METHOD_getBounds25 = 25;
    private static final int METHOD_getClientProperty26 = 26;
    private static final int METHOD_getComponentAt27 = 27;
    private static final int METHOD_getComponentZOrder28 = 28;
    private static final int METHOD_getConditionForKeyStroke29 = 29;
    private static final int METHOD_getDefaultLocale30 = 30;
    private static final int METHOD_getFontMetrics31 = 31;
    private static final int METHOD_getInsets32 = 32;
    private static final int METHOD_getListeners33 = 33;
    private static final int METHOD_getLocation34 = 34;
    private static final int METHOD_getMnemonic35 = 35;
    private static final int METHOD_getMousePosition36 = 36;
    private static final int METHOD_getPopupLocation37 = 37;
    private static final int METHOD_getPropertyChangeListeners38 = 38;
    private static final int METHOD_getSize39 = 39;
    private static final int METHOD_getToolTipLocation40 = 40;
    private static final int METHOD_getToolTipText41 = 41;
    private static final int METHOD_gotFocus42 = 42;
    private static final int METHOD_grabFocus43 = 43;
    private static final int METHOD_handleEvent44 = 44;
    private static final int METHOD_hasFocus45 = 45;
    private static final int METHOD_hide46 = 46;
    private static final int METHOD_imageUpdate47 = 47;
    private static final int METHOD_insets48 = 48;
    private static final int METHOD_inside49 = 49;
    private static final int METHOD_invalidate50 = 50;
    private static final int METHOD_isAncestorOf51 = 51;
    private static final int METHOD_isFocusCycleRoot52 = 52;
    private static final int METHOD_isLightweightComponent53 = 53;
    private static final int METHOD_keyDown54 = 54;
    private static final int METHOD_keyUp55 = 55;
    private static final int METHOD_layout56 = 56;
    private static final int METHOD_list57 = 57;
    private static final int METHOD_locate58 = 58;
    private static final int METHOD_location59 = 59;
    private static final int METHOD_lostFocus60 = 60;
    private static final int METHOD_minimumSize61 = 61;
    private static final int METHOD_mouseDown62 = 62;
    private static final int METHOD_mouseDrag63 = 63;
    private static final int METHOD_mouseEnter64 = 64;
    private static final int METHOD_mouseExit65 = 65;
    private static final int METHOD_mouseMove66 = 66;
    private static final int METHOD_mouseUp67 = 67;
    private static final int METHOD_move68 = 68;
    private static final int METHOD_nextFocus69 = 69;
    private static final int METHOD_paint70 = 70;
    private static final int METHOD_paintAll71 = 71;
    private static final int METHOD_paintComponents72 = 72;
    private static final int METHOD_paintImmediately73 = 73;
    private static final int METHOD_postEvent74 = 74;
    private static final int METHOD_preferredSize75 = 75;
    private static final int METHOD_prepareImage76 = 76;
    private static final int METHOD_print77 = 77;
    private static final int METHOD_printAll78 = 78;
    private static final int METHOD_printComponents79 = 79;
    private static final int METHOD_putClientProperty80 = 80;
    private static final int METHOD_registerKeyboardAction81 = 81;
    private static final int METHOD_remove82 = 82;
    private static final int METHOD_removeAll83 = 83;
    private static final int METHOD_removeNotify84 = 84;
    private static final int METHOD_removePropertyChangeListener85 = 85;
    private static final int METHOD_repaint86 = 86;
    private static final int METHOD_requestDefaultFocus87 = 87;
    private static final int METHOD_requestFocus88 = 88;
    private static final int METHOD_requestFocusInWindow89 = 89;
    private static final int METHOD_resetKeyboardActions90 = 90;
    private static final int METHOD_reshape91 = 91;
    private static final int METHOD_resize92 = 92;
    private static final int METHOD_revalidate93 = 93;
    private static final int METHOD_scrollRectToVisible94 = 94;
    private static final int METHOD_setBounds95 = 95;
    private static final int METHOD_setComponentZOrder96 = 96;
    private static final int METHOD_setDefaultLocale97 = 97;
    private static final int METHOD_setMnemonic98 = 98;
    private static final int METHOD_show99 = 99;
    private static final int METHOD_size100 = 100;
    private static final int METHOD_toString101 = 101;
    private static final int METHOD_transferFocus102 = 102;
    private static final int METHOD_transferFocusBackward103 = 103;
    private static final int METHOD_transferFocusDownCycle104 = 104;
    private static final int METHOD_transferFocusUpCycle105 = 105;
    private static final int METHOD_unregisterKeyboardAction106 = 106;
    private static final int METHOD_update107 = 107;
    private static final int METHOD_updateUI108 = 108;
    private static final int METHOD_validate109 = 109;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[110];
    
        try {
            methods[METHOD_action0] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("action", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_action0].setDisplayName ( "" );
            methods[METHOD_add1] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("add", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_add1].setDisplayName ( "" );
            methods[METHOD_addNotify2] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("addNotify", new Class[] {})); // NOI18N
            methods[METHOD_addNotify2].setDisplayName ( "" );
            methods[METHOD_addPropertyChangeListener3] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_addPropertyChangeListener3].setDisplayName ( "" );
            methods[METHOD_applyComponentOrientation4] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("applyComponentOrientation", new Class[] {java.awt.ComponentOrientation.class})); // NOI18N
            methods[METHOD_applyComponentOrientation4].setDisplayName ( "" );
            methods[METHOD_areFocusTraversalKeysSet5] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("areFocusTraversalKeysSet", new Class[] {Integer.TYPE})); // NOI18N
            methods[METHOD_areFocusTraversalKeysSet5].setDisplayName ( "" );
            methods[METHOD_bounds6] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("bounds", new Class[] {})); // NOI18N
            methods[METHOD_bounds6].setDisplayName ( "" );
            methods[METHOD_checkImage7] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("checkImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_checkImage7].setDisplayName ( "" );
            methods[METHOD_computeVisibleRect8] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("computeVisibleRect", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_computeVisibleRect8].setDisplayName ( "" );
            methods[METHOD_contains9] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("contains", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_contains9].setDisplayName ( "" );
            methods[METHOD_countComponents10] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("countComponents", new Class[] {})); // NOI18N
            methods[METHOD_countComponents10].setDisplayName ( "" );
            methods[METHOD_createImage11] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("createImage", new Class[] {java.awt.image.ImageProducer.class})); // NOI18N
            methods[METHOD_createImage11].setDisplayName ( "" );
            methods[METHOD_createToolTip12] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("createToolTip", new Class[] {})); // NOI18N
            methods[METHOD_createToolTip12].setDisplayName ( "" );
            methods[METHOD_createVolatileImage13] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("createVolatileImage", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_createVolatileImage13].setDisplayName ( "" );
            methods[METHOD_deliverEvent14] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("deliverEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_deliverEvent14].setDisplayName ( "" );
            methods[METHOD_disable15] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("disable", new Class[] {})); // NOI18N
            methods[METHOD_disable15].setDisplayName ( "" );
            methods[METHOD_dispatchEvent16] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("dispatchEvent", new Class[] {java.awt.AWTEvent.class})); // NOI18N
            methods[METHOD_dispatchEvent16].setDisplayName ( "" );
            methods[METHOD_doClick17] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("doClick", new Class[] {})); // NOI18N
            methods[METHOD_doClick17].setDisplayName ( "" );
            methods[METHOD_doLayout18] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("doLayout", new Class[] {})); // NOI18N
            methods[METHOD_doLayout18].setDisplayName ( "" );
            methods[METHOD_enable19] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("enable", new Class[] {})); // NOI18N
            methods[METHOD_enable19].setDisplayName ( "" );
            methods[METHOD_enableInputMethods20] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("enableInputMethods", new Class[] {Boolean.TYPE})); // NOI18N
            methods[METHOD_enableInputMethods20].setDisplayName ( "" );
            methods[METHOD_findComponentAt21] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("findComponentAt", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_findComponentAt21].setDisplayName ( "" );
            methods[METHOD_firePropertyChange22] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Boolean.TYPE, Boolean.TYPE})); // NOI18N
            methods[METHOD_firePropertyChange22].setDisplayName ( "" );
            methods[METHOD_getActionForKeyStroke23] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getActionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getActionForKeyStroke23].setDisplayName ( "" );
            methods[METHOD_getBaseline24] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getBaseline", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_getBaseline24].setDisplayName ( "" );
            methods[METHOD_getBounds25] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getBounds", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_getBounds25].setDisplayName ( "" );
            methods[METHOD_getClientProperty26] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getClientProperty", new Class[] {java.lang.Object.class})); // NOI18N
            methods[METHOD_getClientProperty26].setDisplayName ( "" );
            methods[METHOD_getComponentAt27] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getComponentAt", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_getComponentAt27].setDisplayName ( "" );
            methods[METHOD_getComponentZOrder28] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getComponentZOrder", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_getComponentZOrder28].setDisplayName ( "" );
            methods[METHOD_getConditionForKeyStroke29] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getConditionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getConditionForKeyStroke29].setDisplayName ( "" );
            methods[METHOD_getDefaultLocale30] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getDefaultLocale", new Class[] {})); // NOI18N
            methods[METHOD_getDefaultLocale30].setDisplayName ( "" );
            methods[METHOD_getFontMetrics31] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getFontMetrics", new Class[] {java.awt.Font.class})); // NOI18N
            methods[METHOD_getFontMetrics31].setDisplayName ( "" );
            methods[METHOD_getInsets32] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getInsets", new Class[] {java.awt.Insets.class})); // NOI18N
            methods[METHOD_getInsets32].setDisplayName ( "" );
            methods[METHOD_getListeners33] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getListeners", new Class[] {java.lang.Class.class})); // NOI18N
            methods[METHOD_getListeners33].setDisplayName ( "" );
            methods[METHOD_getLocation34] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getLocation", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_getLocation34].setDisplayName ( "" );
            methods[METHOD_getMnemonic35] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getMnemonic", new Class[] {})); // NOI18N
            methods[METHOD_getMnemonic35].setDisplayName ( "" );
            methods[METHOD_getMousePosition36] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getMousePosition", new Class[] {Boolean.TYPE})); // NOI18N
            methods[METHOD_getMousePosition36].setDisplayName ( "" );
            methods[METHOD_getPopupLocation37] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getPopupLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getPopupLocation37].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners38] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class})); // NOI18N
            methods[METHOD_getPropertyChangeListeners38].setDisplayName ( "" );
            methods[METHOD_getSize39] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getSize", new Class[] {java.awt.Dimension.class})); // NOI18N
            methods[METHOD_getSize39].setDisplayName ( "" );
            methods[METHOD_getToolTipLocation40] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getToolTipLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipLocation40].setDisplayName ( "" );
            methods[METHOD_getToolTipText41] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("getToolTipText", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipText41].setDisplayName ( "" );
            methods[METHOD_gotFocus42] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("gotFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_gotFocus42].setDisplayName ( "" );
            methods[METHOD_grabFocus43] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("grabFocus", new Class[] {})); // NOI18N
            methods[METHOD_grabFocus43].setDisplayName ( "" );
            methods[METHOD_handleEvent44] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("handleEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_handleEvent44].setDisplayName ( "" );
            methods[METHOD_hasFocus45] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("hasFocus", new Class[] {})); // NOI18N
            methods[METHOD_hasFocus45].setDisplayName ( "" );
            methods[METHOD_hide46] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("hide", new Class[] {})); // NOI18N
            methods[METHOD_hide46].setDisplayName ( "" );
            methods[METHOD_imageUpdate47] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("imageUpdate", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_imageUpdate47].setDisplayName ( "" );
            methods[METHOD_insets48] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("insets", new Class[] {})); // NOI18N
            methods[METHOD_insets48].setDisplayName ( "" );
            methods[METHOD_inside49] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("inside", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_inside49].setDisplayName ( "" );
            methods[METHOD_invalidate50] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("invalidate", new Class[] {})); // NOI18N
            methods[METHOD_invalidate50].setDisplayName ( "" );
            methods[METHOD_isAncestorOf51] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("isAncestorOf", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isAncestorOf51].setDisplayName ( "" );
            methods[METHOD_isFocusCycleRoot52] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("isFocusCycleRoot", new Class[] {java.awt.Container.class})); // NOI18N
            methods[METHOD_isFocusCycleRoot52].setDisplayName ( "" );
            methods[METHOD_isLightweightComponent53] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("isLightweightComponent", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isLightweightComponent53].setDisplayName ( "" );
            methods[METHOD_keyDown54] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("keyDown", new Class[] {java.awt.Event.class, Integer.TYPE})); // NOI18N
            methods[METHOD_keyDown54].setDisplayName ( "" );
            methods[METHOD_keyUp55] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("keyUp", new Class[] {java.awt.Event.class, Integer.TYPE})); // NOI18N
            methods[METHOD_keyUp55].setDisplayName ( "" );
            methods[METHOD_layout56] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("layout", new Class[] {})); // NOI18N
            methods[METHOD_layout56].setDisplayName ( "" );
            methods[METHOD_list57] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("list", new Class[] {java.io.PrintStream.class, Integer.TYPE})); // NOI18N
            methods[METHOD_list57].setDisplayName ( "" );
            methods[METHOD_locate58] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("locate", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_locate58].setDisplayName ( "" );
            methods[METHOD_location59] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("location", new Class[] {})); // NOI18N
            methods[METHOD_location59].setDisplayName ( "" );
            methods[METHOD_lostFocus60] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("lostFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_lostFocus60].setDisplayName ( "" );
            methods[METHOD_minimumSize61] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("minimumSize", new Class[] {})); // NOI18N
            methods[METHOD_minimumSize61].setDisplayName ( "" );
            methods[METHOD_mouseDown62] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("mouseDown", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseDown62].setDisplayName ( "" );
            methods[METHOD_mouseDrag63] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("mouseDrag", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseDrag63].setDisplayName ( "" );
            methods[METHOD_mouseEnter64] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("mouseEnter", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseEnter64].setDisplayName ( "" );
            methods[METHOD_mouseExit65] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("mouseExit", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseExit65].setDisplayName ( "" );
            methods[METHOD_mouseMove66] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("mouseMove", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseMove66].setDisplayName ( "" );
            methods[METHOD_mouseUp67] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("mouseUp", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseUp67].setDisplayName ( "" );
            methods[METHOD_move68] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("move", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_move68].setDisplayName ( "" );
            methods[METHOD_nextFocus69] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("nextFocus", new Class[] {})); // NOI18N
            methods[METHOD_nextFocus69].setDisplayName ( "" );
            methods[METHOD_paint70] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("paint", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paint70].setDisplayName ( "" );
            methods[METHOD_paintAll71] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("paintAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintAll71].setDisplayName ( "" );
            methods[METHOD_paintComponents72] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("paintComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintComponents72].setDisplayName ( "" );
            methods[METHOD_paintImmediately73] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("paintImmediately", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_paintImmediately73].setDisplayName ( "" );
            methods[METHOD_postEvent74] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("postEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_postEvent74].setDisplayName ( "" );
            methods[METHOD_preferredSize75] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("preferredSize", new Class[] {})); // NOI18N
            methods[METHOD_preferredSize75].setDisplayName ( "" );
            methods[METHOD_prepareImage76] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_prepareImage76].setDisplayName ( "" );
            methods[METHOD_print77] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("print", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_print77].setDisplayName ( "" );
            methods[METHOD_printAll78] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("printAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printAll78].setDisplayName ( "" );
            methods[METHOD_printComponents79] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("printComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printComponents79].setDisplayName ( "" );
            methods[METHOD_putClientProperty80] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("putClientProperty", new Class[] {java.lang.Object.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_putClientProperty80].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction81] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, java.lang.String.class, javax.swing.KeyStroke.class, Integer.TYPE})); // NOI18N
            methods[METHOD_registerKeyboardAction81].setDisplayName ( "" );
            methods[METHOD_remove82] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("remove", new Class[] {Integer.TYPE})); // NOI18N
            methods[METHOD_remove82].setDisplayName ( "" );
            methods[METHOD_removeAll83] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("removeAll", new Class[] {})); // NOI18N
            methods[METHOD_removeAll83].setDisplayName ( "" );
            methods[METHOD_removeNotify84] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("removeNotify", new Class[] {})); // NOI18N
            methods[METHOD_removeNotify84].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener85] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_removePropertyChangeListener85].setDisplayName ( "" );
            methods[METHOD_repaint86] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("repaint", new Class[] {Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_repaint86].setDisplayName ( "" );
            methods[METHOD_requestDefaultFocus87] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("requestDefaultFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestDefaultFocus87].setDisplayName ( "" );
            methods[METHOD_requestFocus88] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("requestFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestFocus88].setDisplayName ( "" );
            methods[METHOD_requestFocusInWindow89] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("requestFocusInWindow", new Class[] {})); // NOI18N
            methods[METHOD_requestFocusInWindow89].setDisplayName ( "" );
            methods[METHOD_resetKeyboardActions90] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("resetKeyboardActions", new Class[] {})); // NOI18N
            methods[METHOD_resetKeyboardActions90].setDisplayName ( "" );
            methods[METHOD_reshape91] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("reshape", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_reshape91].setDisplayName ( "" );
            methods[METHOD_resize92] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("resize", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_resize92].setDisplayName ( "" );
            methods[METHOD_revalidate93] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("revalidate", new Class[] {})); // NOI18N
            methods[METHOD_revalidate93].setDisplayName ( "" );
            methods[METHOD_scrollRectToVisible94] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("scrollRectToVisible", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_scrollRectToVisible94].setDisplayName ( "" );
            methods[METHOD_setBounds95] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("setBounds", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_setBounds95].setDisplayName ( "" );
            methods[METHOD_setComponentZOrder96] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("setComponentZOrder", new Class[] {java.awt.Component.class, Integer.TYPE})); // NOI18N
            methods[METHOD_setComponentZOrder96].setDisplayName ( "" );
            methods[METHOD_setDefaultLocale97] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("setDefaultLocale", new Class[] {java.util.Locale.class})); // NOI18N
            methods[METHOD_setDefaultLocale97].setDisplayName ( "" );
            methods[METHOD_setMnemonic98] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("setMnemonic", new Class[] {Integer.TYPE})); // NOI18N
            methods[METHOD_setMnemonic98].setDisplayName ( "" );
            methods[METHOD_show99] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("show", new Class[] {})); // NOI18N
            methods[METHOD_show99].setDisplayName ( "" );
            methods[METHOD_size100] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("size", new Class[] {})); // NOI18N
            methods[METHOD_size100].setDisplayName ( "" );
            methods[METHOD_toString101] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("toString", new Class[] {})); // NOI18N
            methods[METHOD_toString101].setDisplayName ( "" );
            methods[METHOD_transferFocus102] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("transferFocus", new Class[] {})); // NOI18N
            methods[METHOD_transferFocus102].setDisplayName ( "" );
            methods[METHOD_transferFocusBackward103] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("transferFocusBackward", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusBackward103].setDisplayName ( "" );
            methods[METHOD_transferFocusDownCycle104] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("transferFocusDownCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusDownCycle104].setDisplayName ( "" );
            methods[METHOD_transferFocusUpCycle105] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("transferFocusUpCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusUpCycle105].setDisplayName ( "" );
            methods[METHOD_unregisterKeyboardAction106] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("unregisterKeyboardAction", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_unregisterKeyboardAction106].setDisplayName ( "" );
            methods[METHOD_update107] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("update", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_update107].setDisplayName ( "" );
            methods[METHOD_updateUI108] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("updateUI", new Class[] {})); // NOI18N
            methods[METHOD_updateUI108].setDisplayName ( "" );
            methods[METHOD_validate109] = new MethodDescriptor ( org.railsim.gui.DropDownToggleButton.class.getMethod("validate", new Class[] {})); // NOI18N
            methods[METHOD_validate109].setDisplayName ( "" );
        }
        catch( Exception e) {}//GEN-HEADEREND:Methods

		// Here you can add code for customizing the methods array.

        return methods;     }//GEN-LAST:Methods
    private static final int defaultPropertyIndex = -1;//GEN-BEGIN:Idx
    private static final int defaultEventIndex = -1;//GEN-END:Idx

//GEN-FIRST:Superclass
	// Here you can add code for customizing the Superclass BeanInfo.
//GEN-LAST:Superclass
	/**
	 * Gets the bean's
	 * <code>BeanDescriptor</code>s.
	 *
	 * @return BeanDescriptor describing the editable
	 * properties of this bean. May return null if the
	 * information should be obtained by automatic analysis.
	 */
	@Override
	public BeanDescriptor getBeanDescriptor() {
		return getBdescriptor();
	}

	/**
	 * Gets the bean's
	 * <code>PropertyDescriptor</code>s.
	 *
	 * @return An array of PropertyDescriptors describing the editable
	 * properties supported by this bean. May return null if the
	 * information should be obtained by automatic analysis.
	 * <p>
	 * If a property is indexed, then its entry in the result array will
	 * belong to the IndexedPropertyDescriptor subclass of PropertyDescriptor.
	 * A client of getPropertyDescriptors can use "instanceof" to check
	 * if a given PropertyDescriptor is an IndexedPropertyDescriptor.
	 */
	@Override
	public PropertyDescriptor[] getPropertyDescriptors() {
		return getPdescriptor();
	}

	/**
	 * Gets the bean's
	 * <code>EventSetDescriptor</code>s.
	 *
	 * @return An array of EventSetDescriptors describing the kinds of
	 * events fired by this bean. May return null if the information
	 * should be obtained by automatic analysis.
	 */
	@Override
	public EventSetDescriptor[] getEventSetDescriptors() {
		return getEdescriptor();
	}

	/**
	 * Gets the bean's
	 * <code>MethodDescriptor</code>s.
	 *
	 * @return An array of MethodDescriptors describing the methods
	 * implemented by this bean. May return null if the information
	 * should be obtained by automatic analysis.
	 */
	@Override
	public MethodDescriptor[] getMethodDescriptors() {
		return getMdescriptor();
	}

	/**
	 * A bean may have a "default" property that is the property that will
	 * mostly commonly be initially chosen for update by human's who are
	 * customizing the bean.
	 *
	 * @return Index of default property in the PropertyDescriptor array
	 * returned by getPropertyDescriptors.
	 * <P>	Returns -1 if there is no default property.
	 */
	@Override
	public int getDefaultPropertyIndex() {
		return defaultPropertyIndex;
	}

	/**
	 * A bean may have a "default" event that is the event that will
	 * mostly commonly be used by human's when using the bean.
	 *
	 * @return Index of default event in the EventSetDescriptor array
	 * returned by getEventSetDescriptors.
	 * <P>	Returns -1 if there is no default event.
	 */
	@Override
	public int getDefaultEventIndex() {
		return defaultEventIndex;
	}
}
