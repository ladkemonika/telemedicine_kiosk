import 'package:flutter/material.dart';
import 'book_appointment_screen.dart';
import 'doctor_status_screen.dart';
// ✅ KioskChannel import
import '../services/kiosk_channel.dart';

class HomeScreen extends StatelessWidget {
  const HomeScreen({super.key});

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(
        title: const Text("Telemedicine Kiosk"),
        automaticallyImplyLeading: false, // removes back button after login
        actions: [
          // ✅ Hidden escape: Long press 3s to stop kiosk
          GestureDetector(
            onLongPress: () async => await KioskChannel.stop(),
            child: const SizedBox(
              width: 48,
              height: 48,
              child: Icon(Icons.info_outline, size: 0), // invisible
            ),
          ),
        ],
      ),
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            ElevatedButton(
              onPressed: () {
                // TODO: Replace with actual patient and doctor info
                String patientName = "John Doe";
                String doctorId = "doctor_001";

                Navigator.push(
                  context,
                  MaterialPageRoute(
                    builder: (context) => BookAppointmentScreen(
                      patientName: patientName,
                      doctorId: doctorId,
                    ),
                  ),
                );
              },
              child: const Text("Book Appointment"),
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                Navigator.push(
                  context,
                  MaterialPageRoute(
                      builder: (context) => const DoctorStatusScreen()),
                );
              },
              child: const Text("Doctor Status"),
            ),
          ],
        ),
      ),
    );
  }
}
