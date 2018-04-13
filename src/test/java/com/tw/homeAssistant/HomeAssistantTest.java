package com.tw.homeAssistant;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HomeAssistantTest {
  private HomeAssistant assistant;
  private Light light = new Light();
  private HomeTheatre hometheatre = new HomeTheatre ();

  private class LightTurnOn implements Command{
    @Override
    public boolean execute() {
      return light.turnOn ();
    }

    @Override
    public boolean undo() {
      return light.turnOff ();
    }
  }

  private class LightTurnOff implements Command{
    @Override
    public boolean execute() {
      return light.turnOff();
    }

    @Override
    public boolean undo() {
      return light.turnOn ();
    }
  }

  private class Party implements Command{

    @Override
    public boolean execute() {
      return light.turnOn () && hometheatre.volumeFull();
    }

    @Override
    public boolean undo() {
      return light.turnOff () && hometheatre.volumeDown ();
    }
  }

  @Before
  public void setUp() {
    assistant = new HomeAssistant ();
  }

  @Test
  public void shouldTurnOnLight() {
    assistant.add ( "TurnOn",new LightTurnOn () );
    assertTrue ( assistant.listen ( "TurnOn" ) );
  }

  @Test
  public void shouldUndoTurnOnLight() {
    LightTurnOn lightTurnOn = mock(LightTurnOn.class);
    assistant.add ( "TurnOn", lightTurnOn );
    assistant.listen ( "TurnOn" );
    assistant.listen ( "undo" );
    verify ( lightTurnOn ).undo ();
  }

  @Test
  public void shouldTurnOffTheLight() {
    assistant.add ( "TurnOff" ,new LightTurnOff ());
    assertTrue ( assistant.listen ( "TurnOff" ) );
  }

  @Test
  public void shouldUndoTurnOffLight() {
    LightTurnOff lightTurnOff = mock(LightTurnOff.class);
    assistant.add ( "TurnOff", lightTurnOff );
    assistant.listen ( "TurnOff" );
    assistant.listen ( "undo" );
    verify ( lightTurnOff ).undo ();
  }

  @Test
  public void shouldStartParty() {
    assistant.add ( "Aaj bhai ka mood hai" ,new Party ());
    assertTrue ( assistant.listen ( "Aaj bhai ka mood hai" ) );
  }

  @Test
  public void shouldUndoStartParty() {
    Party startParty = mock(Party.class);
    assistant.add ( "Aaj bhai ka mood hai", startParty );
    assistant.listen ( "Aaj bhai ka mood hai" );
    assistant.listen ( "undo" );
    verify ( startParty ).undo ();
  }

  @Test
  public void shouldUndoRecentCommands() {
    Party startParty = mock(Party.class);
    LightTurnOff lightTurnOff = mock(LightTurnOff.class);
    assistant.add ( "Aaj bhai ka mood hai", startParty );
    assistant.add ( "TurnOff", lightTurnOff );
    assistant.listen ( "TurnOff" );
    assistant.listen ( "Aaj bhai ka mood hai" );
    assistant.listen ( "undo" );
    assistant.listen ( "undo" );
    verify ( startParty ).undo ();
    verify ( lightTurnOff ).undo ();
  }

  @Test
  public void shouldReturnFalseIfCommandNotFound() {
    assertFalse ( assistant.listen ( "hiiii" ) );
  }
}
