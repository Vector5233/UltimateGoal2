package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "AutoBlueDelay5", group = "blue")
public class AutoBlueDelay5 extends LinearOpMode {

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
        sleep(5000);

        telemetry.addData("Zone: ", s);
        telemetry.update();

        if (s == "A") {
            drive.driveDistance2(.5, 72, 10000);
            drive.turn(180, true,.5);
            drive.wobbleDeliver();
        }
        else if (s == "B"){
            drive.driveDistance2(.5, 100, 10000);
            drive.wobbleDeliver();
            drive.driveDistance2(.5, -22, 10000);
        }
        else {
            drive.driveDistance2(.5, 120, 10000);
            drive.turn(180, true, .5);
            drive.strafeDistance2(.5,3,10000);
            drive.wobbleDeliver();
            drive.driveDistance2(.5, 48, 10000);
        }
    }

}
