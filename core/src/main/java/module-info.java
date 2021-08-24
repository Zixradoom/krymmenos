
module com.zixradoom.krymmenos.core {
  requires com.zixradoom.datatable.core;
  
  provides java.nio.file.spi.FileTypeDetector with com.zixradoom.krymmenos.core.implv0.KrymmenosFileType;
}