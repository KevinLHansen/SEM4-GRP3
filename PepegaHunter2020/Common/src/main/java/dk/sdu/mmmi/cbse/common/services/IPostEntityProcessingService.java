package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

// @author Group 3

public interface IPostEntityProcessingService {

    void process(GameData gameData, World world);
}
