# Nordic-nRF52-DK-and-Android-BLE-GATT
This is a project using Nordic nRF52 DK and android BLE GATT to build a high temperature reminder.

## Scenario
We assume there is a big factory. In the factory, it will be set up a lot of temperature sensors. Workers will receive the temperature of the sensor through the mobile device and then transmit it to the server for monitoring. Once the temperature exceeds a partuicular value. The server will warn the manager and the manager will give instructions to the workers to ensure that the temperature of the equipment is within the normal range.

## Device
+ Temperature sensor: nrf52
   1. Measuring temperature and broadcasting it
   2. Every temperature sensor has its own unique "realkey"
   3. Only the worker who know the "realkey" can read the real temperature (Access control)
+ Mobile device: Android mobile phone
   1. Use BLE to binding the Temperature sensor
   2. After connecting, worker need to input the "userkey"
   3. Only if the "userkey" is same as "realkey", the worker can read the correct temperature (Access control)
+ Server: Mysql + php
   1. Add worker
   2. Add Device
   3. Authorize worker
   4. Access the temperature of sencor synchronously, if the temperature exceeds a partuicular value, it will show a warning
## Demo Video
