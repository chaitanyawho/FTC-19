package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;

public class ColorSensorTest extends LinearOpMode {
    private ColorSensor ColorSensor = null;
    // Initialise values for Red, Blue & Green Values and Hue

    @Override
    public void runOpMode() throws InterruptedException {
        // Get the sensor configurations from hardwareMap
        ColorSensor = hardwareMap.colorSensor.get("FrontColor");
        ColorSensor.enableLed(true);

        waitForStart();

        // Display all the values, red, green, blue, and hue.

        telemetry.addData("Red:", ColorSensor.red());
        telemetry.addData("Green:", ColorSensor.green());
        telemetry.addData("Blue:", ColorSensor.blue());
        telemetry.addData("Hue:", ColorSensor.argb()); // ColorSensor.argb() returns hue of detected color.

    }
}
