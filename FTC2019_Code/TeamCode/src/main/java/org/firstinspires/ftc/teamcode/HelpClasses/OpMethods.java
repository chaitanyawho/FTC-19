package org.firstinspires.ftc.teamcode.HelpClasses;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.util.ElapsedTime;

/*This is NOT an OpMode, this is just a class containing methods.
This class is to be imported in your other OpModes and used as a shortcut.
 */
public class OpMethods extends OpMode {

    private DcMotor FR;
    private DcMotor FL;
    private DcMotor BR;
    private DcMotor BL;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {

    }

    @Override
    public void loop() {

    }
    public void goForward(double pow, DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setPower(pow);
        FR.setPower(pow);
        BR.setPower(pow);
        BL.setPower(pow);
    }

    public void goBackward(double pow, DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setPower(pow);
        FR.setPower(pow);
        BR.setPower(pow);
        BL.setPower(pow);
    }

    public void goLeft(double pow, DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setPower(pow);
        FR.setPower(pow);
        BR.setPower(pow);
        BL.setPower(pow);
    }

    public void goRight(double pow, DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setPower(pow);
        FR.setPower(pow);
        BR.setPower(pow);
        BL.setPower(pow);
    }

    public void turnLeft(double pow, DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setPower(pow);
        FR.setPower(pow);
        BR.setPower(pow);
        BL.setPower(pow);
    }

    public void turnRight(double pow, DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setPower(pow);
        FR.setPower(pow);
        BR.setPower(pow);
        BL.setPower(pow);
    }

    public void goFrontRight(double pow, DcMotor FL, DcMotor BR) {

        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setPower(pow);
        BR.setPower(pow);
    }

    public void goFrontLeft(double pow, DcMotor FL, DcMotor BR) {

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setPower(pow);
        BR.setPower(pow);
    }

    public void goBackRight(double pow, DcMotor FR, DcMotor BL) {

        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setPower(pow);
        BR.setPower(pow);
    }

    public void goBackLeft(double pow, DcMotor FL, DcMotor BR) {

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setPower(pow);
        BR.setPower(pow);
    }

    public void motorStop(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FL.setPower(0.0);
        FR.setPower(0.0);
        BL.setPower(0.0);
        BR.setPower(0.0);
    }

    public void encoderDrive(int targetTicks, DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FR.setTargetPosition(targetTicks);
        FL.setTargetPosition(targetTicks);
        BR.setTargetPosition(targetTicks);
        BL.setTargetPosition(targetTicks);
    }

    public void addTelemetry(ElapsedTime runtime) {

         telemetry.addData("Status:", "Initialized and Running");
         telemetry.addData("Status:", "Runtime = "+ runtime.toString());
    }

    public void setPow(double pow, DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FR.setPower(pow);
        FL.setPower(pow);
        BR.setPower(pow);
        BL.setPower(pow);
    }

    public void runPos(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void stopReset(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }

    public void runEnc(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void whileBusy(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR) {

        while (FL.isBusy() && FR.isBusy() && BL.isBusy() && BR.isBusy()) {


        }
    }
}
