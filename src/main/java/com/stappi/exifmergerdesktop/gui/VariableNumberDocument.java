/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stappi.exifmergerdesktop.gui;

import java.awt.Toolkit;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 */
public class VariableNumberDocument extends PlainDocument {

    private static final String ERLAUBTE_ZEICHEN = "1234567890";
    private final int maxNoOfDigits;
    private int counter;

    public VariableNumberDocument(int maxNoOfDigits) {

        this.maxNoOfDigits = maxNoOfDigits;

        this.addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                //wird nicht verwendet
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                ((VariableNumberDocument) e.getDocument()).setCounter(e.getLength());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                //wird nicht verwendet
            }
        });
    }

    public void setCounter(int length) {

        this.counter -= length;
    }

    @Override
    public void insertString(int offs, String str, AttributeSet a)
            throws BadLocationException {

        if (str.length() >= maxNoOfDigits) {

            str = str.substring(0, maxNoOfDigits - counter);
        }

        for (int i = 0; i < str.length(); i++) {

            if ((counter >= maxNoOfDigits) || (ERLAUBTE_ZEICHEN.indexOf(str.charAt(i)) == -1)) {

                Toolkit.getDefaultToolkit().beep();

                return;
            } else {
                counter++;
            }
        }

        super.insertString(offs, str, a);
    }
}
