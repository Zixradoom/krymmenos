package com.zixradoom.krymmenos.core.implv0;

import java.nio.ByteBuffer;
import java.util.Objects;

public final class Blob implements Section {

  public static final int DEFAULT_SIZE = 1024;
  
  private ByteBuffer data;
  //private Map < Long, RecordData > cache;
  
  public Blob () {
    data = ByteBuffer.allocate ( DEFAULT_SIZE );
  }
  
  public Blob ( ByteBuffer data ) {
    this.data = data;
  }
  
  public RecordData intur ( ByteBuffer dat ) {
    int location = data.position ();
    int size = Objects.requireNonNull ( dat, "dat is null" ).limit ();
    data.put ( dat );
    return new RecordData ( location, size );
  }
  
  public RecordData intur ( byte[] dat ) {
    return this.intur ( ByteBuffer.wrap ( Objects.requireNonNull ( dat, "dat is null" ) ) );
  }

  @Override
  public ByteBuffer getEncoded () {
    return data.duplicate ().asReadOnlyBuffer ();
  }

  @Override
  public int getEncodedSize () {
    return data.position ();
  }
  
  @Override
  public String toString () {
    return "Blob[data="+data+"]";
  }
  
  public class RecordData {
    public final int location;
    public final int size;
    
    public RecordData ( int location, int size ) {
      this.location = location;
      this.size = size;
    }
    
    @Override
    public String toString () {
      return "RecordData[location="+location+", size="+size+"]";
    }
  }
}
