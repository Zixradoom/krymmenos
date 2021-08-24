package com.zixradoom.krymmenos.core.implv0;

import java.nio.ByteBuffer;

import com.zixradoom.datatable.core.DataTable;

public class SectionDataTable< E extends DataTable.Entry > extends DataTable< E > implements Section {

  public SectionDataTable ( Factory< E > factory ) {
    super( factory );
  }

  public SectionDataTable( Factory< E > factory, ByteBuffer table, int entrySize, int entryCount ) {
    super( factory, table, entrySize, entryCount );
  }

  public SectionDataTable( Factory< E > factory, ByteBuffer table, int entrySize ) {
    super( factory, table, entrySize );
  }
}
