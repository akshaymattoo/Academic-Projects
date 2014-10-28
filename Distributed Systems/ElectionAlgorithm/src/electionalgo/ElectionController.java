package test;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
/**
 * @author Akshay Mattoo 1000995551
 * Class ElectionController is where all the UI components are being made .The election processes are being initiated from here.
 */
public class ElectionController  extends javax.swing.JFrame implements ActionListener {

	//This is for the serialization and deserialization of the id's
		private static final long serialVersionUID = 1L;
		Thread[] threadP = new Thread[5];
		private ProcessCreation p1=null;
		private ProcessCreation p2 = null;
 	    private ProcessCreation p3 = null;
 	    private ProcessCreation p4 = null;
 	    private ProcessCreation p5 = null;
 	    //Ui components like button and textpanel are being declared here.
	    private JButton startElection;
	    private JButton assignPort;
	    private JButton crashButton;
	    private JButton restartButton;
	    private JButton exitButton;
	    private JComboBox<String> processList; 
	    private JTextArea output;
	    private String  pList[] = {"Process1","Process2","Process3","Process4","Process5"};
	    
	    
	    /**
	     * This is the constructor where process threads are initialised and interface method buildInterface is called. 
	     */
	    public ElectionController() {
	    	p1 = new ProcessCreation(1, 9000, this);
	 	    p2 = new ProcessCreation(2, 12000, this);
	 	    p3 = new ProcessCreation(3, 14000, this);
	 	    p4 = new ProcessCreation(4, 11000, this);
	 	    p5 = new ProcessCreation(5, 7000, this);
	 	    //this method call makes the UI for the messages
	        buildInterface();
	        for(int i=0; i<5;i++) {
	            threadP[i] = null;
	        }
	         
	    }
	    
	    
	    /**
	     * Method Name: buidInterface
	     * This method is for making the UI . All the buttons are made on display and the all the action listners for the buttons are set here.
	     */
	    private void buildInterface()
	    {
	    	startElection  = new JButton();
	    	assignPort = new JButton();
	    	processList = new JComboBox<String>(pList);
	    	crashButton  = new JButton();
	    	restartButton = new JButton();
	    	exitButton = new JButton();
	    	output = new JTextArea();
	    	output.setRows(10);
	    	output.setColumns(50);
	    	output.setEditable(false);
	    	//the main scroll panel is being set here 
	    	JScrollPane sp = new JScrollPane(output, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
	                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
	        add(sp,"Center");
	        //all the buttons on the Jpanel are set here
	        JPanel up = new JPanel( new FlowLayout());
	        up.add(assignPort);
	        up.add(startElection);
	        up.add(processList);
	        up.add(crashButton);
	        up.add(restartButton);
	        up.add(exitButton);
	        
	        add(up,"North");
	    	
	    	
	    	startElection.setText("Start Election");
	    	startElection.addActionListener(this);

	    	assignPort.setText("Assign Port");
	    	assignPort.addActionListener(this);
	    	
	    	crashButton.setText("Crash Process");
	    	crashButton.addActionListener(this);
	    	
	    	
	    	restartButton.setText("Restart Process");
	    	restartButton.addActionListener(this);
	    	
	    	exitButton.setText("Exit!!");
	    	exitButton.addActionListener(this);
	    	
	    	//the buttons are disabled here , so that they are not pressed before the operation happens
	    	startElection.setEnabled(false);
	    	processList.setEnabled(false);
	    	crashButton.setEnabled(false);
	    	restartButton.setEnabled(false);
	    	
	    	
	    	setSize(800,400);
	        setVisible(true);
	        pack();
	    }
	    
	    /**
	     * In this method all the event listners are being set according to the specific button that is pressed.
	     */
	    public void actionPerformed(ActionEvent evt) {
	    	
	    	//action on button pressed exit.The application closes
	    	if(evt.getSource() == exitButton){
	    		 System.exit(0);
	    	}
	    	
	    	//On click of this the election starts .
	    	if(evt.getSource() == startElection){
	    		int process  = (int) ((int) 1 + (Math.random() * (4 - 1)));
	    		
	    		p1.electionStart();
	    		
	    		processList.setEnabled(true);
	  	    	crashButton.setEnabled(true);
	  	    	restartButton.setEnabled(true);
	    		/*if(process==1)
	    			p1.electionStart();
	    		
	    		if(process==2)
	    			p2.electionStart();
	    		
	    		if(process==3)
		    		p3.electionStart();
	    		
	    		if(process==4)
		    		p4.electionStart();*/
	    		
	    		 
	    	}
	    	//Here the ports are being assigned .
	    	if(evt.getSource() == assignPort){
	    		threadP[0] = new Thread(p1);
	            threadP[0].start();
	            
	            threadP[1] = new Thread(p2);
	            threadP[1].start();
	            
	            
	            threadP[2] = new Thread(p3);
	            threadP[2].start();
	            
	            
	            threadP[3] = new Thread(p4);
	            threadP[3].start();
	            
	            
	            threadP[4] = new Thread(p5);
	            threadP[4].start();
	            
	            startElection.setEnabled(true);
	            
	    	}
	    	
	    	//Here the crash button is for crasing the process 
	    	if(evt.getSource() == crashButton){
	    		
	    		String processName =(String)processList.getSelectedItem();
	    		if(processName.equals("Process1"))
	    		{
	    			p1.crashProcess();
	                threadP[0].stop();
	                threadP[0] = null;
	    		}
	    		
	    		else if(processName.equals("Process2"))
	    		{
	    			p2.crashProcess();
	                threadP[1].stop();
	                threadP[1] = null;
	    		}
	    		
	    		
	    		else if(processName.equals("Process3"))
	    		{
	    			p3.crashProcess();
	                threadP[2].stop();
	                threadP[2] = null;
	    		}
	    		
	    		
	    		else if(processName.equals("Process4"))
	    		{
	    			p4.crashProcess();
	                threadP[3].stop();
	                threadP[3] = null;
	    		}
	    		
	    		else if(processName.equals("Process5"))
	    		{
	    			p5.crashProcess();
	                threadP[4].stop();
	                threadP[4] = null;
	    		}
	    		 
	    	}
	    	//restart button is to restart the already crashed process. 
	    	if(evt.getSource() == restartButton){
	    		
	    		String processName =(String)processList.getSelectedItem();
	    		if(processName.equals("Process1"))
	    		{
	    			threadP[0] = new Thread(p1);
    		        threadP[0].start();
    		        printOutput(1, "Process number1 has been requested to restart again");
    		        p1.electionStart();
	    		}
	    		
	    		else if(processName.equals("Process2"))
	    		{
	    		        threadP[1] = new Thread(p2);
	    		        threadP[1].start();
	    		        printOutput(2, "Process number2 has been requested to restart again");
	    		        p2.electionStart();
	    		}
	    		
	    		
	    		else if(processName.equals("Process3"))
	    		{
	    		        threadP[2] = new Thread(p3);
	    		        threadP[2].start();
	    		        printOutput(3, "Process number3 has been requested to restart again");
	    		        p3.electionStart();
	    		}
	    		
	    		
	    		else if(processName.equals("Process4"))
	    		{
	    			threadP[3] = new Thread(p4);
		            threadP[3].start();
		            printOutput(3, "Process number4 has been requested to restart again");
		            p4.electionStart();
	    		}
	    		
	    		else if(processName.equals("Process5"))
	    		{
		            threadP[4] = new Thread(p5);
		            threadP[4].start();
		            printOutput(5, "Process number5 has been requested to restart again");
		            p5.electionStart();
	    		}else{
	    			
	    		}
	    		
	    	}
	    }
	    
	    /**
	     * This method is responsible for printing the output on the Jpanel
	     * @param PID
	     * @param msg
	     */
	    public void printOutput(int PID, String msg) {
	    	 output.append(msg+"\n");
	    }

	    /**
	     * This is the main program from where the execution starts
	     * @param args
	     */
	    public static void main(String[] args) {
		
	    	new ElectionController();
		}
}
