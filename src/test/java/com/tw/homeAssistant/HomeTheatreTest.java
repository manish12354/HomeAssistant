package com.tw.homeAssistant;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

public class HomeTheatreTest {
  HomeTheatre homeTheatre = new HomeTheatre ();

  private class HomeTheatrePlay implements Command{
    @Override
    public boolean execute() {
      return homeTheatre.play ();
    }

    @Override
    public boolean undo() {
      return true;
    }
  }
  private class HomeTheatreOn implements Command {
    @Override
    public boolean execute() {
      return homeTheatre.on ();
    }

    @Override
    public boolean undo() {
      return homeTheatre.off ();
    }
  }
  private class HomeTheatreOff implements Command {
    @Override
    public boolean execute() {
      return homeTheatre.off();
    }

    @Override
    public boolean undo() {
      return homeTheatre.on ();
    }
  }
  private class HomeTheatreVolumeDown implements Command{

    @Override
    public boolean execute() {
      return homeTheatre.volumeDown ();
    }

    @Override
    public boolean undo() {
      return homeTheatre.volumeUp ();
    }
  }
  private class HomeTheatreVolumeUp implements Command{

    @Override
    public boolean execute() {
      return homeTheatre.volumeUp ();
    }

    @Override
    public boolean undo() {
      return homeTheatre.volumeDown ();
    }
  }
  private class HomeTheatreVolumeFull implements Command{

    @Override
    public boolean execute() {
      return homeTheatre.volumeFull ();
    }

    @Override
    public boolean undo() {
      return true;
    }
  }
  HomeAssistant assistant;

  @Before
  public void setUp() {
    assistant = new HomeAssistant ();
    assistant.add ( "play" , new HomeTheatrePlay () );
    assistant.add ( "HomeTheatreKoOnKaro" , new HomeTheatreOn () );
    assistant.add ( "HomeTheatreKoOffKaro" , new HomeTheatreOff () );
    assistant.add ( "HomeTheatreKaVolumeKamKaro" , new HomeTheatreVolumeDown () );
    assistant.add ( "HomeTheatreKaVolumeBadhao" , new HomeTheatreVolumeUp () );
    assistant.add ( "VolumeFull" , new HomeTheatreVolumeFull () );
  }

  @Test
  public void shouldPlayMusic() {
    assertTrue(assistant.listen ("play"));
  }

  @Test
  public void shouldUndoPlayMusic() {
    HomeTheatrePlay homeTheatrePlay = mock ( HomeTheatrePlay.class );
    assistant.add ( "play",homeTheatrePlay );
    assistant.listen ( "play" );
    assistant.listen ( "undo" );
    verify ( homeTheatrePlay ).undo ();
  }

  @Test
  public void shouldOnHomeTheatre() {
    assertTrue (  assistant.listen ( "HomeTheatreKoOnKaro" ));
  }

  @Test
  public void shouldUndoOnHomeTheatre() {
    HomeTheatreOn onHomeTheatre = mock ( HomeTheatreOn.class );
    assistant.add ( "HomeTheatreKoOnKaro",onHomeTheatre );
    assistant.listen ( "HomeTheatreKoOnKaro" );
    assistant.listen ( "undo" );
    verify ( onHomeTheatre ).undo ();
  }

  @Test
  public void shouldOffHomeTheatre() {
    assertTrue ( assistant.listen ("HomeTheatreKoOffKaro" ));
  }

  @Test
  public void shouldUndoOffHomeTheatre() {
    HomeTheatreOff offHomeTheatre = mock ( HomeTheatreOff.class );
    assistant.add ( "HomeTheatreKoOffKaro",offHomeTheatre );
    assistant.listen ( "HomeTheatreKoOffKaro" );
    assistant.listen ( "undo" );
    verify ( offHomeTheatre ).undo ();
  }

  @Test
  public void shouldIncreaseVolumeOfHomeTheatre() {
    assertTrue ( assistant.listen ( "HomeTheatreKaVolumeBadhao" ) );
  }

  @Test
  public void shouldUndoIncreaseVolumeOfHomeTheatre() {
    HomeTheatreVolumeUp increaseVolume = mock ( HomeTheatreVolumeUp.class );
    assistant.add ( "HomeTheatreKaVolumeBadhao",increaseVolume );
    assistant.listen ( "HomeTheatreKaVolumeBadhao" );
    assistant.listen ( "undo" );
    verify ( increaseVolume ).undo ();
  }

  @Test
  public void shouldNotDecreaseVolumeOfHomeTheatre() {
    assistant.listen ( "HomeTheatreKaVolumeKamKaro" );
    assertFalse (assistant.listen ( "HomeTheatreKaVolumeKamKaro" )  );
  }

  @Test
  public void shouldFullVolume() {
    assertTrue (assistant.listen ( "VolumeFull" ));
  }

  @Test
  public void shouldUndoFullVolume() {
    HomeTheatreVolumeFull fullVolume = mock ( HomeTheatreVolumeFull.class );
    assistant.add ( "VolumeFull",fullVolume );
    assistant.listen ( "VolumeFull" );
    assistant.listen ( "undo" );
    verify ( fullVolume ).undo ();
  }
}
