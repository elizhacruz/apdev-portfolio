package com.cruz.studentinformationsystem;
import android.app.Activity;
import android.app.AlertDialog;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
public class MainActivity extends Activity {
    // Colors
    private final int NAVY = Color.rgb(23, 59, 112);
    private final int BLUE = Color.rgb(40, 93, 170);
    private final int YELLOW = Color.rgb(245, 197, 66);
    private final int BACKGROUND = Color.rgb(243, 246, 252);
    private final int GRAY = Color.rgb(110, 120, 135);
    // Main layouts
    private LinearLayout loginLayout;
    private LinearLayout dashboardLayout;
    private LinearLayout studentListContainer;
    // Login
    private EditText etUsername;
    private EditText etPassword;
    // Student form
    private EditText etStudentNo;
    private EditText etLastName;
    private EditText etFirstName;
    private EditText etMiddleName;
    private Spinner spProgram;
    private Spinner spYearLevel;
    private TextView tvCollege;
    private TextView tvFormTitle;
    private Button btnSave;
    // Dashboard
    private TextView tvTotalStudents;
    private EditText etSearch;
    // Data
    private final ArrayList<Student> students = new ArrayList<>();
    private int editingPosition = -1;
    // NU Programs
    private final String[] programs = {
            "BS Nursing",
            "BS Medical Technology / Medical Laboratory Science",
            "BS Pharmacy",
            "BS Environmental Planning",
            "BS Architecture",
            "BS Accountancy",
            "BS Management Accounting",
            "BS Business Administration - Financial Management",
            "BS Business Administration - Marketing Management",
            "BS Business Administration - Human Resource Management",
            "BS Computer Science - Digital Forensics",
            "BS Computer Science - Machine Learning",
            "BS Information Technology - Mobile and Web Applications",
            "BS Information Technology - Multimedia Arts and Animation",
            "Associate in Computer Technology",
            "AB English Language Studies",
            "AB Communication",
            "AB Political Science",
            "BS Psychology",
            "Bachelor of Elementary Education",
            "Bachelor of Secondary Education - English",
            "Bachelor of Physical Education",
            "Certificate in Professional Education",
            "BS Civil Engineering",
            "BS Computer Engineering",
            "BS Electrical Engineering",
            "BS Electronics Engineering",
            "BS Mechanical Engineering",
            "BS Environmental and Sanitary Engineering",
            "BS Hospitality Management",
            "BS Tourism Management"
    };
    private final String[] colleges = {
            "College of Allied Health",
            "College of Allied Health",
            "College of Allied Health",
            "College of Architecture, Planning, and Sustainable Built Environment",
            "College of Architecture, Planning, and Sustainable Built Environment",
            "College of Business and Accountancy",
            "College of Business and Accountancy",
            "College of Business and Accountancy",
            "College of Business and Accountancy",
            "College of Business and Accountancy",
            "College of Computing and Information Technologies",
            "College of Computing and Information Technologies",
            "College of Computing and Information Technologies",
            "College of Computing and Information Technologies",
            "College of Computing and Information Technologies",
            "College of Education, Arts, and Sciences",
            "College of Education, Arts, and Sciences",
            "College of Education, Arts, and Sciences",
            "College of Education, Arts, and Sciences",
            "College of Education, Arts, and Sciences",
            "College of Education, Arts, and Sciences",
            "College of Education, Arts, and Sciences",
            "College of Education, Arts, and Sciences",
            "College of Engineering",
            "College of Engineering",
            "College of Engineering",
            "College of Engineering",
            "College of Engineering",
            "College of Engineering",
            "College of Tourism and Hospitality Management",
            "College of Tourism and Hospitality Management"
    };
    private final String[] yearLevels = {
            "1st Year",
            "2nd Year",
            "3rd Year",
            "4th Year",
            "5th Year"
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        setupSpinners();
        setupListeners();
        renderStudents();
    }
    // ==========================================
    // INITIALIZE VIEWS
    // ==========================================
    private void initializeViews() {
        loginLayout = findViewById(R.id.loginLayout);
        dashboardLayout = findViewById(R.id.dashboardLayout);
        studentListContainer = findViewById(R.id.studentListContainer);
        etUsername = findViewById(R.id.etUsername);
        etPassword = findViewById(R.id.etPassword);
        etStudentNo = findViewById(R.id.etStudentNo);
        etLastName = findViewById(R.id.etLastName);
        etFirstName = findViewById(R.id.etFirstName);
        etMiddleName = findViewById(R.id.etMiddleName);
        spProgram = findViewById(R.id.spProgram);
        spYearLevel = findViewById(R.id.spYearLevel);
        tvCollege = findViewById(R.id.tvCollege);
        tvFormTitle = findViewById(R.id.tvFormTitle);
        tvTotalStudents = findViewById(R.id.tvTotalStudents);
        etSearch = findViewById(R.id.etSearch);
        btnSave = findViewById(R.id.btnSave);
    }
    // ==========================================
    // SPINNER SETUP
    // ==========================================
    private void setupSpinners() {
        ArrayAdapter<String> programAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        programs
                );
        programAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spProgram.setAdapter(programAdapter);
        ArrayAdapter<String> yearAdapter =
                new ArrayAdapter<>(
                        this,
                        android.R.layout.simple_spinner_item,
                        yearLevels
                );
        yearAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item
        );
        spYearLevel.setAdapter(yearAdapter);
        spProgram.setOnItemSelectedListener(
                new android.widget.AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(
                            android.widget.AdapterView<?> parent,
                            View view,
                            int position,
                            long id) {
                        tvCollege.setText(colleges[position]);
                    }
                    @Override
                    public void onNothingSelected(
                            android.widget.AdapterView<?> parent) {
                    }
                }
        );
    }
    // ==========================================
    // BUTTON AND SEARCH LISTENERS
    // ==========================================
    private void setupListeners() {
        findViewById(R.id.btnLogin).setOnClickListener(v -> login());
        findViewById(R.id.btnLogout).setOnClickListener(v -> logout());
        btnSave.setOnClickListener(v -> saveStudent());
        findViewById(R.id.btnClear).setOnClickListener(v -> clearForm());
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(
                    CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(
                    CharSequence s, int start, int before, int count) {
                renderStudents();
            }
            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }
    // ==========================================
    // LOGIN
    // ==========================================
    private void login() {
        String username = etUsername.getText().toString().trim();
        String password = etPassword.getText().toString();
        if (username.isEmpty() || password.isEmpty()) {
            Toast.makeText(this,
                    "Please enter your username and password.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        if (username.equals("admin") &&
                password.equals("admin123")) {
            loginLayout.setVisibility(View.GONE);
            dashboardLayout.setVisibility(View.VISIBLE);
            Toast.makeText(this,
                    "Welcome to NU Student Information System!",
                    Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this,
                    "Invalid username or password.",
                    Toast.LENGTH_SHORT).show();
        }
    }
    // ==========================================
    // SAVE / UPDATE STUDENT
    // ==========================================
    private void saveStudent() {
        String studentNo =
                etStudentNo.getText().toString().trim();
        String lastName =
                etLastName.getText().toString().trim();
        String firstName =
                etFirstName.getText().toString().trim();
        String middleName =
                etMiddleName.getText().toString().trim();
        String program =
                spProgram.getSelectedItem().toString();
        String college =
                colleges[spProgram.getSelectedItemPosition()];
        String yearLevel =
                spYearLevel.getSelectedItem().toString();
        // Required field validation
        if (studentNo.isEmpty() ||
                lastName.isEmpty() ||
                firstName.isEmpty()) {
            Toast.makeText(this,
                    "Please complete all required fields.",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        // Check duplicate student numbers
        for (int i = 0; i < students.size(); i++) {
            if (students.get(i).studentNo.equalsIgnoreCase(studentNo)
                    && i != editingPosition) {
                etStudentNo.setError("Student number already exists.");
                return;
            }
        }
        Student student = new Student(
                studentNo,
                lastName,
                firstName,
                middleName,
                program,
                college,
                yearLevel
        );
        if (editingPosition == -1) {
            students.add(student);
            Toast.makeText(this,
                    "Student added successfully!",
                    Toast.LENGTH_SHORT).show();
        } else {
            students.set(editingPosition, student);
            Toast.makeText(this,
                    "Student information updated!",
                    Toast.LENGTH_SHORT).show();
        }
        clearForm();
        renderStudents();
    }
    // ==========================================
    // DISPLAY STUDENTS
    // ==========================================
    private void renderStudents() {
        studentListContainer.removeAllViews();
        String keyword =
                etSearch == null ? "" :
                        etSearch.getText().toString()
                                .trim().toLowerCase(Locale.ROOT);
        int visibleCount = 0;
        for (int i = 0; i < students.size(); i++) {
            Student student = students.get(i);
            String fullName =
                    student.firstName + " " +
                            student.middleName + " " +
                            student.lastName;
            String searchable =
                    (student.studentNo + " " +
                            fullName + " " +
                            student.program + " " +
                            student.college).toLowerCase(Locale.ROOT);
            if (!searchable.contains(keyword)) {
                continue;
            }
            final int position = i;
            studentListContainer.addView(
                    createStudentCard(student, position)
            );
            visibleCount++;
        }
        tvTotalStudents.setText(
                "Total Registered Students: " + students.size()
        );
        if (visibleCount == 0) {
            TextView emptyMessage = new TextView(this);
            emptyMessage.setText(
                    students.isEmpty()
                            ? "No students registered yet.\nAdd your first student using the form above."
                            : "No students found matching your search."
            );
            emptyMessage.setTextColor(GRAY);
            emptyMessage.setTextSize(14);
            emptyMessage.setGravity(Gravity.CENTER);
            emptyMessage.setPadding(20, 35, 20, 35);
            studentListContainer.addView(emptyMessage);
        }
    }
    // ==========================================
    // CREATE STUDENT CARD
    // ==========================================
    private View createStudentCard(Student student, int position) {
        LinearLayout card = new LinearLayout(this);
        card.setOrientation(LinearLayout.VERTICAL);
        card.setPadding(18, 18, 18, 18);
        GradientDrawable cardBackground = new GradientDrawable();
        cardBackground.setColor(Color.WHITE);
        cardBackground.setCornerRadius(18);
        cardBackground.setStroke(1, Color.rgb(225, 231, 240));
        card.setBackground(cardBackground);
        card.setElevation(3);
        LinearLayout.LayoutParams cardParams =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT
                );
        cardParams.setMargins(0, 0, 0, 14);
        card.setLayoutParams(cardParams);
        String fullName =
                student.firstName +
                        (student.middleName.isEmpty()
                                ? " " : " " + student.middleName + " ") +
                        student.lastName;
        // Student name
        TextView name = new TextView(this);
        name.setText(fullName);
        name.setTextColor(NAVY);
        name.setTextSize(17);
        name.setTypeface(null, Typeface.BOLD);
        card.addView(name);
        // Student number
        TextView number = new TextView(this);
        number.setText("Student No.: " + student.studentNo);
        number.setTextColor(BLUE);
        number.setTextSize(13);
        number.setPadding(0, 5, 0, 12);
        card.addView(number);
        addCardDetail(card, "Program", student.program);
        addCardDetail(card, "College", student.college);
        addCardDetail(card, "Year Level", student.yearLevel);
        // Action buttons
        LinearLayout buttons = new LinearLayout(this);
        buttons.setOrientation(LinearLayout.HORIZONTAL);
        buttons.setGravity(Gravity.END);
        buttons.setPadding(0, 15, 0, 0);
        Button editButton = createActionButton("EDIT", NAVY);
        Button deleteButton = createActionButton("DELETE",
                Color.rgb(190, 55, 55));
        editButton.setOnClickListener(v ->
                editStudent(position));
        deleteButton.setOnClickListener(v ->
                deleteStudent(position));
        buttons.addView(editButton);
        buttons.addView(deleteButton);
        card.addView(buttons);
        return card;
    }
    // ==========================================
    // CARD DETAIL TEXT
    // ==========================================
    private void addCardDetail(
            LinearLayout card, String label, String value) {
        TextView detail = new TextView(this);
        detail.setText(label + ": " + value);
        detail.setTextColor(GRAY);
        detail.setTextSize(13);
        detail.setPadding(0, 4, 0, 4);
        card.addView(detail);
    }
    // ==========================================
    // ACTION BUTTON DESIGN
    // ==========================================
    private Button createActionButton(String text, int color) {
        Button button = new Button(this);
        button.setText(text);
        button.setTextSize(11);
        button.setTextColor(Color.WHITE);
        button.setAllCaps(false);
        button.setBackgroundTintList(
                android.content.res.ColorStateList.valueOf(color)
        );
        LinearLayout.LayoutParams params =
                new LinearLayout.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        45
                );
        params.setMargins(5, 0, 5, 0);
        button.setLayoutParams(params);
        return button;
    }
    // ==========================================
    // EDIT STUDENT
    // ==========================================
    private void editStudent(int position) {
        Student student = students.get(position);
        editingPosition = position;
        etStudentNo.setText(student.studentNo);
        etLastName.setText(student.lastName);
        etFirstName.setText(student.firstName);
        etMiddleName.setText(student.middleName);
        for (int i = 0; i < programs.length; i++) {
            if (programs[i].equals(student.program)) {
                spProgram.setSelection(i);
                break;
            }
        }
        for (int i = 0; i < yearLevels.length; i++) {
            if (yearLevels[i].equals(student.yearLevel)) {
                spYearLevel.setSelection(i);
                break;
            }
        }
        tvFormTitle.setText("Edit Student Information");
        btnSave.setText("UPDATE STUDENT");
        // Bring form into view
        ScrollView scrollView = findViewById(R.id.mainScrollView);
        scrollView.smoothScrollTo(0, 0);
        Toast.makeText(this,
                "You are editing " + student.firstName + "'s record.",
                Toast.LENGTH_SHORT).show();
    }
    // ==========================================
    // DELETE STUDENT
    // ==========================================
    private void deleteStudent(int position) {
        Student student = students.get(position);
        new AlertDialog.Builder(this)
                .setTitle("Delete Student")
                .setMessage(
                        "Are you sure you want to delete the record of "
                                + student.firstName + " "
                                + student.lastName + "?"
                )
                .setPositiveButton("Delete", (dialog, which) -> {
                    students.remove(position);
                    if (editingPosition == position) {
                        clearForm();
                    } else if (editingPosition > position) {
                        editingPosition--;
                    }
                    renderStudents();
                    Toast.makeText(this,
                            "Student record deleted.",
                            Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    // ==========================================
    // CLEAR FORM
    // ==========================================
    private void clearForm() {
        editingPosition = -1;
        etStudentNo.setText("");
        etLastName.setText("");
        etFirstName.setText("");
        etMiddleName.setText("");
        spProgram.setSelection(0);
        spYearLevel.setSelection(0);
        tvFormTitle.setText("Register New Student");
        btnSave.setText("ADD STUDENT");
        etStudentNo.setError(null);
    }
    // ==========================================
    // LOGOUT
    // ==========================================
    private void logout() {
        new AlertDialog.Builder(this)
                .setTitle("Logout")
                .setMessage("Are you sure you want to logout?")
                .setPositiveButton("Yes", (dialog, which) -> {
                    clearForm();
                    loginLayout.setVisibility(View.VISIBLE);
                    dashboardLayout.setVisibility(View.GONE);
                    etUsername.setText("");
                    etPassword.setText("");
                })
                .setNegativeButton("Cancel", null)
                .show();
    }
    // ==========================================
    // STUDENT MODEL
    // ==========================================
    private static class Student {
        String studentNo;
        String lastName;
        String firstName;
        String middleName;
        String program;
        String college;
        String yearLevel;
        Student(
                String studentNo,
                String lastName,
                String firstName,
                String middleName,
                String program,
                String college,
                String yearLevel) {
            this.studentNo = studentNo;
            this.lastName = lastName;
            this.firstName = firstName;
            this.middleName = middleName;
            this.program = program;
            this.college = college;
            this.yearLevel = yearLevel;
        }
    }
}