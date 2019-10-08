package org.firstinspires.ftc.teamcode.Sensors;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import static com.qualcomm.robotcore.hardware.DistanceSensor.distanceOutOfRange;

public class DistanceSensorTest extends LinearOpMode {
    private DistanceSensor fDist = null;

    @Override
    public void runOpMode() throws InterruptedException {
        fDist = hardwareMap.get(DistanceSensor.class, "fDistSensor");

        waitForStart();

        double distCM = fDist.getDistance(DistanceUnit.CM);
        double distInch = fDist.getDistance(DistanceUnit.INCH);
        double distM = fDist.getDistance(DistanceUnit.METER);

        if (distCM != distanceOutOfRange && distInch != distanceOutOfRange
                && distM != distanceOutOfRange) {

            telemetry.addData("Distance in CM = ", distCM);
            telemetry.addData("Distance in Inches = ", distInch);
            telemetry.addData("Distance in Meters = ", distM);
        } else
            telemetry.addLine("Failed! Object Out Of Range!");
    }
}
