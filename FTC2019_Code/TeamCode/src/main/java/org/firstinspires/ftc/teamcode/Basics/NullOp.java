package org.firstinspires.ftc.teamcode.Basics;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name = "NullOp", group = "Testing")
public class NullOp extends OpMode {

    private ElapsedTime runtime = new ElapsedTime();

    @Override
    public void init() {
        telemetry.addData("Status", "Initialized");
    }

    /* This method will be called ONCE when start is pressed */
    @Override
    public void start() {
        runtime.reset();
    }

    /* This method will be called repeatedly in a loop */
    @Override
    public void loop() {
        telemetry.addData("Status", "Run Time: " + runtime.toString());
    }
}




