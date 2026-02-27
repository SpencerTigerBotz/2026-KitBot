package frc.robot.subsystems;

import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.SparkMaxConfig;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.spark.SparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FuelConstants.*;
import static frc.robot.Constants.LifterConstants.*;

public class LifterSubsystem extends SubsystemBase {
  private final SparkMax feederRoller;
  private final SparkMax intakeLauncherRoller;

  private final SparkMax rightLifter;
  private final SparkMax leftLifter;
  
  /** Creates a new CANBallSubsystem. */
  // public CANFuelSubsystem(LightsSubsystem lights) {
  public LifterSubsystem() {
    // this.lights = lights;

    leftLifter = new SparkMax(LEFT_LIFTER_MOTOR_ID, null);
    rightLifter = new SparkMax(RIGHT_LIFTER_MOTOR_ID, null);

    intakeLauncherRoller = new SparkMax(INTAKE_LAUNCHER_MOTOR_ID, MotorType.kBrushed);
    feederRoller = new SparkMax(FEEDER_MOTOR_ID, MotorType.kBrushed);
  }

  public void runLifter(double speed){
    leftLifter.set(speed);
    rightLifter.set(speed);
  }

  public Command runLifterCommand(double speed){
    return run(()->runLifter(0.4));
  }

  @Override
  public void periodic() {
    
  }
}
