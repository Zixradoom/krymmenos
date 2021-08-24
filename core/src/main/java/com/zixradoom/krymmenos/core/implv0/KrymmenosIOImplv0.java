package com.zixradoom.krymmenos.core.implv0;

import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.List;

import com.zixradoom.datatable.core.DataTable;
import com.zixradoom.datatable.core.SimpleDataTable;
import com.zixradoom.krymmenos.core.KrymmenosIO;

public class KrymmenosIOImplv0 implements KrymmenosIO {
  
  @Override
  public Object read ( Path file ) {
    throw new UnsupportedOperationException ( "not implemented" );
  }

  @Override
  public void write ( Path file, Object obj ) throws IOException {
    FileChannel fc = FileChannel.open ( file, StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE );
    
    SectionData < Blob, ? > shstrtabData = new SectionData <> ();
    shstrtabData.section = new Blob ();
    
    //SectionData < ? extends SectionDataTable< StringTable.Entry >, Blob > shstrtab = new SectionData <> ();
    SectionData < StringTable, Blob > shstrtab = new SectionData <> ();
    
    DataTable < SectionHeaderEntry > sht = new SimpleDataTable <> ( new SectionHeaderEntry.Factory () );
    sht.newEntry ();
    SectionHeaderEntry she = sht.newEntry ();
    
    List < SectionData < ? extends Section, ? extends Section > > sections = List.of ( shstrtabData, shstrtab );
    
    Header header = new Header ();
    header.setFileVersionMajor ( (short) getFileVersionMajor () );
    header.setFileVersionMinor ( (short) getFileVersionMinor () );
    header.setSectionHeaderTableEntryCount ( (short) 0 );
    header.setSectionheaderTableEntrySize ( (short) SectionHeaderEntry.OFFSET_END );
    header.setSectionHeaderTableOffset ( Header.OFFSET_END );
    header.setSignatureOffset ( 0 );
    header.setSignatureOffset ( Header.OFFSET_END );
    
    fc.write ( header.getEncoded () );
    fc.close ();
  }

  @Override
  public int getFileVersionMajor () {
    return 0;
  }
  
  @Override
  public int getFileVersionMinor () {
    return 0;
  }
  
  private class SectionData < E extends Section, T extends Section > {
    private E section;
    private SectionData < T, ? > linked;
    private SectionHeaderEntry header;
  }
}
