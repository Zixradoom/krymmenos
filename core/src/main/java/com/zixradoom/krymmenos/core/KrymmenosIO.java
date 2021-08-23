package com.zixradoom.krymmenos.core;

import java.nio.file.Path;

public interface KrymmenosIO {
  
  public static final int MAGIC = 0x7070a07;
  
  Object read ( Path file );
  void write ( Path file, Object obj );
  int getFileVersionMajor ();
  int getFileVersionMinor ();
}
