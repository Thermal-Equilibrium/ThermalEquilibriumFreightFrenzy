package org.firstinspires.ftc.teamcode.Controls.SISOControls;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.Controls.Coefficients.PVParams;

import homeostasis.utils.State;

public class PVControl {

	protected PVParams coefficients;
	protected double previousFeedbackOutput = 0;
	protected boolean isProcessComplete = false;
	protected ElapsedTime timer = new ElapsedTime();
	boolean hasStarted = false;
	private double integralSum = 0;
	private State previousStateError = new State();
	protected State error = new State();

	public PVControl(PVParams coefficients) {
		this.coefficients = coefficients;
	}

	public double calculate(double positionReference,
							double velocityReference, double positionState,  double velocityState) {

		return calculate(new State(positionReference,velocityReference),
						 new State(positionState,velocityState));

	}

	public double calculate(State reference,State state) {
		error = reference.stateError(state);
		double feedback = calculateFeedback(error);
		double feedforward = calculateFeedforward(reference.getVelocity(), feedback);
		isProcessComplete = isCompleteCalc(error);
		double integralAction = calculateIntegral(error) * coefficients.getKi();
		return feedback + feedforward + integralAction;
	}

	protected double calculateIntegral(State error) {
		if (!hasStarted) {
			timer.reset();
			hasStarted = true;
		}
		if (error.getPosition() > 0 && previousStateError.getPosition() < 0 || error.getPosition() < 0 || previousStateError.getPosition() > 0) {
			integralSum = 0;
		}

		if (Math.abs(integralSum) * coefficients.getKi() > 1) {
			integralSum = Math.signum(integralSum) * 1 / coefficients.getKi();
		}

		integralSum += error.getPosition() * timer.seconds();
		timer.reset();
		previousStateError = error;
		return integralSum;
	}

	protected double calculateFeedback(State error) {
		return error.getPosition() * coefficients.getKp()
			 + error.getVelocity() * coefficients.getKpV();
	}

	protected double calculateFeedforward(double referenceVelocity, double feedbackOutput) {
		double out = referenceVelocity * coefficients.getKv()
				   + Math.signum(feedbackOutput) * coefficients.getKs()
				   + previousFeedbackOutput * coefficients.getAff();
		previousFeedbackOutput = feedbackOutput;
		return out;
	}

	public boolean isCompleteCalc(State error) {
		return Math.abs(error.getVelocity()) < coefficients.getCutOffVelo()
				|| Math.abs(error.getPosition()) < coefficients.getCutOffPos();
	}

	public boolean isProcessComplete() {
		return isProcessComplete;
	}

	public State getError() {
		return error;
	}
}

