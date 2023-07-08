import java.util.*;
import java.io.*;

class Menu {
    public void options() {
        System.out.println("  ---   EMPLOYEE MANAGEMENT SYSTEM   ---   ");
        System.out.println("1 : Insert Employee Details");
        System.out.println("2 : View Employee Details ");
        System.out.println("3 : Delete an Employee Record ");
        System.out.println("4 : Update Employee Details");
        System.out.println("5 : Search Employee by ID");
        System.out.println("6 : To Exit the Employee Management System");
    }
}

class EmployeeDetail {
    String name;
    String email;
    String position;
    String employee_id;
    String employee_salary;

    public void getInformation() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter Employee's name: ");
        name = sc.nextLine();
        System.out.print("Enter Employee's ID: ");
        employee_id = sc.nextLine();
        System.out.print("Enter Employee's Email ID: ");
        email = sc.nextLine();
        System.out.print("Enter Employee's Position: ");
        position = sc.nextLine();
        System.out.print("Enter Employee's Salary: ");
        employee_salary = sc.nextLine();
    }

    public String toString() { 
          return "Employee ID: " + employee_id + "\n"
                + "Employee Name: " + name + "\n"
                + "Email EmailId: " + email + "\n"
                + "Employee Position: " + position + "\n"
                + "Employee Salary: " + employee_salary + "\n";
    }
}
/*Add the employee to the file */
class Employee_Add {
    public static void addEmployee(EmployeeDetail employee) {
        try {
            File file = new File("employee_details.txt");
            if (!file.exists()) {
                file.createNewFile();
            }

            Scanner scanner = new Scanner(file);
            boolean duplicateId = false;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.startsWith("Employee ID: " + employee.employee_id)) {
                    duplicateId = true;
                    break;
                }
            }
            scanner.close();

            if (duplicateId) {
                System.out.println("Employee ID already exists. Please enter a unique ID.");
            } else {
                FileWriter writer = new FileWriter(file, true);
                writer.write(employee.toString() + "\n");
                writer.close();
                System.out.println("\nEmployee has been added successfully");
            }
        } catch (IOException e) {
            System.out.println("An error occurred while adding the employee: " + e.getMessage());
        }
    }
/* View the Employee Details */
    public void viewFile() throws Exception {
        File file = new File("employee_details.txt");
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine()) {
            System.out.println(sc.nextLine());
        }
    }
}
/*remove the employee details */
class Employee_Remove {
    public void removeEmployee(String employeeId) {
        File file = new File("employee_details.txt");

        try (RandomAccessFile raf = new RandomAccessFile(file, "rw")) {
            long currentPosition = 0;
            String line;

            while ((line = raf.readLine()) != null) {
                if (line.startsWith("Employee ID: " + employeeId)) {
                    skipEmployeeEntry(raf);
                    long nextPosition = raf.getFilePointer();
                    byte[] buffer = new byte[(int) (raf.length() - nextPosition)];
                    raf.read(buffer);
                    raf.seek(currentPosition);
                    raf.write(buffer);
                    raf.setLength(currentPosition + buffer.length);
                    System.out.println("\nEmployee has been removed successfully");
                    return;
                }
                currentPosition = raf.getFilePointer();
            }

            System.out.println("\nEmployee does not exist");
        } catch (IOException e) {
            System.out.println("An error occurred while removing the employee: " + e.getMessage());
        }
    }

    private void skipEmployeeEntry(RandomAccessFile raf) throws IOException {
        for (int i = 0; i < 5; i++) {
            raf.readLine();
        }
    }
}

/* Update the Employee Detail */
class Employee_Update {
    public void updateFile(String s, String o, String n) throws IOException {
        File file = new File("employee_details.txt");
        Scanner sc = new Scanner(file);
        String fileContext = "";
        while (sc.hasNextLine()) {
            fileContext = fileContext + "\n" + sc.nextLine();
        }
        FileWriter myWriter = new FileWriter("employee_details.txt");
        fileContext = fileContext.replaceAll(o, n);
        myWriter.write(fileContext);
        myWriter.close();
    }
}

/*search for the employee */
class Employee_Search {
    public void searchEmployee(String employeeId) {
        try {
            File file = new File("employee_details.txt");
            Scanner scanner = new Scanner(file);
            boolean found = false;

            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.contains("Employee ID: " + employeeId)) {
                    found = true;
                    System.out.println(line);
                    for (int i = 0; i < 4; i++) {
                        System.out.println(scanner.nextLine());
                    }
                    break;
                }
            }

            if (!found) {
                System.out.println("Employee not found.");
            }
        } catch (FileNotFoundException e) {
            System.out.println("Employee details file not found.");
        }
    }
}
/* Exit the Program */
class CodeExit {
    public void out() {
        System.out.println("    WEEK-1 INFOTRIXS TASK    ");
        System.exit(0);
    }
}

class EmployeeManagementSystem {
    public static void main(String args[]) {

        Scanner sc = new Scanner(System.in);
        Employee_Add epa = new Employee_Add();
        EmployeeDetail employee = new EmployeeDetail();

        int choice = 0;

        /*** Calling Menu Class function ****/
        Menu menu = new Menu();
        menu.options();

        /*** Initializing loop for Menu Choices ***/
        while (choice != 6) {
            System.out.print("\nPlease Enter choice: ");
            choice = Integer.parseInt(sc.nextLine());

            /** Switch Case Statements **/
            switch (choice) {
                case 1:
                    EmployeeDetail emp = new EmployeeDetail();
                    employee.getInformation();
                    Employee_Add.addEmployee(employee);
                    System.out.print("\nPress Enter to Continue...");
                    sc.nextLine();
                    menu.options();
                    break;

                case 2:
                    try {
                        epa.viewFile();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    System.out.print("\nPress Enter to Continue...");
                    sc.nextLine();
                    menu.options();
                    break;

                   case 3:
                    System.out.print("\nEnter Employee's ID: ");
                    String id = sc.nextLine();
                    Employee_Remove epr = new Employee_Remove();
                    epr.removeEmployee(id);
                    System.out.print("\nPress Enter to Continue...");
                    sc.nextLine();
                    menu.options();
                    break;

                case 4:
                System.out.print("\nPlease Enter Employee's ID: ");
                    String empID = sc.nextLine();
                    try {
                        epa.viewFile();
                    } catch (Exception e) {
                        System.out.println(e);
                    }
                    Employee_Update epu = new Employee_Update();
                    System.out.print("Please Enter the detail you want to Update: ");
                    System.out.print("\nFor Example:\n");
                    System.out.println("If you want to change the Name, then enter the current Name and press Enter. Then write the new Name and press Enter. It will update the Name.\n");
                    String oldDetail = sc.nextLine();
                    System.out.print("Please Enter the Updated Info: ");
                    String newDetail = sc.nextLine();
                    try {
                        epu.updateFile(empID, oldDetail, newDetail);
                        System.out.print("\nPress Enter to Continue...");
                        sc.nextLine();
                        System.out.print("\033[H\033[2J");
                        menu.options();
                    } catch (IOException e) {
                        System.out.println(e);
                    }
                    break;
                case 5:
                    System.out.print("\nEnter Employee's ID: ");
                    String searchId = sc.nextLine();
                    Employee_Search eps = new Employee_Search();
                    eps.searchEmployee(searchId);
                    System.out.print("\nPress Enter to Continue...");
                    sc.nextLine();
                    menu.options();
                    break;

                case 6:
                    CodeExit obj = new CodeExit();
                    obj.out();
                    break;
            }
        }
    }
}
