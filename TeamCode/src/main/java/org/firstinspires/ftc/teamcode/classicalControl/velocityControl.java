package org.firstinspires.ftc.teamcode.classicalControl;

import org.firstinspires.ftc.teamcode.filter.LowPassFilter;
import org.firstinspires.ftc.teamcode.subsystems.dashboard;

import homeostasis.systems.DcMotorPlant;

public class velocityControl {

	DcMotorPlant motor;

	protected PIDFCoefficients coefficients = new PIDFCoefficients(0,0,0,0,0.0003767);
	protected NonlinearPID pidController = new NonlinearPID(coefficients);
	protected LowPassFilter rateLimiter = new LowPassFilter(0.3);

	public void initializeControllerSettings() {

	}
	public velocityControl(DcMotorPlant motor, PIDFCoefficients coefficients) {
		this.motor = motor;
		this.coefficients = coefficients;
		this.pidController = new NonlinearPID(coefficients);
		initializeControllerSettings();
	}

	public velocityControl(DcMotorPlant motor) {
		this.motor = motor;
		initializeControllerSettings();
	}

	public void voltageCorrectedControl(double targetVelocity, double targetVoltage) {
		double state = motor.getState().getVelocity();
		double target = rateLimiter.updateEstimate(targetVelocity);
		double output = pidController.calculateOutput(target,state);
		dashboard.packet.put("measured velo", state);
		dashboard.packet.put("rate limited velo", target);
		dashboard.packet.put("target velo", targetVelocity);
		motor.input(output * (12.5/targetVoltage));
	}

	public void controlMotor(double targetVelocity) {

		double state = motor.getState().getVelocity();
		double output = pidController.calculateOutput(targetVelocity,state);

		motor.input(output);
	}


	public void setCoefficients(PIDFCoefficients coefficients) {
		this.coefficients = coefficients;
		this.pidController = new NonlinearPID(coefficients);
	}
}
