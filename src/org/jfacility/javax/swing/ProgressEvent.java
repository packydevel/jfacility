package org.jfacility.javax.swing;

import java.util.EventObject;

public class ProgressEvent extends EventObject {
	public ProgressEvent(Object source, boolean icontray, String date) {
		super(source);
	}
}
