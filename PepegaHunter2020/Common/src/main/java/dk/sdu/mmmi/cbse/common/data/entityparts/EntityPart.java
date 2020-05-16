package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

// @author Group 3

public interface EntityPart {
    
    void process(GameData gameData, Entity entity);
}
