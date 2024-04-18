/** Juan Fernandez COP 4020-01 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Cell {
    
    /** attributes of Cell class */
    private String oem;
    private String model;
    private Integer launchAnnounced;
    private String launchStatus;
    private String bodyDimensions;
    private Float bodyWeight;
    private String bodySim;
    private String displayType;
    private Float displaySize;
    private String displayResolution;
    private String featuresSensors;
    private String platformOs;

    // HashMap to store objects of Cell
    static HashMap<Integer, Cell> cellsMap = new HashMap<>();
    
    // parameterized constructor
    public Cell(String oem, String model, Integer launchAnnounced, String launchStatus, String bodyDimensions,
                Float bodyWeight, String bodySim, String displayType, Float displaySize, String displayResolution,
                String featuresSensors, String platformOs) {
        this.oem = oem;
        this.model = model;
        this.launchAnnounced = launchAnnounced;
        this.launchStatus = launchStatus;
        this.bodyDimensions = bodyDimensions;
        this.bodyWeight = bodyWeight;
        this.bodySim = bodySim;
        this.displayType = displayType;
        this.displaySize = displaySize;
        this.displayResolution = displayResolution;
        this.featuresSensors = featuresSensors;
        this.platformOs = platformOs;
    }

    /** getter and setter methods */
    public String getOem() {
        return oem;
    }

    public void setOem(String oem) {
        this.oem = oem;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public Integer getLaunchAnnounced() {
        return launchAnnounced;
    }

    public void setLaunchAnnounced(Integer launchAnnounced) {
        this.launchAnnounced = launchAnnounced;
    }

    public String getLaunchStatus() {
        return launchStatus;
    }

    public void setLaunchStatus(String launchStatus) {
        this.launchStatus = launchStatus;
    }

    public String getBodyDimensions() {
        return bodyDimensions;
    }

    public void setBodyDimensions(String bodyDimensions) {
        this.bodyDimensions = bodyDimensions;
    }

    public Float getBodyWeight() {
        return bodyWeight;
    }

    public void setBodyWeight(Float bodyWeight) {
        this.bodyWeight = bodyWeight;
    }

    public String getBodySim() {
        return bodySim;
    }

    public void setBodySim(String bodySim) {
        this.bodySim = bodySim;
    }

    public String getDisplayType() {
        return displayType;
    }

    public void setDisplayType(String displayType) {
        this.displayType = displayType;
    }

    public Float getDisplaySize() {
        return displaySize;
    }

    public void setDisplaySize(Float displaySize) {
        this.displaySize = displaySize;
    }

    public String getDisplayResolution() {
        return displayResolution;
    }

    public void setDisplayResolution(String displayResolution) {
        this.displayResolution = displayResolution;
    }

    public String getFeaturesSensors() {
        return featuresSensors;
    }

    public void setFeaturesSensors(String featuresSensors) {
        this.featuresSensors = featuresSensors;
    }

    public String getPlatformOs() {
        return platformOs;
    }

    public void setPlatformOs(String platformOs) {
        this.platformOs = platformOs;
    }
    
    // method to validate and parse string data
    private static String validateString(String data) {
        if (data.equals("-") || data.trim().isEmpty()) {
            return null; // Replace missing or invalid string data with null
        }
        return data.trim();
    }

    // method to validate a string as alphanumeric or containing only alphabetic characters
    private static String validateAlphanumericOrLettersOnly(String data) {
        if (data.equals("-") || data.trim().isEmpty()) {
            // replace missing data with null
            return null; 
        }
        // regular expression to match strings containing at least one letter and optional numbers + allowed other chars like (),
        String regex = "^([a-zA-Z(),\\-\\s]*[0-9]+[a-zA-Z0-9(),\\-\\s]*|[a-zA-Z(),\\-\\s]+)$";

        // check if the data matches the regex pattern
        if (data.matches(regex)) {
            // return original data if it is alphanumeric
            return data; 
        } else {
            System.out.println("Invalid data format: " + data);
            // replace invalid data with null
            return null; 
        }
    }
    
    // method to extract the substring up to the first comma in the given string
    private static String extractUpToFirstComma(String data) {
        if (data == null || data.equals("-") || data.trim().isEmpty()) {
            // replace missing data with null
            return null; 
        }
        // regular expression to match everything up to the first comma
        String regex = "^(.*?),.*$";
        // if the string contains a comma, extract the substring up to the first comma
        if (data.contains(",")) {
            return data.replaceAll(regex, "$1");
        } else {
            // return the entire string if there is no comma
            return data;
        }
    }
  
    // method to validate and parse weight data in grams
    private static Float validateFloatGrams(String data) {
        if (data == null || data.equals("-") || data.trim().isEmpty()) {
            // replace missing data with null
            return null; 
        }
        // regular expression to match an integer before the letter 'g'
        String regex = "\\d+(\\.\\d+)?\\s*g.*";

        // check if the data matches the regex pattern
        if (data.trim().matches(regex)) {
            // extract the integer before 'g' and parse it into a float. split at whitespace and take the first part
            String grams = data.split("\\s")[0]; 
            return Float.parseFloat(grams);
        } else {
            System.out.println("Invalid weight format: " + data);
            // replace invalid data with null
            return null;
        }
    }
    
    // method to validate and parse data as float in inches
    private static Float validateFloatInches(String data) {
        // replace missing data with null
        if (data.equals("-") || data.trim().isEmpty()) {
            return null; 
        }
        // regular expression to match an integer or a float before the word "inches" with anything after it
        String regex = "^(\\d+(\\.\\d+)?)\\s*inches.*$";
        // check if the data matches the regex pattern
        if (data.matches(regex)) {
            // extract the float value before "inches" and parse it into a float
            String floatString = data.split("\\s+")[0]; // split by whitespace
            return Float.parseFloat(floatString);
        } else {
            System.out.println("Invalid inches format: " + data);
            // replace invalid data with null
            return null; 
        }
    }

    // method to validate and parse year data and to extract year substring from input string
    private static Integer validateYear(String data) {
        // replace missing year data with null
        if (data.equals("-") || data.trim().isEmpty()) {
            return null; // Replace missing year data with null
        }
        
        // regular expression to match 4-digit year
        String regex = "\\b\\d{4}\\b";
        
        // check if the input string matches the regex
        if (data.matches(".*" + regex + ".*")) {
            // extract and return the matched substring (year)
            return Integer.parseInt(data.replaceAll(".*(" + regex + ").*", "$1"));
        } else {
            System.out.println("Invalid year format: " + data);
            // replace invalid year format with null
            return null; 
        }
    }
        
    // method to validate and parse year data or 'Discontinued'/'Cancelled'
    private static String validateYearOrStatus(String data) {
        // replace missing data with null
        if (data.equals("-") || data.trim().isEmpty()) {
            return null;
        }
        
        // regular expression to match 'Discontinued' or 'Cancelled'
        if (data.equalsIgnoreCase("Discontinued") || data.equalsIgnoreCase("Cancelled")) {
            return data;
        }
        
        // regular expression to match 4-digit year
        String regex = "\\b\\d{4}\\b";

        // check if the data matches the regex pattern
        if (data.matches(".*" + regex + ".*")) {
            // extract and return the matched substring (year)
            return data.replaceAll(".*(" + regex + ").*", "$1");
        } else {
            System.out.println("Invalid year or status format: " + data);
            // replace invalid data with null
            return null; 
        }
    }
    
    // method to replace "No", "Yes" values with null
    private static String replaceYesNoValues(String data) {
        if (data.toString().equalsIgnoreCase("No") || data.toString().equalsIgnoreCase("Yes")) {
            // replace "No" or "Yes" with null
            return null; 
        } else {
            // return original data if not "No" or "Yes"
            return data; 
        }
    }

    // method to safely get the value at a specific index in the array
    private static String getValueAtIndex(String[] data, int index) {
        if (data != null && index >= 0 && index < data.length) {
            String value = data[index];
            // return value if not null or empty
            return (value != null && !value.isEmpty()) ? value : null; // Return value if not null or empty
        } else {
             // return null if the index is out of bounds or if data is null
            return null;
        }
    }
     
    // method to convert CSV data to Cell objects
    public static HashMap<Integer, Cell> readCSV(String filename) {

        BufferedReader br = null;
        String line = "";
        String data[] = null;
        String cvsSplitBy = ",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)";

       
        try {
            // read the file
            br = new BufferedReader(new FileReader(filename));
            // ignore the columns name
            line = br.readLine();
            // read file data
            int recordNumber = 0;
            while ((line = br.readLine()) != null) {
                // spliit line by ,
                data = line.split(cvsSplitBy, -1);
                // perform data cleaning and transformation 
                // setting oem + model as identifier
                int id = recordNumber;
                recordNumber++;
                
                // remove double quotes from each element of the data array
                for (int i = 0; i < data.length; i++) {
                    if (data[i].startsWith("\"") && data[i].endsWith("\"")) {
                        // remove the opening and closing quotes
                        data[i] = data[i].substring(1, data[i].length() - 1);
                    }
                }
                // gettiing the data from data array and storing in respective variable
                String oem = validateString(data[0]);
                String model = validateString(data[1]);
                Integer launchAnnounced = validateYear(data[2]);
                String launchStatus =validateYearOrStatus(data[3]);
                String bodyDimensions = validateString(data[4]);
                Float bodyWeight = validateFloatGrams(data[5]);
                String bodySim = replaceYesNoValues(data[6]);
                String displayType = validateString(data[7]);
                Float displaySize = validateFloatInches(data[8]);
                String displayResolution = validateString(data[9]);
                String featuresSensors = Cell.validateAlphanumericOrLettersOnly(data[10]);
                String platformOs = extractUpToFirstComma(getValueAtIndex(data, 11));

                // create an object of Cell
                Cell cell = new Cell(oem, model, launchAnnounced, launchStatus, bodyDimensions, bodyWeight,
                        bodySim, displayType, displaySize, displayResolution, featuresSensors, platformOs);

                // add cell object into cells map
                cellsMap.put(recordNumber, cell);
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found: " + filename);
        } catch (IOException e) {
            System.out.println("Error reading file: " + filename);
        } catch (NumberFormatException e) {
            System.out.println("Error parsing number in file: " + filename);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.err.println("Error closing BufferedReader.");
            }
        }

        return cellsMap;
    }

    // main method
    public static void main(String[] args) {
        System.out.println("Reading and cleaning the data ... please wait.");
        // calls the function to read data file 
        readCSV("cells.csv");
        System.out.println("\nData has been cleaned and loaded.\n");        
        
        // call function to display menu to user
        displayMenu();
    }
    
    // function to display menu to user
    public static void displayMenu(){
        // scanner object to take input from user
        Scanner input = new Scanner(System.in);
        
        String userChoice = "";
        // keep showing menu to user until user opt to cancel
        while(!userChoice.equalsIgnoreCase("0")){
            System.out.println("\nMenu:"
                    + "\nPress 1 to search by record number"
                    + "\nPress 2 to count phones with one featured sensor"
                    + "\nPress 3 to find cell with heaviest body weight"
                    + "\nPress 4 to get count of cells by launch year"
                    + "\nPress 5 to get count by oem"
                    + "\nPress 6 to calculate which company has the highest average weight of the phone body"
                    + "\nPress 7 to find phones announced and released in different years"
                    + "\nPress 8 to find year with most phone launches"
                    + "\nPress 0 to exit"
            );
            System.out.print("Please enter your choice: ");
            userChoice = input.nextLine();
            // call respective function based on user choice
            switch(userChoice){
                case "1":
                    searchByRecordNumber();
                    break;
                case "2":
                    countPhonesWithOneFeaturedSensor();
                    break;
                case "3":
                    findCellWithHeaviestBodyWeight();
                    break;
                case "4":
                    getCountOfCellsByLaunchYear();
                    break;  
                case "5":
                    getCountByOem();
                    break;
                case "6":
                    getCompanyWithHighestAverageWeight(cellsMap);
                    break;
                case "7":
                    findPhonesAnnouncedAndReleasedInDifferentYears();
                    break;   
                case "8":
                    findYearWithMostPhoneLaunches();
                    break; 
                case "0":
                    endProgram();
                    break;
                default:
                    System.out.println("Invalid Choice. Please try again.");
                    break;
            }
        }
    }
    
    // function to calculate which company has the highest average weight of the phone body
    public static void getCompanyWithHighestAverageWeight(HashMap<Integer, Cell> cellsMap) {
        // HashMap to store total weight for each company
        HashMap<String, Float> totalWeightByCompany = new HashMap<>();
        // HashMap to store total count for each company
        HashMap<String, Integer> countByCompany = new HashMap<>();
        // HashMap to store average weight for each company        
        HashMap<String, Float> averageWeightByCompany = new HashMap<>();

        // calculate total weight and count for each company
        for (Cell cell : cellsMap.values()) {
            String oem = cell.getOem();
            Float weight = cell.getBodyWeight();
            if (oem != null && weight != null) {
                // get exiting weight
                Float currentWeight = totalWeightByCompany.get(oem);
                if (currentWeight == null) {
                    currentWeight = 0f;
                }
                // add weight i existing
                totalWeightByCompany.put(oem, currentWeight + weight);
                
                // get existing count
                Integer currentCount = countByCompany.get(oem);
                if (currentCount == null) {
                    currentCount = 0;
                }
                // add count in existing
                countByCompany.put(oem, currentCount + 1);
            }
        }

        // calculate average weight for each company
        for (String oem : totalWeightByCompany.keySet()) {
            int count = countByCompany.get(oem);
            float totalWeight = totalWeightByCompany.get(oem);
            float averageWeight = totalWeight / count;
            averageWeightByCompany.put(oem, averageWeight);
        }

        // find company with the highest average weight
        String companyWithHighestAverageWeight = null;
        float highestAverageWeight = Float.MIN_VALUE;
        
        // display header
        System.out.printf("%-20s %10s\n", "Company", "Average Weight");

        // traverse through the loop
        for (String oem : averageWeightByCompany.keySet()) {
            float averageWeight = averageWeightByCompany.get(oem);
            // display average weight of each company
            System.out.printf("%-20s %10.2f\n", oem, averageWeight);
            if (averageWeight > highestAverageWeight) {
                highestAverageWeight = averageWeight;
                companyWithHighestAverageWeight = oem;
            }
        }

        System.out.println("The company with the highest average weight is: " + companyWithHighestAverageWeight + " which is equal to " + highestAverageWeight);
    }
    
    // method to find phones anounced in one year and released in another 
    public static void findPhonesAnnouncedAndReleasedInDifferentYears() {
        System.out.println("Phones that were announced in one year and released in another:");

        // iterate through all cell records
        for (Cell cell : cellsMap.values()) {
            // get launched announced year
            Integer launchAnnouncedYear = cell.getLaunchAnnounced();
            // get release status
            String launchStatus = cell.getLaunchStatus();

            // check if both launchAnnouncedYear and launchStatus are not null
            if (launchAnnouncedYear != null && launchStatus != null && !launchStatus.equalsIgnoreCase("Discontinued") && !launchStatus.equalsIgnoreCase("Cancelled")) {
                // extract the year part from the launchStatus
                Integer releaseYear = Integer.parseInt(launchStatus);

                // check if launchAnnouncedYear and releaseYear are different
                if (!launchAnnouncedYear.equals(releaseYear)) {
                    // print the oem and model of the phone
                    System.out.println("OEM: " + cell.getOem() + ", Model: " + cell.getModel());
                }
            }
        }
    }

    // method to count phones with one feaatured sensor
    public static void countPhonesWithOneFeaturedSensor() {
        int count = 0;

        // iterate through all cell records
        for (Cell cell : cellsMap.values()) {
            // get feature sensor
            String featuresSensors = cell.getFeaturesSensors();
            // check if featuresSensors is not null or empty
            if (featuresSensors != null && !featuresSensors.isEmpty()) {
                // split featuresSensors by comma
                String[] sensors = featuresSensors.split(",");
                // check if there's only one sensor
                if (sensors.length == 1) {
                    count++;
                }
            }
        }

        System.out.println("There are " + count + " phones with one feature sensor.");
    }

    // method to find year with most phone launches
    public static int findYearWithMostPhoneLaunches() {
        // create a HashMap to store the count of launches for each year
        HashMap<Integer, Integer> yearCounts = new HashMap<>();

        // iterate through all cell records
        for (Cell cell : cellsMap.values()) {
            // get launch announced 
            Integer launchYear = cell.getLaunchAnnounced();

            // check if the launch year is later than 1999 and not null
            if (launchYear != null && launchYear > 1999) {
                // get existing count
                Integer currentCount = yearCounts.get(launchYear);
                if (currentCount == null) {
                    currentCount = 0;
                }
                // add count in existing
                yearCounts.put(launchYear, currentCount + 1);
            }
        }

        // find the year with the maximum count of launches
        int maxCount = 0;
        int yearWithMostLaunches = 0;
        for (HashMap.Entry<Integer, Integer> entry : yearCounts.entrySet()) {
            if (entry.getValue() > maxCount) {
                maxCount = entry.getValue();
                yearWithMostLaunches = entry.getKey();
            }
        }

        System.out.println("The year with most phone launches is " + yearWithMostLaunches + " which is equal to " + maxCount);
        return yearWithMostLaunches;
    }
    
    // method to search cell data by record number
    private static void searchByRecordNumber() {
        Scanner input = new Scanner(System.in);
        int recordNumber;
        try {
            System.out.print("Please enter record number: ");
            recordNumber = input.nextInt();
            Cell cell = cellsMap.get(recordNumber);
            if(cell == null){
                System.out.println("Record with number " + recordNumber + " does not found.");
            }else{             
                System.out.println("Record with number " + recordNumber + " found:");
                System.out.println(cell);   
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid input! Please enter a valid record number (an integer).");
        }
    }
  
    // method to find cell with largest display size
    private static void findCellWithLargestDisplaySize() {
        Cell largestDisplaySizeCell = null;
        // loop through all records of cells 
        for (Cell cell : cellsMap.values()) {
            if (largestDisplaySizeCell == null || (cell.getDisplaySize() != null && cell.getDisplaySize() > largestDisplaySizeCell.getDisplaySize())) {
                largestDisplaySizeCell = cell;
            }
        }
        
        System.out.println("\nCell details with largest display size is following: ");
        System.out.println(largestDisplaySizeCell);
    }

    // method to find cell with largest display size
    public static void findCellWithHeaviestBodyWeight() {
        Cell heaviestCell = null;
        // loop through all records of cells 
        for (Cell cell : cellsMap.values()) {
            if (heaviestCell == null || (cell.getBodyWeight() != null && cell.getBodyWeight() > heaviestCell.getBodyWeight())) {
                heaviestCell = cell;
            }
        }
        
        System.out.println("\nCell details with heaviest body details is following: ");
        System.out.println(heaviestCell);
    }

    // method to get count of cells launched on specific year
    public static void getCountOfCellsByLaunchYear() {
        Scanner input = new Scanner(System.in);        
        int yearToSearch;
        try {
            System.out.print("Please enter launch year: ");
            yearToSearch = input.nextInt();
            int count = 0;
            for (Cell cell : cellsMap.values()) {
                if (cell.getLaunchAnnounced() != null) {
                    int year = cell.getLaunchAnnounced();
                    if(year == yearToSearch){
                        count++;
                    }
                }
            }
            System.out.println("There are "+ count+ " cells launched on year " + yearToSearch);
        } catch (InputMismatchException e) {
            System.out.println("Invalid year! Please enter a valid year (for example: 2010).");
        }
    }

    // method to get number of cells by oem (for example: Google, Haier etc)
    public static void getCountByOem() {
        Scanner input = new Scanner(System.in);        
        String oemToSearch;
        try {
            System.out.print("Please enter oem: ");
            oemToSearch = input.nextLine();
            int count = 0;
            for (Cell cell : cellsMap.values()) {
                if (cell.getOem() != null) {
                    String cellOem = cell.getOem();
                    if(cellOem.equalsIgnoreCase(oemToSearch)){
                        count++;
                    }
                }
            }
            System.out.println("There are "+ count+ " cells with oem " + oemToSearch);
        } catch (InputMismatchException e) {
            System.out.println("Invalid oem! Please enter a valid oem (for example: Google, Haier).");
        }
    }
    
    // method to print cell details
    @Override
    public String toString() {
        return "Cell : " + "\n"+
                "oem = " + oem + "\n" +
                "model = " + model + "\n" +
                "launchAnnounced = " + launchAnnounced + "\n" +
                "launchStatus = " + launchStatus + "\n" + 
                "bodyDimensions = " + bodyDimensions + "\n" +
                "bodyWeight = " + bodyWeight + "\n" +
                "bodySim = " + bodySim + "\n" +
                "displayType = " + displayType + "\n" + 
                "displaySize = " + displaySize + "\n" +
                "displayResolution = " + displayResolution + "\n" +
                "featuresSensors = " + featuresSensors + "\n" +
                "platformOs = " + platformOs + "\n";
    }

    // method to terminate the program
    public static void endProgram(){
        System.out.println("Thank you for using our system...");
        System.exit(0);
    }
}
