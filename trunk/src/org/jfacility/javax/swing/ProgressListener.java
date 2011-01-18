package org.jfacility.javax.swing;

import java.util.EventListener;

public interface ProgressListener extends EventListener {
    public void objReceived(ProgressEvent evt);
}