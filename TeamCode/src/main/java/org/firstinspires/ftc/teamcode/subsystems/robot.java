package org.firstinspires.ftc.teamcode.subsystems;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.geometry.Vector3D;

import java.util.ArrayList;

public class robot {

    private final ArrayList<subsystem> subsystems = new ArrayList<>();


    public VoltageSensor batterVoltageSensor;

    public drivetrain driveTrain = new drivetrain(batterVoltageSensor);


    public dashboard dashBoard = new dashboard();


    public differentialDriveOdom odometry = new differentialDriveOdom();


    /**
     * full robot constructor, use for competition deployment
     */
    public robot() {

        subsystems.add(driveTrain);
        subsystems.add(dashBoard);
        subsystems.add(odometry);

    }


    /**
     * initialize only drive train and dashboard subsystems
     *
     * @param hwmap HardwareMap instance
     */
    public void initMinimal(HardwareMap hwmap) {
        batterVoltageSensor = hwmap.voltageSensor.iterator().next();
        driveTrain.init(hwmap);
        dashBoard.init(hwmap);
        odometry.init(hwmap);
    }

    /**
     * initialization including reset of subsystems
     *
     * @param hwmap HardwareMap instance
     */
    public void init(HardwareMap hwmap) {
        initMinimal(hwmap);
    }

    /**
     * initialization but without resetting certain subsystems
     * such as the encoder of linear slides and things that need to retain position between auto and teleop
     * @param hwmap HardwareMap instance
     */
    public void initWithoutReset(HardwareMap hwmap) {
        driveTrain.initNoReset(hwmap);
        dashBoard.initNoReset(hwmap);
        odometry.initNoReset(hwmap);

    }

    /**
     * obtain the robot position
     *
     * @return drivetrain position
     */
    public Vector3D getRobotPose() {
        return odometry.subsystemState();
    }
    public void setRobotPose(Vector3D pose) {
        odometry.setPositionEstimate(pose);
    }

    /**
     * @return array list of subsystems
     */
    public ArrayList<subsystem> getSubsystems() {
        return subsystems;
    }


    public Vector3D getVelocity() {
        return odometry.getVelocity();
    }
}
