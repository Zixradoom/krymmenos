package com.zixradoom.krymmenos.core.implv0;

import java.nio.ByteBuffer;
import java.util.Objects;

import com.zixradoom.krymmenos.core.KrymmenosIO;

public final class Header {
  
  public static final int OFFSET_MAGIC = 0;
  public static final int OFFSET_FILE_VERSION_MAJOR = OFFSET_MAGIC + Integer.BYTES;
  public static final int OFFSET_FILE_VERSION_MINOR = OFFSET_FILE_VERSION_MAJOR + Short.BYTES;
  public static final int OFFSET_SECTION_HEADER_TABLE_ENTRY_COUNT = OFFSET_FILE_VERSION_MINOR + Short.BYTES;
  public static final int OFFSET_SECTION_HEADER_TABLE_ENTRY_SIZE = OFFSET_SECTION_HEADER_TABLE_ENTRY_COUNT + Short.BYTES;
  public static final int OFFSET_SECTION_HEADER_TABLE_OFFSET = OFFSET_SECTION_HEADER_TABLE_ENTRY_SIZE + Short.BYTES;
  public static final int OFFSET_SIGNATURE_ALGORITHM = OFFSET_SECTION_HEADER_TABLE_OFFSET + Long.BYTES;
  public static final int OFFSET_SECTION_HEADER_NAME_INDEX = OFFSET_SIGNATURE_ALGORITHM + Short.BYTES;
  public static final int OFFSET_SIGNATURE_OFFSET = OFFSET_SECTION_HEADER_NAME_INDEX + Short.BYTES;
  public static final int OFFSET_END = OFFSET_SIGNATURE_OFFSET + Long.BYTES;
  
  private ByteBuffer data;
  
  public Header () {
    this ( ByteBuffer.allocate ( OFFSET_END ) );
    data.putInt ( OFFSET_MAGIC, KrymmenosIO.MAGIC );
  }
  
  public Header ( ByteBuffer data ) {
    this.data = Objects.requireNonNull ( data, "data is null" );
    if ( data.capacity () < OFFSET_END ) {
      throw new IllegalArgumentException ( String.format ( "data capacity too small, %d < %d", data.capacity (), OFFSET_END ) );
    }
    
    updateBufferPositions ();
  }
  
  public int getMagic () {
    return data.get ( OFFSET_MAGIC );
  }
  
  public int getFileVersionMajor () {
    return data.getShort ( OFFSET_FILE_VERSION_MAJOR );
  }
  
  public void setFileVersionMajor ( short major ) {
    data.putShort ( OFFSET_FILE_VERSION_MINOR, major );
  }
  
  public int getFileVersionMinor () {
    return data.getShort ( OFFSET_FILE_VERSION_MINOR );
  }
  
  public void setFileVersionMinor ( short minor ) {
    data.putShort ( OFFSET_FILE_VERSION_MINOR, minor );
  }
  
  public int getSectionHeaderTableEntryCount () {
    return data.getShort ( OFFSET_SECTION_HEADER_TABLE_ENTRY_COUNT );
  }
  
  public void setSectionHeaderTableEntryCount ( short count ) {
    data.putShort ( OFFSET_SECTION_HEADER_TABLE_ENTRY_COUNT, count );
  }
  
  public int getSectionHeaderTableEntrySize () {
    return data.getShort ( OFFSET_SECTION_HEADER_TABLE_ENTRY_SIZE );
  }
  
  public void setSectionHeaderTableEntrySize ( short size ) {
    data.putShort ( OFFSET_SECTION_HEADER_TABLE_ENTRY_SIZE, size );
  }
  
  public int getSectionHeaderNameIndex () {
    return data.getShort ( OFFSET_SECTION_HEADER_NAME_INDEX );
  }
  
  public void setSectionHeaderNameIndex ( short index ) {
    data.putShort ( OFFSET_SECTION_HEADER_NAME_INDEX, index );
  }
  
  public long getSectionHeaderTableOffset () {
    return data.getLong ( OFFSET_SECTION_HEADER_TABLE_OFFSET );
  }
  
  public void setSectionHeaderTableOffset ( long offset ) {
    data.putLong ( OFFSET_SECTION_HEADER_TABLE_OFFSET, offset );
  }
  
  public int getSignatureAlgorthim () {
    return data.getInt ( OFFSET_SIGNATURE_ALGORITHM );
  }
  
  public void setSignatureAlgorthim ( int algo ) {
    data.putInt ( OFFSET_SIGNATURE_ALGORITHM, algo );
  }
  
  public long getSignatureOffset () {
    return data.getLong ( OFFSET_SIGNATURE_OFFSET );
  }
  
  public void setSignatureOffset ( long offset ) {
    data.putLong ( OFFSET_SIGNATURE_OFFSET, offset );
  }
  
  public ByteBuffer getEncoded () {
    return data.duplicate ().asReadOnlyBuffer ();
  }
  
  public int getEncodedSize () {
    return data.limit ();
  }
  
  private void updateBufferPositions () {
    data.limit ( OFFSET_END );
    data.position ( data.limit () );
  }
}
