package Proj;

import java.io.FileNotFoundException;

public interface ISaveScript {
    public void save(String filename, Game game) throws FileNotFoundException;

    public Game load(String filename) throws FileNotFoundException;
}
