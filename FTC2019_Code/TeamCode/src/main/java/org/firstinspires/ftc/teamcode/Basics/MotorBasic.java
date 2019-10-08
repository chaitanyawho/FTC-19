package org.firstinspires.ftc.teamcode.Basics;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;

@TeleOp(name = "MotorTest")
public class MotorBasic extends OpMode {

    ElapsedTime runtime = new ElapsedTime();
    DcMotor Dc1;


    public void loop() {
        double power;
            telemetry.addData("Status:", "Initialized and Running....");
            telemetry.addData("Status", "Run Time: " + runtime.toString());

            double drive = -gamepad1.right_stick_y;
            double turn = gamepad1.right_stick_x;
            power = Range.clip(drive + turn, -1.0, 1.0);

            Dc1.setPower(power);

            telemetry.addData("Motors", "center (%.2f)", power);
            Dc1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void init() {

        Dc1 = hardwareMap.dcMotor.get("Test");

    }
}
