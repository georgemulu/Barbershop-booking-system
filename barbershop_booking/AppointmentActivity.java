package com.example.barbershop_booking;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AppointmentActivity extends AppCompatActivity {

    private EditText serviceInput;
    private EditText dateInput;
    private EditText timeInput;
    private Button confirmButton;

    private FirebaseAuth auth;
    private DatabaseReference database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_appointment);

        // Initialize Firebase Auth and Database
        auth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance().getReference("Appointments");

        // Find the views by their IDs
        serviceInput = findViewById(R.id.serviceInput);
        dateInput = findViewById(R.id.dateInput);
        timeInput = findViewById(R.id.timeInput);
        confirmButton = findViewById(R.id.confirmButton);

        // Set click listener for the Confirm Appointment button
        confirmButton.setOnClickListener(v -> handleConfirmAppointment());
    }

    private void handleConfirmAppointment() {
        // Get the user's input
        String service = serviceInput.getText().toString().trim();
        String date = dateInput.getText().toString().trim();
        String time = timeInput.getText().toString().trim();

        // Validate the input
        if (service.isEmpty() || date.isEmpty() || time.isEmpty()) {
            Toast.makeText(this, "Please fill in all the fields", Toast.LENGTH_SHORT).show();
            return;
        }

        // Save appointment details to Firebase
        FirebaseUser currentUser = auth.getCurrentUser();
        if (currentUser != null) {
            String userId = currentUser.getUid();

            // Create an Appointment object
            Appointment appointment = new Appointment(service, date, time);

            // Save the appointment under the user's UID
            database.child(userId).push().setValue(appointment)
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            Toast.makeText(this, "Appointment confirmed!", Toast.LENGTH_SHORT).show();
                            // Clear the input fields
                            clearInputs();
                        } else {
                            Toast.makeText(this, "Failed to confirm appointment: " + task.getException().getMessage(), Toast.LENGTH_LONG).show();
                        }
                    });
        } else {
            Toast.makeText(this, "User not logged in. Please log in to make an appointment.", Toast.LENGTH_SHORT).show();
        }
    }

    private void clearInputs() {
        serviceInput.setText("");
        dateInput.setText("");
        timeInput.setText("");
    }

    // Appointment model class
    public static class Appointment {
        public String service;
        public String date;
        public String time;

        public Appointment() {
            // Default constructor required for calls to DataSnapshot.getValue(Appointment.class)
        }

        public Appointment(String service, String date, String time) {
            this.service = service;
            this.date = date;
            this.time = time;
        }
    }
}
