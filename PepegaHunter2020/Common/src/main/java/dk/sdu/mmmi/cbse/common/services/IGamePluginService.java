package dk.sdu.mmmi.cbse.common.services;

import dk.sdu.mmmi.cbse.common.data.GameData;
import dk.sdu.mmmi.cbse.common.data.World;

// @author Group 3

public interface IGamePluginService {

    void start(GameData gameData, World world);

    void stop(GameData gameData, World world);
}
