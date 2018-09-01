# Nordic-nRF52-DK-and-Android-BLE-GATT
This is a project using Nordic nRF52 DK and android BLE GATT to build a high temperature reminder.

## Scenario
We assume there is a big factory. In the factory, it will be set up a lot of temperature sensors. Workers will receive the temperature of the sensor through the mobile device and then transmit it to the server for monitoring. Once the temperature exceeds a partuicular value. The server will warn the manager and the manager will give instructions to the workers to ensure that the temperature of the equipment is within the normal range.

## Develop tools and techniques
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
<Table>
   <tr>
      <td><a href="http://www.youtube.com/watch?feature=player_embedded&v=-EyBObxK2J4
" target="_blank"><img src="http://img.youtube.com/vi/-EyBObxK2J4/0.jpg" 
alt="Mobile demo" width="240" height="180" border="10" /></a></td>
      <td><a href="http://www.youtube.com/watch?feature=player_embedded&v=onTLXl7MEwo
" target="_blank"><img src="http://img.youtube.com/vi/onTLXl7MEwo/0.jpg" 
alt="Mobile demo" width="240" height="180" border="10" /></a></td>
   </tr>
   <tr>
      <td align="center">Mobile demo</td>
      <td align="center">Server demo</td>
   </tr>
</Table>





