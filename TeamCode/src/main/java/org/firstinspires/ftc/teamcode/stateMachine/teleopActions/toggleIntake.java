package org.firstinspires.ftc.teamcode.stateMachine.teleopActions;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.teamcode.stateMachine.teleopAction;
import org.firstinspires.ftc.teamcode.subsystems.robot;

import static org.firstinspires.ftc.teamcode.subsystems.scoringMechanisms.deposit.depositStates.COLLECTION;
import static org.firstinspires.ftc.teamcode.subsystems.scoringMechanisms.intake.intakeStates.OFF;
import static org.firstinspires.ftc.teamcode.subsystems.scoringMechanisms.intake.intakeStates.ON;

public class toggleIntake implements teleopAction {


	protected robot robot;
	protected Gamepad gamepad1;
	protected Gamepad gamepad2;


	public toggleIntake(robot robot, Gamepad gamepad1, Gamepad gamepad2) {
		this.robot = robot;
		this.gamepad1 = gamepad1;
		this.gamepad2 = gamepad2;
	}

	@Override
	public void initialRun() {

	}

	@Override
	public void periodic() {

		if (robot.Deposit.getState().equals(COLLECTION) || gamepad1.right_trigger > 0.5) {
			robot.Intake.setState(ON);
		} else robot.Intake.setState(OFF);

	}

	@Override
	public boolean isComplete() {
		return false;
	}

	@Override
	public boolean shouldRun() {
		return true;
	}

	@Override
	public void reset() {

	}

	@Override
	public boolean hasPerformedInitialRun() {
		return true;
	}
}
