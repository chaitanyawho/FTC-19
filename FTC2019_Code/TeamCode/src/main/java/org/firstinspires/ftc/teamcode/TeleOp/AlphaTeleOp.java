package org.firstinspires.ftc.teamcode.TeleOp;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.HelpClasses.OpMethods;

@TeleOp(name = "AlphaTeleOp", group = "TeleOp")
public class AlphaTeleOp extends OpMode {

    private DcMotor FL, FR, BL, BR, LinearTilt, Extender, Latch;
    private OpMethods opms = new OpMethods();
    public ElapsedTime runtime = new ElapsedTime();
    private CRServo PipeShaft;
    private Servo BasketTilt;

    @Override
    public void init() {

        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        Extender = hardwareMap.dcMotor.get("Extender");
        LinearTilt = hardwareMap.dcMotor.get("LinearTilt");
        PipeShaft = hardwareMap.crservo.get("Collector");
        BasketTilt = hardwareMap.servo.get("BasketTilt");
        Latch = hardwareMap.dcMotor.get("Latch");

        Latch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LinearTilt.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        opms.motorStop(FL, FR, BL, BR);

    }

    @Override
    public void loop() {
        telemetry.addData("Status:", "Initialized....");
        telemetry.addData("Status:", "Runtime = " + runtime.toString());

        Extender.setDirection(DcMotor.Direction.FORWARD);
        LinearTilt.setDirection(DcMotor.Direction.FORWARD);
        PipeShaft.setDirection(CRServo.Direction.FORWARD);
        Latch.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        if (gamepad1.dpad_up) {
            opms.goBackward(0.8, FR, FL, BR, BL);
        } else if (gamepad1.dpad_down) {
            opms.goForward(0.8, FR, FL, BR, BL);
        } else if (gamepad1.dpad_right) {
            opms.goRight(0.8, FR, FL, BR, BL);
        } else if (gamepad1.dpad_left) {
            opms.goLeft(0.8, FR, FL, BR, BL);
        } else if (gamepad1.left_bumper) {
            opms.turnLeft(0.8, FR, FL, BR, BL);
        } else if (gamepad1.right_bumper) {
            opms.turnRight(0.8, FR, FL, BR, BL);
        } else {
            FL.setPower(0);
            FR.setPower(0);
            BR.setPower(0);
            BL.setPower(0);
        }


        if (gamepad2.left_stick_y > 0) {
            LinearTilt.setPower(1);
        } else if (gamepad2.left_stick_y < 0) {
            LinearTilt.setPower(-1);
        } else
            LinearTilt.setPower(0);


        if (gamepad2.left_trigger > 0) {
            PipeShaft.setPower(0.5);
        } else if (gamepad2.right_trigger > 0) {
            PipeShaft.setPower(-0.5);
        } else
            PipeShaft.setPower(0);

        if (gamepad2.a) {
            BasketTilt.setPosition(0.5);
        } else if (gamepad2.b) {
            BasketTilt.setPosition(0.7);
        } else if (gamepad2.y) {
            BasketTilt.setPosition(1);
        }

        if (gamepad2.left_stick_y > 0) {
            Latch.setPower(1);
        } else if (gamepad2.left_stick_y < 0) {
            Latch.setPower(-1);
        } else
            Latch.setPower(0);

    }
}
