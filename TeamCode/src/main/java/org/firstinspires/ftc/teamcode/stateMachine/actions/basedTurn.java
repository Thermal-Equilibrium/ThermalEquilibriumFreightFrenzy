package org.firstinspires.ftc.teamcode.stateMachine.actions;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.basedControl.basedControl;
import org.firstinspires.ftc.teamcode.basedControl.robotCoefficients;
import org.firstinspires.ftc.teamcode.classicalControl.PIDFCoefficients;
import org.firstinspires.ftc.teamcode.stateMachine.action;
import org.firstinspires.ftc.teamcode.subsystems.robot;

public class basedTurn implements action {

	robot robot;
	basedControl pid;
	PIDFCoefficients coefficients = robotCoefficients.turnCoefficients;
	double targetAngle;
	boolean isComplete = false;
	ElapsedTime timer = new ElapsedTime();
	double timeout = 3;

	public basedTurn(robot robot, double targetAngle) {
		this.robot = robot;
		this.targetAngle = targetAngle;
	}

	@Override
	public void startAction() {
		pid = new basedControl(coefficients, targetAngle, 3, 0.004, Math.toRadians(1));
		timer.reset();
	}

	@Override
	public void runAction() {
		double output = pid.calculateAngle(robot.odometry.subsystemState().getAngleRadians());
		robot.driveTrain.robotRelative(0, output);
		isComplete = (pid.isComplete() || timer.seconds() > timeout) && pid.isStable();
	}

	@Override
	public void stopAction() {
		robot.driveTrain.STOP();
	}

	@Override
	public boolean isActionComplete() {
		return isComplete;
	}

	@Override
	public boolean isActionPersistent() {
		return true;
	}
}
