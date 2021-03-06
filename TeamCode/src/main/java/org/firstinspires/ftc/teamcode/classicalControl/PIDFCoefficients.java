package org.firstinspires.ftc.teamcode.classicalControl;

public class PIDFCoefficients {

    public double Kp;
    public double Ki;
    public double Kd;
    public double Kd2;
    public double Kf;

    public PIDFCoefficients(double Kp, double Ki, double Kd, double Kd2, double Kf) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.Kf = Kf;
        this.Kd2 = Kd2;
    }

    public PIDFCoefficients(double Kp, double Ki, double Kd, double Kf) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.Kd2 = 0;
        this.Kf = Kf;
    }

    public PIDFCoefficients(double Kp, double Ki, double Kd) {
        this.Kp = Kp;
        this.Ki = Ki;
        this.Kd = Kd;
        this.Kd2 = 0;
        this.Kf = 0;
    }


}
