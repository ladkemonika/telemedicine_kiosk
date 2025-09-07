import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';

class BookAppointmentScreen extends StatefulWidget {
  final String patientName;
  final String doctorId;

  const BookAppointmentScreen({
    super.key,
    required this.patientName,
    required this.doctorId,
  });

  @override
  State<BookAppointmentScreen> createState() => _BookAppointmentScreenState();
}

class _BookAppointmentScreenState extends State<BookAppointmentScreen> {
  late DatabaseReference database;
  bool _isLoading = false;

  @override
  void initState() {
    super.initState();
    database = FirebaseDatabase.instance.ref();
  }

  /// Book appointment and update doctor status to online
  Future<void> bookAppointment() async {
    setState(() => _isLoading = true);

    try {
      // Save appointment
      await database.child('appointments').push().set({
        'patient': widget.patientName,
        'doctorId': widget.doctorId,
        'time': DateTime.now().toIso8601String(),
        'status': 'booked',
      });

      // Optionally mark doctor online after booking
      await updateDoctorStatus(true);

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Appointment booked successfully!")),
      );
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Failed to book appointment: $e")),
      );
    } finally {
      setState(() => _isLoading = false);
    }
  }

  /// Update doctor's online status
  Future<void> updateDoctorStatus(bool isOnline) async {
    await database.child('doctor_status/online').set(isOnline);
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Book Appointment")),
      body: Center(
        child: ElevatedButton(
          onPressed: _isLoading ? null : bookAppointment,
          style: ElevatedButton.styleFrom(
            padding: const EdgeInsets.symmetric(horizontal: 24, vertical: 16),
          ),
          child: _isLoading
              ? const SizedBox(
            width: 20,
            height: 20,
            child: CircularProgressIndicator(
              color: Colors.white,
              strokeWidth: 2,
            ),
          )
              : const Text(
            "Book Appointment",
            style: TextStyle(fontSize: 18),
          ),
        ),
      ),
    );
  }
}
