package com.zixradoom.krymmenos.core.implv0;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.function.IntSupplier;
import java.util.function.Supplier;

import com.zixradoom.datatable.core.AbstractDataTableEntry;
import com.zixradoom.datatable.core.DataTable;

public class StringTable extends SectionDataTable< StringTable.Entry > {
  
  public StringTable () {
    super ( new Entry.Factory () );
  }
  
  public StringTable ( ByteBuffer table, int entrySize ) {
    super ( new Entry.Factory (), table, entrySize );
  }
  
  public StringTable ( ByteBuffer table, int entrySize, int entryCount ) {
    super ( new Entry.Factory (), table, entrySize, entryCount );
  }
  
  public StringTable.Entry newEntry ( int offset, int size ) {
    StringTable.Entry entry = newEntry ();
    entry.setOffset ( offset );
    entry.setSize ( size );
    return entry;
  }
  
  public enum Charset {
    INVALID ( 0, null ),
    UTF_8 ( 1, StandardCharsets.UTF_8 );
    
    private int code;
    private java.nio.charset.Charset charset;
    
    private Charset ( int code, java.nio.charset.Charset charset ) {
      this.code = code;
      this.charset = charset;
    }
    
    public int getCode() {
      return code;
    }
    
    public java.nio.charset.Charset getCharset () {
      return charset;
    }
  }

  public static final class Entry extends AbstractDataTableEntry {
    
    public static final int OFFSET_OFFSET = 0;
    public static final int OFFSET_SIZE = OFFSET_OFFSET + Long.BYTES;
    public static final int OFFSET_END = OFFSET_SIZE + Long.BYTES;
    
    protected Entry ( int index, Supplier< ByteBuffer > bufferGetter, IntSupplier entrySizeGetter ) {
      super( index, bufferGetter, entrySizeGetter );
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
    
    public static final class Factory implements DataTable.Factory< StringTable.Entry > {

      @Override
      public Entry createEntry(int index, Supplier< ByteBuffer > bufferGetter, IntSupplier entrySizeGetter) {
        return new Entry (index, bufferGetter, entrySizeGetter );
      }

      @Override
      public int getEntrySize() {
        return Entry.OFFSET_END;
      }
      
    }
  }
}
