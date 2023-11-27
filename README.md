# Hotel Reservation App

This Android app allows users to make hotel reservations by selecting various options such as room type, additional services, and buffet access. The app calculates the total cost and stores the reservation details in an SQLite database.

## Features

- **Room Selection:** Users can choose the type of bed (Single Bed, Queen Bed, King Bed) for their reservation.

- **Additional Services:** Options for additional services such as massage, sauna, and beauty services are available. Users can choose whether to accept additional costs.

- **Buffet Access:** Users can choose buffet access on weekdays or weekends.

- **Beauty Services:** Users can select from various beauty services like manicure, facial, and haircut.

- **Total Price Calculation:** The app dynamically calculates the total cost based on user selections.

- **Reservation Storage:** The reservation details are stored in an SQLite database.

## Usage

1. **Room Selection:**
   - Click on the "Chambre" option in the menu.
   - Select the type of bed from the provided options.
   - Click "Accept" to confirm the selection.

2. **Additional Services:**
   - Click on the respective menu options (e.g., "Massage," "Sauna," "Soins de Beaute").
   - Choose the desired options and click "Accept" to confirm.

3. **Buffet Access:**
   - Click on the "Buffet" option in the menu.
   - Select buffet access for weekdays or weekends.
   - Click "Accept" to confirm.

4. **Beauty Services:**
   - Click on the "Soins de Beaute" option in the menu.
   - Check the desired beauty services.
   - Click "Accept" to confirm.

5. **View Invoice:**
   - Click on the "Facture" option in the menu to view the calculated total cost.

6. **Reservation:**
   - Click on the "Reserve" button to finalize the reservation.
   - A success message will be displayed, and the reservation details will be saved in the database.

## Development

The app is developed using Android Studio and Java. It utilizes an SQLite database for data storage.

### Dependencies

- AndroidX
- SQLite database

### Build and Run

1. Clone the repository.
2. Open the project in Android Studio.
3. Build and run the app on an Android emulator or physical device.

## License

This project is licensed under the [MIT License](LICENSE).

---

**Note:** This README is a template and should be customized based on the specifics of your project.
