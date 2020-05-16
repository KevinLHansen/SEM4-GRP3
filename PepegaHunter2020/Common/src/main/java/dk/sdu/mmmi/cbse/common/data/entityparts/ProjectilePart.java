package dk.sdu.mmmi.cbse.common.data.entityparts;

import dk.sdu.mmmi.cbse.common.data.Entity;
import dk.sdu.mmmi.cbse.common.data.GameData;

// @author Group 3

public class ProjectilePart implements EntityPart {

    String ID;

    @Override
    public void process(GameData gameData, Entity entity) {
    }

    public ProjectilePart(String id) {
        this.ID = id;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }
}
