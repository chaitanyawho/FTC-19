package org.firstinspires.ftc.teamcode.Autonomous;

import com.qualcomm.robotcore.util.ElapsedTime;
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


@Autonomous(name = "TensorFlow Object Detection", group = "Concept")

public class TFSamplingEditing extends LinearOpMode {
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

    DcMotor FL, FR, BL, BR;
    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void runOpMode() {

        FL = hardwareMap.dcMotor.get("FL");
        BR = hardwareMap.dcMotor.get("BR");
        FR = hardwareMap.dcMotor.get("FR");
        BL = hardwareMap.dcMotor.get("BL");

        waitForStart();

        initVuforia();

        setRunToPosition();
        setStopAndResetEncoder();
        setRunUsingEncoder();

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setTargetPosition(158*5);//Come out of the latch
        FR.setTargetPosition(158*5);
        BL.setTargetPosition(158*5);
        BR.setTargetPosition(158*5);

        setWheelbasePower(0.5);
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

        FL.setTargetPosition(158*13);//Go forward
        FR.setTargetPosition(158*13);
        BL.setTargetPosition(158*13);
        BR.setTargetPosition(158*13);

        setWheelbasePower(0.5);
        waitUntilMotorsBusy();
        setWheelbasePower(0);

        setStopAndResetEncoder();
        setRunUsingEncoder();
        setRunToPosition();

        FL.setDirection(DcMotorSimple.Direction.REVERSE);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.FORWARD);

        FL.setTargetPosition(158*9);//Go left
        FR.setTargetPosition(158*9);
        BL.setTargetPosition(158*9);
        BR.setTargetPosition(158*9);

        setWheelbasePower(0.5);
        waitUntilMotorsBusy();
        setWheelbasePower(0);

        setStopAndResetEncoder();
        setRunUsingEncoder();
        setRunToPosition();

        FL.setDirection(DcMotorSimple.Direction.FORWARD);
        FR.setDirection(DcMotorSimple.Direction.FORWARD);
        BL.setDirection(DcMotorSimple.Direction.REVERSE);
        BR.setDirection(DcMotorSimple.Direction.REVERSE);

        FL.setTargetPosition(158*3);//Go forward and hit
        FR.setTargetPosition(158*3);
        BL.setTargetPosition(158*3);
        BR.setTargetPosition(158*3);

        setWheelbasePower(0.5);
        waitUntilMotorsBusy();
        setWheelbasePower(0);
        setStopAndResetEncoder();

        //initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

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

        if (foundGold) {
            telemetry.addData("Will now displace GOLD mineral","");
            telemetry.update();

            setStopAndResetEncoder();
            setRunUsingEncoder();
            setRunToPosition();

            FL.setDirection(DcMotorSimple.Direction.FORWARD);
            FR.setDirection(DcMotorSimple.Direction.FORWARD);
            BL.setDirection(DcMotorSimple.Direction.REVERSE);
            BR.setDirection(DcMotorSimple.Direction.REVERSE);

            FL.setTargetPosition(158*8);//Go forward and hit
            FR.setTargetPosition(158*8);
            BL.setTargetPosition(158*8);
            BR.setTargetPosition(158*8);

            setWheelbasePower(0.5);
            waitUntilMotorsBusy();
            setWheelbasePower(0);

        }
        else {
            telemetry.addData("DID NOT FIND GOLD mineral to DISPLACE","");
            telemetry.addData("Moving on to NEXT MINERAL","");
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

            setWheelbasePower(0.5);
            waitUntilMotorsBusy();
            setWheelbasePower(0);

            for (int ii = 0; ii < 50; ii++)
            {
                sleep(100);
                telemetry.addLine("Searching for GOLD MINERAL " + ii);
                if (tfod != null) {
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

        if (foundGold) {
            telemetry.addData("Will now displace GOLD mineral","");
            telemetry.update();

            setStopAndResetEncoder();
            setRunUsingEncoder();
            setRunToPosition();

            FL.setDirection(DcMotorSimple.Direction.FORWARD);
            FR.setDirection(DcMotorSimple.Direction.FORWARD);
            BL.setDirection(DcMotorSimple.Direction.REVERSE);
            BR.setDirection(DcMotorSimple.Direction.REVERSE);

            FL.setTargetPosition(158*8);//Go forward and hit
            FR.setTargetPosition(158*8);
            BL.setTargetPosition(158*8);
            BR.setTargetPosition(158*8);

            setWheelbasePower(0.5);
            waitUntilMotorsBusy();
            setWheelbasePower(0);
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

            FL.setTargetPosition(158*14);//Go left
            FR.setTargetPosition(158*14);
            BL.setTargetPosition(158*14);
            BR.setTargetPosition(158*14);

            setWheelbasePower(0.5);
            waitUntilMotorsBusy();
            setWheelbasePower(0);

            telemetry.addData("Will now displace GOLD mineral","");
            telemetry.update();

            setStopAndResetEncoder();
            setRunUsingEncoder();
            setRunToPosition();

            FL.setDirection(DcMotorSimple.Direction.FORWARD);
            FR.setDirection(DcMotorSimple.Direction.FORWARD);
            BL.setDirection(DcMotorSimple.Direction.REVERSE);
            BR.setDirection(DcMotorSimple.Direction.REVERSE);

            FL.setTargetPosition(158*8);//Go forward and hit
            FR.setTargetPosition(158*8);
            BL.setTargetPosition(158*8);
            BR.setTargetPosition(158*8);

            setWheelbasePower(0.5);
            waitUntilMotorsBusy();
            setWheelbasePower(0);
        }

        if (tfod != null) {
            tfod.shutdown();
        }

    }

    /**
     * Initialize the Vuforia localization engine.
     */
    private void initVuforia() {
        /*
         * Configure Vuforia by creating a Parameter object, and passing it to the Vuforia engine.
         */
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.FRONT;

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

    private void waitUntilMotorsBusy() {
        while (opModeIsActive() && (runtime.seconds() < 30) &&
                (FL.isBusy() && FR.isBusy() && BL.isBusy() && BR.isBusy())) {
            // Display it for the driver.
            telemetry.addData("Autonomous",  "Motors busy");
            telemetry.update();
        }
    }

    private void setWheelbasePower(double pow) {
        FL.setPower(pow);
        FR.setPower(pow);
        BL.setPower(pow);
        BR.setPower(pow);
    }

    public void setRunUsingEncoder() {
        FL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        FR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BL.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        BR.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
    }

    public void setRunToPosition() {
        FL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        FR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BL.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        BR.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void setStopAndResetEncoder() {
        FR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        FL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BR.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        BL.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
    }
}
