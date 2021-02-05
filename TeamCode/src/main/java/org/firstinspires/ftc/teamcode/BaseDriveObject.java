package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;

public class BaseDriveObject extends Object {
    DcMotor frontLeft, frontRight, backLeft, backRight, intake, sweeper, launcher;
    DcMotorEx wobbleGoalGrabber;
    Servo WGS;
    BNO055IMU imu;
    ElapsedTime elapsedTime;
    LinearOpMode opmode;
    final double ROBOT_RADIUS = 20;
    final double TICKS_PER_INCH_STRAIGHT = (1.056637001) * (383.6 * 2) / (4 * 3.14159265358979323846264);
    final double TICKS_PER_INCH_TURN = TICKS_PER_INCH_STRAIGHT;
    final double TICKS_PER_INCH_STRAFE = (TICKS_PER_INCH_STRAIGHT) * 1.15 * (20.0 / 17.0);
    final double TICKS_PER_DEGREE = (3.14159 / 180) * ROBOT_RADIUS * TICKS_PER_INCH_TURN * 180./250.;
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
    final double WGG_OPENED = 330;
    final double WGG_CLOSED = 0;
    final double WGG_SERVO_OPENED = .45;
    final double WGG_SERVO_CLOSED = 0.8;
    double MAXSPEED = 0.7;
    double MAXSPEEDSWEEPER = 0.3;

    private ElapsedTime strafeTimeout;
    private ElapsedTime driveTimeout;
    private ElapsedTime turnTimeout;
    private ElapsedTime sweeperTimeout;

    //add tenserflow and other variables if needed    /* power of the dcmoters may change while testing */
    ElapsedTime opModeTime = new ElapsedTime();
    /* reset time encoder (?) im not sure what his coding does. Remove if it is not needed */


    public BaseDriveObject(LinearOpMode parent) {
        opmode = parent;

        frontLeft = opmode.hardwareMap.dcMotor.get("frontLeft");
        frontRight = opmode.hardwareMap.dcMotor.get("frontRight");

        backLeft = opmode.hardwareMap.dcMotor.get("backLeft");
        backRight = opmode.hardwareMap.dcMotor.get("backRight");

        intake = opmode.hardwareMap.dcMotor.get("intake");
        sweeper = opmode.hardwareMap.dcMotor.get("sweeper");
        launcher = opmode.hardwareMap.dcMotor.get("launcher");
        wobbleGoalGrabber = (DcMotorEx) opmode.hardwareMap.dcMotor.get("wobbleGoalGrabber");
        WGS = opmode.hardwareMap.servo.get("WGS");

        frontLeft.setDirection(DcMotor.Direction.REVERSE);
        frontRight.setDirection(DcMotor.Direction.FORWARD);

        backRight.setDirection(DcMotorSimple.Direction.FORWARD);
        backLeft.setDirection(DcMotorSimple.Direction.REVERSE);

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit           = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit           = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json"; // see the calibration sample opmode
        parameters.loggingEnabled      = true;
        parameters.loggingTag          = "IMU";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        // Retrieve and initialize the IMU. We expect the IMU to be attached to an I2C port
        // on a Core Device Interface Module, configured to be a sensor of type "AdaFruit IMU",
        // and named "imu".
        imu = opmode.hardwareMap.get(BNO055IMU.class, "imu");
        imu.initialize(parameters);
        imu.startAccelerationIntegration(new Position(), new Velocity(), 10);
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

    public void setTargetAll(int ticks) {
        frontLeft.setTargetPosition(ticks);
        frontRight.setTargetPosition(ticks);
        backLeft.setTargetPosition(ticks);
        backRight.setTargetPosition(ticks);
    }

    public void setPowerAll(double power) {
        frontLeft.setPower(power);
        frontRight.setPower(power);
        backLeft.setPower(power);
        backRight.setPower(power);
    }

    public void setModeDriving() {
        frontRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRight.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        backLeft.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

        /**
     * driveDistance controls linear movement forward/backwards
     * @param power power provided to motor (value between 0 and 1)
     * @param distance distance traveled (inches)
     * @param timeOut time taken before bot stops traveling (ms)
     */
    
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

    public void driveDistance2(double power, double distance, int timeOut) {
        driveTimeout = new ElapsedTime();
        int DRIVE_TIMEOUT = timeOut;
        int ticks = (int) (distance * TICKS_PER_INCH_STRAIGHT);

        if (power > MAXSPEED) {
            power = MAXSPEED;
        }

        setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        setTargetAll(ticks);
        setModeAll(DcMotor.RunMode.RUN_TO_POSITION);
        setPowerAll(power);

        while((backRight.isBusy()||backLeft.isBusy())&&opmode.opModeIsActive()) {
            if (driveTimeout.milliseconds() > timeOut) break;

        }

        stopDriving();

    }

        /**
     *  strafeDistance controls linear movement left/right
     * @param power power provided to motor (value between 0 and 1)
     * @param distance (approximate) distance traveled (inches)
     * @param time time taken before bot will stop moving (ms)
     */

        public void strafeDistance2(double power, double distance, int time) {
            strafeTimeout = new ElapsedTime();
            int STRAFE_TIMEOUT = time;

            int ticks = (int) (distance * TICKS_PER_INCH_STRAFE);

            if (power > MAXSPEED) {
                power = MAXSPEED;
            }

            setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            frontLeft.setTargetPosition(ticks);
            frontRight.setTargetPosition(-ticks);
            backLeft.setTargetPosition(-ticks);
            backRight.setTargetPosition(ticks);

            setModeAll(DcMotor.RunMode.RUN_TO_POSITION);
            if (distance >= 0) {
                frontLeft.setPower(power);
                frontRight.setPower(-power);
                backLeft.setPower(-power);
                backRight.setPower(power);
            } else {
                frontLeft.setPower(-power);
                frontRight.setPower(power);
                backLeft.setPower(power);
                backRight.setPower(-power);
            }
            while ((backRight.isBusy() || backLeft.isBusy()) && opmode.opModeIsActive()) {
                if (strafeTimeout.milliseconds() > STRAFE_TIMEOUT)
                    break;
            }

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
        wobbleGoalGrabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        WGGServoClose();
    }

    public void turn(float angle, boolean CCW, double power){

        int ticks = (int) (angle * TICKS_PER_DEGREE);

        if (power > MAXSPEED) {
            power = MAXSPEED;
        }

        setModeAll(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        if (CCW){
            frontRight.setTargetPosition(-ticks);
            frontLeft.setTargetPosition(ticks);
            backLeft.setTargetPosition(ticks);
            backRight.setTargetPosition(-ticks);

            setModeAll(DcMotor.RunMode.RUN_TO_POSITION);

            backLeft.setPower(power);
            backRight.setPower(power);
            frontRight.setPower(power);
            frontLeft.setPower(power);

            while ((backLeft.isBusy() || backRight.isBusy())&& opmode.opModeIsActive()){
                ;
            }
            stopDriving();
        }
        else{
            frontLeft.setTargetPosition(-ticks);
            frontRight.setTargetPosition(ticks);
            backLeft.setTargetPosition(-ticks);
            backRight.setTargetPosition(ticks);

            setModeAll(DcMotor.RunMode.RUN_TO_POSITION);
            backLeft.setPower(power);
            backRight.setPower(power);
            frontRight.setPower(power);
            frontLeft.setPower(power);

            while ((backLeft.isBusy() || backRight.isBusy())&& opmode.opModeIsActive()){
                ;
            }

            stopDriving();

        }
    }


    public void setLauncher(double power) {
        launcher.setPower(power);
    }

    public void setsweeper(double power, int time){
        sweeperTimeout= new ElapsedTime();
        int SWEEPER_TIMEOUT = time;

        if (power > MAXSPEEDSWEEPER) {
            power = MAXSPEEDSWEEPER;
        }
        sweeper.setPower(power);

        while (sweeper.isBusy() && opmode.opModeIsActive()) {
            if (sweeperTimeout.milliseconds() > SWEEPER_TIMEOUT)
                break;
        }


        public void WGGOpen () {
        //wobbleGoalGrabber.setTargetPosition(530);
        wobbleGoalGrabber.setVelocity(200);
        while ((wobbleGoalGrabber.getCurrentPosition()<WGG_OPENED) && opmode.opModeIsActive()){
            opmode.telemetry.addLine().addData("WGGPosition", wobbleGoalGrabber.getCurrentPosition());
            opmode.telemetry.update();
        }
        wobbleGoalGrabber.setPower(0);
    }
    public void WGGClose() {
            //wobbleGoalGrabber.setTargetPosition(0);
            wobbleGoalGrabber.setVelocity(-200);
            while ((wobbleGoalGrabber.getCurrentPosition()>WGG_CLOSED) && opmode.opModeIsActive()){
                opmode.telemetry.addLine().addData("WGGPosition", wobbleGoalGrabber.getCurrentPosition());
                opmode.telemetry.update();
        }
        wobbleGoalGrabber.setPower(0);
    }
    public void WGGServoOpen() {
            WGS.setPosition(WGG_SERVO_OPENED);
    }
    public void WGGServoClose() {
            WGS.setPosition(WGG_SERVO_CLOSED);
    }

    public void wobbleDeliver(){
            WGGOpen();
            WGGServoOpen();
            opmode.sleep(1000);
            driveDistance2(0.5,-6,1000);
            WGGClose();
            WGGServoClose();
    }

    /*public void turn(float angle, boolean CCW, double power) {
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





