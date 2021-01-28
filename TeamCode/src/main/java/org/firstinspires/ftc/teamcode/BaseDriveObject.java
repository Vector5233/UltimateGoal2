package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class BaseDriveObject extends Object {
    DcMotor frontLeft, frontRight, backLeft, backRight, intake, sweeper, launcher, wobbleGoalGrabber;
    Servo WGS;
    ElapsedTime elapsedTime;
    LinearOpMode opmode;
    final double ROBOT_RADIUS = 22.5;
    final double TICKS_PER_INCH_STRAIGHT = (1.056637001) * (383.6 * 2) / (4 * 3.14159265358979323846264);
    final double TICKS_PER_INCH_TURN = TICKS_PER_INCH_STRAIGHT;
    final double TICKS_PER_INCH_STRAFE = (TICKS_PER_INCH_STRAIGHT) * 1.15 * (20.0 / 17.0);
    final double TICKS_PER_DEGREE = (3.14159 / 180) * ROBOT_RADIUS * TICKS_PER_INCH_TURN;
    final double TOLERANCE = 1;  // idk about the tolerance
/* we are using the same motors with same gear ratio (1:2) parellel to last year
 add the ticks per strafe and ticks per arc if it is needed*/

    final double Tolerance = 3;
    final double FRPower = 1;
    final double FLPower = 1;
    final double BLPower = 1;
    final double BRPower = 1;

    final double COLLECT_POWER = 0.5;

    final boolean BLUE = true;
    final boolean RED = false;
    //blue is default


    double FRpower = 1;
    double FLpower = 1;
    double BRpower = 1;
    double BLpower = 1;
    final double INTAKE_POWER = 0.7;
    final double LAUNCH_POWER = 0.7;

    double MAXSPEED = 0.7;

    private ElapsedTime strafeTimeout;
    private ElapsedTime driveTimeout;
    private ElapsedTime turnTimeout;

    //add tenserflow and other variables if needed    /* power of the dcmoters may change while testing */
    ElapsedTime opModeTime = new ElapsedTime();
    /* reset time encoder (?) im not sure what his coding does. Remove if it is not needed */


    public BaseDriveObject(DcMotor FL, DcMotor FR, DcMotor BL, DcMotor BR, DcMotor WGG, DcMotor IN, DcMotor IN2, Servo WG, LinearOpMode parent) {
        frontLeft = FL;
        frontRight = FR;
        backRight = BR;
        backLeft = BL;
        wobbleGoalGrabber = WGG;
        intake = IN;
        sweeper = IN2;

        WGS = WG;

        opmode = parent;
    }

    public BaseDriveObject(LinearOpMode parent) {
        opmode = parent;

        frontLeft = opmode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opmode.hardwareMap.dcMotor.get("frontRight");

        backLeft = opmode.hardwareMap.dcMotor.get("backLeft");
        backRight = opmode.hardwareMap.dcMotor.get("backRight");

        intake = opmode.hardwareMap.dcMotor.get("intake");
        sweeper = opmode.hardwareMap.dcMotor.get("sweeper");
        launcher = opmode.hardwareMap.dcMotor.get("launcher");
        wobbleGoalGrabber = opmode.hardwareMap.dcMotor.get("wobbleGoalGrabber");
        WGS = opmode.hardwareMap.servo.get("WGS");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);

        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);


    }

    public void stopDriving() {
        frontLeft.setPower(0.0);
        frontRight.setPower(0.0);
        backLeft.setPower(0.0);
        backRight.setPower(0.0);
    }

    public void telemetryDcMotor() {
        opmode.telemetry.addData("FR", frontRight.getCurrentPosition());
        opmode.telemetry.addData("FB", frontLeft.getCurrentPosition());
        opmode.telemetry.addData("BR", backRight.getCurrentPosition());
        opmode.telemetry.addData("BL", backLeft.getCurrentPosition());
        opmode.telemetry.update();
    }



    public void setModeAll(DcMotor.RunMode mode) {
        frontLeft.setMode(mode);
        frontRight.setMode(mode);
        backLeft.setMode(mode);
        backRight.setMode(mode);
    }
    public void setModeDriving() {
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void driveDistance(double power, double distance, int timeOut) {
        driveTimeout = new ElapsedTime();
        int DRIVE_TIMEOUT = timeOut;
        int ticks = (int) (distance * TICKS_PER_INCH_STRAIGHT);

        if (power > MAXSPEED) {
            power = MAXSPEED;
        }


        setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        backRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);

        setModeDriving();
        if (distance >= 0) {
            frontLeft.setPower(power);
            frontRight.setPower(power);
            backRight.setPower(power);
            backLeft.setPower(power);
        }
        else {
            frontLeft.setPower(-power);
            frontRight.setPower(-power);
            backRight.setPower(power);
            backLeft.setPower(power);
        }
        while ((backRight.isBusy() || backLeft.isBusy()) && opmode.opModeIsActive()) {
            if (driveTimeout.milliseconds() > DRIVE_TIMEOUT)
                break;
        }

        //telemetryDcMotor();

        stopDriving();
    }

    public void strafeDistance(double power, double distance, int time) {
        strafeTimeout = new ElapsedTime();
        int STRAFE_TIMEOUT = time;

        int ticks = (int) (distance * TICKS_PER_INCH_STRAFE);

        if (power > MAXSPEED) {
            power = MAXSPEED;
        }

        setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);


        backLeft.setTargetPosition(-ticks);
        backRight.setTargetPosition(ticks);

        setModeDriving();
        if (distance >= 0) {
            frontLeft.setPower(power);
            frontRight.setPower(-power);
            backLeft.setPower(power);
            backRight.setPower(power);
        } else {
            frontLeft.setPower(-power);
            frontRight.setPower(power);
            backLeft.setPower(power);
            backRight.setPower(power);
        }
        while ((backRight.isBusy() || backLeft.isBusy()) && opmode.opModeIsActive()) {
            if (strafeTimeout.milliseconds() > STRAFE_TIMEOUT)
                break;
        }

        stopDriving();
    }

    public void setTurnPowerAll(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }

    public void setTurnPower(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(-power);
        backLeft.setPower(power);
        backRight.setPower(-power);
    }

    public void initialize() {
        opmode.telemetry.addLine("initialized");
        opmode.telemetry.update();
    }

    /* public void turn(float angle, boolean CCW, double power) {
        double currentAngle = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle;
        double targetAngle;

        if (CCW)
        {
            targetAngle = currentAngle - angle;

            while (opmode.opModeIsActive() && currentAngle > targetAngle)
            {
                frontRight.setPower(power);
                frontLeft.setPower(-power);
                backRight.setPower(power);
                backLeft.setPower(-power);

                opmode.telemetry.addData("second Angle: ", imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle);

                opmode.telemetry.update();

                currentAngle = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle;
            }
        }
        else
        {
            targetAngle = currentAngle + angle;

            while (opmode.opModeIsActive() && currentAngle < targetAngle)
            {
                frontRight.setPower(-power);
                frontLeft.setPower(power);
                backRight.setPower(-power);
                backLeft.setPower(power);

                opmode.telemetry.addData("second Angle: ", imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle);

                opmode.telemetry.update();

                currentAngle = imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.ZYX, AngleUnit.DEGREES).secondAngle;
            }
        }

        stopDriving();
    }*/
}





