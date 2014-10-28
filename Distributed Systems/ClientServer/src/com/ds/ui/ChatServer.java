package com.ds.ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.util.*;
import java.net.*;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import static java.lang.System.out;
/**
 * 
 * @author Akshay
 * This class is the Chat Server. There will be only one server running on some port.In this program the port is 9999.
 * This class also has a UI component which will display all the logs.
 */
public class ChatServer extends JFrame  implements ActionListener {
	Set<String> users = new HashSet<String>();
	volatile Set<String>  buddySet = new HashSet<String>();
	Vector<HandleClient> clients = new Vector<HandleClient>();
	
	Map<String, HandleClient> clientMap= new HashMap<String, HandleClient>();
	JTextArea  serverMessages;
	JButton logout;
	public void process() throws Exception {
		ServerSocket server = new ServerSocket(9999, 10);
		out.println("Server Started...");
		//Here the UI component of the server is made
		serverMessages = new JTextArea();
		logout = new JButton("Logout");
		serverMessages.setRows(10);
		serverMessages.setColumns(50);
		serverMessages.setEditable(false);
		
		JScrollPane sp = new JScrollPane(serverMessages, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        add(sp,"Center");
        JPanel bp = new JPanel( new FlowLayout());
        bp.add(logout);
        add(bp,"South");
        logout.addActionListener(this);
        setSize(500,300);
        setVisible(true);
        pack();
		while (true) {
			Socket client = server.accept();
			HandleClient c = new HandleClient(client);
			clients.add(c);
		} // end of while
	}

	/**
	 * Here the logout action is performed , server UI closes and logsoff.
	 */
	public void actionPerformed(ActionEvent evt) {
        if ( evt.getSource() == logout ) {
              // send end to server so that server know about the termination
            System.exit(0);
        }
	}
	/**
	 * This is the main program of the server from where the Chat server starts.
	 * @param args
	 * @throws Exception
	 */
	public static void main(String... args) throws Exception {
		new ChatServer().process();
	} // end of main

	public void boradcast(String user, String message) {
		// send message to all connected users
		for (HandleClient c : clients)
			if (!c.getUserName().equals(user))
				c.sendMessage(user, message);
	}

	
	/**
	 * 
	 * @author Akshay
	 *This class handles the client messages.Here the list of connected users is maintained .
	 */
	class HandleClient extends Thread {
		String name = "";
		String to="";
		BufferedReader input;
		PrintWriter output;
		Socket client;
		boolean flagUser=true;
		boolean fU=true;
		boolean chatFlag=true;
		public HandleClient(Socket client) throws Exception {
			// get input and output streams
			input = new BufferedReader(new InputStreamReader(
					client.getInputStream()));
			output = new PrintWriter(client.getOutputStream(), true);
			// read name
			name = input.readLine();
			
			flagUser=users.add(name);
			
			if(flagUser)
				serverMessages.append("User with name "+name+" joined the chat"+"\n");
			if(flagUser==false){
				JOptionPane.showMessageDialog(null, name+" is already connected");
			}
			 // add to set
			this.client=client;
			clientMap.put(name, this);
			start();
		}

		public void sendMessage(String uname, String msg) {
			output.println(uname + ":" + msg);
		}

		public String getUserName() {
			return name;
		}

		//This run is called for every communication between the server and client.
		public void run() {
			String line;
			try {
				while (true) {
					line = input.readLine();
					//System.out.println(line);
					if(line.equals("online")){
						String message="";
						
						for(String us:users)
							message+=us+"\n";
						JOptionPane.showMessageDialog(null, message);

					}
					
					else{
						if (line.equals("end")) {
							System.out.println("Before : "+buddySet.toString());
							clients.remove(this);
							users.remove(name.trim());
							System.out.println("remove"+to);
							buddySet.remove(to);
							System.out.println("After : "+buddySet.toString());
							chatFlag=true;
							break;
						}
						 
						else{
							if(line.contains("GET")){
								serverMessages.append(line+ "\n");
								String spl[] =line.split("/");
								String from =spl[1];
								String to = spl[3];
								System.out.println("To :"+to+" From :"+from+" flag set :"+buddySet.size());
								
								if(chatFlag==true)
									clientMap.get(to).output.println(from+":"+spl[2]);
						}else{

							if(line.contains("buddy")){
								to=line.split(":")[1];
							}
							
							flagUser=buddySet.add(to); // add to set
							if(flagUser==false){
								JOptionPane.showMessageDialog(null, to+" is already connected");
								chatFlag=false;
							}
						}
							//serverMessages.append(from+" TO "+to+"--"+spl[1] + "\n");
							//boradcast(name, spl[1]);
						}
					}
					// method of outer class - send
											// messages to all
				} // end of while
			} // try
			catch (Exception ex) {
				ex.printStackTrace();
			}
		} // end of run()
	} // end of inner class

} // end of Server