package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "AutoRed", group = "red")
public class AutoRed extends LinearOpMode {

    RingIdentifier ring;
    BaseDriveObject drive;
    String s;

    public void initialize() {
        drive = new BaseDriveObject(this);
        drive.initialize();
        ring = new RingIdentifier(this);

    }

    public void runOpMode(){
        initialize();
        waitForStart();
        s = ring.ringDisplacement();
        telemetry.addData("Zone: ", s);
        telemetry.update();
        sleep(3000);

    }

}
