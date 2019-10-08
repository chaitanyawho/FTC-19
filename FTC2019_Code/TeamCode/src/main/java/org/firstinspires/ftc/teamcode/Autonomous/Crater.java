package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Servo;
//import java.util.stream.Collector;
import com.qualcomm.robotcore.hardware.CRServo;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;


@Autonomous(name = "TensorFlow Object Detection", group = "TensorFlow")
public class Crater extends LinearOpMode {

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "ARv9kpb/////AAABmVsegg6fM0rho0tXwJq3caeAZucYM3k2U66eOW5eGvy7mBC7/OfLZ3K07vTbuMv85Z+O6qBvNtzyOs2wDz2d7ZH/w7IEVC7/U3MBSl9ArNJJIKrx8A27FX7NPw9/O0xRuix6urqCexspihLX0fx/on58JTW6HBGpQfkMhZtchbwVFn64me0qw0oEHeTWzHkDYw3v/0GzicAJmQy5Hl1FAJvvKbCt2YZ039lf/RnQXpEIh7bAJYE4XDTKJ5YjlQgWjrKKj8L8FLXPxz62GvAxmTDCZyKN5lOmW6vtfxGz92g88ycq5VrD2AnFcsozOY9EDmSnB9WR/Zofsot3WASBm2cn5KSGfPoCHPkolK0/e+kU";

    /**
     * {@link #vuforia} is the variable we will use to store our instance of the Vuforia
     * localization engine.
     */
    private VuforiaLocalizer vuforia;

    /**
     * {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
     * Detection engine.
     */
    private TFObjectDetector tfod;

    DcMotor FL, FR, BL, BR, Latch;
    Servo Bumper;
    Servo TeamMark;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {
        FL = hardwareMap.dcMotor.get("FL");
        BR = hardwareMap.dcMotor.get("BR");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        Bumper = hardwareMap.servo.get("Bumper");
        TeamMark = hardwareMap.servo.get("TeamMarker");
        Latch = hardwareMap.dcMotor.get("Latch");

        FL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        FR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BL.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        BR.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        telemetry.addData("Autonomous","Waiting for START" );
        telemetry.update();

        TeamMark.setPosition(0);

        waitForStart();

        runtime.reset();

        Latch.setDirection(DcMotorSimple.Direction.FORWARD);

        Latch.setPower(0.75);

        sleep(3200);

        Latch.setPower(0);

        preMovement();

        setDirForward();

        setTarget(158 * 4); // Come out of the latch

        postMovement(0.7);

        telemetry.addLine("Resetting Bumper" );
        telemetry.update();
        Bumper.setPosition(0);

        setRunToPosition();
        setRunUsingEncoder();
        setStopAndResetEncoder();


        telemetry.addData("Autonomous","Coming out of Latch" );
        telemetry.update();
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setTargetPosition(158*7);//Come out of the latch
        FR.setTargetPosition(158*7);
        BL.setTargetPosition(158*7);
        BR.setTargetPosition(158*7);

        setWheelbasePower(01);
        waitUntilMotorsBusy();
        setWheelbasePower(0);

        setRunToPosition();
        setStopAndResetEncoder();
        setRunUsingEncoder();

        setRunToPosition();

        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setTargetPosition(158*10);//Go forward
        FR.setTargetPosition(158*10);
        BL.setTargetPosition(158*10);
        BR.setTargetPosition(158*10);

        setWheelbasePower(1);
        waitUntilMotorsBusy();
        setWheelbasePower(0);

        setStopAndResetEncoder();

        Bumper.setPosition(0);
        sleep(500);

        setRunUsingEncoder();
        setRunToPosition();

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setTargetPosition(158*5);//Go left
        FR.setTargetPosition(158*5);
        BL.setTargetPosition(158*5);
        BR.setTargetPosition(158*5);

        setWheelbasePower(1);
        waitUntilMotorsBusy();
        setWheelbasePower(0);

        setStopAndResetEncoder();

        InitializeTensorFlow();


        boolean foundGold = TensorFlowMineForGold();
        if (foundGold) {
            telemetry.addData("Will now displace GOLD mineral","1");
            telemetry.update();

            setStopAndResetEncoder();
            setRunUsingEncoder();
            setRunToPosition();

            FL.setDirection(DcMotorSimple.Direction.REVERSE);
            FR.setDirection(DcMotorSimple.Direction.FORWARD);
            BL.setDirection(DcMotorSimple.Direction.REVERSE);
            BR.setDirection(DcMotorSimple.Direction.FORWARD);

            FL.setTargetPosition(158*3);//Go forward and hit
            FR.setTargetPosition(158*3);
            BL.setTargetPosition(158*3);
            BR.setTargetPosition(158*3);

            setWheelbasePower(1);
            waitUntilMotorsBusy();
            setWheelbasePower(0);
            setStopAndResetEncoder();

            setStopAndResetEncoder();

            Bumper.setPosition(0.5);
            sleep(1000);

            setRunUsingEncoder();
            setRunToPosition();

            FL.setDirection(DcMotorSimple.Direction.FORWARD);
            FR.setDirection(DcMotorSimple.Direction.FORWARD);
            BL.setDirection(DcMotorSimple.Direction.REVERSE);
            BR.setDirection(DcMotorSimple.Direction.REVERSE);

            FL.setTargetPosition(158*17);//Go forward and hit
            FR.setTargetPosition(158*17);
            BL.setTargetPosition(158*17);
            BR.setTargetPosition(158*17);

            setWheelbasePower(1);
            waitUntilMotorsBusy();
            setWheelbasePower(0);

            setStopAndResetEncoder();
            setRunUsingEncoder();
            setRunToPosition();

            FL.setDirection(DcMotorSimple.Direction.REVERSE);
            FR.setDirection(DcMotorSimple.Direction.REVERSE);
            BL.setDirection(DcMotorSimple.Direction.FORWARD);
            BR.setDirection(DcMotorSimple.Direction.FORWARD);

            FL.setTargetPosition(158*15);//Go forward and hit
            FR.setTargetPosition(158*15);
            BL.setTargetPosition(158*15);
            BR.setTargetPosition(158*15);

            setWheelbasePower(1);
            waitUntilMotorsBusy();
            setWheelbasePower(0);

            setStopAndResetEncoder();
            setRunUsingEncoder();
            setRunToPosition();


            FL.setDirection(DcMotorSimple.Direction.FORWARD);
            FR.setDirection(DcMotorSimple.Direction.REVERSE);
            BL.setDirection(DcMotorSimple.Direction.FORWARD);
            BR.setDirection(DcMotorSimple.Direction.REVERSE);

            FL.setTargetPosition(158*63);//Go forward
            FR.setTargetPosition(158*63);
            BL.setTargetPosition(158*63);
            BR.setTargetPosition(158*63);

            setWheelbasePower(1);
            waitUntilMotorsBusy();
            setWheelbasePower(0);
            setStopAndResetEncoder();
        }
        else {
            telemetry.addData("DID NOT FIND GOLD mineral to DISPLACE","1");
            telemetry.addData("Moving on to NEXT MINERAL","2");
            telemetry.update();

            setStopAndResetEncoder();
            setRunUsingEncoder();
            setRunToPosition();

            FL.setDirection(DcMotorSimple.Direction.FORWARD);
            FR.setDirection(DcMotorSimple.Direction.REVERSE);
            BL.setDirection(DcMotorSimple.Direction.FORWARD);
            BR.setDirection(DcMotorSimple.Direction.REVERSE);

            FL.setTargetPosition(158*14);//Go left
            FR.setTargetPosition(158*14);
            BL.setTargetPosition(158*14);
            BR.setTargetPosition(158*14);

            setWheelbasePower(1);
            waitUntilMotorsBusy();
            setWheelbasePower(0);

            setStopAndResetEncoder();

            foundGold = TensorFlowMineForGold();
            if (foundGold) {
                telemetry.addData("Will now displace GOLD mineral","3");
                telemetry.addLine("foundGold = " + foundGold);
                telemetry.update();

                setRunUsingEncoder();
                setRunToPosition();

                FL.setDirection(DcMotorSimple.Direction.REVERSE);
                FR.setDirection(DcMotorSimple.Direction.FORWARD);
                BL.setDirection(DcMotorSimple.Direction.REVERSE);
                BR.setDirection(DcMotorSimple.Direction.FORWARD);

                FL.setTargetPosition(158*4);//Go forward and hit
                FR.setTargetPosition(158*4);
                BL.setTargetPosition(158*4);
                BR.setTargetPosition(158*4);

                setWheelbasePower(01);
                waitUntilMotorsBusy();
                setWheelbasePower(0);

                Bumper.setPosition(0.5);
                sleep(1000);


                setStopAndResetEncoder();
                setRunUsingEncoder();
                setRunToPosition();

                FL.setDirection(DcMotorSimple.Direction.FORWARD);
                FR.setDirection(DcMotorSimple.Direction.FORWARD);
                BL.setDirection(DcMotorSimple.Direction.REVERSE);
                BR.setDirection(DcMotorSimple.Direction.REVERSE);

                FL.setTargetPosition(158*17);//Go forward and hit
                FR.setTargetPosition(158*17);
                BL.setTargetPosition(158*17);
                BR.setTargetPosition(158*17);

                setWheelbasePower(1);
                waitUntilMotorsBusy();
                setWheelbasePower(0);

                setStopAndResetEncoder();
                setRunUsingEncoder();
                setRunToPosition();

                FL.setDirection(DcMotorSimple.Direction.REVERSE);
                FR.setDirection(DcMotorSimple.Direction.REVERSE);
                BL.setDirection(DcMotorSimple.Direction.FORWARD);
                BR.setDirection(DcMotorSimple.Direction.FORWARD);

                FL.setTargetPosition(158*13);//Go forward and hit
                FR.setTargetPosition(158*13);
                BL.setTargetPosition(158*13);
                BR.setTargetPosition(158*13);

                setWheelbasePower(01);
                waitUntilMotorsBusy();
                setWheelbasePower(0);

                setStopAndResetEncoder();
                setRunUsingEncoder();
                setRunToPosition();


                FL.setDirection(DcMotorSimple.Direction.FORWARD);
                FR.setDirection(DcMotorSimple.Direction.REVERSE);
                BL.setDirection(DcMotorSimple.Direction.FORWARD);
                BR.setDirection(DcMotorSimple.Direction.REVERSE);

                FL.setTargetPosition(158*50);//Go forward
                FR.setTargetPosition(158*50);
                BL.setTargetPosition(158*50);
                BR.setTargetPosition(158*50);

                setWheelbasePower(1);
                waitUntilMotorsBusy();
                setWheelbasePower(0);

                Bumper.setPosition(0);
                sleep(450);


                setStopAndResetEncoder();
            }
            else {
                telemetry.addData("MOVING ON TO NEXT mineral","");
                telemetry.update();

                setStopAndResetEncoder();
                setRunUsingEncoder();
                setRunToPosition();

                FL.setDirection(DcMotorSimple.Direction.FORWARD);
                FR.setDirection(DcMotorSimple.Direction.REVERSE);
                BL.setDirection(DcMotorSimple.Direction.FORWARD);
                BR.setDirection(DcMotorSimple.Direction.REVERSE);

                FL.setTargetPosition(158*16);//Go left
                FR.setTargetPosition(158*16);
                BL.setTargetPosition(158*16);
                BR.setTargetPosition(158*16);

                setWheelbasePower(01);
                waitUntilMotorsBusy();
                setWheelbasePower(0);

                setStopAndResetEncoder();

                telemetry.addData("Will now displace GOLD mineral","");
                telemetry.update();

                Bumper.setPosition(0.5);
                sleep(550);


                setRunUsingEncoder();
                setRunToPosition();


                FL.setDirection(DcMotorSimple.Direction.REVERSE);
                FR.setDirection(DcMotorSimple.Direction.FORWARD);
                BL.setDirection(DcMotorSimple.Direction.REVERSE);
                BR.setDirection(DcMotorSimple.Direction.FORWARD);

                FL.setTargetPosition(158*5);//Go forward and hit
                FR.setTargetPosition(158*5);
                BL.setTargetPosition(158*5);
                BR.setTargetPosition(158*5);

                setWheelbasePower(1);
                waitUntilMotorsBusy();
                setWheelbasePower(0);

                setStopAndResetEncoder();
                setRunUsingEncoder();
                setRunToPosition();

                FL.setDirection(DcMotorSimple.Direction.FORWARD);
                FR.setDirection(DcMotorSimple.Direction.FORWARD);
                BL.setDirection(DcMotorSimple.Direction.REVERSE);
                BR.setDirection(DcMotorSimple.Direction.REVERSE);

                FL.setTargetPosition(158*14);//Go forward and hit
                FR.setTargetPosition(158*14);
                BL.setTargetPosition(158*14);
                BR.setTargetPosition(158*14);

                setWheelbasePower(01);
                waitUntilMotorsBusy();
                setWheelbasePower(0);


                setStopAndResetEncoder();
                setRunUsingEncoder();
                setRunToPosition();

                FL.setDirection(DcMotorSimple.Direction.REVERSE);
                FR.setDirection(DcMotorSimple.Direction.REVERSE);
                BL.setDirection(DcMotorSimple.Direction.FORWARD);
                BR.setDirection(DcMotorSimple.Direction.FORWARD);

                FL.setTargetPosition(158*9);//Go forward and hit
                FR.setTargetPosition(158*9);
                BL.setTargetPosition(158*9);
                BR.setTargetPosition(158*9);

                setWheelbasePower(1);
                waitUntilMotorsBusy();
                setWheelbasePower(0);

                setStopAndResetEncoder();
                setRunUsingEncoder();
                setRunToPosition();

                FL.setDirection(DcMotorSimple.Direction.FORWARD);
                FR.setDirection(DcMotorSimple.Direction.REVERSE);
                BL.setDirection(DcMotorSimple.Direction.FORWARD);
                BR.setDirection(DcMotorSimple.Direction.REVERSE);

                FL.setTargetPosition(158*34);//Go forward
                FR.setTargetPosition(158*34);
                BL.setTargetPosition(158*34);
                BR.setTargetPosition(158*34);

                setWheelbasePower(1);
                waitUntilMotorsBusy();
                setWheelbasePower(0);

                setStopAndResetEncoder();
            }
        }

        ShutdownTensorFlow();

        preMovement();

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setTargetPosition(79*27);//Turn to depot.
        FR.setTargetPosition(79*27);
        BL.setTargetPosition(79*27);
        BR.setTargetPosition(79*27);

        setWheelbasePower(1);
        waitUntilMotorsBusy();
        setWheelbasePower(0);

        setStopAndResetEncoder();
        setRunUsingEncoder();
        setRunToPosition();

        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setTargetPosition(158*37);//Go backward towards depot.
        FR.setTargetPosition(158*37);
        BL.setTargetPosition(158*37);
        BR.setTargetPosition(158*37);

        setStopAndResetEncoder();

        TeamMark.setPosition(0.5);
        sleep(1000);

        setRunUsingEncoder();
        setRunToPosition();

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setTargetPosition(158*66);//Go forward to crater and park
        FR.setTargetPosition(158*66);
        BL.setTargetPosition(158*66);
        BR.setTargetPosition(158*66);

        setWheelbasePower(01);
        waitUntilMotorsBusy();
        setWheelbasePower(0);

        setStopAndResetEncoder();
    }


    private void ShutdownTensorFlow()
    {
        if (tfod != null) {
            tfod.shutdown();
        }
    }

    private void InitializeTensorFlow()
    {
        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector())
        {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        if (tfod != null) {
            tfod.activate();
        }
    }


    private boolean TensorFlowMineForGold()
    {
        boolean foundGold = false, foundSilver = false;
        for (int ii = 0; ii < 10; ii++)
        {
            sleep(100);
            telemetry.addLine("Searching for GOLD MINERAL " + ii);
            if (tfod != null) {
                // getUpdatedRecognitions() will return null if no new information is available since
                // the last time that call was made.
                List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                if (updatedRecognitions != null) {
                    for (Recognition recognition : updatedRecognitions) {
                        if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                            foundGold = true;
                            telemetry.addData("GOLD MINERAL Found","");
                            break;
                        }
                        if (recognition.getLabel().equals(LABEL_SILVER_MINERAL)) {
                            foundSilver = true;
                            telemetry.addData("Silver Mineral Found","");
                            break;
                        }
                    }
                    telemetry.update();
                }
            }
            if (foundGold || foundSilver)
                break;
        }

        return foundGold;
    }
}

    
