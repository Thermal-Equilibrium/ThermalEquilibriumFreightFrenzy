package org.firstinspires.ftc.teamcode.baseopmode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.geometry.Vector3D;
import org.firstinspires.ftc.teamcode.stateMachine.action;
import org.firstinspires.ftc.teamcode.stateMachine.actions.aimAtPoint;
import org.firstinspires.ftc.teamcode.stateMachine.actions.basedDrive;
import org.firstinspires.ftc.teamcode.stateMachine.actions.basedTurn;
import org.firstinspires.ftc.teamcode.stateMachine.actions.driveToPositionDifferential;
import org.firstinspires.ftc.teamcode.stateMachine.actions.turnToAngle;
import org.firstinspires.ftc.teamcode.stateMachine.scheduler;
import org.firstinspires.ftc.teamcode.subsystems.robot;

import java.util.ArrayList;

public class baseAuto extends LinearOpMode {

	org.firstinspires.ftc.teamcode.subsystems.robot robot;

	protected ArrayList<action> actions = new ArrayList<>();
	protected Vector3D startingPosition = new Vector3D();


	public void setStartingPosition() {

	}


	public void addActions() {

	}

	@Override
	public void runOpMode() {
		robot = new robot();
		robot.init(hardwareMap);

		addActions();
		setStartingPosition();

		scheduler scheduler = new scheduler(hardwareMap, actions, robot.getSubsystems());

		waitForStart();

		while (opModeIsActive()) {


			scheduler.updateStateMachineAndRobot();
		}

	}

	public void safeDrive(Vector3D target) {
		actions.add(new aimAtPoint(robot, target));
		actions.add(new driveToPositionDifferential(robot, target));
		actions.add(new turnToAngle(robot, target.getAngleRadians()));
	}

	public void turn(double angle) {
		actions.add(new basedTurn(robot, angle));
	}

	public void driveDistance(double distance) {
		actions.add(new basedDrive(robot, distance));
	}

	public void turnTo(Vector3D targetPoint) {
		actions.add(new aimAtPoint(robot, targetPoint));
	}

}
