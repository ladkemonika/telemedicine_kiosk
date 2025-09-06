import 'package:flutter/material.dart';
import 'home_screen.dart';
// ✅ Add this import at the top
import '../services/kiosk_channel.dart';

class LoginScreen extends StatelessWidget {
  const LoginScreen({super.key});

  final String hardcodedUser = "admin";
  final String hardcodedPass = "1234";

  @override
  Widget build(BuildContext context) {
    final TextEditingController userController = TextEditingController();
    final TextEditingController passController = TextEditingController();

    return Scaffold(
      appBar: AppBar(title: const Text("Login")),
      body: Padding(
        padding: const EdgeInsets.all(16.0),
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            TextField(
              controller: userController,
              decoration: const InputDecoration(labelText: "Username"),
            ),
            TextField(
              controller: passController,
              decoration: const InputDecoration(labelText: "Password"),
              obscureText: true,
            ),
            const SizedBox(height: 20),
            ElevatedButton(
              onPressed: () {
                if (userController.text == hardcodedUser &&
                    passController.text == hardcodedPass) {
                  Navigator.pushReplacement(
                    context,
                    MaterialPageRoute(builder: (context) => const HomeScreen()),
                  );

                  // ✅ Start kiosk mode after navigation
                  Future.delayed(const Duration(milliseconds: 300), () {
                    KioskChannel.start();
                  });
                } else {
                  ScaffoldMessenger.of(context).showSnackBar(
                    const SnackBar(content: Text("Invalid credentials")),
                  );
                }
              },
              child: const Text("Login"),
            ),
          ],
        ),
      ),
    );
  }
}
