package com.omar.sams.Models.FillData;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.omar.sams.Models.CourseDataModel;
import com.omar.sams.Models.GroupDataModel;
import com.omar.sams.Models.ProfessorDataModel;
import com.omar.sams.Utils.AESCrypt;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddDataActivity extends AppCompatActivity {

    String TAG = "AddDataActivity";
    DatabaseReference mCoursesRef;
    String courseKey = "";
    String coursesDatabaseRef;
    ArrayList<CourseDataModel> courses;

    DatabaseReference mUserRef;
    String profKey = "";
    String profDatabaseRef;
    ArrayList<ProfessorDataModel> professors;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // first you need to run only the add courses then make sure that all courses are in the
        // database, then comment the addCourses() function then uncomment addProfData() and run
        // do not forget before run to change all the new courseID for each professor
        //you can use AI studio from google.

        //addCourses();
        addProfData();
    }

    private void addCourses() {
        courses = new ArrayList<>();
        try {
            courses = fillCoursesData();
        } catch (ParseException e) {
            Log.e(TAG, "Error Occurred" + e.getMessage());

        }


        coursesDatabaseRef = "Courses";
        mCoursesRef = FirebaseDatabase.getInstance().getReference(coursesDatabaseRef);


        for (CourseDataModel courseDataModel : courses) {
            courseKey = mCoursesRef.push().getKey();
            courseDataModel.setCourseId(courseKey);
            mCoursesRef.child(courseKey).setValue(courseDataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.e(TAG, "Done Adding Course");
                    } else {
                        String message = task.getException().getMessage();
                        Log.e(TAG, "Error Occurred" + message);
                    }
                }
            });

        }


    }


    private ArrayList<CourseDataModel> fillCoursesData() throws ParseException {

        ArrayList<CourseDataModel> list = new ArrayList<>();
        DateFormat df = new SimpleDateFormat("dd-mm-yyyy");


        // Lecture dates for Statistics 2
        ArrayList<Date> statsLecDates = new ArrayList<>();
        statsLecDates.add(df.parse("11-05-2024"));
        statsLecDates.add(df.parse("18-05-2024"));
        statsLecDates.add(df.parse("25-05-2024"));
        statsLecDates.add(df.parse("01-06-2024"));
        statsLecDates.add(df.parse("08-06-2024"));
        statsLecDates.add(df.parse("15-06-2024"));

        // Groups for Statistics 2
        ArrayList<GroupDataModel> groupsStats = new ArrayList<>();
        groupsStats.add(new GroupDataModel("Group 1", "Room 303", "9 - 11", "Saturday", statsLecDates));
        groupsStats.add(new GroupDataModel("Group 2", "Room 304", "11 - 1", "Saturday", statsLecDates));
        groupsStats.add(new GroupDataModel("Group 3", "Room 401", "1 - 3", "Saturday", statsLecDates));
        groupsStats.add(new GroupDataModel("Group 4", "Room 405", "3 - 5", "Saturday", statsLecDates));

        // Info for Statistics 2
        CourseDataModel courseStats = new CourseDataModel();
        courseStats.setSemester("Third Term");
        courseStats.setName("Statistics 2");
        courseStats.setProfName("Marwa Abd Allah");
        courseStats.setDriveLink("https://drive.google.com/drive/folders/18YjIAzQ74aSCSo4Md0ASZ9X0-dyduEeY?usp=sharing");
        courseStats.setDescription("Statistics 2 delves into hypothesis testing, estimation techniques, and explores how to draw stronger conclusions from data.");
        courseStats.setNotes("");
        courseStats.setGroups(groupsStats);
        courseStats.setProfId("YUQ98uIcdlacjnh94kO1VsaEn4T2");

        list.add(courseStats);


        ////////////////////////////////////////////////////////////another Subject


        // Lecture dates for English
        ArrayList<Date> eng3LecDates = new ArrayList<>();
        eng3LecDates.add(df.parse("11-05-2024"));
        eng3LecDates.add(df.parse("18-05-2024"));
        eng3LecDates.add(df.parse("25-05-2024"));
        eng3LecDates.add(df.parse("01-06-2024"));
        eng3LecDates.add(df.parse("08-06-2024"));
        eng3LecDates.add(df.parse("15-06-2024"));

        // Groups for English
        ArrayList<GroupDataModel> groupsEng3 = new ArrayList<>();
        groupsEng3.add(new GroupDataModel("Group 1", "Room 410", "11 - 1", "Saturday", eng3LecDates));
        groupsEng3.add(new GroupDataModel("Group 2", "Room 411", "9 - 11", "Saturday", eng3LecDates));
        groupsEng3.add(new GroupDataModel("Group 3", "Room 412", "1 - 3", "Saturday", eng3LecDates));
        groupsEng3.add(new GroupDataModel("Group 4", "Room 413", "3 - 5", "Saturday", eng3LecDates));

        // Info for English
        CourseDataModel courseEng = new CourseDataModel();
        courseEng.setSemester("Third Term");
        courseEng.setName("English 3");
        courseEng.setProfName("Ali Bedawy");
        courseEng.setDriveLink("https://drive.google.com/drive/folders/1fjrEm-M_4Kbc5g2S9WIAj7auoOCRdgs2?usp=sharing");
        courseEng.setDescription("English courses boost your grammar, vocabulary, and speaking fluency, making you a more confident communicator. There's a course for every skill level!");
        courseEng.setNotes("");
        courseEng.setGroups(groupsEng3);
        courseEng.setProfId("kja6oaUyAyc3JLqWrDAJu7GkBky2");

        list.add(courseEng);

        ///////////////////////////////////////////////////////////// another subject


        // Lecture dates for Money & Banking
        ArrayList<Date> mbLecDates = new ArrayList<>();
        mbLecDates.add(df.parse("11-05-2024"));
        mbLecDates.add(df.parse("18-05-2024"));
        mbLecDates.add(df.parse("25-05-2024"));
        mbLecDates.add(df.parse("01-06-2024"));
        mbLecDates.add(df.parse("08-06-2024"));
        mbLecDates.add(df.parse("15-06-2024"));

        // Groups for Money & Banking
        ArrayList<GroupDataModel> groupsMB = new ArrayList<>();
        groupsMB.add(new GroupDataModel("Group 1", "Room 422", "1 - 3", "Saturday", mbLecDates));
        groupsMB.add(new GroupDataModel("Group 2", "Room 423", "9 - 11", "Saturday", mbLecDates));
        groupsMB.add(new GroupDataModel("Group 3", "Room 424", "11 - 1", "Saturday", mbLecDates));
        groupsMB.add(new GroupDataModel("Group 4", "Room 425", "3 - 5", "Saturday", mbLecDates));

        // Info for Money & Banking
        CourseDataModel courseMB = new CourseDataModel();
        courseMB.setSemester("Third Term");
        courseMB.setName("Money & Banking");
        courseMB.setProfName("Ola Shousha");
        courseMB.setDriveLink("https://drive.google.com/drive/folders/1Cul9oY7cKyyI-UMx8NqYwfVrHI5sVSGM?usp=sharing");
        courseMB.setDescription("A money & banking course unveils the workings of the financial system, explaining how banks create money, interest rates function, and how it all affects the economy.");
        courseMB.setNotes("");
        courseMB.setGroups(groupsMB);
        courseMB.setProfId("lhT6NuwOGPatdCTnSHPjYNBhlS62");


        list.add(courseMB);

        //////////////////////////////////////////////// another one

        // Lecture dates for Business Law
        ArrayList<Date> blLecDates = new ArrayList<>();
        blLecDates.add(df.parse("27-11-2024"));
        blLecDates.add(df.parse("03-11-2024"));
        blLecDates.add(df.parse("10-11-2024"));
        blLecDates.add(df.parse("17-11-2024"));
        blLecDates.add(df.parse("24-11-2024"));
        blLecDates.add(df.parse("01-12-2024"));

        // Groups for Business Law
        ArrayList<GroupDataModel> groupsBL = new ArrayList<>();
        groupsBL.add(new GroupDataModel("Group 1", "Room 310", "9 - 11", "Sunday", blLecDates));
        groupsBL.add(new GroupDataModel("Group 2", "Room 311", "11 - 1", "Sunday", blLecDates));
        groupsBL.add(new GroupDataModel("Group 3", "Room 312", "1 - 3", "Sunday", blLecDates));
        groupsBL.add(new GroupDataModel("Group 4", "Room 313", "3 - 5", "Sunday", blLecDates));

        // Info for Business Law
        CourseDataModel courseBL = new CourseDataModel();
        courseBL.setSemester("Third Term");
        courseBL.setName("Business Law");
        courseBL.setProfName("Mostafa Hassan");
        courseBL.setDriveLink("https://drive.google.com/drive/folders/1NCMaUyR48imjLdmQIffpp5u-CP0WoJhO?usp=sharing");
        courseBL.setDescription("Business law teaches you the legal side of running a business, from contracts to copyrights. It helps you avoid trouble and make smart choices.");
        courseBL.setNotes("");
        courseBL.setGroups(groupsBL);
        courseBL.setProfId("d15LObniuWOVfkEGKSnEl9pL1633");


        list.add(courseBL);

        //////////////////////////////////////////////// another one

        // Lecture dates for Operation Management
        ArrayList<Date> omLecDates = new ArrayList<>();
        omLecDates.add(df.parse("27-11-2024"));
        omLecDates.add(df.parse("03-11-2024"));
        omLecDates.add(df.parse("10-11-2024"));
        omLecDates.add(df.parse("17-11-2024"));
        omLecDates.add(df.parse("24-11-2024"));
        omLecDates.add(df.parse("01-12-2024"));

        // Groups for Operation Management
        ArrayList<GroupDataModel> groupsOM = new ArrayList<>();
        groupsOM.add(new GroupDataModel("Group 1", "Room 406", "1 - 3", "Sunday", omLecDates));
        groupsOM.add(new GroupDataModel("Group 2", "Room 407", "9 - 11", "Sunday", omLecDates));
        groupsOM.add(new GroupDataModel("Group 3", "Room 408", "11 - 1", "Sunday", omLecDates));
        groupsOM.add(new GroupDataModel("Group 4", "Room 409", "3 - 5", "Sunday", omLecDates));

        // Info for Operation Management
        CourseDataModel courseOM = new CourseDataModel();
        courseOM.setSemester("Third Term");
        courseOM.setName("Operation Management");
        courseOM.setProfName("Arwa Mohamed");
        courseOM.setDriveLink("https://drive.google.com/drive/folders/1k_WvgLd4oH6ptNSIgn3h1FRZLTMgNaIQ?usp=sharing");
        courseOM.setDescription("Operations management teaches you to streamline business operations, boosting efficiency from start to finish. It's about running things like a well-oiled machine.");
        courseOM.setNotes("");
        courseOM.setGroups(groupsOM);
        courseOM.setProfId("iTPe8EnkYaUNwlfMnYftOrWVDHL2");

        list.add(courseOM);


        ///////////////////// another one

        // Lecture dates for Principles of Marketing
        ArrayList<Date> pomLecDates = new ArrayList<>();
        pomLecDates.add(df.parse("08-05-2024"));
        pomLecDates.add(df.parse("15-05-2024"));
        pomLecDates.add(df.parse("22-05-2024"));
        pomLecDates.add(df.parse("29-05-2024"));
        pomLecDates.add(df.parse("05-06-2024"));
        pomLecDates.add(df.parse("12-06-2024"));

        // Groups for Principles of Marketing
        ArrayList<GroupDataModel> groupsPOM = new ArrayList<>();
        groupsPOM.add(new GroupDataModel("Group 1", "Room 410", "9 - 12", "Wednesday", pomLecDates));
        groupsPOM.add(new GroupDataModel("Group 2", "Room 411", "1 - 4", "Wednesday", pomLecDates));
        groupsPOM.add(new GroupDataModel("Group 3", "Room 412", "4 - 7", "Wednesday", pomLecDates));
        groupsPOM.add(new GroupDataModel("Group 4", "Room 413", "7 - 10", "Wednesday", pomLecDates));

        // Info for Principles of Marketing
        CourseDataModel coursePOM = new CourseDataModel();
        coursePOM.setSemester("Third Term");
        coursePOM.setName("Principles of Marketing");
        coursePOM.setProfName("Ahmed Samir");
        coursePOM.setDriveLink("https://drive.google.com/drive/folders/1kwvbIQYmMsHglJgVuuZGAUoetQEcJLjn?usp=sharing");
        coursePOM.setDescription("Principles of Marketing teaches you the consumer ropes: what they want, how to win them over, and how to get your product in their hands.");
        coursePOM.setNotes("");
        coursePOM.setGroups(groupsPOM);
        coursePOM.setProfId("97DN7JAJpZOv7STYIBr1qV1AaLd2");


        list.add(coursePOM);

        ///////////////////////////////////////////////////another one


        // Lecture dates for Management Information System
        ArrayList<Date> misLecDates = new ArrayList<>();
        misLecDates.add(df.parse("08-05-2024"));
        misLecDates.add(df.parse("15-05-2024"));
        misLecDates.add(df.parse("22-05-2024"));
        misLecDates.add(df.parse("29-05-2024"));
        misLecDates.add(df.parse("05-06-2024"));
        misLecDates.add(df.parse("12-06-2024"));

        //Groups for Management Information System
        ArrayList<GroupDataModel> groupsMIS = new ArrayList<>();
        groupsMIS.add(new GroupDataModel("Group 1", "Room 301", "9 - 11", "Wednesday", misLecDates));
        groupsMIS.add(new GroupDataModel("Group 2", "Room 305", "11 - 1", "Wednesday", misLecDates));
        groupsMIS.add(new GroupDataModel("Group 3", "Room 402", "1 - 3", "Wednesday", misLecDates));
        groupsMIS.add(new GroupDataModel("Group 4", "Room 407", "3 - 5", "Wednesday", misLecDates));

        // Info for Management Information System
        CourseDataModel courseMIS = new CourseDataModel();
        courseMIS.setSemester("Third Term");
        courseMIS.setName("Management Information System");
        courseMIS.setProfName("Heba Sabri");
        courseMIS.setDriveLink("https://drive.google.com/drive/folders/1buEvZVlo-_A5eVgaqeelfV9n7zEmiNku?usp=sharing");
        courseMIS.setDescription("MIS course dives into how businesses leverage technology to manage information and make data-driven choices.");
        courseMIS.setNotes("");
        courseMIS.setGroups(groupsMIS);
        courseMIS.setProfId("PS96elZLVacbXi7dSujg3NbooGG3");

        list.add(courseMIS);

        ////////////////////////////////////////////////////////////another Subject

        // Lecture dates for Scientific Research Methods
        ArrayList<Date> srmLecDates = new ArrayList<>();
        srmLecDates.add(df.parse("08-05-2024"));
        srmLecDates.add(df.parse("15-05-2024"));
        srmLecDates.add(df.parse("22-05-2024"));
        srmLecDates.add(df.parse("29-05-2024"));
        srmLecDates.add(df.parse("05-06-2024"));
        srmLecDates.add(df.parse("12-06-2024"));

        // Groups for Scientific Research Methods
        ArrayList<GroupDataModel> groupsSRM = new ArrayList<>();
        groupsSRM.add(new GroupDataModel("Group 1", "Programs' Center", "8 - 10", "Wednesday", srmLecDates));
        groupsSRM.add(new GroupDataModel("Group 2", "Programs' Center", "10 - 12", "Wednesday", srmLecDates));
        groupsSRM.add(new GroupDataModel("Group 3", "Programs' Center", "12 - 2", "Wednesday", srmLecDates));
        groupsSRM.add(new GroupDataModel("Group 4", "Programs' Center", "2 - 4", "Wednesday", srmLecDates));

        // Info for Scientific Research Methods
        CourseDataModel courseSRM = new CourseDataModel();
        courseSRM.setSemester("Fourth Term");
        courseSRM.setName("Scientific Research Methods");
        courseSRM.setProfName("Marwa Abd El Aziz");
        courseSRM.setDriveLink("https://drive.google.com/drive/folders/1qrgXyZ6zdk9ODx6XccapLtzJdMuwr67Y?usp=sharing");
        courseSRM.setDescription("Learn to ask scientific questions, gather evidence, and analyze it - that's what a Scientific Research Methods course teaches.");
        courseSRM.setNotes("");
        courseSRM.setGroups(groupsSRM);
        courseSRM.setProfId("Px75kWiqzrUMGeZ8m4wUzehTJ1j2");

        list.add(courseSRM);


        //////////////////////////////// another one.

        // Lecture dates for Principles of Finance
        ArrayList<Date> pofLecDates = new ArrayList<>();
        pofLecDates.add(df.parse("27-11-2024"));
        pofLecDates.add(df.parse("03-11-2024"));
        pofLecDates.add(df.parse("10-11-2024"));
        pofLecDates.add(df.parse("17-11-2024"));
        pofLecDates.add(df.parse("24-11-2024"));
        pofLecDates.add(df.parse("01-12-2024"));

        // Groups for Principles of Finance
        ArrayList<GroupDataModel> groupsPOF = new ArrayList<>();
        groupsPOF.add(new GroupDataModel("Group 1", "Room 208", "10 - 12", "Sunday", pofLecDates));
        groupsPOF.add(new GroupDataModel("Group 2", "Room 209", "12 - 2", "Sunday", pofLecDates));
        groupsPOF.add(new GroupDataModel("Group 3", "Room 210", "2 - 4", "Sunday", pofLecDates));
        groupsPOF.add(new GroupDataModel("Group 4", "Room 211", "4 - 6", "Sunday", pofLecDates));

        // Info for Principles of Finance
        CourseDataModel coursePOF = new CourseDataModel();
        coursePOF.setSemester("Fourth Term");
        coursePOF.setName("Principles of Finance");
        coursePOF.setProfName("Mohamed Sameh");
        coursePOF.setDriveLink("https://drive.google.com/drive/folders/1RsRprbN-ndRZE2E6xkf5dyGc4sNSp5CL?usp=sharing");
        coursePOF.setDescription("Principles of Finance teaches you to value investments, assess risk, and make smart financial decisions - for you or your business.");
        coursePOF.setNotes("");
        coursePOF.setGroups(groupsPOF);
        coursePOF.setProfId("y9w9fe97BpOG3QuDY6UmOIdTaQc2");

        list.add(coursePOF);

        ////////////////////////////////////////////////////////////another Subject


        // Lecture dates for Programming
        ArrayList<Date> progLecDates = new ArrayList<>();
        progLecDates.add(df.parse("27-11-2024"));
        progLecDates.add(df.parse("03-11-2024"));
        progLecDates.add(df.parse("10-11-2024"));
        progLecDates.add(df.parse("17-11-2024"));
        progLecDates.add(df.parse("24-11-2024"));
        progLecDates.add(df.parse("01-12-2024"));

        // Groups for Programming
        ArrayList<GroupDataModel> groupsProg = new ArrayList<>();
        groupsProg.add(new GroupDataModel("Group 1", "Programs' Center", "9 - 11", "Sunday", progLecDates));
        groupsProg.add(new GroupDataModel("Group 2", "Programs' Center", "11 - 1", "Sunday", progLecDates));
        groupsProg.add(new GroupDataModel("Group 3", "Programs' Center", "1 - 3", "Sunday", progLecDates));
        groupsProg.add(new GroupDataModel("Group 4", "Programs' Center", "3 - 5", "Sunday", progLecDates));

        // Info for Programming
        CourseDataModel courseProg = new CourseDataModel();
        courseProg.setSemester("Fourth Term");
        courseProg.setName("Programming");
        courseProg.setProfName("Ghada Mostafa");
        courseProg.setDriveLink("https://drive.google.com/drive/folders/1XBvUN3eI19tcrt8x9hPUsNpv15elf03h?usp=sharing");
        courseProg.setDescription("Learn C++ to build powerful software, mastering basics like variables, loops, and object-oriented design. It's used in games, science, and more.");
        courseProg.setNotes("");
        courseProg.setGroups(groupsProg);
        courseProg.setProfId("S4uLLXRRTze9pemKDy3p6NdH5qX2");

        list.add(courseProg);


        ////////////////////////////////////////////////////////////another Subject


        // Lecture dates for Human Resources Management
        ArrayList<Date> hrmLecDates = new ArrayList<>();
        hrmLecDates.add(df.parse("28-11-2024"));
        hrmLecDates.add(df.parse("04-11-2024"));
        hrmLecDates.add(df.parse("11-11-2024"));
        hrmLecDates.add(df.parse("18-11-2024"));
        hrmLecDates.add(df.parse("25-11-2024"));
        hrmLecDates.add(df.parse("02-11-2024"));

        // Groups for Human Resources Management
        ArrayList<GroupDataModel> groupsHRM = new ArrayList<>();
        groupsHRM.add(new GroupDataModel("Group 1", "Room 105", "3 - 6", "Monday", hrmLecDates));
        groupsHRM.add(new GroupDataModel("Group 2", "Room 106", "6 - 9", "Monday", hrmLecDates));
        groupsHRM.add(new GroupDataModel("Group 3", "Room 107", "9 - 12", "Monday", hrmLecDates));
        groupsHRM.add(new GroupDataModel("Group 4", "Room 108", "12 - 3", "Monday", hrmLecDates));

        // Info for Human Resources Management
        CourseDataModel courseHRM = new CourseDataModel();
        courseHRM.setSemester("Fourth Term");
        courseHRM.setName("Human Resources Management");
        courseHRM.setProfName("Fawzi Madkour");
        courseHRM.setDriveLink("https://drive.google.com/drive/folders/1kUZbXA2eQHYVQDWZQzewq9mvDUCWGTKV?usp=sharing");
        courseHRM.setDescription("A Human Resources Management course teaches you the skills to recruit, train, and manage employees, ensuring a smooth-running and happy workplace.");
        courseHRM.setNotes("");
        courseHRM.setGroups(groupsHRM);
        courseHRM.setProfId("tXcWTQKWtAPJA0VmHaYj8nRNzZ32");

        list.add(courseHRM);


        ////////////////////////////////////////////////////////////another Subject


        // Lecture dates for Enterprise Application
        ArrayList<Date> entAppLecDates = new ArrayList<>();
        entAppLecDates.add(df.parse("28-11-2024"));
        entAppLecDates.add(df.parse("04-11-2024"));
        entAppLecDates.add(df.parse("11-11-2024"));
        entAppLecDates.add(df.parse("18-11-2024"));
        entAppLecDates.add(df.parse("25-11-2024"));
        entAppLecDates.add(df.parse("02-11-2024"));

        // Groups for Enterprise Application
        ArrayList<GroupDataModel> groupsEntApp = new ArrayList<>();
        groupsEntApp.add(new GroupDataModel("Group 1", "Room 402", "9 - 11", "Monday", entAppLecDates));
        groupsEntApp.add(new GroupDataModel("Group 2", "Room 402", "11 - 1", "Monday", entAppLecDates));
        groupsEntApp.add(new GroupDataModel("Group 3", "Room 406", "1 - 3", "Monday", entAppLecDates));
        groupsEntApp.add(new GroupDataModel("Group 4", "Room 406", "3 - 5", "Monday", entAppLecDates));

        // Info for Enterprise Application
        CourseDataModel courseEntApp = new CourseDataModel();
        courseEntApp.setSemester("Fourth Term");
        courseEntApp.setName("Enterprise Application");
        courseEntApp.setProfName("Lamia Al Adel");
        courseEntApp.setDriveLink("https://drive.google.com/drive/folders/1ib2fqPqY8hIvRC7aXNkLmP2FwM-EvjZo?usp=sharing");
        courseEntApp.setDescription("Learn to design, develop, and deploy complex applications for large businesses.");
        courseEntApp.setNotes("");
        courseEntApp.setGroups(groupsEntApp);
        courseEntApp.setProfId("Jjb0UHgECVb78PV10wDhjNxwAC02");

        list.add(courseEntApp);


        ///////////////////////////////////// another one

        // Lecture dates for Discrete Math
        ArrayList<Date> dmLecDates = new ArrayList<>();
        dmLecDates.add(df.parse("11-05-2024"));
        dmLecDates.add(df.parse("18-05-2024"));
        dmLecDates.add(df.parse("25-05-2024"));
        dmLecDates.add(df.parse("01-06-2024"));
        dmLecDates.add(df.parse("08-06-2024"));
        dmLecDates.add(df.parse("15-06-2024"));

        // Groups for Discrete Math
        ArrayList<GroupDataModel> groupsDM = new ArrayList<>();
        groupsDM.add(new GroupDataModel("Group 1", "Room 305", "12 - 2", "Saturday", dmLecDates));
        groupsDM.add(new GroupDataModel("Group 2", "Room 306", "2 - 4", "Saturday", dmLecDates));
        groupsDM.add(new GroupDataModel("Group 3", "Room 307", "4 - 6", "Saturday", dmLecDates));
        groupsDM.add(new GroupDataModel("Group 4", "Room 308", "6 - 8", "Saturday", dmLecDates));

        // Info for Discrete Math
        CourseDataModel courseDM = new CourseDataModel();
        courseDM.setSemester("Fourth Term");
        courseDM.setName("Discrete Math");
        courseDM.setProfName("Omar Khalid");
        courseDM.setDriveLink("https://drive.google.com/drive/folders/1jDkYI_jeiPXu468Bw9n6Uy7m2QvuhPtj?usp=sharing");
        courseDM.setDescription("A Discrete Math course dives into the mathematics behind computer science, equipping you with tools for counting, logic, and problem-solving in the digital world.");
        courseDM.setNotes("");
        courseDM.setGroups(groupsDM);
        courseDM.setProfId("GL9VVq8Y7VcK2lspWK4FUbbTzeZ2");

        list.add(courseDM);


        ////////////////////////////////////// another one


        // Lecture dates for English (4)
        ArrayList<Date> eng4LecDates = new ArrayList<>();
        eng4LecDates.add(df.parse("28-11-2024"));
        eng4LecDates.add(df.parse("04-11-2024"));
        eng4LecDates.add(df.parse("11-11-2024"));
        eng4LecDates.add(df.parse("18-11-2024"));
        eng4LecDates.add(df.parse("25-11-2024"));
        eng4LecDates.add(df.parse("02-11-2024"));

        // Groups for English (4)
        ArrayList<GroupDataModel> groupsEng4 = new ArrayList<>();
        groupsEng4.add(new GroupDataModel("Group 1", "Hall 3", "2 - 4", "Monday", eng4LecDates));
        groupsEng4.add(new GroupDataModel("Group 2", "Hall 4", "4 - 6", "Monday", eng4LecDates));
        groupsEng4.add(new GroupDataModel("Group 3", "Hall 5", "6 - 8", "Monday", eng4LecDates));
        groupsEng4.add(new GroupDataModel("Group 4", "Hall 6", "8 - 10", "Monday", eng4LecDates));

        // Info for English (4)
        CourseDataModel courseEng4 = new CourseDataModel();
        courseEng4.setSemester("Fourth Term");
        courseEng4.setName("English (4)");
        courseEng4.setProfName("Suzan Amen");
        courseEng4.setDriveLink("https://drive.google.com/drive/folders/1ZM3aFqUy5g5JbFld1xUP5qGT7HTpvhzh?usp=sharing");
        courseEng4.setDescription("English 4 analyzes literature across genres and history, while also strengthening writing and grammar.");
        courseEng4.setNotes("");
        courseEng4.setGroups(groupsEng4);
        courseEng4.setProfId("EiFZSPqfdvMeNuMNXHYDsfq5B7x2");

        list.add(courseEng4);


        return list;
    }


    private void addProfData() {
        professors = new ArrayList<>();

        professors = fillProfessorsData();

        profDatabaseRef = "Users";
        mUserRef = FirebaseDatabase.getInstance().getReference(profDatabaseRef);


        for (ProfessorDataModel professorDataModel : professors) {
            profKey = professorDataModel.getProfId();
            mUserRef.child(profKey).setValue(professorDataModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Log.e(TAG, "Done Adding Professor");
                    } else {
                        String message = task.getException().getMessage();
                        Log.e(TAG, "Error Occurred" + message);
                    }
                }
            });
        }
    }

    private ArrayList<ProfessorDataModel> fillProfessorsData() {
        ArrayList<ProfessorDataModel> list = new ArrayList<>();


        String password;
        try {
            password = AESCrypt.encrypt("prof@123");
        } catch (Exception e) {
            password = "prof@123";
        }

        // Professor 1
        ProfessorDataModel prof_1 = new ProfessorDataModel();
        prof_1.setProfId("YUQ98uIcdlacjnh94kO1VsaEn4T2");
        prof_1.setName("Marwa Abd Allah");
        prof_1.setEmail("marwa_abd_allah@sams.edu.eg");
        prof_1.setPassword(password);
        prof_1.setCourseId("-NxuY4qFQMQiZJD_DEnx"); // Statistics 2
        list.add(prof_1);

        // Professor 2
        ProfessorDataModel prof_2 = new ProfessorDataModel();
        prof_2.setProfId("kja6oaUyAyc3JLqWrDAJu7GkBky2");
        prof_2.setName("Ali Bedawy");
        prof_2.setEmail("ali_bedawy@sams.edu.eg");
        prof_2.setPassword(password);
        prof_2.setCourseId("-NxuY4re4P4wOp3sS-ap"); // English 3
        list.add(prof_2);

        // Professor 3
        ProfessorDataModel prof_3 = new ProfessorDataModel();
        prof_3.setProfId("lhT6NuwOGPatdCTnSHPjYNBhlS62");
        prof_3.setName("Ola Shousha");
        prof_3.setEmail("ola_shousha@sams.edu.eg");
        prof_3.setPassword(password);
        prof_3.setCourseId("-NxuY4sGYOL8cp0waLt6"); // Money & Banking
        list.add(prof_3);

        // Professor 4
        ProfessorDataModel prof_4 = new ProfessorDataModel();
        prof_4.setProfId("d15LObniuWOVfkEGKSnEl9pL1633");
        prof_4.setName("Mostafa Hassan");
        prof_4.setEmail("mostafa_hassan@sams.edu.eg");
        prof_4.setPassword(password);
        prof_4.setCourseId("-NxuY4sd6so_2oyUq_eA"); // Business Law
        list.add(prof_4);

        // Professor 5
        ProfessorDataModel prof_5 = new ProfessorDataModel();
        prof_5.setProfId("iTPe8EnkYaUNwlfMnYftOrWVDHL2");
        prof_5.setName("Arwa Mohamed");
        prof_5.setEmail("arwa_mohamed@sams.edu.eg");
        prof_5.setPassword(password);
        prof_5.setCourseId("-NxuY4t3zJ90HwPrbj5v"); // Operation Management
        list.add(prof_5);

        // Professor 6
        ProfessorDataModel prof_6 = new ProfessorDataModel();
        prof_6.setProfId("97DN7JAJpZOv7STYIBr1qV1AaLd2");
        prof_6.setName("Ahmed Samir");
        prof_6.setEmail("ahmed_samir@sams.edu.eg");
        prof_6.setPassword(password);
        prof_6.setCourseId("-NxuY4tNF4VFzpYMLYDs"); // Principles of Marketing
        list.add(prof_6);

        // Professor 7
        ProfessorDataModel prof_7 = new ProfessorDataModel();
        prof_7.setProfId("PS96elZLVacbXi7dSujg3NbooGG3");
        prof_7.setName("Heba Sabri");
        prof_7.setEmail("heba_sabri@sams.edu.eg");
        prof_7.setPassword(password);
        prof_7.setCourseId("-NxuY4u-P7PoN8m-cwxG"); // Management Information System
        list.add(prof_7);

        // Professor 8
        ProfessorDataModel prof_8 = new ProfessorDataModel();
        prof_8.setProfId("Px75kWiqzrUMGeZ8m4wUzehTJ1j2");
        prof_8.setName("Marwa Abd El Aziz");
        prof_8.setEmail("marwa_abd_el_aziz@sams.edu.eg");
        prof_8.setPassword(password);
        prof_8.setCourseId("-NxuY4uHxVu6olVOd322"); // Scientific Research Methods
        list.add(prof_8);

        // Professor 9
        ProfessorDataModel prof_9 = new ProfessorDataModel();
        prof_9.setProfId("y9w9fe97BpOG3QuDY6UmOIdTaQc2");
        prof_9.setName("Mohamed Sameh");
        prof_9.setEmail("mohamed_sameh@sams.edu.eg");
        prof_9.setPassword(password);
        prof_9.setCourseId("-NxuY4uZM0QOAnZI0wXr"); // Principles of Finance
        list.add(prof_9);

        // Professor 10
        ProfessorDataModel prof_10 = new ProfessorDataModel();
        prof_10.setProfId("S4uLLXRRTze9pemKDy3p6NdH5qX2");
        prof_10.setName("Ghada Mostafa");
        prof_10.setEmail("ghada_mostafa@sams.edu.eg");
        prof_10.setPassword(password);
        prof_10.setCourseId("-NxuY4uoENSFS3TsveKA"); // Programming
        list.add(prof_10);

        // Professor 11
        ProfessorDataModel prof_11 = new ProfessorDataModel();
        prof_11.setProfId("Jjb0UHgECVb78PV10wDhjNxwAC02");
        prof_11.setName("Lamia Al Adel");
        prof_11.setEmail("lamia_al_adel@sams.edu.eg");
        prof_11.setPassword(password);
        prof_11.setCourseId("-NxuY4vK_65z5TEaY_bL"); // Enterprise Application
        list.add(prof_11);

        // Professor 12
        ProfessorDataModel prof_12 = new ProfessorDataModel();
        prof_12.setProfId("EiFZSPqfdvMeNuMNXHYDsfq5B7x2");
        prof_12.setName("Suzan Amen");
        prof_12.setEmail("suzan_amen@sams.edu.eg");
        prof_12.setPassword(password);
        prof_12.setCourseId("-NxuY4vo3Ps9i-wtQ4a1"); // English (4)
        list.add(prof_12);

        // Professor 13
        ProfessorDataModel prof_13 = new ProfessorDataModel();
        prof_13.setProfId("GL9VVq8Y7VcK2lspWK4FUbbTzeZ2");
        prof_13.setName("Omar Khalid");
        prof_13.setEmail("omar_khalid@sams.edu.eg");
        prof_13.setPassword(password);
        prof_13.setCourseId("-NxuY4vbO1TDy7l2Hr48"); // Discrete Math
        list.add(prof_13);

        // Professor 14
        ProfessorDataModel prof_14 = new ProfessorDataModel();
        prof_14.setProfId("tXcWTQKWtAPJA0VmHaYj8nRNzZ32");
        prof_14.setName("Fawzi Madkour");
        prof_14.setEmail("fawzi_madkour@sams.edu.eg");
        prof_14.setPassword(password);
        prof_14.setCourseId("-NxuY4v5MwlwZb-yRRNe"); // Human Resources Management
        list.add(prof_14);

        return list;
    }

}