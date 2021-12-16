package org.firstinspires.ftc.teamcode.opmodes.competetionOpModes;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.commandBase.autoActions.Intake.TurnOffIntake;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.Misc.Delay;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.SlideControl.DepositFreight;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.SlideControl.GoToBottomDeposit;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.SlideControl.GoToHighDeposit;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.SlideControl.GoToInState;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.SlideControl.GoToMidDeposit;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.Intake.TurnOnIntake;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.Ducks.setDuckWheel;
import org.firstinspires.ftc.teamcode.subsystems.DuckWheel;
import org.firstinspires.ftc.teamcode.templateOpModes.BaseAuto;
import org.firstinspires.ftc.teamcode.geometry.Vector3D;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.DrivetrainControl.AimAtPoint;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.DrivetrainControl.Drive;
import org.firstinspires.ftc.teamcode.commandBase.autoActions.DrivetrainControl.Turn;

@Autonomous
public class RedDuckAuto extends BaseAuto {
    public Vector3D startPosition;
    public Vector3D goalPosition;
    public Vector3D carousel;
    public Vector3D park;
    public Vector3D leftCapStone;
    public Vector3D middleCapstone;
    public Vector3D rightCapstone;
    public double exitCarouselAngle;
    public double DISTANCE_BACK_FROM_GOAL = -13;

    @Override
    public void setStartingPosition() {
        startPosition = new Vector3D(-39, -56, Math.toRadians(-90));
        goalPosition = new Vector3D(-12, -20, 0);
        carousel = new Vector3D(-60,-50,0);
        park = new Vector3D(-60,-35,0);
        leftCapStone = new Vector3D(-48 - 2, -36,0);
        middleCapstone = new Vector3D(-48 - 12, -36,0);
        rightCapstone = new Vector3D(-48 - 20, -36,0 );
        exitCarouselAngle = Math.toRadians(-105);
        robot.setRobotPose(startPosition);

    }

    @Override
    public void setVisionSettings() {
        setVisionForRightVisible();
    }

    @Override
    public void addActions() {

        switch (TSEPosition) {

            case LEFT:
                // scoring level 1
                actions.add(new GoToBottomDeposit(robot));
                break;
            case MIDDLE:
                // scoring level 2
                actions.add(new GoToMidDeposit(robot));
                break;
            case RIGHT:
                // scoring level 3
                actions.add(new GoToHighDeposit(robot));
                break;
        }
        // deploy slides

        // drive to goal to deposit

        switch (TSEPosition) {
            case LEFT:
                //deposits first freight
                actions.add(new Drive(robot,-15));
                actions.add(new AimAtPoint(robot, goalPosition, false, true));
                actions.add(new Drive(robot,goalPosition,-1, DISTANCE_BACK_FROM_GOAL +1));
                actions.add(new DepositFreight(robot));

                // drive to carousel
                actions.add(new Drive(robot,carousel,1,-2));
                actions.add(new GoToInState(robot));
                actions.add(new TurnOnIntake(robot,false));
                actions.add(new Turn(robot,startPosition.getAngleRadians()));

                // push wheel against carousel
                actions.add(new setDuckWheel(robot, DuckWheel.DuckWheelState.OTHER_ON));
                actions.add(new TurnOnIntake(robot, true));
                actions.add(new Drive(robot, 7.5));
                actions.add(new Delay(2500));
                actions.add(new setDuckWheel(robot, DuckWheel.DuckWheelState.OFF));
                actions.add(new Turn(robot,exitCarouselAngle));
                actions.add(new Drive(robot, 3));
                actions.add(new TurnOffIntake(robot));

                // deposit the duck
                actions.add(new GoToHighDeposit(robot));
                actions.add(new Drive(robot,goalPosition,-1, DISTANCE_BACK_FROM_GOAL ));

                // actions.add(new AimAtPoint(robot,goalPosition,false, true));
                actions.add(new DepositFreight(robot));

                // go to park
                actions.add(new Drive(robot, 10));
                actions.add(new AimAtPoint(robot, park, false, true));
                actions.add(new GoToInState(robot));
                actions.add(new Drive(robot,park,-1));

                // make sure we are parked
                actions.add(new Turn(robot,startPosition.getAngleRadians()));
                actions.add(new Drive(robot,-3));


                break;
            case MIDDLE:

                actions.add(new Drive(robot,-27));
                actions.add(new Drive(robot,goalPosition,-1 , DISTANCE_BACK_FROM_GOAL -3));
                actions.add(new DepositFreight(robot));

                // drive to carousel
                actions.add(new Drive(robot,carousel,1,-2));
                actions.add(new GoToInState(robot));
                actions.add(new TurnOnIntake(robot,false));
                actions.add(new Turn(robot,startPosition.getAngleRadians()));

                // push wheel against carousel
                actions.add(new setDuckWheel(robot, DuckWheel.DuckWheelState.OTHER_ON));
                actions.add(new TurnOnIntake(robot, true));
                actions.add(new Drive(robot, 8));
                actions.add(new Delay(2500));
                actions.add(new setDuckWheel(robot, DuckWheel.DuckWheelState.OFF));
                actions.add(new Turn(robot,exitCarouselAngle));
                actions.add(new Drive(robot, 3));
                actions.add(new TurnOffIntake(robot));

                // deposit the duck
                actions.add(new GoToHighDeposit(robot));
                actions.add(new Drive(robot,goalPosition,-1, DISTANCE_BACK_FROM_GOAL ));

                // actions.add(new AimAtPoint(robot,goalPosition,false, true));
                actions.add(new DepositFreight(robot));

                // go to park
                actions.add(new Drive(robot, 7));
                actions.add(new AimAtPoint(robot, park, false, true));
                actions.add(new GoToInState(robot));
                actions.add(new Drive(robot,park,-1));

                // make sure we are parked
                actions.add(new Turn(robot,startPosition.getAngleRadians()));
                actions.add(new Drive(robot,-3));
                break;
            case RIGHT:

                actions.add(new Drive(robot,-20));
                actions.add(new AimAtPoint(robot, goalPosition, false, true));
                actions.add(new Drive(robot,goalPosition,-1, DISTANCE_BACK_FROM_GOAL +1));
                actions.add(new DepositFreight(robot));

                // drive to carousel
                actions.add(new Drive(robot,carousel,1,-2));
                actions.add(new GoToInState(robot));
                actions.add(new TurnOnIntake(robot,false));
                actions.add(new Turn(robot,startPosition.getAngleRadians()));

                // push wheel against carousel
                actions.add(new setDuckWheel(robot, DuckWheel.DuckWheelState.OTHER_ON));
                actions.add(new TurnOnIntake(robot, true));
                actions.add(new Drive(robot, 7.5));
                actions.add(new Delay(2500));
                actions.add(new setDuckWheel(robot, DuckWheel.DuckWheelState.OFF));
                actions.add(new Turn(robot,exitCarouselAngle));
                actions.add(new Drive(robot, 3));
                actions.add(new TurnOffIntake(robot));

                // deposit the duck
                actions.add(new GoToHighDeposit(robot));
                actions.add(new Drive(robot,goalPosition,-1, DISTANCE_BACK_FROM_GOAL ));

                // actions.add(new AimAtPoint(robot,goalPosition,false, true));
                actions.add(new DepositFreight(robot));

                // go to park
                actions.add(new Drive(robot, 10));
                actions.add(new AimAtPoint(robot, park, false, true));
                actions.add(new GoToInState(robot));
                actions.add(new Drive(robot,park,-1));

                // make sure we are parked
                actions.add(new Turn(robot,startPosition.getAngleRadians()));
                actions.add(new Drive(robot,-4.5));
                break;
        }


    }
}
