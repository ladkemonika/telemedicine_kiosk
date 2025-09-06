import 'package:flutter/services.dart';

class KioskChannel {
  static const MethodChannel _ch = MethodChannel('kiosk');

  static Future<void> start() async {
    try {
      await _ch.invokeMethod('startKiosk');
    } catch (e) {
      // ignore or show a SnackBar if you want
    }
  }

  static Future<void> stop() async {
    try {
      await _ch.invokeMethod('stopKiosk');
    } catch (e) {
      // ignore
    }
  }
}
