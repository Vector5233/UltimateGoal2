package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "AutoBlueLauncher", group = "blue")
public class AutoBlueLauncher extends LinearOpMode {

    /*TODO:
     * Test zone B
     * Test zone C
     * modify ring detection so that we drive up to the rings before ID
     * Reduce times, esp for zone C
     */
    RingIdentifier ring;
    BaseDriveObject drive;
    String s;
    Launcher launcher;


    public void initialize() {
        drive = new BaseDriveObject(this);
        drive.initialize();
        ring = new RingIdentifier(this);
        launcher = new Launcher(drive.launcher, drive.sweeper, drive.intake, drive.intakeServo, this);

    }

    public void runOpMode(){
        initialize();
        waitForStart();
        drive.driveDistance2(.5, 24, 10000);
        s = ring.ringDisplacement();
        telemetry.addData("Zone: ", s);
        telemetry.update();


        // all distances reduced by 24"
        driveLauncher();
        launcher.launch();
        angleCorrection();

        deliverWobbleGoal(s);
    }

    public void driveLauncher() {
        drive.driveDistance2(.5, 34, 10000);
        drive.turn(20,false,0.5);
    }
    public void angleCorrection() {
        drive.turn(20, true, 0.5);
    }
    public void deliverWobbleGoal(String s) {
        if (s == "A") {
            drive.driveDistance2(.5, 14, 10000);
            drive.turn(180, true,.5);
            drive.wobbleDeliver();
        }
        else if (s == "B"){
            drive.driveDistance2(.5, 38, 10000);
            drive.wobbleDeliver();
            drive.driveDistance2(.5, -26, 10000);
        }
        else {
            drive.driveDistance2(.5, 48, 10000);
            drive.turn(180, true, .5);
            drive.strafeDistance2(.5,3,10000);
            drive.wobbleDeliver();
            drive.driveDistance2(.5, 48, 10000);
        }

    }
}