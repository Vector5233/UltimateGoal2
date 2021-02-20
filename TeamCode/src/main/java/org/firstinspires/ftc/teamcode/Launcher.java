package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;

public class Launcher {

    DcMotorEx launcher;
    DcMotor sweeper, intake;
    CRServo intakeServo;
    final int LAUNCH_VELOCITY = 1275;
    LinearOpMode parent;
    final int START_UP_TIME = 2000;
    final double INTAKE_SERVO_POWER = 1;
    final double SWEEPER_POWER = -1;
    final double INTAKE_POWER = -1;
    final double LAUNCHER_POWER = 1;
    public Launcher(DcMotorEx l, DcMotor s, DcMotor i, CRServo iS, LinearOpMode p){

        launcher = l;
        sweeper = s;
        intake = i;
        parent = p;
        intakeServo = iS;



    }


    public void launch(){
        launcher.setVelocity(LAUNCH_VELOCITY);
        parent.sleep(START_UP_TIME);
        sweeper.setPower(SWEEPER_POWER);
        intake.setPower(INTAKE_POWER);
        intakeServo.setPower(INTAKE_SERVO_POWER);
        parent.sleep(4000);
        launcher.setPower(LAUNCHER_POWER);
        sweeper.setPower(0);
        intake.setPower(0);
        intakeServo.setPower(0);
        launcher.setPower(0);
    }
}
