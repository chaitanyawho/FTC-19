package org.firstinspires.ftc.teamcode.Basics;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

public class CatchException extends OpMode {

    int x;

    @Override
    public void init() {

    }

    @Override
    public void loop() {

        for (x=1; x<11; x++) {

            try {

                wait(5000);
            } catch (InterruptedException e) {

                telemetry.addData("Caught:", "Interrupted Exception");
            }
        }
    }
}
