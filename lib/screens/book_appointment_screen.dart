import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';

class BookAppointmentScreen extends StatefulWidget {
  const BookAppointmentScreen({super.key, required String patientName, required String doctorId});

  @override
  State<BookAppointmentScreen> createState() => _BookAppointmentScreenState();
}

class _BookAppointmentScreenState extends State<BookAppointmentScreen> {
  late DatabaseReference database;
  bool _isLoading = false;

  final TextEditingController _patientController = TextEditingController();
  final TextEditingController _doctorController = TextEditingController();

  @override
  void initState() {
    super.initState();
    database = FirebaseDatabase.instance.ref();
  }

  Future<void> bookAppointment() async {
    if (_patientController.text.isEmpty || _doctorController.text.isEmpty) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Please enter patient name and doctor ID")),
      );
      return;
    }

    setState(() => _isLoading = true);

    try {
      await database.child('appointments').push().set({
        'patient': _patientController.text.trim(),
        'doctorId': _doctorController.text.trim(),
        'time': DateTime.now().toIso8601String(),
        'status': 'booked',
      });

      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Appointment booked successfully!")),
      );

      _patientController.clear();
      _doctorController.clear();
    } catch (e) {
      ScaffoldMessenger.of(context).showSnackBar(
        SnackBar(content: Text("Failed to book appointment: $e")),
      );
    } finally {
      setState(() => _isLoading = false);
    }
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Book Appointment")),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          children: [
            TextField(
              controller: _patientController,
              decoration: const InputDecoration(
                labelText: "Patient Name",
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 16),
            TextField(
              controller: _doctorController,
              decoration: const InputDecoration(
                labelText: "Doctor ID",
                border: OutlineInputBorder(),
              ),
            ),
            const SizedBox(height: 24),
            ElevatedButton(
              onPressed: _isLoading ? null : bookAppointment,
              child: _isLoading
                  ? const CircularProgressIndicator(color: Colors.white)
                  : const Text("Book Appointment"),
            ),
          ],
        ),
      ),
    );
  }
}
