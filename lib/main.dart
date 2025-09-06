import 'package:flutter/material.dart';
import 'screens/splash_screen.dart';
import 'package:firebase_core/firebase_core.dart';

void main() async {
  WidgetsFlutterBinding.ensureInitialized();

  await Firebase.initializeApp(
    options: const FirebaseOptions(
      apiKey: "AIzaSyBD5zzCuwcZQuK_r6LjzNgCQPFG7Ynu04Q",
      authDomain: "telemedicinekiosk-ed507.firebaseapp.com",
      databaseURL: "https://telemedicinekiosk-ed507-default-rtdb.firebaseio.com",
      projectId: "telemedicinekiosk-ed507",
      storageBucket: "telemedicinekiosk-ed507.appspot.com", // ✅ corrected
      messagingSenderId: "76616365181",
      appId: "1:76616365181:web:fdf19161c872d8b336ad14",
      measurementId: "G-WSC7189K0K",
    ),
  );

  runApp(const MyApp());
}

class MyApp extends StatelessWidget {
  const MyApp({super.key});

  @override
  Widget build(BuildContext context) {
    return MaterialApp(
      title: 'Telemedicine Kiosk',
      debugShowCheckedModeBanner: false,
      theme: ThemeData(
        colorScheme: ColorScheme.fromSeed(seedColor: Colors.blue),
      ),
      home: const SplashScreen(),
    );
  }
}
