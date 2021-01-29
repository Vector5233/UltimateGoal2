package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous (name = "DriveTestOpMode", group = "test")
public class DriveTestOpMode extends LinearOpMode {
    BaseDriveObject drive;

    public void initialize() {
        drive = new BaseDriveObject(this);
        drive.initialize();

    }

    public void runOpMode() {
        initialize();
        waitForStart();
        telemetry.addLine("Hello");
        telemetry.update();
        sleep(2000);
        drive.turn(180, true, .5);
        sleep(2000);
        drive.turn(90, false, .5);
        drive.telemetryDcMotor();
        sleep(2000);
        telemetry.addLine("Done");
        telemetry.update();
    }

}
