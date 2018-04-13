package com.tw.homeAssistant;

public class HomeTheatre {
  private int volume;

  public HomeTheatre() {
    this.volume = 0;
  }

  public boolean play() {
    return true;
  }

  public boolean on() {
    return true;
  }

  public boolean off() {
    return true;
  }

  public boolean volumeUp() {
    if (!(volume>4)){
       ++volume;
       return true;
    }
    return false;
  }

  public boolean volumeDown() {
    if (!(volume==0)){
      --volume;
      return true;
    }
    return false;
  }

  public boolean volumeFull() {
    volume = 5;
    return true;
  }
}
