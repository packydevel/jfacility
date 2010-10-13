package org.jfacility.swing;

import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;

/**
 * 
 * @author luca
 */
public class ComboBoxEditor extends DefaultCellEditor {
	public ComboBoxEditor(String[] items) {
		super(new JComboBox(items));
	}
}