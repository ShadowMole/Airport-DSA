import java.io.*;

public class Driver {
    public static BufferedReader stdin = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        System.out.println("Welcome to the airport simulator!  Please enjoy your time!");

        System.out.println("Please enter the number of runways:");
        int run = Integer.parseInt(stdin.readLine());
        System.out.println(run);

        AscendinglyOrderedList<Plane> planes = new AscendinglyOrderedList<>();
        ListArrayBasedPlus<Runway> runways = new ListArrayBasedPlus<>();
        ListArrayBasedPlus<Integer> info = new ListArrayBasedPlus<>(); //Will be used to hold other information
        info.add(0, 1); //Current runway to take off from
        info.add(1, 0); //Number of planes that have taken off
        info.add(2, 0); //Number of open runways
        info.add(3, 0); //Number of planes waiting to reenter runways
        info.add(4, 0); //Number of planes waiting to take off


        for (int i = 0; i < run; i++) {
            System.out.println("Please enter the name of runway " + (i + 1) + ": ");
            String s = stdin.readLine();
            System.out.println(s);
            boolean flag = false;
            for(int j = 0; j < runways.size() && !flag;j++) {
                if(s.equals(runways.get(j).getName())){
                    flag = true;
                }
            }
            while(flag){
                flag =false;
                System.out.println("That name already exists! Please enter a new name: ");
                s = stdin.readLine();
                System.out.println(s);
                for(int j = 0; j < runways.size() && !flag;j++) {
                    if(s.equals(runways.get(j).getName())){
                        flag = true;
                    }
                }
            }
            runways.add(runways.size(), new Runway(s, runways.size() + 1));
            int temp = info.get(2);
            info.remove(2);
            info.add(2, temp + 1);
        }

        boolean finished = false;
        while (!finished) {
            printMenu();
            int command = Integer.parseInt(stdin.readLine());
            System.out.println(command);
            finished = processCommand(command, planes, runways, info);
        }
    }

    public static boolean processCommand(int command, AscendinglyOrderedList<Plane> planes, ListArrayBasedPlus<Runway> runways, ListArrayBasedPlus<Integer> info) throws IOException {
        boolean wantToQuit = false;

        switch (command) {
            case 1:
                newPlane(planes, runways, info);
                break;

            case 2:
                takeOff(planes, runways, info);
                break;

            case 3:
                reEnter(planes, runways, info);
                break;

            case 4:
                runwayOpen(runways, info);
                break;

            case 5:
                runwayClose(planes, runways, info);
                break;

            case 6:
                planesWaiting(planes, runways, info);
                break;

            case 7:
                waitingInfo(planes, runways, info);
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

    public static void newPlane(AscendinglyOrderedList<Plane> planes, ListArrayBasedPlus<Runway> runways, ListArrayBased<Integer> info) throws IOException {
        System.out.println("Please enter the flight number: ");
        String fn = stdin.readLine();
        System.out.println(fn);
        boolean exists = false;
        for(int i = 0; i < planes.size() && !exists; i++) {
            if(fn.equals(planes.get(i).getFlightNumber())) {
                exists = true;
            }
        }
        while (exists) {
            System.out.println("That flight number is already used. Please enter a new flight number: ");
            fn = stdin.readLine();
            System.out.println(fn);
            for(int i = 0; i < planes.size() && !exists; i++) {
                if(fn.equals(planes.get(i).getFlightNumber())) {
                    exists = true;
                }
            }
        }

        System.out.println("Please enter the destination: ");
        String d = stdin.readLine();
        System.out.println(d);

        System.out.println("Please enter the runway name: ");
        String s = stdin.readLine();
        System.out.println(s);
        boolean flag = false;
        int order = 0;
        for(int i = 0; i < runways.size() && !flag; i++){
            if(s.equals(runways.get(i).getName()) && runways.get(i).getActive()){
                flag = true;
                order = runways.get(i).getOrder();
            }
        }
        while (!flag) {
            System.out.println("That runway does not exist. Please enter a new runway name: ");
            s = stdin.readLine();
            System.out.println(s);
            for(int i = 0; i < runways.size() && !flag; i++){
                if(s.equals(runways.get(i).getName()) && runways.get(i).getActive()){
                    flag = true;
                    order = runways.get(i).getOrder();
                }
            }
        }

        Plane p = new Plane(fn, order, d);
        if(planes.isEmpty()){
            planes.add(0, p);
            p.setOrder(1);
            System.out.println("A plane with flight number: " + fn + " has entered runway: " + s);
        }else {
            int index = planes.search(p);
            if (index >= planes.size()) {
                index -= (2 * planes.size());
                planes.add(index, p);
                p.setOrder(1);
                System.out.println("A plane with flight number: " + fn + " has entered runway: " + s);
            } else {
                boolean sFlag = false;
                for (int i = index; i < planes.size() && !sFlag; i++) {
                    if (p.getRunway() == planes.get(i).getRunway()) {
                    } else {
                        planes.add(i, p);
                        if (i == 0 || planes.get(i - 1).getOrder() == 0 || planes.get(i - 1).getRunway() != p.getRunway()) {
                            p.setOrder(1);
                        } else {
                            p.setOrder(planes.get(i - 1).getOrder() + 1);
                        }
                        sFlag = true;
                        System.out.println("A plane with flight number: " + fn + " has entered runway: " + s);
                    }
                }
                if(!sFlag){
                    p.setOrder(planes.get(planes.size() - 1).getOrder() + 1);
                    planes.add(planes.size(), p);
                    System.out.println("A plane with flight number: " + fn + " has entered runway: " + s);
                }
            }
        }
        int temp = info.get(4);
        info.remove(4);
        info.add(4, (temp + 1));
    }

    public static void takeOff(AscendinglyOrderedList<Plane> planes, ListArrayBasedPlus<Runway> runways, ListArrayBasedPlus<Integer> info) throws IOException {
        if(info.get(4) == 0){
            System.out.println("No plane on any runway!");
        }else {
            int index = planes.search(new Plane("", info.get(0), ""));
            if (index < 0 || index >= planes.size() || !(runways.get(info.get(0))).getActive()) {
                int temp = info.get(0);
                info.remove(0);
                info.add(0, (temp + 1) % runways.size());
                index = planes.search(new Plane("", info.get(0), ""));
            }
            while (index < 0 || index >= planes.size() || !(runways.get(info.get(0))).getActive()) {
                int temp = info.get(0);
                info.remove(0);
                info.add(0, (temp + 1) % runways.size());
                index = planes.search(new Plane("", info.get(0), ""));
                System.out.println("While 1" + index);
            }
            Plane p = planes.get(index);
            planes.remove(index);
            System.out.println("Is Flight " + p.getFlightNumber() + " cleared for take off? (Y/N)");
            String s = stdin.readLine();
            System.out.println(s);
            p.setOrder(0);
            if (s.equals("Y")) {
                System.out.println("Flight " + p.getFlightNumber() + " has now taken off from runway " + runways.get(info.get(0)).getName());
                int temp = info.get(1);
                info.remove(1);
                info.add(1, (temp + 1));
                p.setRunway(0);
                planes.add(0,p);
            } else {
                System.out.println("Flight " + p.getFlightNumber() + " is now waiting to be allowed to re-enter a runway.");
                int newIndex = planes.search(p);
                System.out.println("While 2" + newIndex);
                if (newIndex >= planes.size()) {
                    newIndex -= (2 * planes.size());
                    newIndex--;
                }
                System.out.println("While 2" + newIndex);

                while (newIndex >= 0 && planes.get(newIndex).getRunway() == p.getRunway() && planes.get(newIndex).getOrder() != 0) {
                    newIndex--;
                    System.out.println("While 2" + newIndex);
                }
                planes.add(newIndex + 1, p);
                int temp = info.get(3);
                info.remove(3);
                info.add(3, (temp + 1));
            }
            int temp = info.get(0);
            info.remove(0);
            info.add(0, (temp + 1) % runways.size());
            temp = info.get(4);
            info.remove(4);
            info.add(4, (temp - 1));
        }
    }

    public static void numberTakeOff(ListArrayBasedPlus<Integer> info) {
        System.out.println(info.get(1) + " planes have taken off.");
    }

    public static void waitingInfo(AscendinglyOrderedList<Plane> planes, ListArrayBasedPlus<Runway> runways, ListArrayBasedPlus<Integer> info) {
        if (info.get(3) == 0) {
            System.out.println("There are no planes waiting to reenter runways.");
        } else {
            for (int i = 0; i < runways.size(); i++) {
                if(runways.get(i).getActive()) {
                    int index = planes.search(new Plane("", runways.get(i).getOrder(), ""));
                    if(index < planes.size()){
                        while(index > 0 && planes.get(index - 1).getRunway() == runways.get(i).getOrder()){
                            index--;
                        }
                        boolean end = false;
                        while(!end){
                            if(index >= planes.size() || planes.get(index).getOrder() != 0 || planes.get(index).getRunway() != runways.get(i).getOrder()){
                                end = true;
                            }else{
                                System.out.println(planes.get(index));
                                index++;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void reEnter(AscendinglyOrderedList<Plane> planes, ListArrayBased<Runway> runways, ListArrayBased<Integer> info) throws IOException {
        if(info.get(3) == 0){
            System.out.println("There are no planes waiting to re-enter runways.");
        } else {
            System.out.println("Please enter the flight number: ");
            String fn = stdin.readLine();
            System.out.println(fn);
            int index = -1;
            for(int i = 0; i < planes.size(); i++){
                if(planes.get(i).getFlightNumber().equals(fn) && planes.get(i).getRunway() != 0 && planes.get(i).getOrder() == 0){
                    index = i;
                }
            }
            while (index == -1) {
                System.out.println("That flight number is not waiting. Please enter a new flight number: ");
                fn = stdin.readLine();
                System.out.println(fn);
                for(int i = 0; i < planes.size(); i++){
                    if(planes.get(i).getFlightNumber().equals(fn) && planes.get(i).getRunway() != 0 && planes.get(i).getOrder() == 0){
                        index = i;
                    }
                }
            }
            Plane p = planes.get(index);
            planes.remove(index);
            int i = index;
            while(i < runways.size() && planes.get(i).getRunway() == p.getRunway()) {
                i++;
            }
            planes.add(i, p);
            if(planes.get(i - 1).getRunway() == p.getRunway()){
                p.setOrder(planes.get(i- 1).getOrder() + 1);
            }else{
                p.setOrder(1);

            }
            String runway = "";
            boolean end = false;
            for(int j = 0; !end; j++){
                if(runways.get(j).getOrder() == p.getRunway()){
                    runway = runways.get(j).getName();
                    end = true;
                }
            }
            System.out.println("Flight " + p.getFlightNumber() + " has re-entered runway " + runway);
            int temp = info.get(3);
            info.remove(3);
            info.add(3, (temp - 1));
        }
    }

    public static void runwayOpen(ListArrayBasedPlus<Runway> runways, ListArrayBased<Integer> info) throws IOException {
        System.out.println("Enter the name of the new runway you want to open: ");
        String s = stdin.readLine();
        System.out.println(s);
        boolean exists = false;
        for(int j = 0; j < runways.size() && !exists; j++){
            if(runways.get(j).getName().equals(s)){
                exists = true;
            }
        }
        while (exists) {
            System.out.println("That name is already used. Please enter a new name: ");
            s = stdin.readLine();
            System.out.println(s);
            exists = false;
            for(int i = 0; i < runways.size() && !exists; i++){
                if(runways.get(i).getName().equals(s)){
                    exists = true;
                }
            }
        }
        runways.add(runways.size(), new Runway(s, runways.get(runways.size() - 1).getOrder() + 1));
        System.out.println("Runway " + s + " is now open.");
        int temp = info.get(2);
        info.remove(2);
        info.add(2, temp + 1);
}

    public static void runwayClose(AscendinglyOrderedList<Plane> planes, ListArrayBasedPlus<Runway> runways, ListArrayBased<Integer> info) throws IOException {
        if (info.get(2) == 0) {
                System.out.println("There are no open runways at the airport.");
            } else {
                System.out.println("Enter the name of the runway you want to close: ");
                String r = stdin.readLine();
                System.out.println(r);
                boolean found = false;
                int run = -1;
                for (int i = 0; i < runways.size() && !found; i++) {
                    if (runways.get(i).getName().equals(r)) {
                        found = true;
                        run = runways.get(i).getOrder();
                        runways.get(i).setInactive();
                    }
                }
                while (!found) {
                    System.out.println("There is no runway by that name at the airport. Please enter a new name: ");
                    r = stdin.readLine();
                    System.out.println(r);
                    for (int i = 0; i < runways.size() && !found; i++) {
                        if (runways.get(i).getName().equals(r)) {
                            found = true;
                            run = runways.get(i).getOrder();
                            runways.get(i).setInactive();
                        }
                    }
                }
            int index = planes.search(new Plane("", run, ""));
            if(index <= planes.size()){
                boolean beginning = false;
                while(!beginning){
                    if(planes.get(index - 1).getRunway() !=  run){
                        beginning = true;
                    }else{
                        index--;
                    }
                }
                boolean end = false;
                while(!end){
                    if(planes.get(index).getRunway() != run){
                        end = true;
                    }else {
                        Plane p = planes.get(index);
                        planes.remove(index);
                        int newIndex = -1;
                        System.out.println("Enter the name of the runway you want to close: ");
                        String n = stdin.readLine();
                        System.out.println(n);
                        boolean sfound = false;
                        int srun = -1;
                        for (int i = 0; i < runways.size() && !sfound; i++) {
                            if (runways.get(i).getName().equals(n)) {
                                sfound = true;
                                srun = runways.get(i).getOrder();
                                runways.get(i).setInactive();
                            }
                        }
                        while (!sfound) {
                            System.out.println("There is no runway by that name at the airport. Please enter a new name: ");
                            n = stdin.readLine();
                            System.out.println(n);
                            for (int i = 0; i < runways.size() && !sfound; i++) {
                                if (runways.get(i).getName().equals(n)) {
                                    sfound = true;
                                    srun = runways.get(i).getOrder();
                                    runways.get(i).setInactive();
                                }
                            }
                        }
                        p.setRunway(srun);
                        newIndex = planes.search(p);
                        if (newIndex <= planes.size()) {
                            boolean place = false;
                            if (p.getOrder() == 0){
                                while(!place){
                                    if(planes.get(newIndex).getOrder() != 0 && planes.get(newIndex).getRunway() == run){
                                        newIndex--;
                                    }else{
                                        planes.add(newIndex, p);
                                        place = true;
                                    }
                                }
                            }else{
                                while(!place){
                                    if(planes.get(newIndex).getRunway() == run){
                                        newIndex++;
                                    }else{
                                        planes.add(newIndex, p);
                                        p.setOrder(planes.get(newIndex - 1).getOrder() + 1);
                                        place = true;
                                    }
                                }
                            }
                        }else{
                            if(p.getOrder() != 0){
                                p.setOrder(1);
                            }
                            newIndex -= (2 * planes.size());
                            planes.add(newIndex, p);
                        }
                        if(newIndex < index) {
                            index++;
                        }
                    }
                }
            }
            int temp = info.get(2);
            info.remove(2);
            info.add(2, temp - 1);
        }
    }

    public static void planesWaiting(AscendinglyOrderedList<Plane> planes, ListArrayBasedPlus<Runway> runways, ListArrayBased<Integer> info) {
        if (info.get(2) == 0 || planes.isEmpty()) {
            System.out.println("There are no planes waiting to take off at this airport.");
        }else{
            for (int i = 0; i < runways.size(); i++) {
                if(runways.get(i).getActive()) {
                    System.out.println(runways.get(i));
                    int index = planes.search(new Plane("", runways.get(i).getOrder(), ""));
                    if(index < planes.size()){
                        while(index > 0 && planes.get(index - 1).getRunway() == runways.get(i).getOrder() && planes.get(index - 1).getOrder() > 0){
                            index--;
                        }
                        boolean end = false;
                        while(!end && index < planes.size()){
                            if(planes.get(index).getRunway() != runways.get(i).getOrder()){
                                end = true;
                            }else if(planes.get(index).getOrder() != 0){
                                System.out.println(planes.get(index));
                                index++;
                            }else{
                                index++;
                            }
                        }
                    }
                }
            }
        }
    }

    public static void printMenu() {
        System.out.println("Select from the following options: \n\t1. Plane enters the system.\n\t2. Plane takes off.\n\t3. Plane is allowed to re-enter a runway.\n\t4. Runway opens.\n\t5. Runway closes.\n\t6. Display info about planes waiting to take off.\n\t7. Display info about planes waiting to be allowed to re-enter a runway.\n\t8. Display number of planes who have taken off.\n\t9. End the program.");
    }
}