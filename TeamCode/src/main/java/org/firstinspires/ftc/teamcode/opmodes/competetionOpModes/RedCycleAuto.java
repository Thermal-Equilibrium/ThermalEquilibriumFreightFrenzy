package org.firstinspires.ftc.teamcode.opmodes.competetionOpModes;

import android.service.quicksettings.Tile;

import com.acmerobotics.roadrunner.drive.Drive;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Geometry.Vector3D;
import org.firstinspires.ftc.teamcode.commandBase.action;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.DrivetrainControl.DriveToIntake;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.DrivetrainControl.DriveToPosition;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.Intake.DeployIntake;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.Intake.TurnOffIntake;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.Intake.TurnOnIntake;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.Misc.Delay;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.Misc.MutlipleAction;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.Misc.OffsetOdom;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.SlideControl.DepositFreight;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.SlideControl.GoToHighDeposit;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.SlideControl.GoToInState;
import org.firstinspires.ftc.teamcode.templateOpModes.BaseAuto;
import org.opencv.core.Mat;

import java.util.Vector;

@Autonomous
public class RedCycleAuto extends BaseAuto {


    Vector3D depositPosition = new Vector3D(+ 4,-TILE * 2 + 5 ,Math.toRadians(-55));
    Vector3D start = new Vector3D(TILE / 2.0, -TILE * 3 + 8.375, Math.toRadians(-90));

    Vector3D readyForCollection1 = new Vector3D(TILE - 11 , -TILE * 3 + 8, Math.toRadians(0));
    Vector3D readyForCollection2 = new Vector3D(TILE / 3, -TILE * 3 + 8, Math.toRadians(0));
    Vector3D readyForCollection3 = new Vector3D(TILE / 3, -TILE * 3 + 10.5, Math.toRadians(0));
    Vector3D readyForPark = new Vector3D(TILE / 3, -TILE * 3 + 11, Math.toRadians(0));

    Vector3D InWarehouse1 = new Vector3D(TILE * 2 - 10, readyForCollection1.getY(), Math.toRadians(0));
    Vector3D InWarehouse2 = new Vector3D(TILE * 2 - 6, readyForCollection2.getY(), Math.toRadians(0));
    Vector3D InWarehouse3 = new Vector3D(TILE * 2 - 6, readyForCollection3.getY(), Math.toRadians(0));

    Vector3D collect1 = new Vector3D(TILE * 2, readyForCollection1.getY(), Math.toRadians(0));
    Vector3D collect2 = new Vector3D(TILE * 2, readyForCollection2.getY(), Math.toRadians(0));
    Vector3D collect3 = new Vector3D(TILE * 2, readyForCollection3.getY(), Math.toRadians(0));
    Vector3D parked = new Vector3D(TILE * 2 - 6, readyForCollection3.getY(), Math.toRadians(0));

    Vector3D gapPose = new Vector3D(TILE, readyForPark.getY(), Math.toRadians(0));
    Vector3D Test = new Vector3D(24,24, Math.toRadians(0));



    @Override
    public void setStartingPosition() {
        robot.setRobotPose(start);
    }

    @Override
    public void setVisionSettings() {

    }

    @Override
    public void addActions() {

        //deposits pre load and drops intake Lift up
        actions.add(new MutlipleAction(new action[]{new DriveToPosition(robot,depositPosition) , new GoToHighDeposit(robot), new DeployIntake(robot)}));
        actions.add(new DepositFreight(robot));
        actions.add(new Delay(200));

        //agaisnt wall drives into warehouse Lift down
        actions.add(new MutlipleAction(new action[]{new DriveToPosition(robot,readyForCollection1) , new DeployIntake(robot), new GoToInState(robot),}));
        //actions.add(new DriveToPosition(robot,InWarehouse1));

        //Intake first frieght
        //actions.add(new MutlipleAction(new action[]{new DriveToPosition(robot,collect1) , new TurnOnIntake(robot,true),}));
                //actions.add(new DriveToIntake(robot,collect1,5,false));
/*
        //Deposits 2 cube Lift Up
        actions.add(new DriveToPosition(robot,readyForCollection1, 1.5, false));
        actions.add(new MutlipleAction(new action[]{new DriveToPosition(robot,depositPosition) , new GoToHighDeposit(robot),}));

        //agaisnt wall drives into warehouse Lift down
        actions.add(new MutlipleAction(new action[]{new DriveToPosition(robot,readyForCollection2) , new GoToInState(robot),}));
        actions.add(new DriveToPosition(robot,InWarehouse2));


        //Intake second frieght
        actions.add(new MutlipleAction(new action[]{new DriveToPosition(robot,collect2) , new TurnOnIntake(robot, true),}));

        //Deposits 3 cube
        actions.add(new DriveToPosition(robot,readyForCollection2, 1.5, false));
        actions.add(new MutlipleAction(new action[]{new DriveToPosition(robot,depositPosition) , new GoToHighDeposit(robot),}));

        //agaisnt wall drives into warehouse
        actions.add(new MutlipleAction(new action[]{new DriveToPosition(robot,readyForCollection3) , new GoToInState(robot),}));
        actions.add(new DriveToPosition(robot,InWarehouse3));

        //Intake first frieght

        //Deposits 4 cube
        actions.add(new DriveToPosition(robot,readyForCollection3, 1.5, false));
        actions.add(new MutlipleAction(new action[]{new DriveToPosition(robot,depositPosition) , new GoToHighDeposit(robot),}));

        //agaisnt wall than parks
        actions.add(new DriveToPosition(robot,readyForPark, 1.5,false));
        actions.add(new DriveToPosition(robot,parked));*/
    }
}
