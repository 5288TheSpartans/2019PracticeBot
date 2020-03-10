/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

//import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.CounterBase.EncodingType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
// import frc.robot.commands.ArcadeDriveCommand;

/**
 * Add your docs here.
 */
public class DrivetrainSubsystem extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private double leftPower, rightPower;
  private final double numberOfTicks = 2048;
  private final double wheelCircumference = (6 * Math.PI);
  public boolean useThirdDriveMotor = true;

  // private Encoder leftEncoder = new Encoder(RobotMap.leftDriveEncoder1, RobotMap.leftDriveEncoder2, false,
  //     EncodingType.k4X);
  // private Encoder rightEncoder = new Encoder(RobotMap.rightDriveEncoder1, RobotMap.rightDriveEncoder2, true,
  //     EncodingType.k4X);

  private ADXRS450_Gyro gyro = new ADXRS450_Gyro();

  private VictorSP leftDriveMotor1 = new VictorSP(RobotMap.leftDriveMotor1);
  private VictorSP leftDriveMotor2 = new VictorSP(RobotMap.leftDriveMotor2);
  private VictorSP leftDriveMotor3 = new VictorSP(RobotMap.leftDriveMotor3);

  private VictorSP rightDriveMotor1 = new VictorSP(RobotMap.rightDriveMotor1);
  private VictorSP rightDriveMotor2 = new VictorSP(RobotMap.rightDriveMotor2);
  private VictorSP rightDriveMotor3 = new VictorSP(RobotMap.rightDriveMotor3);

  public DrivetrainSubsystem() {
    // leftEncoder.setDistancePerPulse(wheelCircumference / numberOfTicks);
    // rightEncoder.setDistancePerPulse(wheelCircumference / numberOfTicks);
    // leftEncoder.setMaxPeriod(5);
    // rightEncoder.setMaxPeriod(5);
    // leftEncoder.setMinRate(0);
    // rightEncoder.setMinRate(0);
    // leftEncoder.setSamplesToAverage(1);
    // rightEncoder.setSamplesToAverage(1);

    gyro.calibrate();

  }

  @Override
  public void initDefaultCommand() {

    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
    // setDefaultCommand(new ArcadeDriveCommand());
  }

  // Set power to the LEFT side of the drivetrain.
  public void setLeftPower(double power) {
    leftPower = power;

  }

  // Set power to the RIGHT side of the drivetrain.
  public void setRightPower(double power) {
    rightPower = power;
  }

  public void updateOutputs() {
    if(useThirdDriveMotor) {
      leftDriveMotor1.set(-leftPower*RobotMap.driveSpeedMultiplier);
      leftDriveMotor2.set(-leftPower*RobotMap.driveSpeedMultiplier);
      leftDriveMotor3.set(-leftPower*RobotMap.driveSpeedMultiplier);
      rightDriveMotor1.set(rightPower*RobotMap.driveSpeedMultiplier);
      rightDriveMotor2.set(rightPower*RobotMap.driveSpeedMultiplier);
      rightDriveMotor3.set(rightPower*RobotMap.driveSpeedMultiplier);
    } 
    else {
      leftDriveMotor1.set(-leftPower*RobotMap.driveSpeedMultiplier);
      leftDriveMotor2.set(0);
      leftDriveMotor3.set(0);
      rightDriveMotor1.set(rightPower*RobotMap.driveSpeedMultiplier);
      rightDriveMotor2.set(0);
      rightDriveMotor3.set(0);

    }
  
  }

  public void putEncoderValues() {
    // SmartDashboard.putNumber("Left Encoder Raw", leftEncoder.getRaw());
    // SmartDashboard.putNumber("Right Encoder Raw", rightEncoder.getRaw());
 //   SmartDashboard.putNumber("Left Encoder Dist Per Pulse", leftEncoder.getDistancePerPulse());
 //   SmartDashboard.putNumber("Right Encoder Dist Per Pulse", rightEncoder.getDistancePerPulse());
  }

  // public double getLeftDistanceInches() {
  //   return leftEncoder.getDistance();
  // }

  // public double getRightDistanceInches() {
  //   return rightEncoder.getDistance();
  // }

  public double getGyroAngle() {
    return gyro.getAngle();
  }

  public void resetGyro() {
    gyro.reset();
  }

  // public void resetEncoders() {
  //   leftEncoder.reset();
  //   rightEncoder.reset();
  // }

  public double accelerateDriveSpeed() {
    if (Robot.m_oi.getPrimaryControllerLeftStickY() > 0)
      return Math.pow(Robot.m_oi.getPrimaryControllerLeftStickY(), 2);
    else
      return -Math.pow(Robot.m_oi.getPrimaryControllerLeftStickY(), 2);
  }

  public double slopeDriveSpeed(double slopeInput) {
    //double slopeInput = Robot.m_oi.getPrimaryControllerLeftStickY();
    if(!Robot.isWithinDeadzone(slopeInput))
        if (slopeInput > 0) {
          return (slopeInput-0.15)*1.15;
        }
        else {
          return (slopeInput+0.15)*1.15;
        }

    else {
      return 0.0;
    }
  }
}