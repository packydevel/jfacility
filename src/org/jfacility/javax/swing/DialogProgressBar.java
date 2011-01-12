package org.jfacility.javax.swing;

import java.awt.BorderLayout;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JProgressBar;

/**
 * 
 * @author luca
 */
public class DialogProgressBar extends JDialog{
    private JProgressBar bar;
    private JButton jbAbort;

    public DialogProgressBar(Frame owner, String title, int max) {
        super(owner, title, ModalityType.DOCUMENT_MODAL);
        setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setPreferredSize(new Dimension(300, 80));
        setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        bar = new JProgressBar(0, max);
        bar.setValue(0);
        bar.setStringPainted(true);
        
        jbAbort = new JButton(" Abort ");
        jbAbort.setPreferredSize(new Dimension(100, 25));
        jbAbort.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                //TODO creare evento custom abort   tipo exception
            }
        });
        
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(bar, BorderLayout.CENTER);
        getContentPane().add(jbAbort, BorderLayout.SOUTH);
        pack();
        /* Non è chiaro il motivo ma se la jdialog modale è resa visibile in un thread a parte
         * allora l'attività di aggiornamento sullo stato di avanzamento della progress bar
         * sarà visibile.
         * Se non si utilizza un thread a parte per rendere visibile la jdialog allora
         * il fatto che la jdialog è modale blocca tutte le attività.
         */
        Thread t = new Thread(new Runnable()  {
            @Override
            public void run() {
                DialogProgressBar.this.setVisible(true);
            }
        });
        t.start();
    }

    public void setProgress(int progress) {
    	bar.setValue(progress);
    	if (progress == bar.getMaximum())
            dispose();
    }

}