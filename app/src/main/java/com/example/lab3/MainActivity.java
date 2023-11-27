package com.example.lab3;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private double totalPrice = 0.0;
    private DbHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the dbHelper
        dbHelper = new DbHelper(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                handleMenuClick(item);
                return true;
            }
        });
    }

    public void showDatePickerDialog(View view) {
        EditText editTextDate = (EditText) view;
        DatePickerFragment newFragment = new DatePickerFragment();
        newFragment.setEditTextDate(editTextDate);
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    private void handleMenuClick(MenuItem item) {
        // Show different messages based on the selected option
        if (item.getItemId() == R.id.chambre) {
            showBedDialog();
        } else if (item.getItemId() == R.id.massage) {
            showOptionsDialog("Select the type of Massage", new String[]{"Accept Additional ($)", "No"}, 40, 52);
        } else if (item.getItemId() == R.id.sauna) {
            showOptionsDialog("Select the type of Sauna", new String[]{"Accept Additional ($)", "No"}, 25, 35);
        } else if (item.getItemId() == R.id.buffet) {
            showBuffetOptionsDialog();
        } else if (item.getItemId() == R.id.soins_de_beaute) {
            showBeautyServicesDialog();
        } else if (item.getItemId() == R.id.facture) {
            showInvoice();
        } else if (item.getItemId() == R.id.quitter) {
            showMessage("Quitter selected");
        }
    }

    private void showBedDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select the type of bed");

        final String[] bedOptions = {"Single Bed", "Queen Bed", "King Bed"};
        final int[] selectedOption = {0}; // Default: Single Bed

        builder.setSingleChoiceItems(bedOptions, 0, (dialog, which) -> selectedOption[0] = which);

        builder.setPositiveButton("Accept", (dialog, which) -> {
            String selectedBedType = bedOptions[selectedOption[0]];
            updateTotalPrice(78, 80);
            showMessage("You selected: " + selectedBedType);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showOptionsDialog(String title, String[] options, double weekdayPrice, double weekendPrice) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title);

        final int[] selectedOption = {0}; // Default: Accept

        builder.setSingleChoiceItems(options, 0, (dialog, which) -> selectedOption[0] = which);

        builder.setPositiveButton("Accept", (dialog, which) -> {
            String selectedOptionText = options[selectedOption[0]];
            if (selectedOptionText.equals("Additional ($)")) {
                updateTotalPrice(0, 12); // Additional cost
            } else if (selectedOptionText.equals("No")) {
                updateTotalPrice(0, 0); // No additional cost
            }
            showMessage("You selected: " + selectedOptionText);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showBuffetOptionsDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select access to the buffet");

        final String[] buffetOptions = {"Weekday", "Weekend"};
        final int[] selectedOption = {0}; // Default: Weekday

        builder.setSingleChoiceItems(buffetOptions, 0, (dialog, which) -> selectedOption[0] = which);

        builder.setPositiveButton("Accept", (dialog, which) -> {
            String selectedOptionText = buffetOptions[selectedOption[0]];
            if (selectedOptionText.equals("Weekday")) {
                updateTotalPrice(32, 0); // Buffet price on weekdays
            } else if (selectedOptionText.equals("Weekend")) {
                updateTotalPrice(39, 0); // Buffet price on weekends
            }
            showMessage("You selected: Access to the Kings' buffet - " + selectedOptionText);
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showBeautyServicesDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Select beauty services");

        LinearLayout layout = new LinearLayout(this);
        layout.setOrientation(LinearLayout.VERTICAL);

        final CheckBox checkBoxManicure = new CheckBox(this);
        checkBoxManicure.setText("Manicure");
        layout.addView(checkBoxManicure);

        final CheckBox checkBoxFacial = new CheckBox(this);
        checkBoxFacial.setText("Facial");
        layout.addView(checkBoxFacial);

        final CheckBox checkBoxHaircut = new CheckBox(this);
        checkBoxHaircut.setText("Haircut");
        layout.addView(checkBoxHaircut);

        builder.setView(layout);

        builder.setPositiveButton("Accept", (dialog, which) -> {
            if (checkBoxManicure.isChecked()) {
                updateTotalPrice(22, 30); // Manicure price
            }
            if (checkBoxFacial.isChecked()) {
                updateTotalPrice(25, 35); // Facial price
            }
            if (checkBoxHaircut.isChecked()) {
                updateTotalPrice(32, 40); // Haircut price
            }

            showMessage("You selected beauty services");
        });

        builder.setNegativeButton("Cancel", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showInvoice() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Invoice");

        builder.setMessage("The total cost is: $ " + totalPrice);

        builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private void showMessage(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void updateTotalPrice(double weekdayPrice, double weekendPrice) {
        // Update the total price based on the selected options
        totalPrice += (weekdayPrice + weekendPrice);
    }

    // Method to show a Toast indicating that the reservation was successful
    private void showSuccessfulReservation() {
        Toast.makeText(this, "Reservation successful!", Toast.LENGTH_SHORT).show();
    }

    // Method to handle the click on the reserve button
    public void onClickReserve(View view) {
        showSuccessfulReservation();
        saveReservationToDatabase();

        // Reset UI components
        resetFields();
    }

    // Method to reset UI components
    private void resetFields() {
        // Clear the date field
        EditText editTextDate = findViewById(R.id.editTextFechaSalida);
        EditText editTextDate2 = findViewById(R.id.editTextFechaLlegada);
        editTextDate.setText("");
        editTextDate2.setText("");

        // Reset the total price
        totalPrice = 0.0;
    }

    // Method to save the reservation to the database
    private void saveReservationToDatabase() {
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new reservation record
        ContentValues values = new ContentValues();
        // You can add more columns as needed
        values.put(DbHelper.COLUMN_ROOM_TYPE, "Selected Room Type");
        values.put(DbHelper.COLUMN_PRICE, totalPrice);

        // Insert the new record into the database
        long newRowId = db.insert(DbHelper.TABLE_RESERVATIONS, null, values);

        // Check if the insertion was successful
        if (newRowId != -1) {
            showMessage("Reservation saved in database");
        } else {
            showMessage("Error saving reservation");
        }

        // Close the database connection
        db.close();
    }

    @Override
    protected void onDestroy() {
        // Close the dbHelper when the activity is destroyed
        dbHelper.close();
        super.onDestroy();
    }
}
