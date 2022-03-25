# MAMN01 - Hello Sensor
## Individuell programmeringsuppgift

Resurser som har använts:

### Grunden

- Android Developer: "Build Your First App" https://developer.android.com/training/basics/firstapp/index.html
- För att skapa nya aktiviteter (Compass och Accelerometer) har jag använt mig utav delar av "Build a simple user interface": https://developer.android.com/training/basics/firstapp/building-ui

### Accelerometer
- Sensor Event: https://developer.android.com/reference/android/hardware/SensorEvent.html
- Sensor Manager: https://developer.android.com/reference/android/hardware/SensorManager.html
- YouTube-video: https://www.youtube.com/watch?v=YrI2pCZC8cc
    - Med denna video kunde jag importera de objekt jag behövde samt uppdatera mina
    TextView-objekt i layouten, då sensorn kände av förändringar
    - Uppdateringarna gör jag i metoden onSensorChanged() som kommer från interfacet
    SensorEventListener som klassen implementerar

- För att avrunda decimalerna tog jag hjälp av denna sida: https://www.baeldung.com/java-round-decimal-number

- Hittade hjälp på Stack Overflow om hur man kunde skapa en **string resource** med placeholders
Gjorde detta eftersom jag fick varningar om att man inte skulle konkatenera text med setText

### Compass
- Kollade denna video: https://www.youtube.com/watch?v=nOQxq2YpEjQ
- Har haft svårt att testa kompassen, när jag testar i emulatorn är det ofta som den ej visar rätt



## Eventuella modifieringar

### Acceleromter

- Jag har lagt till så att man ser vilken lutning telefonen har,
åtminstone för de exakta lutningarna (upp, ner, vänster, höger)
- När telefonen ligger platt (med skärmen uppåt) blir bakgrunden grön och det visas en text "Flat"


### Compass
- När kompassen pekar norr, så blir texten som visar riktning och grader röd