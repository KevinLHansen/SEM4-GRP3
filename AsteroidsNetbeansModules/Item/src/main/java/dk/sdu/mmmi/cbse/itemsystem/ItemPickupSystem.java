//package dk.sdu.mmmi.cbse.itemsystem;
//
//// @author Kevin Hansen
//import dk.sdu.mmmi.cbse.common.data.Entity;
//import dk.sdu.mmmi.cbse.common.data.GameData;
//import dk.sdu.mmmi.cbse.common.data.World;
//import dk.sdu.mmmi.cbse.common.data.entityparts.PositionPart;
//import dk.sdu.mmmi.cbse.common.services.IEntityProcessingService;
//import org.openide.util.lookup.ServiceProvider;
//import org.openide.util.lookup.ServiceProviders;
//
//@ServiceProviders(value = {
//    @ServiceProvider(service = IEntityProcessingService.class),})
//
//public class ItemPickupSystem implements IEntityProcessingService {
//
//    @Override
//    public void process(GameData gameData, World world) {
//
//        // process all existing items in world
//        for (Entity item : world.getEntities(Item.class)) {
//            PositionPart pp = item.getPart(PositionPart.class);
//        }
//    }
//
//}
