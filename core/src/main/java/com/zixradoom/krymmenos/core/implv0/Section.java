package com.zixradoom.krymmenos.core.implv0;

import java.nio.ByteBuffer;

public interface Section {
  ByteBuffer getEncoded ();
  int getEncodedSize ();
}
