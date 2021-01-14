package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous (name = "TensorflowTest", group = "test")
public class TensorflowTest extends LinearOpMode {
    final double CENTER_PIXELS = 400.0;
    final double FRONT_CENTER_TO_WEBCAM = 3.5;

    VuforiaLocalizer vuforia;
    TFObjectDetector tfod;

    public static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
    public static final String LABEL_FIRST_ELEMENT = "Quad";
    public static final String LABEL_SECOND_ELEMENT = "Single";

    public void initializeVuforia(){
VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.vuforiaLicenseKey = "AaEXPAP/////AAABmfSqfgUnFEgorvdIMud+E1ptq5XASvJAx4PQuxM+AlGmUpefMqD8EB2F8xlKK/Z7qD5v0X/DL6t9rcJJDVK78xCXm+ARbXUOKB/xxeX8XAk4WDnQxkM+kR0xV+shs7NXUgJptb5W1JXVhKAnBW+dmtZ6pJCIWtMsDHEPMvN5lU3+QZWECMikOLDm9PnbhMRkur83hvJlpFdiwzp5LDQfxEDin4tuTbSU1FqWjwuMPK/z3q03cCaswJKM5rOT2NYafYHIezh+eK1QuKPDXoGoYKkh7VGhwZenhZi/1BcfuhBP1YFIOQb4DU5QGul+Cz5J58eY+OwE/sjJbQoGX6qsb6zU4BFecspUGYCcMZMRxCbH";
        parameters.cameraName = hardwareMap.get(WebcamName.class, "Webcam 1");
        vuforia = ClassFactory.getInstance().createVuforia(parameters);
}

    public void initializeTfod() {
 int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "Id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfodParameters.minimumConfidence = 0.6;
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
        if (tfod != null) {
                    tfod.activate();
      }
}
    
        public void runOpMode(){

        initializeVuforia();
        initializeTfod();
        waitForStart();
        // results 2020.12.19 -- "Objects detected 0".   No visual feedback.
            while (opModeIsActive()) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                        }
                        telemetry.update();
                    }
                }
            }
        }
    

