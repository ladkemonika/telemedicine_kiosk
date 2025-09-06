import 'package:firebase_database/firebase_database.dart';

class BackendService {
  // Root reference
  static final DatabaseReference _db = FirebaseDatabase.instance.ref();

  // Log an action under /logs
  static Future<void> logAction(String action, {Map<String, dynamic>? extra}) async {
    final entry = {
      'action': action,
      'timestamp': DateTime.now().toIso8601String(),
      if (extra != null) ...extra,
    };
    try {
      await _db.child('logs').push().set(entry);
    } catch (e) {
      // Handle silently for now (network offline etc.)
      print('Failed to log action: $e');
    }
  }

  // Create an appointment under /appointments
  static Future<void> createAppointment({
    required String patient,
    String? doctorId,
    String? note,
  }) async {
    final data = {
      'patient': patient,
      'doctorId': doctorId ?? '',
      'note': note ?? '',
      'time': DateTime.now().toIso8601String(),
      'status': 'PENDING',
    };
    try {
      await _db.child('appointments').push().set(data);
      await logAction('Appointment booked', extra: {'patient': patient});
    } catch (e) {
      print('Failed to create appointment: $e');
      rethrow;
    }
  }

  // Set doctor online/offline
  static Future<void> setDoctorOnline(bool online) async {
    try {
      await _db.child('doctor_status/online').set(online);
      await logAction(online ? 'Doctor went ONLINE' : 'Doctor went OFFLINE');
    } catch (e) {
      print('Failed to update doctor status: $e');
    }
  }
}
