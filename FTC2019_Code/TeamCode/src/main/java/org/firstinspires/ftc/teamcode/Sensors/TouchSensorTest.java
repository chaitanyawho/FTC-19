package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.TouchSensor;

public class TouchSensorTest extends LinearOpMode {
    TouchSensor fTouch = null;

    @Override
    public void runOpMode() throws InterruptedException {
        fTouch = hardwareMap.touchSensor.get("fTouch");

        waitForStart();

        if (fTouch.getValue() > 0.2) // Or you could also use fTouch.isPressed() as condition
            telemetry.addData("Touch Sensor:", "Is being pressed");
        else
            telemetry.addData("Touch Sensor:","Is not being pressed");
    }
}
