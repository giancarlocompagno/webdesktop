package it.bradipo.webdesktop;

/*
 * Copyright (c) 1995, 2008, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of Oracle or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */ 

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

/* 
 * ButtonDemo.java requires the following files:
 *   images/right.gif
 *   images/middle.gif
 *   images/left.gif
 */
public class ServerGUI extends JPanel{
    protected JButton start,stop;
    protected JTextField field;
    protected JLabel label;
    
    protected Server server;
    
    protected JCheckBox enableHttps;
    protected JCheckBox enableAuthentication;

    public ServerGUI() {
        
    	
    	
        field = new JTextField("6060",10);
        field.setHorizontalAlignment(JTextField.RIGHT);
        field.setBorder(BorderFactory.createTitledBorder("Inserisci la porta"));
        field.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(KeyEvent e) {
                char c = e.getKeyChar();
                if  (! (c>='0' && c <='9') || (c == KeyEvent.VK_BACK_SPACE) || (c == KeyEvent.VK_DELETE)) {
                	getToolkit().beep();
                	e.consume();
                }
            }
        });
        
    	
    	start = new JButton("Avvio server");
        start.setVerticalTextPosition(AbstractButton.CENTER);
        start.setHorizontalTextPosition(AbstractButton.LEADING);
        

        stop = new JButton("Stop Server");
        stop.setVerticalTextPosition(AbstractButton.BOTTOM);
        stop.setHorizontalTextPosition(AbstractButton.CENTER);
        
        
        label = new JLabel("Status: da avviare");
        
        enableHttps = new JCheckBox("Enable Https",true);
        enableAuthentication = new JCheckBox("Enable Authentication",true);

        start.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					int port = Integer.parseInt(field.getText());
					boolean enableHttpsB = enableHttps.isSelected();
					boolean enableAuthenticationB = enableAuthentication.isSelected();
					server = new Server(port,enableHttpsB,enableAuthenticationB);
					server.start();
					label.setText("Status: avviato");
				} catch (Exception e1) {
					label.setText(e1.getMessage());
					e1.printStackTrace();
				}
			}
		});
        stop.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				server.stop();
				server = null;
				label.setText("Status: stoppato");
				
			}
		});

        //Listen for actions on buttons 1 and 3.
        

        

        //Add Components to this container, using the default FlowLayout.
        add(field);
        add(enableHttps);
        add(enableAuthentication);
        add(start);
        add(stop);
        add(label);
    }

   

    /**
     * Create the GUI and show it.  For thread safety, 
     * this method should be invoked from the 
     * event-dispatching thread.
     */
    private static void createAndShowGUI() {

        //Create and set up the window.
        JFrame frame = new JFrame("Desktop Remoto");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create and set up the content pane.
        ServerGUI newContentPane = new ServerGUI();
        newContentPane.setOpaque(true); //content panes must be opaque
        frame.setContentPane(newContentPane);

        //Display the window.
        frame.pack();
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI(); 
            }
        });
    }
}
