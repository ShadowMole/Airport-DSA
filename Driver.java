import java.io.*;
public class Driver{
    public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));
    public static void main(String[] args)throws IOException{
        System.out.println("Welcome to the airport simulator!  Please enjoy your time!");

        System.out.println("Please enter the number of runways:");
        int run = Integer.parseInt(stdin.readLine());
        System.out.println(run);

        ListArrayBasedPlus<Runway> runways = new ListArrayBasedPlus<>();
        AscendinglyOrderedStringList runwayNames = new AscendinglyOrderedStringList();
        AscendinglyOrderedStringList flightNumbers = new AscendinglyOrderedStringList();
        AscendinglyOrderedPlaneList hangar = new AscendinglyOrderedPlaneList();
        ListArrayBasedPlus<Integer> info = new ListArrayBasedPlus<>(); //Will be used to hold other information
        info.add(0,0); //Current runway to take off from
        info.add(1,0); //Number of planes that have taken off

        for(int i = 0; i < run; i++){
            System.out.println("Please enter the name of runway " + (i + 1) + ": ");
            String s = stdin.readLine();
            System.out.println(s);
            while(runwayNames.search(s) < runwayNames.size()){
                System.out.println("That name is already used. Please enter a new name: ");
                s = stdin.readLine();
                System.out.println(s);
            }
            runwayNames.add(s);
            runways.add(runways.size(), new Runway(s));
        }

        boolean finished = false;
        while (! finished) {
            printMenu();
            int command = Integer.parseInt(stdin.readLine());
            System.out.println(command);
            finished = processCommand(command, runways, runwayNames, flightNumbers, hangar, info);
        }
    }

    public static boolean processCommand(int command, ListArrayBasedPlus<Runway> runways, AscendinglyOrderedStringList runwayNames, AscendinglyOrderedStringList flightNumbers, AscendinglyOrderedPlaneList hangar, ListArrayBasedPlus<Integer> info)throws IOException{
        boolean wantToQuit = false;

        switch(command){
            case 1:
                newPlane(runways, runwayNames, flightNumbers);
                break;

            case 2:
                takeOff(runways, hangar, info);
                break;

            case 3:
                reEnter(runways, hangar);
                break;

            case 4:
            	runwayOpen(runways, runwayNames);
                break;

            case 5:
            	runwayClose(runways, runwayNames, info, hangar, flightNumbers);
                break;

            case 6:
            	planesWaiting(runways);
                break;

            case 7:
                waitingInfo(hangar);
                break;

            case 8:
                numberTakeOff(info);
                break;

            case 9:
                wantToQuit = true;
                break;
        }
        return wantToQuit;
    }

    public static void newPlane(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedStringList runwayNames, AscendinglyOrderedStringList flightNumbers)throws IOException{
        System.out.println("Please enter the flight number: ");
        String fn = stdin.readLine();
        System.out.println(fn);
        while(flightNumbers.search(fn) < flightNumbers.size()){
            System.out.println("That flight number is already used. Please enter a new flight number: ");
            fn = stdin.readLine();
            System.out.println(fn);
        }
        flightNumbers.add(fn);

        System.out.println("Please enter the runway name: ");
        String s = stdin.readLine();
        System.out.println(s);
        while(runwayNames.search(s) >= runwayNames.size()){
            System.out.println("That is not a current runway. Please enter a new name: ");
            s = stdin.readLine();
            System.out.println(s);
        }

        System.out.println("Please enter the destination: ");
        String d = stdin.readLine();
        System.out.println(d);

        boolean found = false;
        for(int i = 0; !found && i < runways.size(); i++){
            if(s.equals(runways.get(i).getName())){
                found = true;
                runways.get(i).addPlane(new Plane(fn, s, d));
            }
        }
        System.out.println("A plane with flight number: " + fn + " has entered runway: " + s);
    }

    public static void takeOff(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedPlaneList hangar, ListArrayBasedPlus<Integer> info)throws IOException {
        boolean noPlanes = false;
        int runwayCheck = 0; //Number of Runways Checked
        while (runways.get(info.get(0)).isEmpty() && !noPlanes) {
            int temp = info.get(0);
            info.remove(0);
            info.add(0, (temp + 1) % runways.size());
            runwayCheck++;
            if (runwayCheck == runways.size() && runways.get(info.get(0)).isEmpty()) {  //If we have checked all runways and no planes were found
                noPlanes = true;
            }
        }
        if (noPlanes) {
            System.out.println("No plane on any runway!");
        } else {
            Plane p = runways.get(info.get(0)).takeOff();
            System.out.println("Is Flight " + p.getFlightNumber() + " cleared for take off? (Y/N)");
            String s = stdin.readLine();
            System.out.println(s);

            if (s.equals("Y")) {
                System.out.println("Flight " + p.getFlightNumber() + " has now taken off from runway " + runways.get(info.get(0)).getName());
                int temp = info.get(1);
                info.remove(1);
                info.add(1, (temp + 1));
            } else {
                hangar.add(p);
                System.out.println("Flight " + p.getFlightNumber() + " is now waiting to be allowed to re-enter a runway.");
            }
            int temp = info.get(0);
            info.remove(0);
            info.add(0, (temp + 1) % runways.size());
        }
    }

    public static void numberTakeOff(ListArrayBasedPlus<Integer> info){
        System.out.println(info.get(1) + " planes have taken off.");
    }

    public static void waitingInfo(AscendinglyOrderedPlaneList hangar){
        if(hangar.isEmpty()){
            System.out.println("There are no planes waiting to re-enter runways.");
        }else {
            System.out.println(hangar);
        }
    }

    public static void reEnter(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedPlaneList hangar)throws IOException{
        if(hangar.isEmpty()){
            System.out.println("There are no planes waiting to re-enter runways.");
        }else {
            System.out.println("Please enter the flight number: ");
            String fn = stdin.readLine();
            System.out.println(fn);
            int index = hangar.search(fn);
            while (index >= hangar.size()) {
                System.out.println("That flight number is not waiting. Please enter a new flight number: ");
                fn = stdin.readLine();
                System.out.println(fn);
                index = hangar.search(fn);
            }
            Plane p = hangar.get(index);
            hangar.remove(index);
            for(int i = 0; i < runways.size(); i++){
                if(runways.get(i).getName().equals(p.getRunway())){
                    runways.get(i).addPlane(p);
                    System.out.println("Flight " + p.getFlightNumber() + " has re-entered runway " + runways.get(i).getName());
                }
            }
        }
    }
    
    public static void runwayOpen(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedStringList runwayNames) throws IOException
    {
    	for(int i = 0; i < 1; i++)
    	{
    		System.out.println("Enter the name of the new runway you want to open: ");
            String s = stdin.readLine();
            System.out.println(s);        
            
            while(runwayNames.search(s) < runwayNames.size()){
                System.out.println("That name is already used. Please enter a new name: ");
                s = stdin.readLine();
                System.out.println(s);
            }
            runwayNames.add(s);
            runways.add(runways.size(), new Runway(s));
            System.out.println("Runway " + s + " is now open.");
    	}    	
    }
    
    public static void runwayClose(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedStringList runwayNames,ListArrayBasedPlus<Integer> info, AscendinglyOrderedPlaneList hangar, AscendinglyOrderedStringList flightNumbers) throws IOException
    {
    	if(runways.isEmpty())
    	{
    		System.out.println("There are no open runways at the airport.");
    	}
    	else
    	{
    		boolean succ = true;
        	boolean stop = false;
        	
    		System.out.println("Enter the name of the new runway you want to close: ");
            String r = stdin.readLine();
            System.out.println(r);            
            while(runwayNames.search(r) >= runwayNames.size()){
                System.out.println("There is no runway by that name at the airport. Please enter a new name: ");
                r = stdin.readLine();
                System.out.println(r);
            } 
            
            for(int i = 0; i < runways.size() && stop == false; i++)
        	{
        		if(runways.get(i).getName().equals(r))
        		{
        			stop = true;
        			succ = true;       			    			   				
        		}
        		else
        		{
        			succ = false;
        		}
        		    		
        	}
        	if(succ)
        	{
        		for(int k = 0; k < runways.size(); k++)
        		{
        			System.out.println("Enter runway for plane " + flightNumbers.get(info.get(k)));
        			String f = stdin.readLine();
                    System.out.println(f);
                    if(runwayNames.search(f) >= runwayNames.size())
                    {
                    	System.out.println("No such runway!");
                    }
                    else if(runwayNames.search(f) < runwayNames.size())
                    {
                    	System.out.println("This is the runway that is closing!");
                    }
                    else
                    {
                    	System.out.println(runways.get(k));
                    }
        		}
        	}  
        	else
        	{
        		System.out.println("There is no such Runway.");
        	}

            
    	}
    }
    
    public static void planesWaiting(ListArrayBasedPlus<Runway> runways)
    {
    	if(runways.isEmpty())
    	{
    		System.out.println("There are no runways at this airport.");
    	}
    	else
    	{
    		for(int i = 0; i < runways.size(); i++)
    		{
    			System.out.println("These planes are waiting for takeoff on runway " + runways.get(i).getName() + ": ");
    			System.out.println("Flight " + runways.get(i).getPlanes() + ".");
    			
    		}
    	}
    }

    public static void printMenu(){
        System.out.println("Select from the following options: \n\t1. Plane enters the system.\n\t2. Plane takes off.\n\t3. Plane is allowed to re-enter a runway.\n\t4. Runway opens.\n\t5. Runway closes.\n\t6. Display info about planes waiting to take off.\n\t7. Display info about planes waiting to be allowed to re-enter a runway.\n\t8. Display number of planes who have taken off.\n\t9. End the program.");
    }
}