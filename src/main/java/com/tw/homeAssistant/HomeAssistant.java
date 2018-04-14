package com.tw.homeAssistant;

import java.util.HashMap;
import java.util.Stack;

public class HomeAssistant {

  private final HashMap <String, Command> commands;
  private Stack<Command > previousInstructions;

  public HomeAssistant() {
    this.commands = new HashMap<> (  );
    this.previousInstructions=new Stack <> (  );
  }


  public void add(String instruction, Command command) {
    commands.put ( instruction,command );
  }

  public boolean listen(String instruction) {
    Command command = commands.get ( instruction );
    if (instruction=="undo"&& (!previousInstructions.empty ())) {
      return previousInstructions.pop ().undo ();
    }
    if (command!=null) {
      previousInstructions.add(command );
      return command.execute ();
    }
    return false;
  }
}
