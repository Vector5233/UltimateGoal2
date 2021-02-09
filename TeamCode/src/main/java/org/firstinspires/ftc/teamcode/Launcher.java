package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Launcher {

    DcMotorEx launcher;
    DcMotor sweeper, intake;
    final int LAUNCH_VELOCITY = 1275;
    LinearOpMode parent;
    final int START_UP_TIME = 2000;


    public Launcher(DcMotorEx l, DcMotor s, DcMotor i, LinearOpMode p){

        launcher = l;
        sweeper = s;
        intake = i;
        parent = p;

    }


    public void launch(){
        launcher.setVelocity(LAUNCH_VELOCITY);
        parent.sleep(START_UP_TIME);
        sweeper.setPower(1);
        intake.setPower(1);
        parent.sleep(5000);
        launcher.setPower(0);
        sweeper.setPower(0);
        intake.setPower(0);
    }
}
