package ru.mirea.sda.employeedb;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Database;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Entity;
import androidx.room.Insert;
import androidx.room.PrimaryKey;
import androidx.room.Query;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.Update;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private AppDatabase db;
    private StudentDao dao;
    private EditText studentName, studentGroup;
    private Button save, update, delete;
    private RecyclerView recyclerView;
    private StudentAdapter adapter;
    private long selectedStudentId = -1;

    @Entity(tableName = "students")
    public static class Student {
        @PrimaryKey(autoGenerate = true)
        public long id;
        public String name;
        public String group;
    }

    @Dao
    public interface StudentDao {
        @Query("SELECT * FROM students")
        List<Student> getAll();

        @Query("SELECT * FROM students WHERE id = :id")
        Student getById(long id);

        @Insert
        void insert(Student student);

        @Update
        void update(Student student);

        @Delete
        void delete(Student student);
    }

    @Database(entities = {Student.class}, version = 1)
    public abstract static class AppDatabase extends RoomDatabase {
        public abstract StudentDao studentDao();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = Room.databaseBuilder(getApplicationContext(), AppDatabase.class, "students_db")
                .allowMainThreadQueries()
                .build();
        dao = db.studentDao();

        studentName = findViewById(R.id.heroname);
        studentGroup = findViewById(R.id.herotext);
        save = findViewById(R.id.save);
        update = findViewById(R.id.update);
        delete = findViewById(R.id.delete);
        recyclerView = findViewById(R.id.rv_superheroes);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new StudentAdapter(new ArrayList<>());
        recyclerView.setAdapter(adapter);

        loadStudentsToList();

        save.setOnClickListener(v -> {
            String name = studentName.getText().toString().trim();
            String group = studentGroup.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Введите имя студента", Toast.LENGTH_LONG).show();
                return;
            }
            Student student = new Student();
            student.name = name;
            student.group = group;
            dao.insert(student);
            Toast.makeText(this, "Студент добавлен", Toast.LENGTH_LONG).show();
            clearFields();
            loadStudentsToList();
        });

        update.setOnClickListener(v -> {
            if (selectedStudentId == -1) {
                Toast.makeText(this, "Выберите студента для обновления", Toast.LENGTH_LONG).show();
                return;
            }
            String name = studentName.getText().toString().trim();
            String group = studentGroup.getText().toString().trim();
            if (name.isEmpty()) {
                Toast.makeText(this, "Введите имя студента", Toast.LENGTH_LONG).show();
                return;
            }
            Student student = dao.getById(selectedStudentId);
            if (student == null) {
                Toast.makeText(this, "Студент не найден", Toast.LENGTH_LONG).show();
                return;
            }
            student.name = name;
            student.group = group;
            dao.update(student);
            Toast.makeText(this, "Студент обновлён", Toast.LENGTH_LONG).show();
            clearFields();
            selectedStudentId = -1;
            loadStudentsToList();
        });

        delete.setOnClickListener(v -> {
            if (selectedStudentId == -1) {
                Toast.makeText(this, "Выберите студента для удаления", Toast.LENGTH_LONG).show();
                return;
            }
            Student student = dao.getById(selectedStudentId);
            if (student == null) {
                Toast.makeText(this, "Студент не найден", Toast.LENGTH_LONG).show();
                return;
            }
            dao.delete(student);
            Toast.makeText(this, "Студент удалён", Toast.LENGTH_LONG).show();
            clearFields();
            selectedStudentId = -1;
            loadStudentsToList();
        });
    }

    private void loadStudentsToList() {
        List<Student> students = dao.getAll();
        adapter.setStudents(students);
    }

    private void clearFields() {
        studentName.setText("");
        studentGroup.setText("");
    }

    private class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.StudentViewHolder> {

        private List<Student> students;

        public StudentAdapter(List<Student> students) {
            this.students = students;
        }

        public void setStudents(List<Student> students) {
            this.students = students;
            notifyDataSetChanged();
        }

        @NonNull
        @Override
        public StudentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
            return new StudentViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull StudentViewHolder holder, int position) {
            Student student = students.get(position);
            holder.bind(student);
        }

        @Override
        public int getItemCount() {
            return students.size();
        }

        class StudentViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
            private android.widget.TextView textView;
            private Student currentStudent;

            public StudentViewHolder(@NonNull View itemView) {
                super(itemView);
                textView = itemView.findViewById(android.R.id.text1);
                itemView.setOnClickListener(this);
            }

            void bind(Student student) {
                currentStudent = student;
                textView.setText(student.name);
            }

            @Override
            public void onClick(View v) {
                selectedStudentId = currentStudent.id;
                studentName.setText(currentStudent.name);
                studentGroup.setText(currentStudent.group);
                Toast.makeText(MainActivity.this, "Студент выбран: " + currentStudent.name, Toast.LENGTH_LONG).show();
            }
        }
    }
}