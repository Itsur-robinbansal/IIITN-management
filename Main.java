import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

class FileReadWriteException extends Exception {
    public String message;
    public FileReadWriteException(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}
class InvalidInputException extends Exception {
    public String message;
    public InvalidInputException(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return message;
    }
}

// general operations
interface GeneralOperations {
    void add() throws Exception;
    void search() throws Exception;
    void update() throws Exception;
    void delete() throws Exception;
    void showAll() throws Exception;
}

// faculty Management
class FacultyManagement 
{
    public static void runFacultyOperation() throws Exception {
        FacultyOperations facultyOps = new FacultyOperations();
        Scanner sc = new Scanner(System.in);
        int option = 0;

        try {
            while (option != 6) {
                System.out.println("\n----- Manage Faculty -----");
                System.out.println("1. Add Faculty");
                System.out.println("2. Update Faculty");
                System.out.println("3. Search Faculty");
                System.out.println("4. Delete Faculty");
                System.out.println("5. Show All Faculty");
                System.out.println("6. Exit");
                System.out.print("\nSelect Option: ");

                option = sc.nextInt();

                switch (option) {
                    case 1:
                        facultyOps.add();
                        break;
                    case 2:
                        facultyOps.update();
                        break;
                    case 3:
                        facultyOps.search();
                        break;
                    case 4:
                        facultyOps.delete();
                        break;
                    case 5:
                        facultyOps.showAll();
                        break;
                    case 6:
                        System.out.println("Program Exited");
                        break;
                    default:
                        System.out.println("Invalid Input");
                }
            }
        } catch (InputMismatchException error) {
            throw new InvalidInputException("Invalid input given");
        } catch (InvalidInputException error) {
            throw error;
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        } finally {
            sc.close();
        }
    }
}
abstract class FacultyBase {
    abstract void setId(String id);

    abstract String getId();

    abstract void setName(String name);

    abstract String getName();

    abstract void setAge(int age);

    abstract int getAge();

    abstract void setGender(String gender);

    abstract String getGender();

    abstract void setDepartment(String department);

    abstract String getDepartment();

    abstract void setSalary(double salary);

    abstract double getSalary();

    abstract void showDetails();

}
class Faculty extends FacultyBase
 {
    private String id;
    private String name;
    private int age;
    private String gender;
    private String department;
    private double salary;

    public Faculty() 
    {
    }

    public Faculty(String id, String name, int age, String gender, String department, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.department = department;
        this.salary = salary;
    }

    @Override
    void setId(String id) {
        this.id = id;
    }

    @Override
    String getId() {
        return id;
    }

    @Override
    void setName(String name) {
        this.name = name;
    }

    @Override
    String getName() {
        return name;
    }

    @Override
    void setAge(int age) {
        this.age = age;
    }

    @Override
    int getAge() {
        return age;
    }

    @Override
    void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    String getGender() {
        return gender;
    }

    @Override
    void setDepartment(String department) {
        this.department = department;
    }

    @Override
    String getDepartment() {
        return department;
    }

    @Override
    void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    double getSalary() {
        return salary;
    }

    @Override
    void showDetails() {
        System.out.println("\n-----//-----//-----");
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Department: " + department);
        System.out.println("Salary: " + salary);
    }

    String facultyToStr() {
        return id + "\r\n" + name + "\r\n" + age + "\r\n" + gender + "\r\n" + department + "\r\n" + salary;
    }
}
class FacultyOperations implements GeneralOperations {
    FacultyIO facultyIO;
    Scanner sc;

    public FacultyOperations() {
        facultyIO = new FacultyIO();
        sc = new Scanner(System.in);
    }

    @Override
    public void add() throws Exception {
        try {
            System.out.println("\n---- Enter Faculty Information ----");
            System.out.print("ID: ");
            String id = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = sc.nextInt();

            System.out.print("Gender(Male/Female): ");
            sc.nextLine();
            String gender = sc.nextLine();

            System.out.print("Department: ");
            String department = sc.nextLine();

            System.out.print("Salary: ");
            double salary = sc.nextDouble();

            Faculty newFaculty = new Faculty(id, name, age, gender, department, salary);
            facultyIO.writeIntoFile(newFaculty);
            System.out.println("\n-----///----- New faculty added -----///-----");
            sc.nextLine();

        } catch (InputMismatchException error) {
            throw new InvalidInputException("Invalid input given");
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void search() throws Exception {
        try {
            System.out.print("\nEnter faculty ID to search: ");
            String facultyId = sc.nextLine();
            Faculty faculty = facultyIO.searchFromFile(facultyId);
            if (faculty.getId() == null) {
                System.out.println("///=== No faculty found with this ID ===///");
            } else {
                faculty.showDetails();
            }
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void update() throws Exception {
        try {
            System.out.print("\nEnter faculty ID to update: ");
            String facultyId = sc.nextLine();
            Faculty faculty = facultyIO.searchFromFile(facultyId);
            if (faculty.getId() == null) {
                System.out.println("No faculty found with this ID");
            } else {
                String facultyStr = faculty.facultyToStr();
                System.out.println("\n---- Edit Faculty Information ----");

                System.out.print("Name (" + faculty.getName() + "): ");
                String name = sc.nextLine();

                System.out.print("Age (" + faculty.getAge() + "): ");
                String age = sc.nextLine();

                System.out.print("Gender (" + faculty.getGender() + "): ");
                String gender = sc.nextLine();

                System.out.print("Department (" + faculty.getDepartment() + "): ");
                String department = sc.nextLine();

                System.out.print("Salary (" + faculty.getSalary() + "): ");
                String salary = sc.nextLine();

                Faculty updatedFaculty = new Faculty(
                        faculty.getId(),
                        name == "" ? faculty.getName() : name,
                        age == "" ? faculty.getAge() : Integer.parseInt(age),
                        gender == "" ? faculty.getGender() : gender,
                        department == "" ? faculty.getDepartment() : department,
                        salary == "" ? faculty.getSalary() : Double.parseDouble(salary));
                String updatedFacultyStr = updatedFaculty.facultyToStr();
                facultyIO.updateData(facultyStr, updatedFacultyStr);
                System.out.println("\n-----///----- Faculty data updated -----///-----");
            }
        } catch (InputMismatchException error) {
            throw new InvalidInputException("Invalid input given");
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void delete() throws Exception {
        try {
            System.out.print("\nEnter faculty ID to delete: ");
            String facultyId = sc.nextLine();
            Faculty faculty = facultyIO.searchFromFile(facultyId);
            if (faculty.getId() == null) {
                System.out.println("///=== No faculty found with this ID ===///");
            } else {
                String facultyStr = faculty.facultyToStr() + "\r\n\r\n";
                facultyIO.updateData(facultyStr, "");
                System.out.println("\n-----///----- Faculty deleted -----///-----");
            }
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void showAll() throws Exception {
        try {

            Faculty[] facultyList = facultyIO.getAllFaculty();

            for (Faculty faculty : facultyList) {
                faculty.showDetails();
            }

        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }
}
class FacultyIO {
    private String fileName = "FacultyData.txt";
    private File file;
    private FileWriter writer;
    private FileReader reader;
    private BufferedReader bfr;

    public void writeIntoFile(Faculty faculty) throws Exception {

        try {
            file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(faculty.getId() + "\r\n");
            writer.write(faculty.getName() + "\r\n");
            writer.write(faculty.getAge() + "\r\n");
            writer.write(faculty.getGender() + "\r\n");
            writer.write(faculty.getDepartment() + "\r\n");
            writer.write(faculty.getSalary() + "\r\n");
            writer.write("\r\n");
            writer.flush();
            writer.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File read error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }
    }

    public Faculty searchFromFile(String facultyId) throws Exception {
        Faculty faculty = new Faculty();

        try {
            file = new File(fileName);
            reader = new FileReader(file);
            bfr = new BufferedReader(reader);
            String temp;
            int counter = 0;

            while ((temp = bfr.readLine()) != null) {

                if (temp.contains(facultyId) && temp.length() == facultyId.length()) {
                    faculty.setId(temp);
                    counter++;
                } else {
                    switch (counter) {
                        case 1: {
                            faculty.setName(temp);
                            counter++;
                        }
                            ;
                            break;
                        case 2: {
                            faculty.setAge(Integer.parseInt(temp));
                            counter++;
                        }
                            ;
                            break;
                        case 3: {
                            faculty.setGender(temp);
                            counter++;
                        }
                            ;
                            break;
                        case 4: {
                            faculty.setDepartment(temp);
                            counter++;
                        }
                            ;
                            break;
                        case 5: {
                            faculty.setSalary(Double.parseDouble(temp));
                            counter++;
                        }
                    }
                }
            }

            bfr.close();
            reader.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File read error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }

        return faculty;
    }

    public void updateData(String oldData, String newData) throws Exception {

        try {
            file = new File(fileName);
            reader = new FileReader(file);
            bfr = new BufferedReader(reader);

            String oldFacultyData = "";
            String temp;

            while ((temp = bfr.readLine()) != null) {
                oldFacultyData += temp + "\r\n";
            }

            String newFacultyData = oldFacultyData.replace(oldData, newData);

            bfr.close();
            reader.close();

            writer = new FileWriter(file);
            writer.write(newFacultyData);
            writer.flush();
            writer.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File update error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }
    }

    public Faculty[] getAllFaculty() throws Exception {
        Faculty[] faculty = new Faculty[] {};

        try {
            file = new File(fileName);
            reader = new FileReader(file);
            bfr = new BufferedReader(reader);
            String temp;
            int wordCount = 0;
            int facultyCount = 0;

            while ((temp = bfr.readLine()) != null) {
                if (temp.length() > 0)
                    wordCount++;
            }

            faculty = new Faculty[wordCount / 6];
            wordCount = 0;

            bfr.close();
            reader.close();

            reader = new FileReader(file);
            bfr = new BufferedReader(reader);

            while ((temp = bfr.readLine()) != null) {
                if (temp.length() > 0) {
                    if (faculty[facultyCount] == null) {
                        faculty[facultyCount] = new Faculty();
                    }
                    switch (wordCount) {
                        case 0:
                            faculty[facultyCount].setId(temp);
                            break;
                        case 1:
                            faculty[facultyCount].setName(temp);
                            break;
                        case 2:
                            faculty[facultyCount].setAge(Integer.parseInt(temp));
                            break;
                        case 3:
                            faculty[facultyCount].setGender(temp);
                            break;
                        case 4:
                            faculty[facultyCount].setDepartment(temp);
                            break;
                        case 5:
                            faculty[facultyCount].setSalary(Double.parseDouble(temp));
                            break;
                    }

                    wordCount++;

                } else {
                    facultyCount++;
                    wordCount = 0;
                }
            }

            bfr.close();
            reader.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File read error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }

        return faculty;
    }
}

// student Management36
class StudentManagement {
    public static void runStudentOperation() throws Exception {
        StudentOperations studentOps = new StudentOperations();
        Scanner sc = new Scanner(System.in);
        int option = 0;

        try {
            while (option != 6) {
                System.out.println("\n----- Manage Students -----");
                System.out.println("1. Add Student");
                System.out.println("2. Update Student");
                System.out.println("3. Search Student");
                System.out.println("4. Delete Student");
                System.out.println("5. Show All Students");
                System.out.println("6. Exit");
                System.out.print("\nSelect Option: ");

                option = sc.nextInt();

                switch (option) {
                    case 1:
                        studentOps.add();
                        break;
                    case 2:
                        studentOps.update();
                        break;
                    case 3:
                        studentOps.search();
                        break;
                    case 4:
                        studentOps.delete();
                        break;
                    case 5:
                        studentOps.showAll();
                        break;
                    case 6:
                        System.out.println("Program Exited");
                        break;
                    default:
                        System.out.println("Invalid Input");
                }
            }
        } catch (InputMismatchException error) {
            throw new InvalidInputException("Invalid input given");
        } catch (InvalidInputException error) {
            throw error;
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        } finally {
            sc.close();
        }
    }
}
abstract class StudentBase {
    abstract String getId();

    abstract void setId(String id);

    abstract String getName();

    abstract void setName(String name);

    abstract int getAge();

    abstract void setAge(int age);

    abstract String getGender();

    abstract void setGender(String gender);

    abstract double getCGPA();

    abstract void setCGPA(double cgpa);

    abstract int getCreditPassed();

    abstract void setCreditPassed(int creditPassed);

    abstract void showDetails();
}
class Student extends StudentBase {
    private String id;
    private String name;
    private int age;
    private String gender;
    private double cgpa;
    private int creditPassed;

    public Student() {
    }

    public Student(String id, String name, int age, String gender, double cgpa, int creditPassed) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.cgpa = cgpa;
        this.creditPassed = creditPassed;
    }

    @Override
    void setId(String id) {
        this.id = id;
    }

    @Override
    String getId() {
        return id;
    }

    @Override
    void setName(String name) {
        this.name = name;
    }

    @Override
    String getName() {
        return name;
    }

    @Override
    void setAge(int age) {
        this.age = age;
    }

    @Override
    int getAge() {
        return age;
    }

    @Override
    void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    String getGender() {
        return gender;
    }

    @Override
    void setCGPA(double cgpa) {
        this.cgpa = cgpa;
    }

    @Override
    double getCGPA() {
        return cgpa;
    }

    @Override
    void setCreditPassed(int creditPassed) {
        this.creditPassed = creditPassed;
    }

    @Override
    int getCreditPassed() {
        return creditPassed;
    }

    @Override
    void showDetails() {
        System.out.println("\n-----//-----//-----");
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("CGPA: " + cgpa);
        System.out.println("Credit Passed: " + creditPassed);
    }

    String studentToStr() {
        return id + "\r\n" + name + "\r\n" + age + "\r\n" + gender + "\r\n" + cgpa + "\r\n" + creditPassed;
    }
}
class StudentIO {
    private String fileName = "StudentData.txt";
    private File file;
    private FileWriter writer;
    private FileReader reader;
    private BufferedReader bfr;

    public void writeIntoFile(Student student) throws Exception {

        try {
            file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(student.getId() + "\r\n");
            writer.write(student.getName() + "\r\n");
            writer.write(student.getAge() + "\r\n");
            writer.write(student.getGender() + "\r\n");
            writer.write(student.getCGPA() + "\r\n");
            writer.write(student.getCreditPassed() + "\r\n");
            writer.write("\r\n");
            writer.flush();
            writer.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File read error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }
    }

    public Student searchFromFile(String studentId) throws Exception {
        Student student = new Student();

        try {
            file = new File(fileName);
            reader = new FileReader(file);
            bfr = new BufferedReader(reader);
            String temp;
            int counter = 0;

            while ((temp = bfr.readLine()) != null) {

                if (temp.contains(studentId) && temp.length() == studentId.length()) {
                    student.setId(temp);
                    counter++;
                } else {
                    switch (counter) {
                        case 1: {
                            student.setName(temp);
                            counter++;
                        }
                            ;
                            break;
                        case 2: {
                            student.setAge(Integer.parseInt(temp));
                            counter++;
                        }
                            ;
                            break;
                        case 3: {
                            student.setGender(temp);
                            counter++;
                        }
                            ;
                            break;
                        case 4: {
                            student.setCGPA(Double.parseDouble(temp));
                            counter++;
                        }
                            ;
                            break;
                        case 5: {
                            student.setCreditPassed(Integer.parseInt(temp));
                            counter++;
                        }
                    }
                }
            }

            bfr.close();
            reader.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File read error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }

        return student;
    }

    public void updateData(String oldData, String newData) throws Exception {

        try {
            file = new File(fileName);
            reader = new FileReader(file);
            bfr = new BufferedReader(reader);

            String oldStudentData = "";
            String temp;

            while ((temp = bfr.readLine()) != null) {
                oldStudentData += temp + "\r\n";
            }

            String newStudentData = oldStudentData.replace(oldData, newData);

            bfr.close();
            reader.close();

            writer = new FileWriter(file);
            writer.write(newStudentData);
            writer.flush();
            writer.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File update error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }
    }

    public Student[] getAllStudent() throws Exception {
        Student[] students = new Student[] {};

        try {
            file = new File(fileName);
            reader = new FileReader(file);
            bfr = new BufferedReader(reader);
            String temp;
            int wordCount = 0;
            int studentCount = 0;

            while ((temp = bfr.readLine()) != null) {
                if (temp.length() > 0)
                    wordCount++;
            }

            students = new Student[wordCount / 6];
            wordCount = 0;

            bfr.close();
            reader.close();

            reader = new FileReader(file);
            bfr = new BufferedReader(reader);

            while ((temp = bfr.readLine()) != null) {
                if (temp.length() > 0) {
                    if (students[studentCount] == null) {
                        students[studentCount] = new Student();
                    }
                    switch (wordCount) {
                        case 0:
                            students[studentCount].setId(temp);
                            break;
                        case 1:
                            students[studentCount].setName(temp);
                            break;
                        case 2:
                            students[studentCount].setAge(Integer.parseInt(temp));
                            break;
                        case 3:
                            students[studentCount].setGender(temp);
                            break;
                        case 4:
                            students[studentCount].setCGPA(Double.parseDouble(temp));
                            break;
                        case 5:
                            students[studentCount].setCreditPassed(Integer.parseInt(temp));
                            break;
                    }

                    wordCount++;

                } else {
                    studentCount++;
                    wordCount = 0;
                }
            }

            bfr.close();
            reader.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File read error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }

        return students;

    }
}
class StudentOperations implements GeneralOperations {
    StudentIO studentIO;
    Scanner sc;

    public StudentOperations() {
        studentIO = new StudentIO();
        sc = new Scanner(System.in);
    }

    @Override
    public void add() throws Exception {
        try {
            System.out.println("\n---- Enter Student Information ----");
            System.out.print("ID: ");
            String id = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = sc.nextInt();

            System.out.print("Gender(Male/Female): ");
            sc.nextLine();
            String gender = sc.nextLine();

            System.out.print("Cgpa: ");
            double cgpa = sc.nextDouble();

            System.out.print("Credit Passed: ");
            int creditPassed = sc.nextInt();

            Student newStudent = new Student(id, name, age, gender, cgpa, creditPassed);
            studentIO.writeIntoFile(newStudent);
            System.out.println("\n-----///----- New student added -----///-----");
            sc.nextLine();

        } catch (InputMismatchException error) {
            throw new InvalidInputException("Invalid input given");
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void search() throws Exception {
        try {
            System.out.print("\nEnter student ID to search: ");
            String studentId = sc.nextLine();
            Student student = studentIO.searchFromFile(studentId);
            if (student.getId() == null) {
                System.out.println("///=== No student found with this ID ===///");
            } else {
                student.showDetails();
            }
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void update() throws Exception {
        try {
            System.out.print("\nEnter student ID to update: ");
            String studentId = sc.nextLine();
            Student student = studentIO.searchFromFile(studentId);
            if (student.getId() == null) {
                System.out.println("No student found with this ID");
            } else {
                String studentStr = student.studentToStr();
                System.out.println("\n---- Edit Student Information ----");

                System.out.print("Name (" + student.getName() + "): ");
                String name = sc.nextLine();

                System.out.print("Age (" + student.getAge() + "): ");
                String age = sc.nextLine();

                System.out.print("Gender (" + student.getGender() + "): ");
                String gender = sc.nextLine();

                System.out.print("Cgpa (" + student.getCGPA() + "): ");
                String cgpa = sc.nextLine();

                System.out.print("Credit Passed (" + student.getCreditPassed() + "): ");
                String creditPassed = sc.nextLine();

                Student updatedStudent = new Student(
                        student.getId(),
                        name == "" ? student.getName() : name,
                        age == "" ? student.getAge() : Integer.parseInt(age),
                        gender == "" ? student.getGender() : gender,
                        cgpa == "" ? student.getCGPA() : Double.parseDouble(cgpa),
                        creditPassed == "" ? student.getCreditPassed() : Integer.parseInt(creditPassed));
                String updatedStudentStr = updatedStudent.studentToStr();
                studentIO.updateData(studentStr, updatedStudentStr);
                System.out.println("\n-----///----- Student data updated -----///-----");
            }
        } catch (InputMismatchException error) {
            throw new InvalidInputException("Invalid input given");
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void delete() throws Exception {
        try {
            System.out.print("\nEnter student ID to delete: ");
            String studentId = sc.nextLine();
            Student student = studentIO.searchFromFile(studentId);
            if (student.getId() == null) {
                System.out.println("///=== No student found with this ID ===///");
            } else {
                String studentStr = student.studentToStr() + "\r\n\r\n";
                studentIO.updateData(studentStr, "");
                System.out.println("\n-----///----- Student deleted -----///-----");
            }
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void showAll() throws Exception {
        try {

            Student[] students = studentIO.getAllStudent();

            for (Student student : students) {
                student.showDetails();
            }

        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }
}

// staff Management
class StaffManagement {
    public static void runStaffOperation() throws Exception {
        StaffOperations StaffOps = new StaffOperations();
        Scanner sc = new Scanner(System.in);
        int option = 0;

        try {
            while (option != 6) {
                System.out.println("\n----- Manage Staffs -----");
                System.out.println("1. Add Staff");
                System.out.println("2. Update Staff");
                System.out.println("3. Search Staff");
                System.out.println("4. Delete Staff");
                System.out.println("5. Show All Staffs");
                System.out.println("6. Exit");
                System.out.print("\nSelect Option: ");

                option = sc.nextInt();

                switch (option) {
                    case 1:
                        StaffOps.add();
                        break;
                    case 2:
                        StaffOps.update();
                        break;
                    case 3:
                        StaffOps.search();
                        break;
                    case 4:
                        StaffOps.delete();
                        break;
                    case 5:
                        StaffOps.showAll();
                        break;
                    case 6:
                        System.out.println("Program Exited");
                        break;
                    default:
                        System.out.println("Invalid Input");
                }
            }
        } catch (InputMismatchException error) {
            throw new InvalidInputException("Invalid input given");
        } catch (InvalidInputException error) {
            throw error;
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        } finally {
            sc.close();
        }
    }
}
abstract class StaffBase {
    abstract void setId(String id);

    abstract String getId();

    abstract void setName(String name);

    abstract String getName();

    abstract void setAge(int age);

    abstract int getAge();

    abstract void setGender(String gender);

    abstract String getGender();

    abstract void setSalary(double salary);

    abstract double getSalary();

    abstract void showDetails();
}
class Staff extends StaffBase {
    private String id;
    private String name;
    private int age;
    private String gender;
    private double salary;

    public Staff() {
    }

    public Staff(String id, String name, int age, String gender, double salary) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.salary = salary;
    }

    @Override
    void setId(String id) {
        this.id = id;
    }

    @Override
    String getId() {
        return id;
    }

    @Override
    void setName(String name) {
        this.name = name;
    }

    @Override
    String getName() {
        return name;
    }

    @Override
    void setAge(int age) {
        this.age = age;
    }

    @Override
    int getAge() {
        return age;
    }

    @Override
    void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    String getGender() {
        return gender;
    }

    @Override
    void setSalary(double salary) {
        this.salary = salary;
    }

    @Override
    double getSalary() {
        return salary;
    }

    @Override
    void showDetails() {
        System.out.println("\n-----//-----//-----");
        System.out.println("Id: " + id);
        System.out.println("Name: " + name);
        System.out.println("Age: " + age);
        System.out.println("Gender: " + gender);
        System.out.println("Salary: " + salary);
    }

    String StaffToStr() {
        return id + "\r\n" + name + "\r\n" + age + "\r\n" + gender + "\r\n" + salary;
    }
}
class StaffIO {
    private String fileName = "StaffData.txt";
    private File file;
    private FileWriter writer;
    private FileReader reader;
    private BufferedReader bfr;

    public void writeIntoFile(Staff Staff) throws Exception {

        try {
            file = new File(fileName);
            if (!file.exists()) {
                file.createNewFile();
            }
            writer = new FileWriter(file, true);
            writer.write(Staff.getId() + "\r\n");
            writer.write(Staff.getName() + "\r\n");
            writer.write(Staff.getAge() + "\r\n");
            writer.write(Staff.getGender() + "\r\n");
            writer.write(Staff.getSalary() + "\r\n");
            writer.write("\r\n");
            writer.flush();
            writer.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File read error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }
    }

    public Staff searchFromFile(String StaffId) throws Exception {
        Staff Staff = new Staff();

        try {
            file = new File(fileName);
            reader = new FileReader(file);
            bfr = new BufferedReader(reader);
            String temp;
            int counter = 0;

            while ((temp = bfr.readLine()) != null) {

                if (temp.contains(StaffId) && temp.length() == StaffId.length()) {
                    Staff.setId(temp);
                    counter++;
                } else {
                    switch (counter) {
                        case 1: {
                            Staff.setName(temp);
                            counter++;
                        }
                            ;
                            break;
                        case 2: {
                            Staff.setAge(Integer.parseInt(temp));
                            counter++;
                        }
                            ;
                            break;
                        case 3: {
                            Staff.setGender(temp);
                            counter++;
                        }
                            ;
                            break;

                        case 4: {
                            Staff.setSalary(Double.parseDouble(temp));
                            counter++;
                        }
                    }
                }
            }

            bfr.close();
            reader.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File read error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }

        return Staff;
    }

    public void updateData(String oldData, String newData) throws Exception {

        try {
            file = new File(fileName);
            reader = new FileReader(file);
            bfr = new BufferedReader(reader);

            String oldStaffData = "";
            String temp;

            while ((temp = bfr.readLine()) != null) {
                oldStaffData += temp + "\r\n";
            }

            String newStaffData = oldStaffData.replace(oldData, newData);

            bfr.close();
            reader.close();

            writer = new FileWriter(file);
            writer.write(newStaffData);
            writer.flush();
            writer.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File update error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }
    }

    public Staff[] getAllStaff() throws Exception {
        Staff[] Staff = new Staff[] {};

        try {
            file = new File(fileName);
            reader = new FileReader(file);
            bfr = new BufferedReader(reader);
            String temp;
            int wordCount = 0;
            int StaffCount = 0;

            while ((temp = bfr.readLine()) != null) {
                if (temp.length() > 0)
                    wordCount++;
            }

            Staff = new Staff[wordCount / 5];
            wordCount = 0;

            bfr.close();
            reader.close();

            reader = new FileReader(file);
            bfr = new BufferedReader(reader);

            while ((temp = bfr.readLine()) != null) {
                if (temp.length() > 0) {
                    if (Staff[StaffCount] == null) {
                        Staff[StaffCount] = new Staff();
                    }
                    switch (wordCount) {
                        case 0:
                            Staff[StaffCount].setId(temp);
                            break;
                        case 1:
                            Staff[StaffCount].setName(temp);
                            break;
                        case 2:
                            Staff[StaffCount].setAge(Integer.parseInt(temp));
                            break;
                        case 3:
                            Staff[StaffCount].setGender(temp);
                            break;
                        case 4:
                            Staff[StaffCount].setSalary(Double.parseDouble(temp));
                            break;
                    }

                    wordCount++;

                } else {
                    StaffCount++;
                    wordCount = 0;
                }
            }

            bfr.close();
            reader.close();

        } catch (IOException error) {
            throw new FileReadWriteException("File read error");
        } catch (Exception error) {
            throw new Exception("Some error occurred");
        }

        return Staff;
    }
}
class StaffOperations implements GeneralOperations {
    StaffIO StaffIO;
    Scanner sc;

    public StaffOperations() {
        StaffIO = new StaffIO();
        sc = new Scanner(System.in);
    }

    @Override
    public void add() throws Exception {
        try {
            System.out.println("\n---- Enter Staff Information ----");
            System.out.print("ID: ");
            String id = sc.nextLine();

            System.out.print("Name: ");
            String name = sc.nextLine();

            System.out.print("Age: ");
            int age = sc.nextInt();

            System.out.print("Gender(Male/Female): ");
            sc.nextLine();
            String gender = sc.nextLine();

            System.out.print("Salary: ");
            double salary = sc.nextDouble();

            Staff newStaff = new Staff(id, name, age, gender, salary);
            StaffIO.writeIntoFile(newStaff);
            System.out.println("\n-----///----- New Staff added -----///-----");
            sc.nextLine();

        } catch (InputMismatchException error) {
            throw new InvalidInputException("Invalid input given");
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void search() throws Exception {
        try {
            System.out.print("\nEnter Staff ID to search: ");
            String StaffId = sc.nextLine();
            Staff Staff = StaffIO.searchFromFile(StaffId);
            if (Staff.getId() == null) {
                System.out.println("///=== No Staff found with this ID ===///");
            } else {
                Staff.showDetails();
            }
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void update() throws Exception {
        try {
            System.out.print("\nEnter Staff ID to update: ");
            String StaffId = sc.nextLine();
            Staff Staff = StaffIO.searchFromFile(StaffId);
            if (Staff.getId() == null) {
                System.out.println("No Staff found with this ID");
            } else {
                String StaffStr = Staff.StaffToStr();
                System.out.println("\n---- Edit Staff Information ----");

                System.out.print("Name (" + Staff.getName() + "): ");
                String name = sc.nextLine();

                System.out.print("Age (" + Staff.getAge() + "): ");
                String age = sc.nextLine();

                System.out.print("Gender (" + Staff.getGender() + "): ");
                String gender = sc.nextLine();

                System.out.print("Salary (" + Staff.getSalary() + "): ");
                String salary = sc.nextLine();

                Staff updatedStaff = new Staff(
                        Staff.getId(),
                        name == "" ? Staff.getName() : name,
                        age == "" ? Staff.getAge() : Integer.parseInt(age),
                        gender == "" ? Staff.getGender() : gender,
                        salary == "" ? Staff.getSalary() : Double.parseDouble(salary));
                String updatedStaffStr = updatedStaff.StaffToStr();
                StaffIO.updateData(StaffStr, updatedStaffStr);
                System.out.println("\n-----///----- Staff data updated -----///-----");
            }
        } catch (InputMismatchException error) {
            throw new InvalidInputException("Invalid input given");
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void delete() throws Exception {
        try {
            System.out.print("\nEnter Staff ID to delete: ");
            String StaffId = sc.nextLine();
            Staff Staff = StaffIO.searchFromFile(StaffId);
            if (Staff.getId() == null) {
                System.out.println("///=== No Staff found with this ID ===///");
            } else {
                String StaffStr = Staff.StaffToStr() + "\r\n\r\n";
                StaffIO.updateData(StaffStr, "");
                System.out.println("\n-----///----- Staff deleted -----///-----");
            }
        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }

    @Override
    public void showAll() throws Exception {
        try {

            Staff[] Staffs = StaffIO.getAllStaff();

            for (Staff Staff : Staffs) {
                Staff.showDetails();
            }

        } catch (FileReadWriteException error) {
            throw error;
        } catch (Exception error) {
            throw error;
        }
    }
}

// main function
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("\n.....Welcome to IIIT Nagpur Management System.....\n");
        System.out.println("1. Faculty Management");
        System.out.println("2. Student Management");
        System.out.println("3. Staff Management");
        System.out.print("\nSelect Option: ");

        try {
            int managementOption = sc.nextInt();

            switch (managementOption) {
                case 1:
                    FacultyManagement.runFacultyOperation();
                    break;
                case 2:
                    StudentManagement.runStudentOperation();
                    break;
                case 3:
                    StaffManagement.runStaffOperation();
                    break;
                default:
                    System.out.println("Unknown Operation");
            }

        } catch (InputMismatchException error) {

            System.out.println("\n");
            System.out.println("Error: Invalid input given");
            System.out.println("");

        } catch (InvalidInputException error) {

            System.out.println("\n");
            System.out.println("Error: " + error.toString());
            System.out.println("");

        } catch (FileReadWriteException error) {

            System.out.println("\n");
            System.out.println("Error: " + error.toString());
            System.out.println("");

        } catch (Exception error) {

            System.out.println("\n");
            System.out.println("Error: " + error.toString());
            System.out.println("");

        } finally {
            sc.close();
        }
    }
}