package com.zixradoom.krymmenos.core.implv0;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class KrymmenosIOImplv0Test {

  private static final Logger LOGGER = LoggerFactory.getLogger ( KrymmenosIOImplv0Test.class );
  
  @Test
  public void write1Test () throws IOException {
    Path tempDir = Path.of ( System.getProperty ( "java.io.tmpdir" ) );
    Path temp = tempDir.resolve ( String.format ( "com.zixradoom.krymmenos.core.implv0.KrymmenosIOImplv0Test.write1Test-%s.kry", UUID.randomUUID () ) );
    KrymmenosIOImplv0 kryio = new KrymmenosIOImplv0 ();
    kryio.write ( temp, null );
    LOGGER.info ( "{}", temp );
  }
}
