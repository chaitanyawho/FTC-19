package org.firstinspires.ftc.teamcode.Basics;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

@TeleOp(name = "ServoT1", group = "Motors")
public class RotateServo extends OpMode {

    ElapsedTime runtime = new ElapsedTime();
    Servo servoRightArm;
    Servo servoLeftArm;

    @Override
    public void init() {

        servoLeftArm = hardwareMap.servo.get("servoRHS");
        servoRightArm = hardwareMap.servo.get("servoRHS");

    }

    @Override
    public void loop() {

        telemetry.addData("Status:", "Initialized and Running....");
        telemetry.addData("Time","Runtime=" + runtime.toString());


        if (gamepad1.left_stick_button) {
            servoLeftArm.setPosition(1);
        } else {
            servoLeftArm.setPosition(0.002);
        }

        if (gamepad1.right_stick_button) {
            servoRightArm.setPosition(1);
        } else {
            servoRightArm.setPosition(0.002);
        }
    }
}
