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
    
    DataTable < SectionHeaderEntry > sht = new SimpleDataTable <> ( new SectionHeaderEntry.Factory () );
    sht.newEntry ();
    
    SectionData < Blob, ? > shstrtabData = new SectionData <> ();
    shstrtabData.section = new Blob ();
    shstrtabData.header = sht.newEntry ();
    shstrtabData.header.setType ( SectionHeaderEntry.Type.BLOB );
    
    //SectionData < ? extends SectionDataTable< StringTable.Entry >, Blob > shstrtab = new SectionData <> ();
    SectionData < StringTable, Blob > shstrtab = new SectionData <> ();
    shstrtab.section = new StringTable ();
    shstrtab.section.newEntry ();
    shstrtab.linked = shstrtabData;
    shstrtab.header = sht.newEntry ();
    shstrtab.header.setLink ( (short) shstrtab.linked.header.getIndex () );
    shstrtab.header.setType ( SectionHeaderEntry.Type.STRING_TABLE );
    shstrtab.header.setInfo ( StringTable.Charset.UTF_8.getCode () );
    shstrtab.header.setDataEntrySize ( StringTable.Entry.OFFSET_END );
    
    shstrtabData.nameLocation = shstrtabData.section.intur ( ".shstrtab.data".getBytes ( StringTable.Charset.UTF_8.getCharset () ) );
    shstrtabData.nameRecord = shstrtab.section.newEntry ( shstrtabData.nameLocation.location, shstrtabData.nameLocation.size );
    shstrtabData.header.setNameIndex ( shstrtabData.nameRecord.getIndex () );
    shstrtab.nameLocation = shstrtabData.section.intur ( ".shstrtab".getBytes ( StringTable.Charset.UTF_8.getCharset () ) );
    shstrtab.nameRecord = shstrtab.section.newEntry ( shstrtab.nameLocation.location, shstrtab.nameLocation.size );
    shstrtab.header.setNameIndex ( shstrtab.nameRecord.getIndex () );
    
    Header header = new Header ();
    header.setFileVersionMajor ( (short) getFileVersionMajor () );
    header.setFileVersionMinor ( (short) getFileVersionMinor () );
    header.setSectionHeaderTableEntryCount ( (short) sht.getEntryCount () );
    header.setSectionHeaderTableEntrySize ( (short) SectionHeaderEntry.OFFSET_END );
    header.setSectionHeaderNameIndex ( (short) shstrtab.header.getIndex () );

    List < SectionData < ? extends Section, ? extends Section > > sections = List.of ( shstrtabData, shstrtab );
    long offset = header.getEncodedSize ();
    for ( var sd : sections ) {
      sd.header.setOffset ( offset );
      sd.header.setSize ( sd.section.getEncodedSize () );
      offset += sd.section.getEncodedSize ();
    }
    header.setSectionHeaderTableOffset ( offset );
    header.setSignatureOffset ( offset + sht.getEncodedSize () );
    
    fc.write ( header.getEncoded ().flip () );
    
    for ( var sd : sections ) {
      fc.write ( sd.section.getEncoded ().flip () );
    }
    
    fc.write ( sht.getEncoded ().flip () );
    
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
    private Blob.RecordData nameLocation;
    private StringTable.Entry nameRecord;
    private SectionData < T, ? > linked;
    private SectionHeaderEntry header;
  }
}
