package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


    @Autonomous(name = "AutoBlue", group = "blue")
    public class AutoBlue extends LinearOpMode {

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
            if (s == "A") {
                drive.driveDistance2(.5, 72, 5000);
                drive.turn(180, true,.5);
                drive.wobbleDeliver();
            }

        }
}
