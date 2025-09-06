import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';

class BookAppointmentScreen extends StatefulWidget {
  const BookAppointmentScreen({super.key});

  @override
  State<BookAppointmentScreen> createState() => _BookAppointmentScreenState();
}

class _BookAppointmentScreenState extends State<BookAppointmentScreen> {
  late DatabaseReference database; // declare here

  @override
  void initState() {
    super.initState();
    // assign after Firebase initialized
    database = FirebaseDatabase.instance.ref();
  }

  void bookAppointment() {
    // Mock appointment booking
    database.child('appointments').push().set({
      'patient': 'John Doe',
      'time': DateTime.now().toString(),
    }).then((_) {
      ScaffoldMessenger.of(context).showSnackBar(
        const SnackBar(content: Text("Appointment booked successfully!")),
      );
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Book Appointment")),
      body: Center(
        child: ElevatedButton(
          onPressed: bookAppointment,
          child: const Text("Book Appointment"),
        ),
      ),
    );
  }
}
