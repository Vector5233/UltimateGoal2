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

        if (s == "A") {
            drive.driveDistance2(0.5, 72, 10000);
            sleep(1000);
            drive.strafeDistance2(0.5, 15, 10000);
            drive.wobbleDeliver();
        }
        else if (s == "B") {
            drive.driveDistance2(0.5,96,10000);
            sleep(1000);
            drive.wobbleDeliver();
            sleep(1000);
            drive.driveDistance2(0.5,-20,10000);
            }
        else {
            drive.driveDistance2(0.5,120,10000);
            sleep(1000);
            drive.strafeDistance2(0.5,18,10000);
            drive.wobbleDeliver();
            sleep(1000);
            drive.driveDistance2(0.5,-44,10000);
        }
    }
}
