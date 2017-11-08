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
        AscendinglyOrderedIntList flightNumbers = new AscendinglyOrderedIntList();
        AscendinglyOrderedPlaneList hangar = new AscendinglyOrderedPlaneList();

        for(int i = 0; i < run; i++){
            System.out.println("Please enter the runway " + (i + 1) + ": ");
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
            finished = processCommand(command, runways, runwayNames, flightNumbers, hangar);
        }
    }

    public static boolean processCommand(int command, ListArrayBasedPlus<Runway> runways, AscendinglyOrderedStringList runwayNames, AscendinglyOrderedIntList flightNumbers, AscendinglyOrderedPlaneList hangar)throws IOException{
        boolean wantToQuit = false;

        switch(command){
            case 1:
                newPlane(runways, runwayNames, flightNumbers);
                break;

            case 2:

                break;

            case 3:

                break;

            case 4:

                break;

            case 5:

                break;

            case 6:

                break;

            case 7:

                break;

            case 8:

                break;

            case 9:
                wantToQuit = true;
                break;
        }
        return wantToQuit;
    }

    public static void newPlane(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedStringList runwayNames, AscendinglyOrderedIntList flightNumbers)throws IOException{
        System.out.println("Please enter the flight number: ");
        Integer fn = Integer.parseInt(stdin.readLine());
        System.out.println(fn);
        while(flightNumbers.search(fn) < flightNumbers.size()){
            System.out.println("That flight number is already used. Please enter a new flight number: ");
            fn = Integer.parseInt(stdin.readLine());
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
        boolean found = false;
        for(int i = 0; !found && i < runways.size(); i++){
            if(s.equals(runways.get(i).getName())){
                found = true;
                runways.get(i).addPlane(new Plane(fn, s));
            }
        }
        System.out.println("A plane with flight number: " + fn + " has entered runway: " + s);
    }

    public static void printMenu(){
        System.out.println("Select from the following options: \n\t1. Plane enters the system.\n\t2. Plane takes off.\n\t3. Plane is allowed to re-enter a runway.\n\t4. Runway opens.\n\t5. Runway closes.\n\t6. Display info about planes waiting to take off.\n\t7. Display info about planes waiting to be allowed to re-enter a runway.\n\t8. Display number of planes who have taken off.\n\t9. End the program.");
    }
}