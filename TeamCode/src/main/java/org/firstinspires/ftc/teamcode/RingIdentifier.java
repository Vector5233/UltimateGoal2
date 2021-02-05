package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

public class RingIdentifier {
  

  final double CENTER_PIXELS = 400.0;
  final double FRONT_CENTER_TO_WEBCAM = 3.5;

  VuforiaLocalizer vuforia;
  TFObjectDetector tfod;
  LinearOpMode parent;

  public static final String TFOD_MODEL_ASSET = "UltimateGoal.tflite";
  public static final String LABEL_FIRST_ELEMENT = "Quad";
  public static final String LABEL_SECOND_ELEMENT = "Single";

  public RingIdentifier(LinearOpMode p) {
    parent=p;
    initializeVuforia();
    initializeTfod();

  }

  public void initializeVuforia() {
    VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
    parameters.vuforiaLicenseKey = "AaEXPAP/////AAABmfSqfgUnFEgorvdIMud+E1ptq5XASvJAx4PQuxM+AlGmUpefMqD8EB2F8xlKK/Z7qD5v0X/DL6t9rcJJDVK78xCXm+ARbXUOKB/xxeX8XAk4WDnQxkM+kR0xV+shs7NXUgJptb5W1JXVhKAnBW+dmtZ6pJCIWtMsDHEPMvN5lU3+QZWECMikOLDm9PnbhMRkur83hvJlpFdiwzp5LDQfxEDin4tuTbSU1FqWjwuMPK/z3q03cCaswJKM5rOT2NYafYHIezh+eK1QuKPDXoGoYKkh7VGhwZenhZi/1BcfuhBP1YFIOQb4DU5QGul+Cz5J58eY+OwE/sjJbQoGX6qsb6zU4BFecspUGYCcMZMRxCbH";
    parameters.cameraName = parent.hardwareMap.get(WebcamName.class, "Webcam 1");
    vuforia = ClassFactory.getInstance().createVuforia(parameters);
  }

  public void initializeTfod() {
    int tfodMonitorViewId = parent.hardwareMap.appContext.getResources().getIdentifier("tfodMonitorViewId", "Id", parent.hardwareMap.appContext.getPackageName());
    TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
    tfodParameters.minimumConfidence = 0.6;
    tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
    tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_FIRST_ELEMENT, LABEL_SECOND_ELEMENT);
    if (tfod != null) {
      tfod.activate();
    }
  }

  public String ringDisplacement(){
    parent.sleep(2000);
    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
    if (updatedRecognitions.size() == 0) {
      return "A";
    }
    else if (updatedRecognitions.size()==1) {
      return "B";

    }
    else {
      return "C";
    }

  }
}
