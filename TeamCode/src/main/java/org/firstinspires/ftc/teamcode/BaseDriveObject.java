package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class BaseDriveObject extends Object {
    DcMotor frontLeft, frontRight, backLeft, backRight, collector, WGG;
    Servo WGS;
    ElapsedTime elapsedTime;
    LinearOpMode opmode;
    final double ROBOT_RADIUS = 22.5;
    final double TICKS_PER_INCH_STRAIGHT = (383.6 * 2) / (4 * 3.14159265358979323846264);
    final double TICKS_PER_INCH_TURN = TICKS_PER_INCH_STRAIGHT;
/* we are using the same motors with same gear ratio (1:2) parellel to last year
 add the ticks per strafe and ticks per arc if it is needed*/
 */

    final double Tolerance = 3;
    final double FRPower = 1;
    final double FLPower = 1;
    final double BLPower  = 1;
    final double BRPower = 1;

    final double COLLECT_POWER = 0.5;
    /* power of the dcmoters may change while testing */

    ElapsedTime opModeTime = new ElapsedTime();
    /* reset time encoder (?) im not sure what his coding does. Remove if it is not needed */


    public BaseDriveObject(LinearOpMode parent) {
        opmode = parent;

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);



    }


    public void initialize() {

        WGG.setZeroPowerBehavior();
    }
}
