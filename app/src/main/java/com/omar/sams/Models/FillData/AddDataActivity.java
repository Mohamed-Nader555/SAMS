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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AddDataActivity extends AppCompatActivity {

    String TAG = "AddDataActivity";
    Date date = new Date();
    DatabaseReference mCoursesRef;
    String courseKey = "";
    String databaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addCourses();
    }


    private void addCourses() {
        ArrayList<CourseDataModel> courses = new ArrayList<>();

        try {
            courses = fillCoursesData();
        } catch (ParseException e) {
            Log.e(TAG, "Error Occurred" + e.getMessage());

        }


        databaseRef = "Courses";
        mCoursesRef = FirebaseDatabase.getInstance().getReference(databaseRef);


        for (CourseDataModel courseDataModel : courses) {

            courseKey = mCoursesRef.push().getKey();
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
        groupsEng3.add(new GroupDataModel("Group 1", "R 410", "11 - 1", "Saturday", eng3LecDates));
        groupsEng3.add(new GroupDataModel("Group 2", "R 411", "9 - 11", "Saturday", eng3LecDates));
        groupsEng3.add(new GroupDataModel("Group 3", "R 412", "1 - 3", "Saturday", eng3LecDates));
        groupsEng3.add(new GroupDataModel("Group 4", "R 413", "3 - 5", "Saturday", eng3LecDates));

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
        groupsMB.add(new GroupDataModel("Group 1", "R 422", "1 - 3", "Saturday", mbLecDates));
        groupsMB.add(new GroupDataModel("Group 2", "R 423", "9 - 11", "Saturday", mbLecDates));
        groupsMB.add(new GroupDataModel("Group 3", "R 424", "11 - 1", "Saturday", mbLecDates));
        groupsMB.add(new GroupDataModel("Group 4", "R 425", "3 - 5", "Saturday", mbLecDates));

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
        groupsBL.add(new GroupDataModel("Group 1", "R 310", "9 - 11", "Sunday", blLecDates));
        groupsBL.add(new GroupDataModel("Group 2", "R 311", "11 - 1", "Sunday", blLecDates));
        groupsBL.add(new GroupDataModel("Group 3", "R 312", "1 - 3", "Sunday", blLecDates));
        groupsBL.add(new GroupDataModel("Group 4", "R 313", "3 - 5", "Sunday", blLecDates));

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
        groupsOM.add(new GroupDataModel("Group 1", "R 406", "1 - 3", "Sunday", omLecDates));
        groupsOM.add(new GroupDataModel("Group 2", "R 407", "9 - 11", "Sunday", omLecDates));
        groupsOM.add(new GroupDataModel("Group 3", "R 408", "11 - 1", "Sunday", omLecDates));
        groupsOM.add(new GroupDataModel("Group 4", "R 409", "3 - 5", "Sunday", omLecDates));

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
        groupsPOM.add(new GroupDataModel("Group 1", "R 410", "9 - 12", "Wednesday", pomLecDates));
        groupsPOM.add(new GroupDataModel("Group 2", "R 411", "1 - 4", "Wednesday", pomLecDates));
        groupsPOM.add(new GroupDataModel("Group 3", "R 412", "4 - 7", "Wednesday", pomLecDates));
        groupsPOM.add(new GroupDataModel("Group 4", "R 413", "7 - 10", "Wednesday", pomLecDates));

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
        groupsPOF.add(new GroupDataModel("Group 1", "R 208", "10 - 12", "Sunday", pofLecDates));
        groupsPOF.add(new GroupDataModel("Group 2", "R 209", "12 - 2", "Sunday", pofLecDates));
        groupsPOF.add(new GroupDataModel("Group 3", "R 210", "2 - 4", "Sunday", pofLecDates));
        groupsPOF.add(new GroupDataModel("Group 4", "R 211", "4 - 6", "Sunday", pofLecDates));

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
        groupsHRM.add(new GroupDataModel("Group 1", "R 105", "3 - 6", "Monday", hrmLecDates));
        groupsHRM.add(new GroupDataModel("Group 2", "R 106", "6 - 9", "Monday", hrmLecDates));
        groupsHRM.add(new GroupDataModel("Group 3", "R 107", "9 - 12", "Monday", hrmLecDates));
        groupsHRM.add(new GroupDataModel("Group 4", "R 108", "12 - 3", "Monday", hrmLecDates));

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
        groupsEntApp.add(new GroupDataModel("Group 1", "R 402", "9 - 11", "Monday", entAppLecDates));
        groupsEntApp.add(new GroupDataModel("Group 2", "R 402", "11 - 1", "Monday", entAppLecDates));
        groupsEntApp.add(new GroupDataModel("Group 3", "R 406", "1 - 3", "Monday", entAppLecDates));
        groupsEntApp.add(new GroupDataModel("Group 4", "R 406", "3 - 5", "Monday", entAppLecDates));

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
        groupsDM.add(new GroupDataModel("Group 1", "R 305", "12 - 2", "Saturday", dmLecDates));
        groupsDM.add(new GroupDataModel("Group 2", "R 306", "2 - 4", "Saturday", dmLecDates));
        groupsDM.add(new GroupDataModel("Group 3", "R 307", "4 - 6", "Saturday", dmLecDates));
        groupsDM.add(new GroupDataModel("Group 4", "R 308", "6 - 8", "Saturday", dmLecDates));

        // Info for Discrete Math
        CourseDataModel courseDM = new CourseDataModel();
        courseDM.setSemester("Fourth Term");
        courseDM.setName("Discrete Math");
        courseDM.setProfName("Marwa Abd Allah");
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


}