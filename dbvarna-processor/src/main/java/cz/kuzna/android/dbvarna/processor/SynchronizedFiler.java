package cz.kuzna.android.dbvarna.processor;

import java.io.IOException;

import javax.annotation.processing.Filer;
import javax.lang.model.element.Element;
import javax.tools.FileObject;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;

/**
 * @author Radek Kuznik
 */
public class SynchronizedFiler implements Filer {

    private final Filer parent;

    public SynchronizedFiler(Filer parent) {
        this.parent = parent;
    }

    @Override
    public JavaFileObject createSourceFile(CharSequence name, Element... originatingElements) throws IOException {
        synchronized (parent) {
            return parent.createSourceFile(name, originatingElements);
        }
    }

    @Override
    public JavaFileObject createClassFile(CharSequence name, Element... originatingElements) throws IOException {
        synchronized (parent) {
            return parent.createClassFile(name, originatingElements);
        }
    }

    @Override
    public FileObject createResource(JavaFileManager.Location location, CharSequence pkg, CharSequence relativeName, Element... originatingElements) throws IOException {
        synchronized (parent) {
            return parent.createResource(location, pkg, relativeName, originatingElements);
        }
    }

    @Override
    public FileObject getResource(JavaFileManager.Location location, CharSequence pkg, CharSequence relativeName) throws IOException {
        synchronized (parent) {
            return parent.getResource(location, pkg, relativeName);
        }
    }
}