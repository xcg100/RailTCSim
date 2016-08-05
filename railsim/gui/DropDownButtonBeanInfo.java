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
public class DropDownButtonBeanInfo extends SimpleBeanInfo {

    // Bean descriptor information will be obtained from introspection.//GEN-FIRST:BeanDescriptor
    private static BeanDescriptor beanDescriptor = null;
    private static BeanDescriptor getBdescriptor(){
//GEN-HEADEREND:BeanDescriptor

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
    private static final int PROPERTY_defaultButton = 28;
    private static final int PROPERTY_defaultCapable = 29;
    private static final int PROPERTY_disabledIcon = 30;
    private static final int PROPERTY_disabledSelectedIcon = 31;
    private static final int PROPERTY_displayable = 32;
    private static final int PROPERTY_displayedMnemonicIndex = 33;
    private static final int PROPERTY_doubleBuffered = 34;
    private static final int PROPERTY_dropTarget = 35;
    private static final int PROPERTY_enabled = 36;
    private static final int PROPERTY_focusable = 37;
    private static final int PROPERTY_focusCycleRoot = 38;
    private static final int PROPERTY_focusCycleRootAncestor = 39;
    private static final int PROPERTY_focusListeners = 40;
    private static final int PROPERTY_focusOwner = 41;
    private static final int PROPERTY_focusPainted = 42;
    private static final int PROPERTY_focusTraversable = 43;
    private static final int PROPERTY_focusTraversalKeys = 44;
    private static final int PROPERTY_focusTraversalKeysEnabled = 45;
    private static final int PROPERTY_focusTraversalPolicy = 46;
    private static final int PROPERTY_focusTraversalPolicyProvider = 47;
    private static final int PROPERTY_focusTraversalPolicySet = 48;
    private static final int PROPERTY_font = 49;
    private static final int PROPERTY_fontSet = 50;
    private static final int PROPERTY_foreground = 51;
    private static final int PROPERTY_foregroundSet = 52;
    private static final int PROPERTY_graphics = 53;
    private static final int PROPERTY_graphicsConfiguration = 54;
    private static final int PROPERTY_height = 55;
    private static final int PROPERTY_hideActionText = 56;
    private static final int PROPERTY_hierarchyBoundsListeners = 57;
    private static final int PROPERTY_hierarchyListeners = 58;
    private static final int PROPERTY_horizontalAlignment = 59;
    private static final int PROPERTY_horizontalTextPosition = 60;
    private static final int PROPERTY_icon = 61;
    private static final int PROPERTY_iconTextGap = 62;
    private static final int PROPERTY_ignoreRepaint = 63;
    private static final int PROPERTY_inheritsPopupMenu = 64;
    private static final int PROPERTY_inputContext = 65;
    private static final int PROPERTY_inputMap = 66;
    private static final int PROPERTY_inputMethodListeners = 67;
    private static final int PROPERTY_inputMethodRequests = 68;
    private static final int PROPERTY_inputVerifier = 69;
    private static final int PROPERTY_insets = 70;
    private static final int PROPERTY_itemListeners = 71;
    private static final int PROPERTY_items = 72;
    private static final int PROPERTY_keyListeners = 73;
    private static final int PROPERTY_label = 74;
    private static final int PROPERTY_layout = 75;
    private static final int PROPERTY_lightweight = 76;
    private static final int PROPERTY_locale = 77;
    private static final int PROPERTY_location = 78;
    private static final int PROPERTY_locationOnScreen = 79;
    private static final int PROPERTY_managingFocus = 80;
    private static final int PROPERTY_margin = 81;
    private static final int PROPERTY_maximumSize = 82;
    private static final int PROPERTY_maximumSizeSet = 83;
    private static final int PROPERTY_minimumSize = 84;
    private static final int PROPERTY_minimumSizeSet = 85;
    private static final int PROPERTY_mnemonic = 86;
    private static final int PROPERTY_model = 87;
    private static final int PROPERTY_mouseListeners = 88;
    private static final int PROPERTY_mouseMotionListeners = 89;
    private static final int PROPERTY_mousePosition = 90;
    private static final int PROPERTY_mouseWheelListeners = 91;
    private static final int PROPERTY_multiClickThreshhold = 92;
    private static final int PROPERTY_name = 93;
    private static final int PROPERTY_nextFocusableComponent = 94;
    private static final int PROPERTY_opaque = 95;
    private static final int PROPERTY_optimizedDrawingEnabled = 96;
    private static final int PROPERTY_paintingForPrint = 97;
    private static final int PROPERTY_paintingTile = 98;
    private static final int PROPERTY_parent = 99;
    private static final int PROPERTY_peer = 100;
    private static final int PROPERTY_preferredSize = 101;
    private static final int PROPERTY_preferredSizeSet = 102;
    private static final int PROPERTY_pressedIcon = 103;
    private static final int PROPERTY_propertyChangeListeners = 104;
    private static final int PROPERTY_registeredKeyStrokes = 105;
    private static final int PROPERTY_requestFocusEnabled = 106;
    private static final int PROPERTY_rolloverEnabled = 107;
    private static final int PROPERTY_rolloverIcon = 108;
    private static final int PROPERTY_rolloverSelectedIcon = 109;
    private static final int PROPERTY_rootPane = 110;
    private static final int PROPERTY_selected = 111;
    private static final int PROPERTY_selectedIcon = 112;
    private static final int PROPERTY_selectedObjects = 113;
    private static final int PROPERTY_showing = 114;
    private static final int PROPERTY_size = 115;
    private static final int PROPERTY_text = 116;
    private static final int PROPERTY_toolkit = 117;
    private static final int PROPERTY_toolTipText = 118;
    private static final int PROPERTY_topLevelAncestor = 119;
    private static final int PROPERTY_transferHandler = 120;
    private static final int PROPERTY_treeLock = 121;
    private static final int PROPERTY_UI = 122;
    private static final int PROPERTY_UIClassID = 123;
    private static final int PROPERTY_valid = 124;
    private static final int PROPERTY_validateRoot = 125;
    private static final int PROPERTY_verifyInputWhenFocusTarget = 126;
    private static final int PROPERTY_verticalAlignment = 127;
    private static final int PROPERTY_verticalTextPosition = 128;
    private static final int PROPERTY_vetoableChangeListeners = 129;
    private static final int PROPERTY_visible = 130;
    private static final int PROPERTY_visibleRect = 131;
    private static final int PROPERTY_width = 132;
    private static final int PROPERTY_x = 133;
    private static final int PROPERTY_y = 134;

    // Property array 
    /*lazy PropertyDescriptor*/
    private static PropertyDescriptor[] getPdescriptor(){
        PropertyDescriptor[] properties = new PropertyDescriptor[135];
    
        try {
            properties[PROPERTY_accessibleContext] = new PropertyDescriptor ( "accessibleContext", org.railsim.gui.DropDownButton.class, "getAccessibleContext", null ); // NOI18N
            properties[PROPERTY_action] = new PropertyDescriptor ( "action", org.railsim.gui.DropDownButton.class, "getAction", "setAction" ); // NOI18N
            properties[PROPERTY_actionCommand] = new PropertyDescriptor ( "actionCommand", org.railsim.gui.DropDownButton.class, "getActionCommand", "setActionCommand" ); // NOI18N
            properties[PROPERTY_actionListeners] = new PropertyDescriptor ( "actionListeners", org.railsim.gui.DropDownButton.class, "getActionListeners", null ); // NOI18N
            properties[PROPERTY_actionMap] = new PropertyDescriptor ( "actionMap", org.railsim.gui.DropDownButton.class, "getActionMap", "setActionMap" ); // NOI18N
            properties[PROPERTY_alignmentX] = new PropertyDescriptor ( "alignmentX", org.railsim.gui.DropDownButton.class, "getAlignmentX", "setAlignmentX" ); // NOI18N
            properties[PROPERTY_alignmentY] = new PropertyDescriptor ( "alignmentY", org.railsim.gui.DropDownButton.class, "getAlignmentY", "setAlignmentY" ); // NOI18N
            properties[PROPERTY_ancestorListeners] = new PropertyDescriptor ( "ancestorListeners", org.railsim.gui.DropDownButton.class, "getAncestorListeners", null ); // NOI18N
            properties[PROPERTY_autoscrolls] = new PropertyDescriptor ( "autoscrolls", org.railsim.gui.DropDownButton.class, "getAutoscrolls", "setAutoscrolls" ); // NOI18N
            properties[PROPERTY_background] = new PropertyDescriptor ( "background", org.railsim.gui.DropDownButton.class, "getBackground", "setBackground" ); // NOI18N
            properties[PROPERTY_backgroundSet] = new PropertyDescriptor ( "backgroundSet", org.railsim.gui.DropDownButton.class, "isBackgroundSet", null ); // NOI18N
            properties[PROPERTY_baselineResizeBehavior] = new PropertyDescriptor ( "baselineResizeBehavior", org.railsim.gui.DropDownButton.class, "getBaselineResizeBehavior", null ); // NOI18N
            properties[PROPERTY_border] = new PropertyDescriptor ( "border", org.railsim.gui.DropDownButton.class, "getBorder", "setBorder" ); // NOI18N
            properties[PROPERTY_borderPainted] = new PropertyDescriptor ( "borderPainted", org.railsim.gui.DropDownButton.class, "isBorderPainted", "setBorderPainted" ); // NOI18N
            properties[PROPERTY_bounds] = new PropertyDescriptor ( "bounds", org.railsim.gui.DropDownButton.class, "getBounds", "setBounds" ); // NOI18N
            properties[PROPERTY_changeListeners] = new PropertyDescriptor ( "changeListeners", org.railsim.gui.DropDownButton.class, "getChangeListeners", null ); // NOI18N
            properties[PROPERTY_colorModel] = new PropertyDescriptor ( "colorModel", org.railsim.gui.DropDownButton.class, "getColorModel", null ); // NOI18N
            properties[PROPERTY_component] = new IndexedPropertyDescriptor ( "component", org.railsim.gui.DropDownButton.class, null, null, "getComponent", null ); // NOI18N
            properties[PROPERTY_componentCount] = new PropertyDescriptor ( "componentCount", org.railsim.gui.DropDownButton.class, "getComponentCount", null ); // NOI18N
            properties[PROPERTY_componentListeners] = new PropertyDescriptor ( "componentListeners", org.railsim.gui.DropDownButton.class, "getComponentListeners", null ); // NOI18N
            properties[PROPERTY_componentOrientation] = new PropertyDescriptor ( "componentOrientation", org.railsim.gui.DropDownButton.class, "getComponentOrientation", "setComponentOrientation" ); // NOI18N
            properties[PROPERTY_componentPopupMenu] = new PropertyDescriptor ( "componentPopupMenu", org.railsim.gui.DropDownButton.class, "getComponentPopupMenu", "setComponentPopupMenu" ); // NOI18N
            properties[PROPERTY_components] = new PropertyDescriptor ( "components", org.railsim.gui.DropDownButton.class, "getComponents", null ); // NOI18N
            properties[PROPERTY_containerListeners] = new PropertyDescriptor ( "containerListeners", org.railsim.gui.DropDownButton.class, "getContainerListeners", null ); // NOI18N
            properties[PROPERTY_contentAreaFilled] = new PropertyDescriptor ( "contentAreaFilled", org.railsim.gui.DropDownButton.class, "isContentAreaFilled", "setContentAreaFilled" ); // NOI18N
            properties[PROPERTY_cursor] = new PropertyDescriptor ( "cursor", org.railsim.gui.DropDownButton.class, "getCursor", "setCursor" ); // NOI18N
            properties[PROPERTY_cursorSet] = new PropertyDescriptor ( "cursorSet", org.railsim.gui.DropDownButton.class, "isCursorSet", null ); // NOI18N
            properties[PROPERTY_debugGraphicsOptions] = new PropertyDescriptor ( "debugGraphicsOptions", org.railsim.gui.DropDownButton.class, "getDebugGraphicsOptions", "setDebugGraphicsOptions" ); // NOI18N
            properties[PROPERTY_defaultButton] = new PropertyDescriptor ( "defaultButton", org.railsim.gui.DropDownButton.class, "isDefaultButton", null ); // NOI18N
            properties[PROPERTY_defaultCapable] = new PropertyDescriptor ( "defaultCapable", org.railsim.gui.DropDownButton.class, "isDefaultCapable", "setDefaultCapable" ); // NOI18N
            properties[PROPERTY_disabledIcon] = new PropertyDescriptor ( "disabledIcon", org.railsim.gui.DropDownButton.class, "getDisabledIcon", "setDisabledIcon" ); // NOI18N
            properties[PROPERTY_disabledSelectedIcon] = new PropertyDescriptor ( "disabledSelectedIcon", org.railsim.gui.DropDownButton.class, "getDisabledSelectedIcon", "setDisabledSelectedIcon" ); // NOI18N
            properties[PROPERTY_displayable] = new PropertyDescriptor ( "displayable", org.railsim.gui.DropDownButton.class, "isDisplayable", null ); // NOI18N
            properties[PROPERTY_displayedMnemonicIndex] = new PropertyDescriptor ( "displayedMnemonicIndex", org.railsim.gui.DropDownButton.class, "getDisplayedMnemonicIndex", "setDisplayedMnemonicIndex" ); // NOI18N
            properties[PROPERTY_doubleBuffered] = new PropertyDescriptor ( "doubleBuffered", org.railsim.gui.DropDownButton.class, "isDoubleBuffered", "setDoubleBuffered" ); // NOI18N
            properties[PROPERTY_dropTarget] = new PropertyDescriptor ( "dropTarget", org.railsim.gui.DropDownButton.class, "getDropTarget", "setDropTarget" ); // NOI18N
            properties[PROPERTY_enabled] = new PropertyDescriptor ( "enabled", org.railsim.gui.DropDownButton.class, "isEnabled", "setEnabled" ); // NOI18N
            properties[PROPERTY_focusable] = new PropertyDescriptor ( "focusable", org.railsim.gui.DropDownButton.class, "isFocusable", "setFocusable" ); // NOI18N
            properties[PROPERTY_focusCycleRoot] = new PropertyDescriptor ( "focusCycleRoot", org.railsim.gui.DropDownButton.class, "isFocusCycleRoot", "setFocusCycleRoot" ); // NOI18N
            properties[PROPERTY_focusCycleRootAncestor] = new PropertyDescriptor ( "focusCycleRootAncestor", org.railsim.gui.DropDownButton.class, "getFocusCycleRootAncestor", null ); // NOI18N
            properties[PROPERTY_focusListeners] = new PropertyDescriptor ( "focusListeners", org.railsim.gui.DropDownButton.class, "getFocusListeners", null ); // NOI18N
            properties[PROPERTY_focusOwner] = new PropertyDescriptor ( "focusOwner", org.railsim.gui.DropDownButton.class, "isFocusOwner", null ); // NOI18N
            properties[PROPERTY_focusPainted] = new PropertyDescriptor ( "focusPainted", org.railsim.gui.DropDownButton.class, "isFocusPainted", "setFocusPainted" ); // NOI18N
            properties[PROPERTY_focusTraversable] = new PropertyDescriptor ( "focusTraversable", org.railsim.gui.DropDownButton.class, "isFocusTraversable", null ); // NOI18N
            properties[PROPERTY_focusTraversalKeys] = new IndexedPropertyDescriptor ( "focusTraversalKeys", org.railsim.gui.DropDownButton.class, null, null, "getFocusTraversalKeys", "setFocusTraversalKeys" ); // NOI18N
            properties[PROPERTY_focusTraversalKeysEnabled] = new PropertyDescriptor ( "focusTraversalKeysEnabled", org.railsim.gui.DropDownButton.class, "getFocusTraversalKeysEnabled", "setFocusTraversalKeysEnabled" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicy] = new PropertyDescriptor ( "focusTraversalPolicy", org.railsim.gui.DropDownButton.class, "getFocusTraversalPolicy", "setFocusTraversalPolicy" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicyProvider] = new PropertyDescriptor ( "focusTraversalPolicyProvider", org.railsim.gui.DropDownButton.class, "isFocusTraversalPolicyProvider", "setFocusTraversalPolicyProvider" ); // NOI18N
            properties[PROPERTY_focusTraversalPolicySet] = new PropertyDescriptor ( "focusTraversalPolicySet", org.railsim.gui.DropDownButton.class, "isFocusTraversalPolicySet", null ); // NOI18N
            properties[PROPERTY_font] = new PropertyDescriptor ( "font", org.railsim.gui.DropDownButton.class, "getFont", "setFont" ); // NOI18N
            properties[PROPERTY_fontSet] = new PropertyDescriptor ( "fontSet", org.railsim.gui.DropDownButton.class, "isFontSet", null ); // NOI18N
            properties[PROPERTY_foreground] = new PropertyDescriptor ( "foreground", org.railsim.gui.DropDownButton.class, "getForeground", "setForeground" ); // NOI18N
            properties[PROPERTY_foregroundSet] = new PropertyDescriptor ( "foregroundSet", org.railsim.gui.DropDownButton.class, "isForegroundSet", null ); // NOI18N
            properties[PROPERTY_graphics] = new PropertyDescriptor ( "graphics", org.railsim.gui.DropDownButton.class, "getGraphics", null ); // NOI18N
            properties[PROPERTY_graphicsConfiguration] = new PropertyDescriptor ( "graphicsConfiguration", org.railsim.gui.DropDownButton.class, "getGraphicsConfiguration", null ); // NOI18N
            properties[PROPERTY_height] = new PropertyDescriptor ( "height", org.railsim.gui.DropDownButton.class, "getHeight", null ); // NOI18N
            properties[PROPERTY_hideActionText] = new PropertyDescriptor ( "hideActionText", org.railsim.gui.DropDownButton.class, "getHideActionText", "setHideActionText" ); // NOI18N
            properties[PROPERTY_hierarchyBoundsListeners] = new PropertyDescriptor ( "hierarchyBoundsListeners", org.railsim.gui.DropDownButton.class, "getHierarchyBoundsListeners", null ); // NOI18N
            properties[PROPERTY_hierarchyListeners] = new PropertyDescriptor ( "hierarchyListeners", org.railsim.gui.DropDownButton.class, "getHierarchyListeners", null ); // NOI18N
            properties[PROPERTY_horizontalAlignment] = new PropertyDescriptor ( "horizontalAlignment", org.railsim.gui.DropDownButton.class, "getHorizontalAlignment", "setHorizontalAlignment" ); // NOI18N
            properties[PROPERTY_horizontalTextPosition] = new PropertyDescriptor ( "horizontalTextPosition", org.railsim.gui.DropDownButton.class, "getHorizontalTextPosition", "setHorizontalTextPosition" ); // NOI18N
            properties[PROPERTY_icon] = new PropertyDescriptor ( "icon", org.railsim.gui.DropDownButton.class, "getIcon", "setIcon" ); // NOI18N
            properties[PROPERTY_iconTextGap] = new PropertyDescriptor ( "iconTextGap", org.railsim.gui.DropDownButton.class, "getIconTextGap", "setIconTextGap" ); // NOI18N
            properties[PROPERTY_ignoreRepaint] = new PropertyDescriptor ( "ignoreRepaint", org.railsim.gui.DropDownButton.class, "getIgnoreRepaint", "setIgnoreRepaint" ); // NOI18N
            properties[PROPERTY_inheritsPopupMenu] = new PropertyDescriptor ( "inheritsPopupMenu", org.railsim.gui.DropDownButton.class, "getInheritsPopupMenu", "setInheritsPopupMenu" ); // NOI18N
            properties[PROPERTY_inputContext] = new PropertyDescriptor ( "inputContext", org.railsim.gui.DropDownButton.class, "getInputContext", null ); // NOI18N
            properties[PROPERTY_inputMap] = new PropertyDescriptor ( "inputMap", org.railsim.gui.DropDownButton.class, "getInputMap", null ); // NOI18N
            properties[PROPERTY_inputMethodListeners] = new PropertyDescriptor ( "inputMethodListeners", org.railsim.gui.DropDownButton.class, "getInputMethodListeners", null ); // NOI18N
            properties[PROPERTY_inputMethodRequests] = new PropertyDescriptor ( "inputMethodRequests", org.railsim.gui.DropDownButton.class, "getInputMethodRequests", null ); // NOI18N
            properties[PROPERTY_inputVerifier] = new PropertyDescriptor ( "inputVerifier", org.railsim.gui.DropDownButton.class, "getInputVerifier", "setInputVerifier" ); // NOI18N
            properties[PROPERTY_insets] = new PropertyDescriptor ( "insets", org.railsim.gui.DropDownButton.class, "getInsets", null ); // NOI18N
            properties[PROPERTY_itemListeners] = new PropertyDescriptor ( "itemListeners", org.railsim.gui.DropDownButton.class, "getItemListeners", null ); // NOI18N
            properties[PROPERTY_items] = new PropertyDescriptor ( "items", org.railsim.gui.DropDownButton.class, null, "setItems" ); // NOI18N
            properties[PROPERTY_items].setPreferred ( true );
            properties[PROPERTY_items].setDisplayName ( "Items" );
            properties[PROPERTY_items].setShortDescription ( "GUI Text:ActionCommand" );
            properties[PROPERTY_keyListeners] = new PropertyDescriptor ( "keyListeners", org.railsim.gui.DropDownButton.class, "getKeyListeners", null ); // NOI18N
            properties[PROPERTY_label] = new PropertyDescriptor ( "label", org.railsim.gui.DropDownButton.class, "getLabel", "setLabel" ); // NOI18N
            properties[PROPERTY_layout] = new PropertyDescriptor ( "layout", org.railsim.gui.DropDownButton.class, "getLayout", "setLayout" ); // NOI18N
            properties[PROPERTY_lightweight] = new PropertyDescriptor ( "lightweight", org.railsim.gui.DropDownButton.class, "isLightweight", null ); // NOI18N
            properties[PROPERTY_locale] = new PropertyDescriptor ( "locale", org.railsim.gui.DropDownButton.class, "getLocale", "setLocale" ); // NOI18N
            properties[PROPERTY_location] = new PropertyDescriptor ( "location", org.railsim.gui.DropDownButton.class, "getLocation", "setLocation" ); // NOI18N
            properties[PROPERTY_locationOnScreen] = new PropertyDescriptor ( "locationOnScreen", org.railsim.gui.DropDownButton.class, "getLocationOnScreen", null ); // NOI18N
            properties[PROPERTY_managingFocus] = new PropertyDescriptor ( "managingFocus", org.railsim.gui.DropDownButton.class, "isManagingFocus", null ); // NOI18N
            properties[PROPERTY_margin] = new PropertyDescriptor ( "margin", org.railsim.gui.DropDownButton.class, "getMargin", "setMargin" ); // NOI18N
            properties[PROPERTY_maximumSize] = new PropertyDescriptor ( "maximumSize", org.railsim.gui.DropDownButton.class, "getMaximumSize", "setMaximumSize" ); // NOI18N
            properties[PROPERTY_maximumSizeSet] = new PropertyDescriptor ( "maximumSizeSet", org.railsim.gui.DropDownButton.class, "isMaximumSizeSet", null ); // NOI18N
            properties[PROPERTY_minimumSize] = new PropertyDescriptor ( "minimumSize", org.railsim.gui.DropDownButton.class, "getMinimumSize", "setMinimumSize" ); // NOI18N
            properties[PROPERTY_minimumSizeSet] = new PropertyDescriptor ( "minimumSizeSet", org.railsim.gui.DropDownButton.class, "isMinimumSizeSet", null ); // NOI18N
            properties[PROPERTY_mnemonic] = new PropertyDescriptor ( "mnemonic", org.railsim.gui.DropDownButton.class, null, "setMnemonic" ); // NOI18N
            properties[PROPERTY_model] = new PropertyDescriptor ( "model", org.railsim.gui.DropDownButton.class, "getModel", "setModel" ); // NOI18N
            properties[PROPERTY_mouseListeners] = new PropertyDescriptor ( "mouseListeners", org.railsim.gui.DropDownButton.class, "getMouseListeners", null ); // NOI18N
            properties[PROPERTY_mouseMotionListeners] = new PropertyDescriptor ( "mouseMotionListeners", org.railsim.gui.DropDownButton.class, "getMouseMotionListeners", null ); // NOI18N
            properties[PROPERTY_mousePosition] = new PropertyDescriptor ( "mousePosition", org.railsim.gui.DropDownButton.class, "getMousePosition", null ); // NOI18N
            properties[PROPERTY_mouseWheelListeners] = new PropertyDescriptor ( "mouseWheelListeners", org.railsim.gui.DropDownButton.class, "getMouseWheelListeners", null ); // NOI18N
            properties[PROPERTY_multiClickThreshhold] = new PropertyDescriptor ( "multiClickThreshhold", org.railsim.gui.DropDownButton.class, "getMultiClickThreshhold", "setMultiClickThreshhold" ); // NOI18N
            properties[PROPERTY_name] = new PropertyDescriptor ( "name", org.railsim.gui.DropDownButton.class, "getName", "setName" ); // NOI18N
            properties[PROPERTY_nextFocusableComponent] = new PropertyDescriptor ( "nextFocusableComponent", org.railsim.gui.DropDownButton.class, "getNextFocusableComponent", "setNextFocusableComponent" ); // NOI18N
            properties[PROPERTY_opaque] = new PropertyDescriptor ( "opaque", org.railsim.gui.DropDownButton.class, "isOpaque", "setOpaque" ); // NOI18N
            properties[PROPERTY_optimizedDrawingEnabled] = new PropertyDescriptor ( "optimizedDrawingEnabled", org.railsim.gui.DropDownButton.class, "isOptimizedDrawingEnabled", null ); // NOI18N
            properties[PROPERTY_paintingForPrint] = new PropertyDescriptor ( "paintingForPrint", org.railsim.gui.DropDownButton.class, "isPaintingForPrint", null ); // NOI18N
            properties[PROPERTY_paintingTile] = new PropertyDescriptor ( "paintingTile", org.railsim.gui.DropDownButton.class, "isPaintingTile", null ); // NOI18N
            properties[PROPERTY_parent] = new PropertyDescriptor ( "parent", org.railsim.gui.DropDownButton.class, "getParent", null ); // NOI18N
            properties[PROPERTY_peer] = new PropertyDescriptor ( "peer", org.railsim.gui.DropDownButton.class, "getPeer", null ); // NOI18N
            properties[PROPERTY_preferredSize] = new PropertyDescriptor ( "preferredSize", org.railsim.gui.DropDownButton.class, "getPreferredSize", "setPreferredSize" ); // NOI18N
            properties[PROPERTY_preferredSizeSet] = new PropertyDescriptor ( "preferredSizeSet", org.railsim.gui.DropDownButton.class, "isPreferredSizeSet", null ); // NOI18N
            properties[PROPERTY_pressedIcon] = new PropertyDescriptor ( "pressedIcon", org.railsim.gui.DropDownButton.class, "getPressedIcon", "setPressedIcon" ); // NOI18N
            properties[PROPERTY_propertyChangeListeners] = new PropertyDescriptor ( "propertyChangeListeners", org.railsim.gui.DropDownButton.class, "getPropertyChangeListeners", null ); // NOI18N
            properties[PROPERTY_registeredKeyStrokes] = new PropertyDescriptor ( "registeredKeyStrokes", org.railsim.gui.DropDownButton.class, "getRegisteredKeyStrokes", null ); // NOI18N
            properties[PROPERTY_requestFocusEnabled] = new PropertyDescriptor ( "requestFocusEnabled", org.railsim.gui.DropDownButton.class, "isRequestFocusEnabled", "setRequestFocusEnabled" ); // NOI18N
            properties[PROPERTY_rolloverEnabled] = new PropertyDescriptor ( "rolloverEnabled", org.railsim.gui.DropDownButton.class, "isRolloverEnabled", "setRolloverEnabled" ); // NOI18N
            properties[PROPERTY_rolloverIcon] = new PropertyDescriptor ( "rolloverIcon", org.railsim.gui.DropDownButton.class, "getRolloverIcon", "setRolloverIcon" ); // NOI18N
            properties[PROPERTY_rolloverSelectedIcon] = new PropertyDescriptor ( "rolloverSelectedIcon", org.railsim.gui.DropDownButton.class, "getRolloverSelectedIcon", "setRolloverSelectedIcon" ); // NOI18N
            properties[PROPERTY_rootPane] = new PropertyDescriptor ( "rootPane", org.railsim.gui.DropDownButton.class, "getRootPane", null ); // NOI18N
            properties[PROPERTY_selected] = new PropertyDescriptor ( "selected", org.railsim.gui.DropDownButton.class, "isSelected", "setSelected" ); // NOI18N
            properties[PROPERTY_selectedIcon] = new PropertyDescriptor ( "selectedIcon", org.railsim.gui.DropDownButton.class, "getSelectedIcon", "setSelectedIcon" ); // NOI18N
            properties[PROPERTY_selectedObjects] = new PropertyDescriptor ( "selectedObjects", org.railsim.gui.DropDownButton.class, "getSelectedObjects", null ); // NOI18N
            properties[PROPERTY_showing] = new PropertyDescriptor ( "showing", org.railsim.gui.DropDownButton.class, "isShowing", null ); // NOI18N
            properties[PROPERTY_size] = new PropertyDescriptor ( "size", org.railsim.gui.DropDownButton.class, "getSize", "setSize" ); // NOI18N
            properties[PROPERTY_text] = new PropertyDescriptor ( "text", org.railsim.gui.DropDownButton.class, "getText", "setText" ); // NOI18N
            properties[PROPERTY_toolkit] = new PropertyDescriptor ( "toolkit", org.railsim.gui.DropDownButton.class, "getToolkit", null ); // NOI18N
            properties[PROPERTY_toolTipText] = new PropertyDescriptor ( "toolTipText", org.railsim.gui.DropDownButton.class, "getToolTipText", "setToolTipText" ); // NOI18N
            properties[PROPERTY_topLevelAncestor] = new PropertyDescriptor ( "topLevelAncestor", org.railsim.gui.DropDownButton.class, "getTopLevelAncestor", null ); // NOI18N
            properties[PROPERTY_transferHandler] = new PropertyDescriptor ( "transferHandler", org.railsim.gui.DropDownButton.class, "getTransferHandler", "setTransferHandler" ); // NOI18N
            properties[PROPERTY_treeLock] = new PropertyDescriptor ( "treeLock", org.railsim.gui.DropDownButton.class, "getTreeLock", null ); // NOI18N
            properties[PROPERTY_UI] = new PropertyDescriptor ( "UI", org.railsim.gui.DropDownButton.class, "getUI", "setUI" ); // NOI18N
            properties[PROPERTY_UIClassID] = new PropertyDescriptor ( "UIClassID", org.railsim.gui.DropDownButton.class, "getUIClassID", null ); // NOI18N
            properties[PROPERTY_valid] = new PropertyDescriptor ( "valid", org.railsim.gui.DropDownButton.class, "isValid", null ); // NOI18N
            properties[PROPERTY_validateRoot] = new PropertyDescriptor ( "validateRoot", org.railsim.gui.DropDownButton.class, "isValidateRoot", null ); // NOI18N
            properties[PROPERTY_verifyInputWhenFocusTarget] = new PropertyDescriptor ( "verifyInputWhenFocusTarget", org.railsim.gui.DropDownButton.class, "getVerifyInputWhenFocusTarget", "setVerifyInputWhenFocusTarget" ); // NOI18N
            properties[PROPERTY_verticalAlignment] = new PropertyDescriptor ( "verticalAlignment", org.railsim.gui.DropDownButton.class, "getVerticalAlignment", "setVerticalAlignment" ); // NOI18N
            properties[PROPERTY_verticalTextPosition] = new PropertyDescriptor ( "verticalTextPosition", org.railsim.gui.DropDownButton.class, "getVerticalTextPosition", "setVerticalTextPosition" ); // NOI18N
            properties[PROPERTY_vetoableChangeListeners] = new PropertyDescriptor ( "vetoableChangeListeners", org.railsim.gui.DropDownButton.class, "getVetoableChangeListeners", null ); // NOI18N
            properties[PROPERTY_visible] = new PropertyDescriptor ( "visible", org.railsim.gui.DropDownButton.class, "isVisible", "setVisible" ); // NOI18N
            properties[PROPERTY_visibleRect] = new PropertyDescriptor ( "visibleRect", org.railsim.gui.DropDownButton.class, "getVisibleRect", null ); // NOI18N
            properties[PROPERTY_width] = new PropertyDescriptor ( "width", org.railsim.gui.DropDownButton.class, "getWidth", null ); // NOI18N
            properties[PROPERTY_x] = new PropertyDescriptor ( "x", org.railsim.gui.DropDownButton.class, "getX", null ); // NOI18N
            properties[PROPERTY_y] = new PropertyDescriptor ( "y", org.railsim.gui.DropDownButton.class, "getY", null ); // NOI18N
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
            eventSets[EVENT_actionListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "actionListener", java.awt.event.ActionListener.class, new String[] {"actionPerformed"}, "addActionListener", "removeActionListener" ); // NOI18N
            eventSets[EVENT_ancestorListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "ancestorListener", javax.swing.event.AncestorListener.class, new String[] {"ancestorAdded", "ancestorMoved", "ancestorRemoved"}, "addAncestorListener", "removeAncestorListener" ); // NOI18N
            eventSets[EVENT_changeListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "changeListener", javax.swing.event.ChangeListener.class, new String[] {"stateChanged"}, "addChangeListener", "removeChangeListener" ); // NOI18N
            eventSets[EVENT_componentListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "componentListener", java.awt.event.ComponentListener.class, new String[] {"componentHidden", "componentMoved", "componentResized", "componentShown"}, "addComponentListener", "removeComponentListener" ); // NOI18N
            eventSets[EVENT_containerListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "containerListener", java.awt.event.ContainerListener.class, new String[] {"componentAdded", "componentRemoved"}, "addContainerListener", "removeContainerListener" ); // NOI18N
            eventSets[EVENT_focusListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "focusListener", java.awt.event.FocusListener.class, new String[] {"focusGained", "focusLost"}, "addFocusListener", "removeFocusListener" ); // NOI18N
            eventSets[EVENT_hierarchyBoundsListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "hierarchyBoundsListener", java.awt.event.HierarchyBoundsListener.class, new String[] {"ancestorMoved", "ancestorResized"}, "addHierarchyBoundsListener", "removeHierarchyBoundsListener" ); // NOI18N
            eventSets[EVENT_hierarchyListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "hierarchyListener", java.awt.event.HierarchyListener.class, new String[] {"hierarchyChanged"}, "addHierarchyListener", "removeHierarchyListener" ); // NOI18N
            eventSets[EVENT_inputMethodListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "inputMethodListener", java.awt.event.InputMethodListener.class, new String[] {"caretPositionChanged", "inputMethodTextChanged"}, "addInputMethodListener", "removeInputMethodListener" ); // NOI18N
            eventSets[EVENT_itemListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "itemListener", java.awt.event.ItemListener.class, new String[] {"itemStateChanged"}, "addItemListener", "removeItemListener" ); // NOI18N
            eventSets[EVENT_keyListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "keyListener", java.awt.event.KeyListener.class, new String[] {"keyPressed", "keyReleased", "keyTyped"}, "addKeyListener", "removeKeyListener" ); // NOI18N
            eventSets[EVENT_mouseListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "mouseListener", java.awt.event.MouseListener.class, new String[] {"mouseClicked", "mouseEntered", "mouseExited", "mousePressed", "mouseReleased"}, "addMouseListener", "removeMouseListener" ); // NOI18N
            eventSets[EVENT_mouseMotionListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "mouseMotionListener", java.awt.event.MouseMotionListener.class, new String[] {"mouseDragged", "mouseMoved"}, "addMouseMotionListener", "removeMouseMotionListener" ); // NOI18N
            eventSets[EVENT_mouseWheelListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "mouseWheelListener", java.awt.event.MouseWheelListener.class, new String[] {"mouseWheelMoved"}, "addMouseWheelListener", "removeMouseWheelListener" ); // NOI18N
            eventSets[EVENT_propertyChangeListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "propertyChangeListener", java.beans.PropertyChangeListener.class, new String[] {"propertyChange"}, "addPropertyChangeListener", "removePropertyChangeListener" ); // NOI18N
            eventSets[EVENT_vetoableChangeListener] = new EventSetDescriptor ( org.railsim.gui.DropDownButton.class, "vetoableChangeListener", java.beans.VetoableChangeListener.class, new String[] {"vetoableChange"}, "addVetoableChangeListener", "removeVetoableChangeListener" ); // NOI18N
        }
        catch(IntrospectionException e) {
            e.printStackTrace();
        }//GEN-HEADEREND:Events

		// Here you can add code for customizing the event sets array.

        return eventSets;     }//GEN-LAST:Events
    // Method identifiers//GEN-FIRST:Methods
    private static final int METHOD_action0 = 0;
    private static final int METHOD_addNotify1 = 1;
    private static final int METHOD_addPropertyChangeListener2 = 2;
    private static final int METHOD_applyComponentOrientation3 = 3;
    private static final int METHOD_areFocusTraversalKeysSet4 = 4;
    private static final int METHOD_bounds5 = 5;
    private static final int METHOD_checkImage6 = 6;
    private static final int METHOD_computeVisibleRect7 = 7;
    private static final int METHOD_contains8 = 8;
    private static final int METHOD_countComponents9 = 9;
    private static final int METHOD_createImage10 = 10;
    private static final int METHOD_createToolTip11 = 11;
    private static final int METHOD_createVolatileImage12 = 12;
    private static final int METHOD_deliverEvent13 = 13;
    private static final int METHOD_disable14 = 14;
    private static final int METHOD_dispatchEvent15 = 15;
    private static final int METHOD_doClick16 = 16;
    private static final int METHOD_doLayout17 = 17;
    private static final int METHOD_enable18 = 18;
    private static final int METHOD_enableInputMethods19 = 19;
    private static final int METHOD_findComponentAt20 = 20;
    private static final int METHOD_firePropertyChange21 = 21;
    private static final int METHOD_getActionForKeyStroke22 = 22;
    private static final int METHOD_getBaseline23 = 23;
    private static final int METHOD_getBounds24 = 24;
    private static final int METHOD_getClientProperty25 = 25;
    private static final int METHOD_getComponentAt26 = 26;
    private static final int METHOD_getComponentZOrder27 = 27;
    private static final int METHOD_getConditionForKeyStroke28 = 28;
    private static final int METHOD_getDefaultLocale29 = 29;
    private static final int METHOD_getFontMetrics30 = 30;
    private static final int METHOD_getInsets31 = 31;
    private static final int METHOD_getListeners32 = 32;
    private static final int METHOD_getLocation33 = 33;
    private static final int METHOD_getMnemonic34 = 34;
    private static final int METHOD_getMousePosition35 = 35;
    private static final int METHOD_getPopupLocation36 = 36;
    private static final int METHOD_getPropertyChangeListeners37 = 37;
    private static final int METHOD_getSize38 = 38;
    private static final int METHOD_getToolTipLocation39 = 39;
    private static final int METHOD_getToolTipText40 = 40;
    private static final int METHOD_gotFocus41 = 41;
    private static final int METHOD_grabFocus42 = 42;
    private static final int METHOD_handleEvent43 = 43;
    private static final int METHOD_hasFocus44 = 44;
    private static final int METHOD_hide45 = 45;
    private static final int METHOD_imageUpdate46 = 46;
    private static final int METHOD_insets47 = 47;
    private static final int METHOD_inside48 = 48;
    private static final int METHOD_invalidate49 = 49;
    private static final int METHOD_isAncestorOf50 = 50;
    private static final int METHOD_isFocusCycleRoot51 = 51;
    private static final int METHOD_isLightweightComponent52 = 52;
    private static final int METHOD_keyDown53 = 53;
    private static final int METHOD_keyUp54 = 54;
    private static final int METHOD_layout55 = 55;
    private static final int METHOD_list56 = 56;
    private static final int METHOD_locate57 = 57;
    private static final int METHOD_location58 = 58;
    private static final int METHOD_lostFocus59 = 59;
    private static final int METHOD_minimumSize60 = 60;
    private static final int METHOD_mouseDown61 = 61;
    private static final int METHOD_mouseDrag62 = 62;
    private static final int METHOD_mouseEnter63 = 63;
    private static final int METHOD_mouseExit64 = 64;
    private static final int METHOD_mouseMove65 = 65;
    private static final int METHOD_mouseUp66 = 66;
    private static final int METHOD_move67 = 67;
    private static final int METHOD_nextFocus68 = 68;
    private static final int METHOD_paint69 = 69;
    private static final int METHOD_paintAll70 = 70;
    private static final int METHOD_paintComponents71 = 71;
    private static final int METHOD_paintImmediately72 = 72;
    private static final int METHOD_postEvent73 = 73;
    private static final int METHOD_preferredSize74 = 74;
    private static final int METHOD_prepareImage75 = 75;
    private static final int METHOD_print76 = 76;
    private static final int METHOD_printAll77 = 77;
    private static final int METHOD_printComponents78 = 78;
    private static final int METHOD_putClientProperty79 = 79;
    private static final int METHOD_registerKeyboardAction80 = 80;
    private static final int METHOD_remove81 = 81;
    private static final int METHOD_removeAll82 = 82;
    private static final int METHOD_removeNotify83 = 83;
    private static final int METHOD_removePropertyChangeListener84 = 84;
    private static final int METHOD_repaint85 = 85;
    private static final int METHOD_requestDefaultFocus86 = 86;
    private static final int METHOD_requestFocus87 = 87;
    private static final int METHOD_requestFocusInWindow88 = 88;
    private static final int METHOD_resetKeyboardActions89 = 89;
    private static final int METHOD_reshape90 = 90;
    private static final int METHOD_resize91 = 91;
    private static final int METHOD_revalidate92 = 92;
    private static final int METHOD_scrollRectToVisible93 = 93;
    private static final int METHOD_setBounds94 = 94;
    private static final int METHOD_setComponentZOrder95 = 95;
    private static final int METHOD_setDefaultLocale96 = 96;
    private static final int METHOD_setMnemonic97 = 97;
    private static final int METHOD_show98 = 98;
    private static final int METHOD_size99 = 99;
    private static final int METHOD_toString100 = 100;
    private static final int METHOD_transferFocus101 = 101;
    private static final int METHOD_transferFocusBackward102 = 102;
    private static final int METHOD_transferFocusDownCycle103 = 103;
    private static final int METHOD_transferFocusUpCycle104 = 104;
    private static final int METHOD_unregisterKeyboardAction105 = 105;
    private static final int METHOD_update106 = 106;
    private static final int METHOD_updateUI107 = 107;
    private static final int METHOD_validate108 = 108;

    // Method array 
    /*lazy MethodDescriptor*/
    private static MethodDescriptor[] getMdescriptor(){
        MethodDescriptor[] methods = new MethodDescriptor[109];
    
        try {
            methods[METHOD_action0] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("action", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_action0].setDisplayName ( "" );
            methods[METHOD_addNotify1] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("addNotify", new Class[] {})); // NOI18N
            methods[METHOD_addNotify1].setDisplayName ( "" );
            methods[METHOD_addPropertyChangeListener2] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("addPropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_addPropertyChangeListener2].setDisplayName ( "" );
            methods[METHOD_applyComponentOrientation3] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("applyComponentOrientation", new Class[] {java.awt.ComponentOrientation.class})); // NOI18N
            methods[METHOD_applyComponentOrientation3].setDisplayName ( "" );
            methods[METHOD_areFocusTraversalKeysSet4] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("areFocusTraversalKeysSet", new Class[] {Integer.TYPE})); // NOI18N
            methods[METHOD_areFocusTraversalKeysSet4].setDisplayName ( "" );
            methods[METHOD_bounds5] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("bounds", new Class[] {})); // NOI18N
            methods[METHOD_bounds5].setDisplayName ( "" );
            methods[METHOD_checkImage6] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("checkImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_checkImage6].setDisplayName ( "" );
            methods[METHOD_computeVisibleRect7] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("computeVisibleRect", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_computeVisibleRect7].setDisplayName ( "" );
            methods[METHOD_contains8] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("contains", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_contains8].setDisplayName ( "" );
            methods[METHOD_countComponents9] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("countComponents", new Class[] {})); // NOI18N
            methods[METHOD_countComponents9].setDisplayName ( "" );
            methods[METHOD_createImage10] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("createImage", new Class[] {java.awt.image.ImageProducer.class})); // NOI18N
            methods[METHOD_createImage10].setDisplayName ( "" );
            methods[METHOD_createToolTip11] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("createToolTip", new Class[] {})); // NOI18N
            methods[METHOD_createToolTip11].setDisplayName ( "" );
            methods[METHOD_createVolatileImage12] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("createVolatileImage", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_createVolatileImage12].setDisplayName ( "" );
            methods[METHOD_deliverEvent13] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("deliverEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_deliverEvent13].setDisplayName ( "" );
            methods[METHOD_disable14] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("disable", new Class[] {})); // NOI18N
            methods[METHOD_disable14].setDisplayName ( "" );
            methods[METHOD_dispatchEvent15] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("dispatchEvent", new Class[] {java.awt.AWTEvent.class})); // NOI18N
            methods[METHOD_dispatchEvent15].setDisplayName ( "" );
            methods[METHOD_doClick16] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("doClick", new Class[] {})); // NOI18N
            methods[METHOD_doClick16].setDisplayName ( "" );
            methods[METHOD_doLayout17] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("doLayout", new Class[] {})); // NOI18N
            methods[METHOD_doLayout17].setDisplayName ( "" );
            methods[METHOD_enable18] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("enable", new Class[] {})); // NOI18N
            methods[METHOD_enable18].setDisplayName ( "" );
            methods[METHOD_enableInputMethods19] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("enableInputMethods", new Class[] {Boolean.TYPE})); // NOI18N
            methods[METHOD_enableInputMethods19].setDisplayName ( "" );
            methods[METHOD_findComponentAt20] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("findComponentAt", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_findComponentAt20].setDisplayName ( "" );
            methods[METHOD_firePropertyChange21] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("firePropertyChange", new Class[] {java.lang.String.class, Boolean.TYPE, Boolean.TYPE})); // NOI18N
            methods[METHOD_firePropertyChange21].setDisplayName ( "" );
            methods[METHOD_getActionForKeyStroke22] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getActionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getActionForKeyStroke22].setDisplayName ( "" );
            methods[METHOD_getBaseline23] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getBaseline", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_getBaseline23].setDisplayName ( "" );
            methods[METHOD_getBounds24] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getBounds", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_getBounds24].setDisplayName ( "" );
            methods[METHOD_getClientProperty25] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getClientProperty", new Class[] {java.lang.Object.class})); // NOI18N
            methods[METHOD_getClientProperty25].setDisplayName ( "" );
            methods[METHOD_getComponentAt26] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getComponentAt", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_getComponentAt26].setDisplayName ( "" );
            methods[METHOD_getComponentZOrder27] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getComponentZOrder", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_getComponentZOrder27].setDisplayName ( "" );
            methods[METHOD_getConditionForKeyStroke28] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getConditionForKeyStroke", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_getConditionForKeyStroke28].setDisplayName ( "" );
            methods[METHOD_getDefaultLocale29] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getDefaultLocale", new Class[] {})); // NOI18N
            methods[METHOD_getDefaultLocale29].setDisplayName ( "" );
            methods[METHOD_getFontMetrics30] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getFontMetrics", new Class[] {java.awt.Font.class})); // NOI18N
            methods[METHOD_getFontMetrics30].setDisplayName ( "" );
            methods[METHOD_getInsets31] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getInsets", new Class[] {java.awt.Insets.class})); // NOI18N
            methods[METHOD_getInsets31].setDisplayName ( "" );
            methods[METHOD_getListeners32] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getListeners", new Class[] {java.lang.Class.class})); // NOI18N
            methods[METHOD_getListeners32].setDisplayName ( "" );
            methods[METHOD_getLocation33] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getLocation", new Class[] {java.awt.Point.class})); // NOI18N
            methods[METHOD_getLocation33].setDisplayName ( "" );
            methods[METHOD_getMnemonic34] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getMnemonic", new Class[] {})); // NOI18N
            methods[METHOD_getMnemonic34].setDisplayName ( "" );
            methods[METHOD_getMousePosition35] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getMousePosition", new Class[] {Boolean.TYPE})); // NOI18N
            methods[METHOD_getMousePosition35].setDisplayName ( "" );
            methods[METHOD_getPopupLocation36] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getPopupLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getPopupLocation36].setDisplayName ( "" );
            methods[METHOD_getPropertyChangeListeners37] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getPropertyChangeListeners", new Class[] {java.lang.String.class})); // NOI18N
            methods[METHOD_getPropertyChangeListeners37].setDisplayName ( "" );
            methods[METHOD_getSize38] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getSize", new Class[] {java.awt.Dimension.class})); // NOI18N
            methods[METHOD_getSize38].setDisplayName ( "" );
            methods[METHOD_getToolTipLocation39] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getToolTipLocation", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipLocation39].setDisplayName ( "" );
            methods[METHOD_getToolTipText40] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("getToolTipText", new Class[] {java.awt.event.MouseEvent.class})); // NOI18N
            methods[METHOD_getToolTipText40].setDisplayName ( "" );
            methods[METHOD_gotFocus41] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("gotFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_gotFocus41].setDisplayName ( "" );
            methods[METHOD_grabFocus42] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("grabFocus", new Class[] {})); // NOI18N
            methods[METHOD_grabFocus42].setDisplayName ( "" );
            methods[METHOD_handleEvent43] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("handleEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_handleEvent43].setDisplayName ( "" );
            methods[METHOD_hasFocus44] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("hasFocus", new Class[] {})); // NOI18N
            methods[METHOD_hasFocus44].setDisplayName ( "" );
            methods[METHOD_hide45] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("hide", new Class[] {})); // NOI18N
            methods[METHOD_hide45].setDisplayName ( "" );
            methods[METHOD_imageUpdate46] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("imageUpdate", new Class[] {java.awt.Image.class, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_imageUpdate46].setDisplayName ( "" );
            methods[METHOD_insets47] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("insets", new Class[] {})); // NOI18N
            methods[METHOD_insets47].setDisplayName ( "" );
            methods[METHOD_inside48] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("inside", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_inside48].setDisplayName ( "" );
            methods[METHOD_invalidate49] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("invalidate", new Class[] {})); // NOI18N
            methods[METHOD_invalidate49].setDisplayName ( "" );
            methods[METHOD_isAncestorOf50] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("isAncestorOf", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isAncestorOf50].setDisplayName ( "" );
            methods[METHOD_isFocusCycleRoot51] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("isFocusCycleRoot", new Class[] {java.awt.Container.class})); // NOI18N
            methods[METHOD_isFocusCycleRoot51].setDisplayName ( "" );
            methods[METHOD_isLightweightComponent52] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("isLightweightComponent", new Class[] {java.awt.Component.class})); // NOI18N
            methods[METHOD_isLightweightComponent52].setDisplayName ( "" );
            methods[METHOD_keyDown53] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("keyDown", new Class[] {java.awt.Event.class, Integer.TYPE})); // NOI18N
            methods[METHOD_keyDown53].setDisplayName ( "" );
            methods[METHOD_keyUp54] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("keyUp", new Class[] {java.awt.Event.class, Integer.TYPE})); // NOI18N
            methods[METHOD_keyUp54].setDisplayName ( "" );
            methods[METHOD_layout55] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("layout", new Class[] {})); // NOI18N
            methods[METHOD_layout55].setDisplayName ( "" );
            methods[METHOD_list56] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("list", new Class[] {java.io.PrintStream.class, Integer.TYPE})); // NOI18N
            methods[METHOD_list56].setDisplayName ( "" );
            methods[METHOD_locate57] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("locate", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_locate57].setDisplayName ( "" );
            methods[METHOD_location58] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("location", new Class[] {})); // NOI18N
            methods[METHOD_location58].setDisplayName ( "" );
            methods[METHOD_lostFocus59] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("lostFocus", new Class[] {java.awt.Event.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_lostFocus59].setDisplayName ( "" );
            methods[METHOD_minimumSize60] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("minimumSize", new Class[] {})); // NOI18N
            methods[METHOD_minimumSize60].setDisplayName ( "" );
            methods[METHOD_mouseDown61] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("mouseDown", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseDown61].setDisplayName ( "" );
            methods[METHOD_mouseDrag62] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("mouseDrag", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseDrag62].setDisplayName ( "" );
            methods[METHOD_mouseEnter63] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("mouseEnter", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseEnter63].setDisplayName ( "" );
            methods[METHOD_mouseExit64] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("mouseExit", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseExit64].setDisplayName ( "" );
            methods[METHOD_mouseMove65] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("mouseMove", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseMove65].setDisplayName ( "" );
            methods[METHOD_mouseUp66] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("mouseUp", new Class[] {java.awt.Event.class, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_mouseUp66].setDisplayName ( "" );
            methods[METHOD_move67] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("move", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_move67].setDisplayName ( "" );
            methods[METHOD_nextFocus68] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("nextFocus", new Class[] {})); // NOI18N
            methods[METHOD_nextFocus68].setDisplayName ( "" );
            methods[METHOD_paint69] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("paint", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paint69].setDisplayName ( "" );
            methods[METHOD_paintAll70] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("paintAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintAll70].setDisplayName ( "" );
            methods[METHOD_paintComponents71] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("paintComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_paintComponents71].setDisplayName ( "" );
            methods[METHOD_paintImmediately72] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("paintImmediately", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_paintImmediately72].setDisplayName ( "" );
            methods[METHOD_postEvent73] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("postEvent", new Class[] {java.awt.Event.class})); // NOI18N
            methods[METHOD_postEvent73].setDisplayName ( "" );
            methods[METHOD_preferredSize74] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("preferredSize", new Class[] {})); // NOI18N
            methods[METHOD_preferredSize74].setDisplayName ( "" );
            methods[METHOD_prepareImage75] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("prepareImage", new Class[] {java.awt.Image.class, java.awt.image.ImageObserver.class})); // NOI18N
            methods[METHOD_prepareImage75].setDisplayName ( "" );
            methods[METHOD_print76] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("print", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_print76].setDisplayName ( "" );
            methods[METHOD_printAll77] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("printAll", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printAll77].setDisplayName ( "" );
            methods[METHOD_printComponents78] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("printComponents", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_printComponents78].setDisplayName ( "" );
            methods[METHOD_putClientProperty79] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("putClientProperty", new Class[] {java.lang.Object.class, java.lang.Object.class})); // NOI18N
            methods[METHOD_putClientProperty79].setDisplayName ( "" );
            methods[METHOD_registerKeyboardAction80] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("registerKeyboardAction", new Class[] {java.awt.event.ActionListener.class, java.lang.String.class, javax.swing.KeyStroke.class, Integer.TYPE})); // NOI18N
            methods[METHOD_registerKeyboardAction80].setDisplayName ( "" );
            methods[METHOD_remove81] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("remove", new Class[] {Integer.TYPE})); // NOI18N
            methods[METHOD_remove81].setDisplayName ( "" );
            methods[METHOD_removeAll82] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("removeAll", new Class[] {})); // NOI18N
            methods[METHOD_removeAll82].setDisplayName ( "" );
            methods[METHOD_removeNotify83] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("removeNotify", new Class[] {})); // NOI18N
            methods[METHOD_removeNotify83].setDisplayName ( "" );
            methods[METHOD_removePropertyChangeListener84] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("removePropertyChangeListener", new Class[] {java.lang.String.class, java.beans.PropertyChangeListener.class})); // NOI18N
            methods[METHOD_removePropertyChangeListener84].setDisplayName ( "" );
            methods[METHOD_repaint85] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("repaint", new Class[] {Long.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_repaint85].setDisplayName ( "" );
            methods[METHOD_requestDefaultFocus86] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("requestDefaultFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestDefaultFocus86].setDisplayName ( "" );
            methods[METHOD_requestFocus87] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("requestFocus", new Class[] {})); // NOI18N
            methods[METHOD_requestFocus87].setDisplayName ( "" );
            methods[METHOD_requestFocusInWindow88] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("requestFocusInWindow", new Class[] {})); // NOI18N
            methods[METHOD_requestFocusInWindow88].setDisplayName ( "" );
            methods[METHOD_resetKeyboardActions89] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("resetKeyboardActions", new Class[] {})); // NOI18N
            methods[METHOD_resetKeyboardActions89].setDisplayName ( "" );
            methods[METHOD_reshape90] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("reshape", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_reshape90].setDisplayName ( "" );
            methods[METHOD_resize91] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("resize", new Class[] {Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_resize91].setDisplayName ( "" );
            methods[METHOD_revalidate92] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("revalidate", new Class[] {})); // NOI18N
            methods[METHOD_revalidate92].setDisplayName ( "" );
            methods[METHOD_scrollRectToVisible93] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("scrollRectToVisible", new Class[] {java.awt.Rectangle.class})); // NOI18N
            methods[METHOD_scrollRectToVisible93].setDisplayName ( "" );
            methods[METHOD_setBounds94] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("setBounds", new Class[] {Integer.TYPE, Integer.TYPE, Integer.TYPE, Integer.TYPE})); // NOI18N
            methods[METHOD_setBounds94].setDisplayName ( "" );
            methods[METHOD_setComponentZOrder95] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("setComponentZOrder", new Class[] {java.awt.Component.class, Integer.TYPE})); // NOI18N
            methods[METHOD_setComponentZOrder95].setDisplayName ( "" );
            methods[METHOD_setDefaultLocale96] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("setDefaultLocale", new Class[] {java.util.Locale.class})); // NOI18N
            methods[METHOD_setDefaultLocale96].setDisplayName ( "" );
            methods[METHOD_setMnemonic97] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("setMnemonic", new Class[] {Integer.TYPE})); // NOI18N
            methods[METHOD_setMnemonic97].setDisplayName ( "" );
            methods[METHOD_show98] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("show", new Class[] {})); // NOI18N
            methods[METHOD_show98].setDisplayName ( "" );
            methods[METHOD_size99] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("size", new Class[] {})); // NOI18N
            methods[METHOD_size99].setDisplayName ( "" );
            methods[METHOD_toString100] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("toString", new Class[] {})); // NOI18N
            methods[METHOD_toString100].setDisplayName ( "" );
            methods[METHOD_transferFocus101] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("transferFocus", new Class[] {})); // NOI18N
            methods[METHOD_transferFocus101].setDisplayName ( "" );
            methods[METHOD_transferFocusBackward102] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("transferFocusBackward", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusBackward102].setDisplayName ( "" );
            methods[METHOD_transferFocusDownCycle103] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("transferFocusDownCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusDownCycle103].setDisplayName ( "" );
            methods[METHOD_transferFocusUpCycle104] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("transferFocusUpCycle", new Class[] {})); // NOI18N
            methods[METHOD_transferFocusUpCycle104].setDisplayName ( "" );
            methods[METHOD_unregisterKeyboardAction105] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("unregisterKeyboardAction", new Class[] {javax.swing.KeyStroke.class})); // NOI18N
            methods[METHOD_unregisterKeyboardAction105].setDisplayName ( "" );
            methods[METHOD_update106] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("update", new Class[] {java.awt.Graphics.class})); // NOI18N
            methods[METHOD_update106].setDisplayName ( "" );
            methods[METHOD_updateUI107] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("updateUI", new Class[] {})); // NOI18N
            methods[METHOD_updateUI107].setDisplayName ( "" );
            methods[METHOD_validate108] = new MethodDescriptor ( org.railsim.gui.DropDownButton.class.getMethod("validate", new Class[] {})); // NOI18N
            methods[METHOD_validate108].setDisplayName ( "" );
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
