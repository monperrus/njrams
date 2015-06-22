package njrams;
import java.io.File;
import java.util.Iterator;

import njrams.interfaces.INgram;

import org.apache.commons.io.FileUtils;

/** recursively iterates over a directory and enumerates all tokens of size n */
class TokenRecIterator implements Iterator<INgram> {
  Iterator<File> files;
  SingleFileTokenIterator it;
  int n;
  Class<? extends INgram> ngramImpl;

  public TokenRecIterator(File f, int n, Class<? extends INgram> ngramImpl) throws Exception {
    files = FileUtils.listFiles(f, new String[] { "java" }, true).iterator();
    this.ngramImpl = ngramImpl;
    this.n = n;
    nextFile();
  }

  void nextFile() {
 try {
    it = createTokenIterator(files.next(), n);
  } catch (Exception e) {
    throw new RuntimeException(e);
  }
  }

  public SingleFileTokenIterator createTokenIterator(File f, int n) throws Exception {
    return new SingleFileTokenIterator(f, n, ngramImpl);
  }

  @Override
  public boolean hasNext() {
    while (!it.hasNext() && files.hasNext()) { nextFile();}    
    return it.hasNext();
  }

  @Override
  public INgram next() {
    while (!it.hasNext() && files.hasNext()) { nextFile();}

    return it.next();
    
  }

  @Override
  public void remove() {
    throw new UnsupportedOperationException();
  }
}
