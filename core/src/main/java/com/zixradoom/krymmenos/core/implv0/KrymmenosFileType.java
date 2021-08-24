package com.zixradoom.krymmenos.core.implv0;

import java.io.IOException;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.spi.FileTypeDetector;

import com.zixradoom.krymmenos.core.KrymmenosIO;

public class KrymmenosFileType extends FileTypeDetector {

  @Override
  public String probeContentType ( Path path ) throws IOException {
    
    try ( FileChannel fc = FileChannel.open ( path, StandardOpenOption.READ ) ) {
      ByteBuffer byteBuffer = ByteBuffer.allocate ( Header.OFFSET_END );
      Header header = new Header ( byteBuffer );
      if ( KrymmenosIO.MAGIC == header.getMagic () ) {
        
        if ( header.getFileVersionMajor () < 0 || header.getFileVersionMinor () < 0 ) {
          return null;
        }
        
        String root = "application/prs.com.zixradoom.krymmenos.v%d-%d";
        return String.format ( root, header.getFileVersionMajor (), header.getFileVersionMinor () );
      } else {
        return null;
      }
    } catch ( BufferUnderflowException bue ) {
      return null;
    }
  }

}
