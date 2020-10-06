package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;


public class TeleOp extends OpMode {
    DcMotor frontRight, frontLeft, backRight, backLeft, collector;
    final double COLLECTPOWER= 0.5;


    public void init() {
        frontRight = hardwareMap.dcMotor.get("frontRight");
        frontLeft = hardwareMap.dcMotor.get("frontLeft");
        backRight = hardwareMap.dcMotor.get("backRight");
        backLeft = hardwareMap.dcMotor.get("backLeft");


        frontRight.setDirection(DcMotor.Direction.REVERSE);
        frontLeft.setDirection(DcMotor.Direction.FORWARD);
        backRight.setDirection(DcMotor.Direction.REVERSE);
        backLeft.setDirection(DcMotor.Direction.FORWARD);

        frontLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frontRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backLeft.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        backRight.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        collector =hardwareMap.dcMotor.get("collector");
        collector.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        collector.setDirection(DcMotor.Direction.FORWARD);


    }

    public void loop() {
        setDriveMotors();
        setCollectorMotor();
    }

    private void setCollectorMotor () {
        if (gamepad1.right_bumper) {
            collector.setPower(COLLECTPOWER);
        } else if (gamepad1.left_bumper) {
            collector.setPower(-COLLECTPOWER);
        } else {
            collector.setPower(0);
        }
    }

    private void setDriveMotors(){
        if(gamepad1.right_stick_y - gamepad1.right_stick_x > 1){
            frontLeft.setPower(1 - gamepad1.left_stick_x/2);
            backRight.setPower(1 + gamepad1.left_stick_x/2);
        }
        else{
            frontLeft.setPower((gamepad1.right_stick_y - gamepad1.right_stick_x) - gamepad1.left_stick_x/2);
            backRight.setPower((gamepad1.right_stick_y - gamepad1.right_stick_x) + gamepad1.left_stick_x/2);
        }
        if(gamepad1.right_stick_y + gamepad1.right_stick_x > 1){
            frontRight.setPower(1 + gamepad1.left_stick_x/2);
            backLeft.setPower(1 - gamepad1.left_stick_x/2);
        }
        else {
            frontRight.setPower((gamepad1.right_stick_y + gamepad1.right_stick_x) + gamepad1.left_stick_x / 2);
            backLeft.setPower((gamepad1.right_stick_y + gamepad1.right_stick_x) - gamepad1.left_stick_x / 2);
        }

    }
}