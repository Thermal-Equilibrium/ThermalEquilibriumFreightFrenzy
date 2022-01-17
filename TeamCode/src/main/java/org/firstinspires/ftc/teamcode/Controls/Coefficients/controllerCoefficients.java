package org.firstinspires.ftc.teamcode.Controls.Coefficients;

public class controllerCoefficients {
	public static final double compBotVelocity = 70;
	public static final double compBotAcceleration = 35;
	// controller coefficients for competition 10wd
	public static final PIDFCoefficients compBotTurn = new PIDFCoefficients(0.6, 0.5, 0,0,0,0.1); // 0.2785000000000002
	public static final PVParams translationCoefficients = new PVParams(0.25,0,1/compBotVelocity,0.07,0,1,1.5);
	// controller coefficients for off season 6wd
	public static final PIDFCoefficients protoBotTurn = new PIDFCoefficients(0.59, 0.16, 0.09, 0, 0, 0.0580);

	// controller coefficients for linear slide subsystem
	public static final PIDFCoefficients slideCoefficients = new PIDFCoefficients(0.007, 0.08, 0);




	public static final double protoBotVelocity = 100;
	public static final double protoBotAcceleration = 100;
	public static final double protoBotJerk = 250;



}
