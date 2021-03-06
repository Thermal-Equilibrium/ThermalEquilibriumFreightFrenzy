package org.firstinspires.ftc.teamcode.baseopmode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.stateMachine.scheduler;
import org.firstinspires.ftc.teamcode.subsystems.robot;

import java.util.ArrayList;

@Autonomous
public class LocalizationTest extends LinearOpMode {

    robot robot = new robot();

    @Override
    public void runOpMode() {
        robot.initMinimal(hardwareMap);


        scheduler scheduler = new scheduler(hardwareMap, new ArrayList<>(), robot.getSubsystems());


        telemetry.addData("ready!", "");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {

            scheduler.updateRobot();

        }

    }
}
