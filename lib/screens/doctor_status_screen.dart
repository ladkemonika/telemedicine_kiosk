import 'package:flutter/material.dart';
import 'package:firebase_database/firebase_database.dart';

class DoctorStatusScreen extends StatefulWidget {
  const DoctorStatusScreen({super.key});

  @override
  State<DoctorStatusScreen> createState() => _DoctorStatusScreenState();
}

class _DoctorStatusScreenState extends State<DoctorStatusScreen> {
  bool isDoctorOnline = false;
  final DatabaseReference _dbRef = FirebaseDatabase.instance.ref("doctor_status/online");

  @override
  void initState() {
    super.initState();

    // Listen for real-time updates
    _dbRef.onValue.listen((event) {
      final status = event.snapshot.value as bool?;
      if (status != null) {
        setState(() {
          isDoctorOnline = status;
        });
      }
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      appBar: AppBar(title: const Text("Doctor Status")),
      body: Center(
        child: Text(
          isDoctorOnline ? "Doctor is ONLINE" : "Doctor is OFFLINE",
          style: TextStyle(
            fontSize: 22,
            fontWeight: FontWeight.bold,
            color: isDoctorOnline ? Colors.green : Colors.red,
          ),
        ),
      ),
    );
  }
}
