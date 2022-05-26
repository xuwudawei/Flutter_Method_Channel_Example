import 'package:flutter/material.dart';
import 'package:flutter/services.dart';

class BatteryHome extends StatefulWidget {
  const BatteryHome({Key? key}) : super(key: key);

  @override
  State<BatteryHome> createState() => _BatteryHomeState();
}

class _BatteryHomeState extends State<BatteryHome> {
  String batteryLevel = 'Waiting for Battery Level...';
  static const batteryChannel = MethodChannel('xuwudawei/battery');

  Future getBatteryLevel() async {
    final arguments = {"name":"David Opoku"};
    final String newBatteryLevel =
        await batteryChannel.invokeMethod('getBatteryLevel', arguments);
    setState(() {
      batteryLevel = newBatteryLevel;
    });
  }

  @override
  Widget build(BuildContext context) {
    return Scaffold(
      body: Center(
        child: Column(
          crossAxisAlignment: CrossAxisAlignment.center,
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            Text(
              batteryLevel,
              style: TextStyle(
                fontSize: 30,
                fontWeight: FontWeight.bold,
              ),
            ),
            SizedBox(
              height: 20,
            ),
            
            ElevatedButton(
              onPressed: getBatteryLevel,
              child: Text("Get Battery Level"),
            ),
          ],
        ),
      ),
    );
  }
}
