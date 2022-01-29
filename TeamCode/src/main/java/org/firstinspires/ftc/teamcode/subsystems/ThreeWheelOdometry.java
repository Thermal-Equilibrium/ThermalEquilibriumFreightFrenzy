package org.firstinspires.ftc.teamcode.subsystems;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.teamcode.Geometry.Vector3D;
import org.firstinspires.ftc.teamcode.roadrunnerquickstart.SampleMecanumDrive;

import homeostasis.Filters.AngleKalmanFilter;

import static org.firstinspires.ftc.teamcode.subsystems.Robot.isCompBot;
import static org.firstinspires.ftc.teamcode.Utils.utils.AngleWrap;
import static org.firstinspires.ftc.teamcode.Utils.utils.drawRobot;
import static org.firstinspires.ftc.teamcode.Utils.utils.normalizeAngleRR;

@Config
public class ThreeWheelOdometry implements subsystem {

	public DcMotorEx LeftEncoder;
	public DcMotorEx RightEncoder;
	public DcMotorEx MiddleEncoder;
	protected Vector3D positionEstimate = new Vector3D();
	protected Vector3D positionEstimateDeltaFieldRelative = new Vector3D();
	protected Vector3D positionEstimateDeltaRobotRelative = new Vector3D();
	private double pitchAngle = 0;
	private double leftPrev = 0;
	private double rightPrev = 0;
	private double middlePrev = 0;
	private final double gearRatio;
	public static double trackWidth =  3.4917806640625058; // TODO: fix this for real robot
	public static double middleWheelOffset = -2;  // TODO: fix this for real robot
	private BNO055IMU imu;
	protected Vector3D initialPosition = new Vector3D();
	protected double IMU_angle = 0;
	double encoderAngle = 0;
	Pose2d previousRoadrunnerPose = new Pose2d();

	protected long counter = 0;
	protected AngleKalmanFilter kalmanFilter;

	Vector3D robotVelocity = new Vector3D();

	SampleMecanumDrive mecanumDriveRR;


	protected OdomState state = OdomState.DEPLOYED;

	double angularVelocity = 0;

	/**
	 * initialize a differential drive robot with odometry
	 */
	public ThreeWheelOdometry() {
		gearRatio = 1;
		kalmanFilter = new AngleKalmanFilter(0);
	}

	@Override
	public void init(HardwareMap hwmap) {

		if (isCompBot) {
			imu = hwmap.get(BNO055IMU.class, "imu");
		} else {
			imu = hwmap.get(BNO055IMU.class, "imu");
		}
		BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
		parameters.mode = BNO055IMU.SensorMode.NDOF;
		parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
		imu.initialize(parameters);
		LeftEncoder = hwmap.get(DcMotorEx.class, "BackRight");
		RightEncoder = hwmap.get(DcMotorEx.class, "FrontLeft");
		MiddleEncoder = hwmap.get(DcMotorEx.class, "BackLeft");
		mecanumDriveRR = new SampleMecanumDrive(hwmap);
	}

	@Override
	public void initNoReset(HardwareMap hwmap) {
		init(hwmap);
	}

	@Override
	public void update() {
		switch (state) {
			case DEPLOYED:
				deployedUpdateRR();
				break;
			case RETRACTED:
				updateIMU();
				break;
		}
	}

	/**
	 * position estimate of the robot
	 * @return get the robots position estimate
	 */
	@Override
	public Vector3D subsystemState() {
		return positionEstimate;
	}

	/**
	 * set the pose estimate based on a vector3d object
	 * @param positionEstimate pose estimate
	 */
	public void setPositionEstimate(Vector3D positionEstimate) {
		this.initialPosition = positionEstimate;
		this.positionEstimate = positionEstimate;
		kalmanFilter.setX(positionEstimate.getAngleRadians());
	}

	public void setInitialPosition(Vector3D positionEstimate) {

		setPositionEstimate(positionEstimate);
		this.mecanumDriveRR.setPoseEstimate(new Pose2d(positionEstimate.getX(),
														positionEstimate.getY(),
														positionEstimate.getAngleRadians()));

	}
	/**
	 *
	 * @return the field relative pose delta
	 */
	public Vector3D getPositionEstimateDelta() {
		return positionEstimateDeltaFieldRelative;
	}

	public void updateIMU() {
		Orientation angle = imu.getAngularOrientation();
		IMU_angle = normalizeAngleRR(angle.firstAngle + initialPosition.getAngleRadians());//normalizeAngleRR(navx.subsystemState().getAngleRadians());
		Dashboard.packet.put("IMU Angle",IMU_angle);
		Dashboard.packet.put("IMU Angle Deg", Math.toDegrees(IMU_angle));
		Dashboard.packet.put("pitch angle", pitchAngle);
		angularVelocity = imu.getAngularVelocity().zRotationRate;
		this.positionEstimate = new Vector3D(0,0,IMU_angle);
	}

	public Vector3D getVelocity() {
		return robotVelocity;
	}


	public double encoderTicksToInches(double ticks) {
		double WHEEL_RADIUS = 1.49606 / 2; // 38 mm wheel
		double ticksPerRevolution = 8192;
		return WHEEL_RADIUS * 2 * Math.PI * gearRatio * ticks / ticksPerRevolution;
	}

	public void setXPose(double xPoseEstimate) {
		Vector3D currentPose = subsystemState();
		currentPose.setX(xPoseEstimate);
		this.setPositionEstimate(currentPose);
	}
	public void setYPose(double yPoseEstimate) {
		Vector3D currentPose = subsystemState();
		currentPose.setY(yPoseEstimate);
		this.setPositionEstimate(currentPose);
	}

	public enum OdomState {
		DEPLOYED,
		RETRACTED
	}

	public void setRetractionState(OdomState state) {
		this.state = state;
	}


	public void deployedUpdateRR() {
		mecanumDriveRR.updatePoseEstimate();
		Pose2d estimate = mecanumDriveRR.getPoseEstimate();
		Pose2d eDel = estimate.minus(previousRoadrunnerPose);
		previousRoadrunnerPose = estimate;
		Vector3D newPosition = positionEstimate.add(new Vector3D(eDel.getX(),eDel.getY(),eDel.getHeading()));

		positionEstimate = new Vector3D(newPosition.getX(),newPosition.getY(),estimate.getHeading());
		System.out.println("Pose estimate from rr" + positionEstimate);
		Pose2d velocity = mecanumDriveRR.getPoseVelocity();
		try {
			robotVelocity = new Vector3D(velocity.getX(),velocity.getY(),velocity.getHeading());
		} catch (NullPointerException e) {
			System.out.println("a null pointer exception has occurred due to velocity not being measured");
		}
		drawRobot(positionEstimate,Dashboard.packet);
	}



	public void setState(OdomState state) {
		this.state = state;
	}
}
