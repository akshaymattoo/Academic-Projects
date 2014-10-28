package com.ds.ui;

import java.io.*;
import java.net.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import static java.lang.System.out;
/**
 * 
 * @author Akshay
 * This is a client class .THis class shows a text area where the incoimng messages are displayed and another from where one can send 
 * messages.THere id connect button from where one can connect to another client.
 */
public class  ChatClient extends JFrame implements ActionListener {
    String uname;
    PrintWriter pw;
    BufferedReader br;
    JTextArea  taMessages;
    JTextField tfInput;
    JTextField tfInputBuddy;
    JButton btnSend,btnExit,btnOnlineUsers,btnConnect;
    static String uList[]=new String[1];
    String buddy="";
    Socket client;
    //This is a constructor which initializes the UI portion of the client.
    public ChatClient(String uname,String servername) throws Exception {
        super(uname);  // set title for frame
        this.uname = uname;
        client  = new Socket(servername,9999);
        br = new BufferedReader( new InputStreamReader( client.getInputStream()) ) ;
        pw = new PrintWriter(client.getOutputStream(),true);
        pw.println(uname);  // send name to server
        buildInterface();//THis method call initializes the UI portion of the client.
     
    }
    
   /**
    * This method is where all the UI portion is made.All the buttons , TextArea are made on the constructor call.
    */
    public void buildInterface() {
    	
    	//Here all the above defined variables are initialized.
        btnSend = new JButton("Send");
        btnExit = new JButton("Exit");
        btnOnlineUsers = new JButton("See Online Users");
        taMessages = new JTextArea();
        taMessages.setRows(10);
        taMessages.setColumns(50);
        taMessages.setEditable(false);
        tfInput  = new JTextField(50);
        tfInputBuddy  = new JTextField(20);
        btnConnect = new JButton("Connect");
        
        JScrollPane sp = new JScrollPane(taMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp,"Center");
        JPanel up = new JPanel( new FlowLayout());
        up.add(tfInputBuddy);
        up.add(btnConnect);
        up.add(btnOnlineUsers);
        add(up,"North");
        
        JPanel bp = new JPanel( new FlowLayout());
        bp.add(tfInput);
        bp.add(btnSend);
        bp.add(btnExit);
        add(bp,"South");
        btnSend.addActionListener(this);
        btnExit.addActionListener(this);
        btnOnlineUsers.addActionListener(this);
        btnConnect.addActionListener(this);
        setSize(500,300);
        setVisible(true);
        pack();
    }
    
    /**
     * This method is called when an action is performed , such as button click of send button , commect button.
     * All the action listner are handled here.  
     */
    public void actionPerformed(ActionEvent evt) {
        
    	if(evt.getSource() == btnConnect){
    		if(tfInputBuddy.getText()!="" || tfInputBuddy.getText()!=null){
    			buddy=tfInputBuddy.getText();
    			pw.println("buddy:"+buddy);
    			new MessagesThread().start();  // create thread for listening for messages
    		}else{
    			JOptionPane.showMessageDialog(null, "Enter User to connect to");
    			return;
    		}
    		//btnConnect.setEnabled(false);
        }
    	if ( evt.getSource() == btnExit ) {
            pw.println("end");  // send end to server so that server know about the termination
            System.exit(0);
        } 
        if(evt.getSource() == btnSend){
            // send message to server
            pw.println("GET/"+this.uname+"/"+tfInput.getText()+"/"+buddy+"/HTTP 1.1");
            taMessages.append("me:"+tfInput.getText() + "\n");
            tfInput.setText("");
        }
        if(evt.getSource() == btnOnlineUsers){
            // send message to server
            pw.println("online");
        }
    }
    
    /**
     * This is the main method of the program.From here the program starts to run.
     * @param args
     */
    public static void main(String ... args) {
    
        // take username from user
        String name = JOptionPane.showInputDialog(null,"Enter your name :", "Username",
             JOptionPane.PLAIN_MESSAGE);
        if(name == null)
        	System.exit(0);
        String servername = "localhost";  
        try {
        	
            new ChatClient( name ,servername);
        } catch(Exception ex) {
            out.println( "Error --> " + ex.getMessage());
        }
        
    } // end of main
    
    /**
     *  Inner class for Messages Thread . Run method is called every time a new thread starts.
     * @author Akshay
     *
     */
    class  MessagesThread extends Thread {
        public void run() {
            String line;
            try {
                while(true) {
                    line = br.readLine();
                    taMessages.append(line + "\n");
                } // end of while
            } catch(Exception ex) {}
        }
    }
} //  end of client
