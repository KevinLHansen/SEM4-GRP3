package dk.sdu.mmmi.cbse.core.managers;

import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.files.FileHandle;

public class AssetsJarFileResolver implements FileHandleResolver {

    @Override
    public FileHandle resolve(String fileName) {

        return new JarFileHandleStream(fileName);
    }

}
