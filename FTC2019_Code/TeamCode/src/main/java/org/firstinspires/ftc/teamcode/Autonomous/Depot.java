package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name = "Depot", group = "Autonomous")

public class Depot extends LinearOpMode {

    private Servo Bumper, Team;
    private CRServo PipeShaft;
    private DcMotor FL, FR, BL, BR, Latch;
    private boolean foundGold, foundSilver;
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "ARv9kpb/////AAABmVsegg6fM0rho0tXwJq3caeAZucYM3k2U66eOW5eGvy7mBC7/OfLZ3K07vTbuMv85Z+O6qBvNtzyOs2wDz2d7ZH/w7IEVC7/U3MBSl9ArNJJIKrx8A27FX7NPw9/O0xRuix6urqCexspihLX0fx/on58JTW6HBGpQfkMhZtchbwVFn64me0qw0oEHeTWzHkDYw3v/0GzicAJmQy5Hl1FAJvvKbCt2YZ039lf/RnQXpEIh7bAJYE4XDTKJ5YjlQgWjrKKj8L8FLXPxz62GvAxmTDCZyKN5lOmW6vtfxGz92g88ycq5VrD2AnFcsozOY9EDmSnB9WR/Zofsot3WASBm2cn5KSGfPoCHPkolK0/e+kU";

    /*
      {@link #vuforia} is the variable we will use to store our instance of the Vuforia
      localization engine.
     */
    private VuforiaLocalizer vuforia;

    /*
      {@link #tfod} is the variable we will use to store our instance of the Tensor Flow Object
      Detection engine.
     */
    private TFObjectDetector tfod;

    private ElapsedTime runtime = new ElapsedTime();

    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;

        //  Instantiate the Vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // Loading trackables is not necessary for the Tensor Flow Object Detection engine.
    }

    /**
     * Initialize the Tensor Flow Object Detection engine.
     */
    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    public void waitUntilMotorsBusy() {
        while (opModeIsActive() && (runtime.seconds() < 30) &&
                (FL.isBusy() && FR.isBusy() && BL.isBusy() && BR.isBusy())) {
            // Display it for the driver.
            telemetry.addData("Autonomous",  "Motors busy");
            telemetry.update();
        }
    }

    public void setWheelbasePower(double pow) {
        FL.setPower(pow);
        FR.setPower(pow);
        BL.setPower(pow);
        BR.setPower(pow);
    }

    public void setTarget(int Ticks) {
        FL.setTargetPosition(Ticks);
        FR.setTargetPosition(Ticks);
        BL.setTargetPosition(Ticks);
        BR.setTargetPosition(Ticks);
    }

    public void preMovement() {
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void postMovement(double pow) {
        setWheelbasePower(pow);
        waitUntilMotorsBusy();
        setWheelbasePower(0);

        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void setDirForward() {
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setDirBack() {
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setDirRight() {
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setDirLeft() {
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setTurnRight() {
        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.FORWARD);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void setTurnLeft() {
        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.REVERSE);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

    }

    public boolean mineForGold() {


        /* Wait for the game to begin */
        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();

        boolean foundGold = false, foundSilver = false;
        if (opModeIsActive()) {
            /* Activate Tensor Flow Object Detection. */
            if (tfod != null) {
                tfod.activate();
            }

            //while (opModeIsActive())
            for (int ii = 0; ii < 50; ii++)
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
        }
        return foundGold;
    }

    @Override
    public void runOpMode() throws InterruptedException {

        FL = hardwareMap.dcMotor.get("FL");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");
        BR = hardwareMap.dcMotor.get("BR");
        Latch = hardwareMap.dcMotor.get("Latch") ;
        Bumper = hardwareMap.servo.get("Bumper");
        Team = hardwareMap.servo.get("TeamMarker");
        PipeShaft =hardwareMap.crservo.get("Collector");
        initVuforia();

        Latch.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Latch.setDirection(DcMotor.Direction.FORWARD);

        Bumper.setPosition(1);
        Team.setPosition(0.3);

        telemetry.addLine("Waiting for Start....");
        waitForStart();

        telemetry.addLine("Unlatching....");
        Latch.setPower(0.75);
        sleep(3200);
        Latch.setPower(0);

        preMovement();
        setDirForward();
        setTarget(158 * 3); // Come out of the latch
        postMovement(0.8);

        preMovement();
        setDirLeft();
        setTarget(158 * 14);
        postMovement(0.8);

        preMovement();
        setDirForward();
        setTarget(158 * 10);
        postMovement(0.8);

        telemetry.addLine("Detetcing Mineral 1");

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }


        foundGold = mineForGold();

        if (foundGold) {
            telemetry.addData("Will now displace GOLD mineral", "1");
            telemetry.update();

            Bumper.setPosition(0.5);

            preMovement();

            setDirLeft();

            setTarget(158*35);

            postMovement(0.8);

            preMovement();

            setTurnLeft();

            setTarget(158*10);

            postMovement(0.8);

            Bumper.setPosition(1);

            Team.setPosition(1);

            preMovement();

            setDirBack();

            setTarget(158 * 55);

            postMovement(1);

            preMovement();

            setDirRight();

            setTarget(80);

            postMovement(0.5);

            preMovement();

            setDirBack();

            setTarget(158 * 7);

            postMovement(0.3);


        } else {


            telemetry.addData("DID NOT FIND GOLD mineral to DISPLACE", "1");
            telemetry.addData("Moving on to NEXT MINERAL", "2");

            preMovement();

            setDirBack();

            setTarget(158 * 14);

            setWheelbasePower(0.8);

            postMovement(0.8);

            telemetry.addLine("Detecting Mineral 2");

            foundGold = mineForGold();

            if (foundGold) {

                telemetry.addData("Will now displace GOLD mineral", "2");
                telemetry.update();

                Bumper.setPosition(0.5);

                preMovement();

                setDirLeft();

                setTarget(158*37);

                postMovement(0.8);

                Bumper.setPosition(1);

                preMovement();

                setDirBack();

                setTarget(158* 13);

                postMovement(0.8);

                preMovement();

                setDirForward();

                setTarget(158* 3);

                postMovement(0.8);

                Team.setPosition(1);

                preMovement();

                setTurnLeft();

                setTarget(158 * 11);

                postMovement(0.8);

                preMovement();

                setDirBack();

                setTarget(158 * 50);

                postMovement(1);

                preMovement();

                setDirRight();

                setTarget(80);

                postMovement(0.5);

                preMovement();

                setDirBack();

                setTarget(158 * 4);

                postMovement(0.3);

            }
            else {


                telemetry.addData("DID NOT FIND GOLD mineral to DISPLACE", "2");
                telemetry.addData("Moving on to NEXT MINERAL", "3");
                telemetry.update();

                preMovement();

                setDirBack();

                setTarget(158 * 15);

                postMovement(0.8);


                telemetry.addData("Will now displace GOLD mineral", "3");
                telemetry.update();

                Bumper.setPosition(0.5);

                preMovement();

                setDirLeft();

                setTarget(158 * 40);

                postMovement(0.8);

                preMovement();

                setTurnLeft();

                setTarget(158 * 10);

                postMovement(0.8);

                preMovement();

                setDirBack();

                setTarget(158 * 51);

                postMovement(1);

                preMovement();

                setDirBack();

                setTarget(79 * 14);

                postMovement(0.3);
            }
        }
    }
}

