
package test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.StringTokenizer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * @author Akshay Mattoo 1000995551
 * This class is where all the main logic is being handled .all the main procession is being handled in this class. 
 * 
 */
public class ProcessCreation implements Runnable{

    /**
     * This is the constructor which takes the id , timer and the process as argument
     * @param id
     * @param tOut
     * @param sElection
     */
    public ProcessCreation(int id, long tOut, ElectionController sElection) {
        this.processId = id;
        this.prtNumber = pOffset+id;
        this.outTime = tOut;
        this.startElection = sElection;
    }
     
    /**
     * This is the run method which is responsible for running the threads
     */
    public void run() {
        try {
            this.sSocket = new ServerSocket(prtNumber);
            //startElection.printOutput(processId, "Process Number:"+processId+" has been assigned portNumber :"+prtNumber);
            while(true) {
                tokenHandler(sSocket.accept());
            }
        } catch (Exception ex) {
        	ex.printStackTrace();
        }
        
    }
    
    /**
     * MethodName : socketTimerhandler
     * This method is called when we have to handle the timer for the socket
     */
    private void socketTimerhandler()
    {
    	try{
	    	String sentence;
	        String modifiedSentence;
	        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));
	
	        Socket clientSocket = new Socket("localhost", 6789);
	       // DataOutputStream outToServer = new DataOutputStream(clientSocket.getOutputStream());
	        BufferedReader inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
	
	        sentence = inFromUser.readLine();
	       // outToServer.writeBytes(sentence + '\n');
	        modifiedSentence = inFromServer.readLine();
	        System.out.println(modifiedSentence);
	        clientSocket.close();
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    /**
     * This method is responsible for handling the tokens , which come from thread for which the process is being created .
     * @param socket
     * @throws InterruptedException
     */
    private void tokenHandler(Socket socket) throws InterruptedException {

        BufferedReader br = null;
        try {
            timerStop();
            Thread.sleep(1000);
            //reading the inputstream using the bufferreader .
            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String token = br.readLine();
            br.close();
            socket.close();
           //splitting the token using the stringtokenizer
            StringTokenizer stringTokenizer = new StringTokenizer(token);
            String value = stringTokenizer.nextToken();
            //string token is being matched with the elect message from the stream
                if(value.equals("elect")){
                	forElect(stringTokenizer,token);
                }
                //string token is being matched with the coordinator message from the stream
                if(value.equals("coord")){
                	forCoordinator(stringTokenizer,token);
                }
              //string token is being matched with the alive message from the stream
                if(value.equals("alive")){
                	forAlive(stringTokenizer);
                }
                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                           
        } catch (IOException ie) {
        	ie.printStackTrace();
        } 
    }
    
    /**
     * helper method for alive
     * @param stringTokenizer
     */
    private void forAlive(StringTokenizer stringTokenizer)
    {

        try {
            if(!((Integer.parseInt(stringTokenizer.nextToken()) > processId))) {
                skAlive();
                timerStart();
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    
    }
    
    /**
     * Helper method for election
     * @param stringTokenizer
     * @param token
     */
    private void forElect(StringTokenizer stringTokenizer , String token)
    {
    	
        startElection.printOutput(processId, "Message for the election: ["+token+"]");
        try {
        	//here the match for the processid is being done
            if(Integer.parseInt(stringTokenizer.nextToken()) == processId) {
                int[] candidates = new int[6];
                candidates[0] = processId;
                int counter = 1;
                while(stringTokenizer.hasMoreTokens()) {
                    candidates[counter] = Integer.parseInt(stringTokenizer.nextToken());
                    counter++;
                }
                checkForResults(candidates);
            }
            else {
            	//message is being sent to the successor
                sendMessageToSucessor(token+" "+processId, processId+1);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    	
    }
    
    /**
     * Helper method for coordinator 
     * @param stringTokenizer
     * @param token
     */
    private void forCoordinator(StringTokenizer stringTokenizer , String token)
    {
        
        try {
            if(Integer.parseInt(stringTokenizer.nextToken()) == processId) {
                coordinatorFlag = true;
                startCoordinator();
            }
            else {
                if(coordinatorFlag) {
                    
                    coordinatorFlag = false;
                    coordinatorStopMessage();
                }
                // message is being sent to the successor
                sendMessageToSucessor(token, processId+1);
            }
        } catch(Exception ex) {
            ex.printStackTrace();
        }
    
    }
    
    /**
     * MethodName : crashProcess
     * This is the method is being called when a process is being told to crash on the key press of crash button on UI 
     */
    public void crashProcess() {
        timerStop();
        try {
            sSocket.close();
            if(coordinatorFlag) {
                coordinatorStopMessage();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        startElection.printOutput(processId, "Process number "+processId+" has been crashed");
    }
     
    /**
     * MethodName : electionStart
     * This method is called when election is issued to start. This method is called when elect is pressed 
     */
    public void electionStart() {
        sendMessageToSucessor("elect  "+processId, processId+1);
        
    }
     
    /**
     * MethodName : skAlive
     * This is method is called for checking the alive processes in the ring
     */
    public void skAlive() {
        sendMessageToSucessor("alive "+processId, processId+1);
    }
    
    /**
     * MethodName checkForResults
     * This method is called when to check the 
     * @param processes
     */
    private void checkForResults(int[] processes) {
        int maxValue = processes[0];
        for(int i = 1; i<processes.length; i++) {
            if(processes[i]>maxValue) {
                maxValue = processes[i];
            }
        }
        tokenCoordinator(maxValue);
    }
    
    private void tokenCoordinator(int cd) {
        sendMessageToSucessor("coord "+cd+" "+processId, processId+1);
    }
   
    private void startCoordinator() {
        startElection.printOutput(processId, "Coordinator after election is [ "+ processId+ "]");
        aliveTimer = new Timer();
        aliveTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                sendMessageToSucessor("alive 0", processId+1);
            }
        }, 0, 4000);
    }
    
    private void coordinatorStopMessage() {
        aliveTimer.cancel();
    }
   
    private void timeRestart() {
        if(threadTimer != null) {
            timerStop();
        }
        timerStart();
    }
    
    private void timerStart() {
        if(threadTimer == null) {
            threadTimer = new Thread() {
                @Override
                public void run() {
                    long startTime = new Date().getTime();
                    while(true) {
                        long currentTime = new Date().getTime();
                        if((currentTime - startTime) >= outTime) {
                            electionStart();
                            timeRestart();
                        }
                    }
                }
            };
            threadTimer.start();
        }
    }
    
    /**
     * This method is being called when the process timer is to be called.
     */
    private void timerStop() {
        if(threadTimer != null) {
            threadTimer.stop();
            threadTimer = null;
        }
    }
     
    /**
     * This method is being called when a message to the successor is to be sent . This is where the message among the ring are being circulated.
     * this message is called for the message sent to the coordinator.
     * @param message
     * @param SPID
     */
    private void sendMessageToSucessor(String message, int SPID) {
        if(SPID>6) {
            SPID = SPID - 6;
        }
        try {
        	int offsetId = pOffset+SPID;
            Socket socket = new Socket(hostname, offsetId);
            PrintWriter out = new PrintWriter(socket.getOutputStream());
            out.println(message);
            out.flush();
            out.close();
            socket.close();
            if(((new StringTokenizer(message).nextToken()).equals("coord"))) {
                startElection.printOutput(processId, "New Coordinator elected after election is ------:> "+message.split(" ")[1]);
                //System.out.println("New Coordinator elected is: "+token);
            }
            
             
        } catch(Exception ex) {
            sendMessageToSucessor(message, SPID+1);
        }
    }
   
    //All the variables are being declared here .These variables are being used in the whole program
    int processId; 
    String hostname = "127.0.0.1"; 
    int prtNumber; 
    long outTime; 
    Thread threadTimer = null;
    Timer aliveTimer = null;
    ElectionController startElection = null;
    int pOffset = 10079; 
    ServerSocket sSocket = null; 
    boolean coordinatorFlag = false;
}
