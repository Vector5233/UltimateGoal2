    package org.firstinspires.ftc.teamcode;

    import com.qualcomm.hardware.lynx.MessageKeyedLock;
    import com.qualcomm.robotcore.eventloop.opmode.OpMode;
    import com.qualcomm.robotcore.hardware.CRServo;
    import com.qualcomm.robotcore.hardware.DcMotor;
    import com.qualcomm.robotcore.hardware.DcMotorSimple;
    import com.qualcomm.robotcore.hardware.Servo;

    //import sun.text.resources.th.BreakIteratorInfo_th;

    @com.qualcomm.robotcore.eventloop.opmode.TeleOp(name="TeleOp", group="Red")
    public class TeleOp extends OpMode {
    DcMotor frontRight, frontLeft, backRight, backLeft, intake,
            sweeper, wobbleGoalGrabber, launcher;
    Servo WGS;
    final double COLLECTPOWER = 1.0;
    final double TICKS_PER_REVOLURION = (383.6 * 2);
    final double LAUNCHER_POWER=1.0;
    final double LAUNCHER_THRESHHOLD=.5;
    final double INTAKE_POWER=1.0;
    final double INTAKE_THRESHOLD=.3;

    final double WOBBLE_GOAL_GRABBER_POWER = 1.0;
    boolean if_pressedGp1X = false;
    double MAXTICK = 383.6/2;
    final double WGS_OPEN = .55;
    final double WGS_CLOSED = 0;





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

        intake = hardwareMap.dcMotor.get("intake");
        intake.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        intake.setDirection(DcMotor.Direction.FORWARD);
        sweeper= hardwareMap.dcMotor.get("sweeper");
        sweeper.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        sweeper.setDirection(DcMotorSimple.Direction.REVERSE);

        launcher= hardwareMap.dcMotor.get("launcher");
        launcher.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        launcher.setDirection(DcMotorSimple.Direction.FORWARD);

        wobbleGoalGrabber = hardwareMap.dcMotor.get("wobbleGoalGrabber");
        wobbleGoalGrabber.setDirection(DcMotorSimple.Direction.REVERSE);
        wobbleGoalGrabber.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        wobbleGoalGrabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        wobbleGoalGrabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        WGS = hardwareMap.servo.get("WGS");
        WGS.setPosition(WGS_CLOSED);
    }

    public void loop() {
        setDriveMotors();
        setIntakeMotor();
        setSweeper();
        setLauncher();
        //setWobbleGoalGrabberEncoder();
        setWobbleGoalGrabber();
        setWobbleGoalServo();
    }

    private void setIntakeMotor() {
        if (gamepad2.right_stick_y > INTAKE_THRESHOLD) {
            intake.setPower(INTAKE_POWER);
        } else if (gamepad2.right_stick_y < -INTAKE_THRESHOLD) {
            intake.setPower(-INTAKE_POWER);
        } else {
            intake.setPower(0);
        }
    }

    private void setSweeper()  {
        if (gamepad2.right_bumper) {
            sweeper.setPower(COLLECTPOWER);
        } else if (gamepad2.left_bumper) {
            sweeper.setPower(-COLLECTPOWER);
        } else {
            sweeper.setPower(0);
        }
    }

    private void setLauncher()  {
        if (gamepad2.right_trigger > LAUNCHER_THRESHHOLD) {
            launcher.setPower(gamepad2.right_trigger * LAUNCHER_POWER);
        } else {
            launcher.setPower(0);
        }
    }

        private void setWobbleGoalGrabber()  {
            if (gamepad1.left_trigger > LAUNCHER_THRESHHOLD) {
                wobbleGoalGrabber.setPower(gamepad1.left_trigger * WOBBLE_GOAL_GRABBER_POWER);
            }
            else if (gamepad1. right_trigger > LAUNCHER_THRESHHOLD) {
                    wobbleGoalGrabber.setPower(gamepad1.right_trigger * -WOBBLE_GOAL_GRABBER_POWER);
            }

            else {
                wobbleGoalGrabber.setPower(0);
            }
        }

        private void setWobbleGoalServo() {
            if (gamepad1.left_bumper) {
                WGS.setPosition(WGS_CLOSED);
            } else if (gamepad1.right_bumper) {
                WGS.setPosition(WGS_OPEN);
            }
        }







    private void setDriveMotors() {
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
