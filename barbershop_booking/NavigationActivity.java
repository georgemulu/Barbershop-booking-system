package com.example.barbershop_booking;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class NavigationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);

        // Set up the Appointment button
        Button appointmentButton = findViewById(R.id.appointmentButton);
        appointmentButton.setOnClickListener(v -> navigateToAppointment());

        // Set up the Services List button
        Button servicesButton = findViewById(R.id.servicesButton);
        servicesButton.setOnClickListener(v -> navigateToServices());
    }

    private void navigateToAppointment() {
        // Navigate to the Appointment Activity
        Intent intent = new Intent(this, AppointmentActivity.class);
        startActivity(intent);
    }

    private void navigateToServices() {
        // Navigate to the Services List Activity
        Intent intent = new Intent(this, ServicesListActivity.class);
        startActivity(intent);
    }
}