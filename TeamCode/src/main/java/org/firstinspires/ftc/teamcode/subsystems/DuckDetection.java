package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.teamcode.vision.ContourPipeline;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraFactory;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvWebcam;

public class DuckDetection implements subsystem {

	Dashboard dash;
	public DuckDetection(Dashboard dashboardInstance) {
		this.dash = dashboardInstance;
	}

	OpenCvWebcam webcam;
	ContourPipeline pipeline = new ContourPipeline();

	@Override
	public void init(HardwareMap hwmap) {
		initNoReset(hwmap);
	}

	@Override
	public void initNoReset(HardwareMap hwmap) {
		int cameraMonitorViewId = hwmap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwmap.appContext.getPackageName());
		webcam = OpenCvCameraFactory.getInstance().createWebcam(hwmap.get(WebcamName.class, "Webcam 1"), cameraMonitorViewId);


		webcam.setPipeline(pipeline);

		webcam.openCameraDeviceAsync(new OpenCvCamera.AsyncCameraOpenListener()
		{
			@Override
			public void onOpened()
			{

				webcam.startStreaming(320, 240, OpenCvCameraRotation.UPRIGHT);
			}

			@Override
			public void onError(int errorCode) {

			}
		});

		dash.startCameraStream(webcam,30);
	}

	@Override
	public void update() {

	}

	@Override
	public ContourPipeline.position subsystemState() {
		return pipeline.getTSEPosition();
	}


}
