// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import java.util.function.DoubleSupplier;

import com.revrobotics.spark.SparkMax;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.DriveConstants.*;

public class CANDriveSubsystem extends SubsystemBase {
  private final SparkMax leftLeader;
  private final SparkMax leftFollower;
  private final SparkMax rightLeader;
  private final SparkMax rightFollower;

  SparkBaseConfig globalConfig = new SparkMaxConfig();
  SparkBaseConfig rightLeaderConfig = new SparkMaxConfig();
  SparkBaseConfig leftFollowerConfig = new SparkMaxConfig();
  SparkBaseConfig rightFollowerConfig = new SparkMaxConfig();

  private final DifferentialDrive drive;

  public CANDriveSubsystem() {
    // Create motor controllers
    leftLeader = new SparkMax(LEFT_LEADER_ID, MotorType.kBrushed);
    leftFollower = new SparkMax(LEFT_FOLLOWER_ID, MotorType.kBrushed);
    rightLeader = new SparkMax(RIGHT_LEADER_ID, MotorType.kBrushed);
    rightFollower = new SparkMax(RIGHT_FOLLOWER_ID, MotorType.kBrushed);

    //figure out leader follower config
    globalConfig
      .smartCurrentLimit(50)
      .idleMode(IdleMode.kBrake);

    rightLeaderConfig
      .apply(globalConfig)
      .inverted(true);

    leftFollowerConfig
      .apply(globalConfig)
      .follow(leftLeader);

    rightFollowerConfig
      .apply(globalConfig)
      .follow(rightLeader);

    //apply configs to motors
    leftLeader.configure(globalConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    leftFollower.configure(leftFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightLeader.configure(rightLeaderConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    rightFollower.configure(rightFollowerConfig, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);

    // DifferentialDrive expects SpeedController / MotorController objects.
    drive = new DifferentialDrive(leftLeader, rightLeader);

    // Safety (optional but recommended)
    drive.setSafetyEnabled(true);
    drive.setDeadband(0.02);
  }

  @Override
  public void periodic() {
  }

  // Command factory to create command to drive the robot with joystick inputs.
  public Command driveArcade(DoubleSupplier xSpeed, DoubleSupplier zRotation) {
    return this.run(() -> drive.arcadeDrive(xSpeed.getAsDouble(), zRotation.getAsDouble()));
  }
}
