package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name = "AutoRedLauncher", group = "Red")
public class AutoRedLauncher extends LinearOpMode {


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
        drive.turn(20,false,0.4);
    }
    public void angleCorrection() {
        drive.turn(20, true, 0.4);
    }
    public void deliverWobbleGoal(String s) {
        if (s == "A") {
            drive.strafeDistance2(0.5, 15, 10000 );
            drive.wobbleDeliver();
        }
        else if (s == "B"){
            drive.driveDistance2(.5, 36,10000);
            drive.wobbleDeliver();
            drive.driveDistance2(.5, -21, 10000);
        }
        else {
            drive.driveDistance2(.7,  57 , 10000);
            drive.strafeDistance2(0.5,  15, 10000);
            drive.wobbleDeliver();
            drive.driveDistance2(.95, -41, 10000);
        }

    }
}