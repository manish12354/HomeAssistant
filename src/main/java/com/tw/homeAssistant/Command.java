package com.tw.homeAssistant;

public interface Command {
  public boolean execute();
  public boolean undo();
}
