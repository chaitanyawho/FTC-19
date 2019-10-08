package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.HelpClasses.OpMethods;

public class LineFollower extends LinearOpMode {
    ColorSensor leftColor = null, rightColor = null;
    DcMotor fLeft, fRight, bLeft, bRight;
    OpMethods opMethods = new OpMethods();

    @Override
    public void runOpMode() throws InterruptedException {

        leftColor = hardwareMap.colorSensor.get("LeftColorSensor");
        rightColor = hardwareMap.colorSensor.get("RightColorSensor");
        leftColor.enableLed(true);
        rightColor.enableLed(true);

        telemetry.addData("Status:", "Both Sensors Active....");

        waitForStart();

        while (opModeIsActive()) {

            //Check if both sensors sense the same colour, if true, move forward
            while (leftColor.argb() > 345 && leftColor.argb() < 15
                    && rightColor.argb() > 345 && rightColor.argb() < 15)
                opMethods.goForward(0.8, fLeft, fRight, bLeft, bRight);

            //Turn bot right if bot is angled to the left
            while (leftColor.argb() < 345 && leftColor.argb() > 15)
                opMethods.turnRight(0.5, fLeft, fRight, bLeft, bRight);

            //Turn bot left if bot is angled to the right
            while (rightColor.argb() < 345 && rightColor.argb() > 15)
                opMethods.turnLeft(0.5, fLeft, fRight, bLeft, bRight);
        }

    }
}
