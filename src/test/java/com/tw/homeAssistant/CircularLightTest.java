package com.tw.homeAssistant;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class CircularLightTest {
  private HomeAssistant assistant;

  private class CircularLightSwitchOn implements Command {
    @Override
    public boolean execute() {
      return true;
    }

    @Override
    public boolean undo() {
      return true;
    }
  }
  private class CircularLightSwitchOff implements Command {
    @Override
    public boolean execute() {
      return true;
    }

    @Override
    public boolean undo() {
      return false;
    }
  }

  @Before
  public void setUp() {
    assistant = new HomeAssistant ();
    assistant.add ( "SwitchOn",new CircularLightSwitchOn() );
    assistant.add ( "SwitchOff",new CircularLightSwitchOff() );
  }

  @Test
  public void shouldSwitchOnCircularLight() {
    assertTrue(assistant.listen("SwitchOff"));
  }

  @Test
  public void shouldSwitchOffCircularLight() {
    assertTrue(assistant.listen ( "SwitchOn" ));
  }

  @Test
  public void shouldTurnOffLightWhenUndoIsCalled() {
    CircularLightSwitchOn circularLightSwitchOn=mock(CircularLightSwitchOn.class);
    assistant.add ( "SwitchOn",circularLightSwitchOn );
    assistant.listen ( "SwitchOn" );
    assistant.listen ( "undo" );
    verify ( circularLightSwitchOn ).undo ();
  }

  @Test
  public void shouldReturnFalseIfNoInstructionIsGiven() {
    CircularLightSwitchOn circularLightSwitchOn=mock(CircularLightSwitchOn.class);
    assistant.add ( "SwitchOn",circularLightSwitchOn );
    assertFalse ( assistant.listen ( "undo" ) );
  }
}
