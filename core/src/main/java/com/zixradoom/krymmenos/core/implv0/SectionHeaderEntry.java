package com.zixradoom.krymmenos.core.implv0;

import java.nio.ByteBuffer;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import com.zixradoom.datatable.core.AbstractDataTableEntry;
import com.zixradoom.datatable.core.DataTable;

public final class SectionHeaderEntry extends AbstractDataTableEntry {

  public static final int OFFSET_NAME_INDEX = 0;
  public static final int OFFSET_TYPE = OFFSET_NAME_INDEX + Integer.BYTES;
  public static final int OFFSET_LINK = OFFSET_TYPE + Short.BYTES;
  public static final int OFFSET_INFO = OFFSET_LINK + Short.BYTES;
  public static final int OFFSET_OFFSET = OFFSET_INFO + Integer.BYTES;
  public static final int OFFSET_SIZE = OFFSET_OFFSET + Long.BYTES;
  public static final int OFFSET_END = OFFSET_SIZE + Long.BYTES;
  
  protected SectionHeaderEntry ( int index, Supplier< ByteBuffer > bufferGetter, IntSupplier entrySizeGetter ) {
    super ( index, bufferGetter, entrySizeGetter );
  }
  
  public int getNameIndex () {
    return buffer ().getInt ( start () + OFFSET_TYPE );
  }
  
  public void setNameIndex ( int index ) {
    buffer ().putInt ( start () + OFFSET_TYPE, index );
  }
  
  public int getTypeRaw () {
    return buffer ().getShort ( start () + OFFSET_TYPE );
  }
  
  public void setTypeRaw ( short rawType ) {
    buffer ().putShort( start () + OFFSET_TYPE, rawType );
  }
  
  public int getLink () {
    return buffer ().getShort ( start () + OFFSET_LINK );
  }
  
  public void setLink ( short link ) {
    buffer ().putShort ( start () + OFFSET_LINK, link );
  }
  
  public int getInfo () {
    return buffer ().getInt ( start () + OFFSET_INFO );
  }
  
  public void setInfo ( int info ) {
    buffer ().putInt ( start () + OFFSET_INFO, info );
  }
  
  public long getOffset () {
    return buffer ().getLong ( start () + OFFSET_OFFSET );
  }
  
  public void setOffset ( long offset ) {
    buffer ().putLong ( start () + OFFSET_OFFSET, offset );
  }
  
  public long getSize () {
    return buffer ().getLong ( start () + OFFSET_SIZE );
  }
  
  public void setSize ( long size ) {
    buffer ().putLong ( start () + OFFSET_SIZE, size );
  }
  
  public static final class Factory implements DataTable.Factory < SectionHeaderEntry > {

    @Override
    public SectionHeaderEntry createEntry ( int arg0, Supplier< ByteBuffer > arg1, IntSupplier arg2 ) {
      return new SectionHeaderEntry ( arg0, arg1, arg2 );
    }

    @Override
    public int getEntrySize () {
      return OFFSET_END;
    }
    
  }
  
  public enum Type {
    INVALID(0, "INVD"),
    BLOB(0x100, "BLOB");
    
    private final int code;
    private final String mnemonic;
    
    private Type ( int code, String mnemonic ) {
      this.code = code;
      this.mnemonic = mnemonic;
    }
    
    public int getCode () {
      return code;
    }
    
    public String getMnemonic () {
      return mnemonic;
    }
  }
}
