import java.io.*;

public class Driver {
    public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the airport simulator!  Please enjoy your time!");

        System.out.println("Please enter the number of runways:");
        int run = Integer.parseInt(stdin.readLine());
        System.out.println(run);

        ListArrayBasedPlus<Runway> runways = new ListArrayBasedPlus<>();
        AscendinglyOrderedList<String> runwayNames = new AscendinglyOrderedList<>();
        AscendinglyOrderedList<String> flightNumbers = new AscendinglyOrderedList<>();
        AscendinglyOrderedList<Plane> hangar = new AscendinglyOrderedList<>();
        ListArrayBasedPlus<Integer> info = new ListArrayBasedPlus<>(); //Will be used to hold other information
        info.add(0, 0); //Current runway to take off from
        info.add(1, 0); //Number of planes that have taken off

        for (int i = 0; i < run; i++) {
            System.out.println("Please enter the name of runway " + (i + 1) + ": ");
            String s = stdin.readLine();
            System.out.println(s);
            while (runwayNames.search(s) < runwayNames.size()) {
                System.out.println("That name is already used. Please enter a new name: ");
                s = stdin.readLine();
                System.out.println(s);
            }
            runwayNames.add(s);
            runways.add(runways.size(), new Runway(s));
        }

        boolean finished = false;
        while (!finished) {
            printMenu();
            int command = Integer.parseInt(stdin.readLine());
            System.out.println(command);
            finished = processCommand(command, runways, runwayNames, flightNumbers, hangar, info);
        }
    }

    public static boolean processCommand(int command, ListArrayBasedPlus<Runway> runways, AscendinglyOrderedList<String> runwayNames, AscendinglyOrderedList<String> flightNumbers, AscendinglyOrderedList<Plane> hangar, ListArrayBasedPlus<Integer> info) throws IOException {
        boolean wantToQuit = false;

        switch (command) {
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
                runwayClose(runways, hangar);
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

    public static void newPlane(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedList<String> runwayNames, AscendinglyOrderedList<String> flightNumbers) throws IOException {
        System.out.println("Please enter the flight number: ");
        String fn = stdin.readLine();
        System.out.println(fn);
        while (flightNumbers.search(fn) < flightNumbers.size()) {
            System.out.println("That flight number is already used. Please enter a new flight number: ");
            fn = stdin.readLine();
            System.out.println(fn);
        }
        flightNumbers.add(fn);

        System.out.println("Please enter the destination: ");
        String d = stdin.readLine();
        System.out.println(d);

        System.out.println("Please enter the runway name: ");
        String s = stdin.readLine();
        System.out.println(s);
        while (runwayNames.search(s) >= runwayNames.size()) {
            System.out.println("That is not a current runway. Please enter a new name: ");
            s = stdin.readLine();
            System.out.println(s);
        }

        boolean found = false;
        for (int i = 0; !found && i < runways.size(); i++) {
            if (s.equals(runways.get(i).getName())) {
                found = true;
                runways.get(i).addPlane(new Plane(fn, s, d));
            }
        }
        System.out.println("A plane with flight number: " + fn + " has entered runway: " + s);
    }

    public static void takeOff(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedList<Plane> hangar, ListArrayBasedPlus<Integer> info) throws IOException {
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

    public static void numberTakeOff(ListArrayBasedPlus<Integer> info) {
        System.out.println(info.get(1) + " planes have taken off.");
    }

    public static void waitingInfo(AscendinglyOrderedList<Plane> hangar) {
        if (hangar.isEmpty()) {
            System.out.println("There are no planes waiting to re-enter runways.");
        } else {
            System.out.println(hangar);
        }
    }

    public static void reEnter(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedList<Plane> hangar) throws IOException {
        if (hangar.isEmpty()) {
            System.out.println("There are no planes waiting to re-enter runways.");
        } else {
            System.out.println("Please enter the flight number: ");
            String fn = stdin.readLine();
            System.out.println(fn);
            Plane compare = new Plane(fn, "", "");
            int index = hangar.search(compare);
            while (index >= hangar.size()) {
                System.out.println("That flight number is not waiting. Please enter a new flight number: ");
                fn = stdin.readLine();
                System.out.println(fn);
                compare = new Plane(fn, "", "");
                index = hangar.search(compare);
            }
            Plane p = hangar.get(index);
            hangar.remove(index);
            for (int i = 0; i < runways.size(); i++) {
                if (runways.get(i).getName().equals(p.getRunway())) {
                    runways.get(i).addPlane(p);
                    System.out.println("Flight " + p.getFlightNumber() + " has re-entered runway " + runways.get(i).getName());
                }
            }
        }
    }

    public static void runwayOpen(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedList<String> runwayNames) throws IOException {
        for (int i = 0; i < 1; i++) {
            System.out.println("Enter the name of the new runway you want to open: ");
            String s = stdin.readLine();
            System.out.println(s);

            while (runwayNames.search(s) < runwayNames.size()) {
                System.out.println("That name is already used. Please enter a new name: ");
                s = stdin.readLine();
                System.out.println(s);
            }
            runwayNames.add(s);
            runways.add(runways.size(), new Runway(s));
            System.out.println("Runway " + s + " is now open.");
        }
    }

    public static void runwayClose(ListArrayBasedPlus<Runway> runways, AscendinglyOrderedList<Plane> hangar) throws IOException {
        if (runways.isEmpty()) {
            System.out.println("There are no open runways at the airport.");
        } else {
            System.out.println("Enter the name of the runway you want to close: ");
            String r = stdin.readLine();
            System.out.println(r);
            boolean found = false;
            Runway run = null;
            for (int i = 0; i < runways.size() && !found; i++) {
                if (runways.get(i).getName().equals(r)) {
                    found = true;
                    run = runways.get(i);
                    runways.remove(i);
                }
            }
            while (!found) {
                System.out.println("There is no runway by that name at the airport. Please enter a new name: ");
                r = stdin.readLine();
                System.out.println(r);
                for (int i = 0; i < runways.size() && !found; i++) {
                    if (runways.get(i).getName().equals(r)) {
                        found = true;
                        run = runways.get(i);
                    }
                }
            }
            while (!(run.isEmpty())) {
                Plane p = run.reassign();
                System.out.println("Enter new runway for flight " + p);
                String f = stdin.readLine();
                System.out.println(f);
                boolean succ = false;  //another found variable but found is already within this scope
                for (int i = 0; i < runways.size() && !succ; i++) {
                    if (runways.get(i).getName().equals(f)) {
                        succ = true;
                        p.setRunway(runways.get(i).getName());
                        runways.get(i).addPlane(p);
                    }
                }
                while (!succ) {
                    System.out.println("There is no runway by that name at the airport. Please enter a new name: ");
                    f = stdin.readLine();
                    System.out.println(f);
                    for (int i = 0; i < runways.size() && !succ; i++) {
                        if (runways.get(i).getName().equals(f)) {
                            succ = true;
                            p.setRunway(runways.get(i).getName());
                            runways.get(i).addPlane(p);
                        }
                    }
                }
            }
            for (int i = 0; i < hangar.size(); i++) {
                if (hangar.get(i).getRunway().equals(run.getName())) {
                    Plane p = hangar.get(i);
                    System.out.println("Enter new runway for flight " + p);
                    String f = stdin.readLine();
                    System.out.println(f);
                    boolean succ = false;  //another found variable but found is already within this scope
                    for (int j = 0; j < runways.size() && !succ; i++) {
                        if (runways.get(j).getName().equals(f)) {
                            succ = true;
                            p.setRunway(runways.get(j).getName());
                        }
                    }
                    while (!succ) {
                        System.out.println("There is no runway by that name at the airport. Please enter a new name: ");
                        f = stdin.readLine();
                        System.out.println(f);
                        for (int j = 0; j < runways.size() && !succ; i++) {
                            if (runways.get(j).getName().equals(f)) {
                                succ = true;
                                p.setRunway(runways.get(j).getName());
                            }
                        }
                    }
                }
            }
        }
    }

    public static void planesWaiting(ListArrayBasedPlus<Runway> runways) {
        if (runways.isEmpty()) {
            System.out.println("There are no runways at this airport.");
        } else {
            for (int i = 0; i < runways.size(); i++) {
                System.out.println("These planes are waiting for takeoff on runway " + runways.get(i).getName() + ": ");
                System.out.println("Flight " + runways.get(i).getPlanes() + ".");

            }
        }
    }


    public static void printMenu() {
        System.out.println("Select from the following options: \n\t1. Plane enters the system.\n\t2. Plane takes off.\n\t3. Plane is allowed to re-enter a runway.\n\t4. Runway opens.\n\t5. Runway closes.\n\t6. Display info about planes waiting to take off.\n\t7. Display info about planes waiting to be allowed to re-enter a runway.\n\t8. Display number of planes who have taken off.\n\t9. End the program.");
    }
}